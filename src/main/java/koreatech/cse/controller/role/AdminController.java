package koreatech.cse.controller.role;

import koreatech.cse.domain.*;
import koreatech.cse.domain.constant.CompCategory;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.constant.StudentStatus;
import koreatech.cse.domain.constant.SubjCategory;
import koreatech.cse.domain.dto.CurriculumRowDTO;
import koreatech.cse.domain.dto.StudentUploadResult;
import koreatech.cse.domain.role.professor.*;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import koreatech.cse.repository.*;
import koreatech.cse.repository.provider.CqiMapper;
import koreatech.cse.service.AuthorityService;
import koreatech.cse.service.CurriculumService;
import koreatech.cse.service.FileService;
import koreatech.cse.service.ProfService;
import koreatech.cse.service.UserService;
import koreatech.cse.util.DateHelper;
import koreatech.cse.util.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Controller
@SessionAttributes({ "studentUser", "profUser", "adminUser", "course", "division", "semester", "menuAccess",
        "assessmentFactor", "profCourse", "graduationCriteria", "cqi" })
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Inject
    private UserMapper userMapper;
    @Inject
    private UploadedFileMapper uploadedFileMapper;
    @Inject
    private DivisionMapper divisionMapper;
    @Inject
    private UserService userService;
    @Inject
    private CourseMapper courseMapper;
    @Inject
    private ProfessorCourseMapper professorCourseMapper;
    @Inject
    private ContactMapper contactMapper;
    @Inject
    private AuthorityService authorityService;
    @Inject
    private CounselingMapper counselingMapper;
    @Inject
    private SemesterMapper semesterMapper;
    @Inject
    private LectureMethodMapper lectureMethodMapper;
    @Inject
    private LectureFundamentalsMapper lectureFundamentalsMapper;
    @Inject
    private EvaluationMethodMapper evaluationMethodMapper;
    @Inject
    private EducationalMediumMapper educationalMediumMapper;
    @Inject
    private EquipmentMapper equipmentMapper;
    @Inject
    private ClassroomMapper classroomMapper;
    @Inject
    private MenuAccessMapper menuAccessMapper;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private FeedbackMapper feedbackMapper;
    @Inject
    private AltCourseMapper altCourseMapper;
    @Inject
    private FileService fileService;
    @Inject
    private AssessmentFactorMapper assessmentFactorMapper;
    @Inject
    private StudentCourseMapper studentCourseMapper;
    @Inject
    private GraduationResearchPlanMapper graduationResearchPlanMapper;
    @Inject
    private AssessmentMapper assessmentMapper;
    @Inject
    private GraduationCriteriaMapper graduationCriteriaMapper;
    @Inject
    private ProfLectureMethodMapper profLectureMethodMapper;
    @Inject
    private LectureContentsMapper lectureContentsMapper;
    @Inject
    private CqiMapper cqiMapper;
    @Inject
    private ClassTimeMapper classTimeMapper;
    @Inject
    private ProfService profService;
    @Inject
    private CertificateMapper certificateMapper;
    @Inject
    private LangCertMapper langCertMapper;
    @Inject
    private CurriculumService curriculumService;

    @ModelAttribute("currentPageRole")
    public String getCurrentPageRole() {
        return "admin";
    }

    private String sanitizeForLog(String value) {
        if (value == null)
            return "null";
        String sanitized = value.replaceAll("[\r\n\t]", "_");
        return sanitized.substring(0, Math.min(sanitized.length(), 200));
    }

    @RequestMapping(value = "/studentManagement/studentRegistration", method = RequestMethod.GET)
    public String studentRegistration(Model model, @RequestParam(required = false) String result) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        User studentUser = new User();
        Contact contact = new Contact();
        studentUser.setContact(contact);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("result", result);
        model.addAttribute("statusList", StudentStatus.values());

        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
        return "role/admin/studentRegistration/studentRegistration";
    }

    @RequestMapping(value = "/studentManagement/studentRegistration", method = RequestMethod.POST)
    public String basic(@ModelAttribute("studentUser") User studentUser, SessionStatus sessionStatus) {

        // Server-side validation for required fields
        String studentNumber = studentUser.getNumber();
        Contact contact = studentUser.getContact();
        String firstName = (contact != null) ? contact.getFirstName() : null;
        String lastName = (contact != null) ? contact.getLastName() : null;

        // Validate required fields
        if (StringUtils.isBlank(studentNumber) || StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
            logger.warn("Manual registration failed: missing required fields (number={}, firstName={}, lastName={})",
                    sanitizeForLog(studentNumber), sanitizeForLog(firstName), sanitizeForLog(lastName));
            sessionStatus.setComplete();
            return "redirect:/admin/studentManagement/studentRegistration?result=validation_error";
        }

        studentUser.setEnabled(false);
        studentUser.setConfirm(false);
        boolean result = userService.register(studentUser);
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/studentRegistration?result=" + (result ? "success" : "fail");
    }

    @RequestMapping(value = "/studentManagement/studentRegistration/importStudents", method = RequestMethod.POST)
    public String importStudents(HttpServletRequest request,
            @RequestParam("file") MultipartFile file,
            @RequestParam("divisionId") int divisionId,
            @RequestParam("schoolYear") int schoolYear,
            @SuppressWarnings("unused") Model model) {

        logger.info("Starting student import for divisionId={}, schoolYear={}", divisionId, schoolYear);

        // Validate file is present
        if (file == null || file.isEmpty()) {
            logger.warn("Import failed: empty file");
            return "redirect:/admin/studentManagement/studentRegistration?importResult=error&errorMessage=empty_file";
        }

        String fileName = file.getOriginalFilename();
        String ext = fileService.getExtension(fileName);

        // Validate file format
        if (!ext.equalsIgnoreCase("xlsx")) {
            logger.warn("Import failed: invalid format '{}'", sanitizeForLog(ext));
            return "redirect:/admin/studentManagement/studentRegistration?importResult=error&errorMessage=invalid_format";
        }

        try {
            // Save file to temp directory via FileService (sanitized + canonical path
            // validated)
            File convFile = fileService.saveMultipartToTempFile(file, request);
            logger.debug("File saved to temp path: {}", sanitizeForLog(convFile.getAbsolutePath()));

            // Process Excel and get detailed result
            StudentUploadResult result = readStudentImportExcel(convFile, divisionId, schoolYear);
            logger.info("Import result: {}", result.getSummary());

            // Determine redirect based on result
            if (result.hasInsertions()) {
                logger.info("Import successful: {} students inserted", result.getInsertedCount());
                String redirectUrl = "redirect:/admin/studentManagement/studentRegistration?importResult=success"
                        + "&inserted=" + result.getInsertedCount()
                        + "&duplicates=" + result.getSkippedDuplicateCount()
                        + "&invalid=" + result.getInvalidRowsCount();
                // Add invalid details if there are any
                if (result.hasInvalidRows()) {
                    redirectUrl += "&" + result.getInvalidDetails();
                }
                return redirectUrl;
            } else if (result.getSkippedDuplicateCount() > 0) {
                logger.info("Import completed: all {} students were duplicates", result.getSkippedDuplicateCount());
                return "redirect:/admin/studentManagement/studentRegistration?importResult=warning"
                        + "&duplicates=" + result.getSkippedDuplicateCount();
            } else if (result.hasInvalidRows()) {
                // All rows were invalid - provide detailed error
                logger.warn("Import failed: all {} rows were invalid", result.getInvalidRowsCount());
                return "redirect:/admin/studentManagement/studentRegistration?importResult=error"
                        + "&errorMessage=all_invalid"
                        + "&invalid=" + result.getInvalidRowsCount()
                        + "&" + result.getInvalidDetails();
            } else {
                logger.warn("Import completed but no valid data found in file");
                return "redirect:/admin/studentManagement/studentRegistration?importResult=error&errorMessage=no_data";
            }

        } catch (Exception e) {
            logger.error("Error processing student import", e);
            return "redirect:/admin/studentManagement/studentRegistration?importResult=error&errorMessage=processing_error";
        }
    }

    /**
     * Reads student data from Excel/CSV file and creates pending student accounts
     */
    private StudentUploadResult readStudentImportExcel(File file, int divisionId, int schoolYear) {
        StudentUploadResult result = new StudentUploadResult();
        FileInputStream fileInputStream = null;
        XSSFWorkbook workbook = null;

        try {
            // Defense-in-depth: re-validate canonical path at point of use
            String canonicalPath = file.getCanonicalPath();
            String allowedBase = new File(System.getProperty("java.io.tmpdir")
                    + File.separator + "ams_temp").getCanonicalPath();
            if (!canonicalPath.startsWith(allowedBase + File.separator)
                    && !canonicalPath.equals(allowedBase)) {
                throw new SecurityException("Path validation failed: file outside allowed directory");
            }
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            final int HEADER_ROW = 1; // Skip header row

            logger.debug("Processing {} rows (excluding header) for divisionId={}, schoolYear={}",
                    rows - HEADER_ROW, divisionId, schoolYear);

            for (int rowIndex = HEADER_ROW; rowIndex < rows; rowIndex++) {
                result.incrementTotalRows();

                try {
                    XSSFRow row = sheet.getRow(rowIndex);
                    if (row == null) {
                        result.incrementInvalidRows();
                        continue;
                    }

                    // Column A: Student Number (Required)
                    String studentNumber = getCellStringValue(row.getCell(0));
                    // Column B: First Name (Required)
                    String firstName = getCellStringValue(row.getCell(1));
                    // Column C: Last Name (Required)
                    String lastName = getCellStringValue(row.getCell(2));

                    // Check ALL required fields and count ALL missing ones
                    boolean hasValidationErrors = false;
                    if (StringUtils.isBlank(studentNumber)) {
                        result.incrementMissingStudentNumber();
                        hasValidationErrors = true;
                        logger.debug("Row {}: missing student number", rowIndex);
                    }
                    if (StringUtils.isBlank(firstName)) {
                        result.incrementMissingFirstName();
                        hasValidationErrors = true;
                        logger.debug("Row {}: missing first name", rowIndex);
                    }
                    if (StringUtils.isBlank(lastName)) {
                        result.incrementMissingLastName();
                        hasValidationErrors = true;
                        logger.debug("Row {}: missing last name", rowIndex);
                    }

                    // Skip row if any required field is missing
                    if (hasValidationErrors) {
                        continue;
                    }

                    // Trim validated values
                    studentNumber = studentNumber.trim();
                    firstName = firstName.trim();
                    lastName = lastName.trim();

                    // Column D: Email/Username (Optional)
                    String username = getCellStringValue(row.getCell(3));
                    if (StringUtils.isNotBlank(username)) {
                        username = username.trim();
                    }

                    // Column E: Admission Year (Optional)
                    String admissionYearStr = getCellStringValue(row.getCell(4));
                    Integer admissionYear = null;
                    if (StringUtils.isNotBlank(admissionYearStr)) {
                        try {
                            admissionYear = Integer.parseInt(admissionYearStr.trim());
                        } catch (NumberFormatException e) {
                            // Ignore invalid admission year
                        }
                    }

                    // Column F: Admission Date (Optional)
                    String admissionDate = getCellStringValue(row.getCell(5));
                    if (StringUtils.isNotBlank(admissionDate)) {
                        admissionDate = admissionDate.trim();
                    }

                    // Check for duplicate student number
                    User existingUser = userMapper.findByNumber(studentNumber);
                    if (existingUser != null) {
                        result.incrementSkippedDuplicate();
                        logger.debug("Student number already exists: {}", studentNumber);
                        continue;
                    }

                    // Create new student user (pending account)
                    User newStudent = new User();
                    newStudent.setNumber(studentNumber);
                    newStudent.setUsername(username);
                    newStudent.setDivisionId(divisionId);
                    newStudent.setSchoolYear(schoolYear);
                    newStudent.setEnabled(false); // Pending - not activated
                    newStudent.setConfirm(false);
                    newStudent.setStatus(StudentStatus.registered.name());

                    // Create contact with name info
                    Contact contact = new Contact();
                    contact.setFirstName(firstName);
                    contact.setLastName(lastName);
                    if (admissionYear != null) {
                        contact.setAdmissionYear(admissionYear);
                    }
                    if (StringUtils.isNotBlank(admissionDate)) {
                        contact.setAdmissionDate(admissionDate);
                    }
                    newStudent.setContact(contact);

                    // Register the student
                    boolean registered = userService.register(newStudent);
                    if (registered) {
                        result.incrementInserted();
                        logger.debug("Inserted student: {} ({} {})", studentNumber, firstName, lastName);
                    } else {
                        result.incrementInvalidRows();
                        logger.warn("Failed to register student: {}", studentNumber);
                    }

                } catch (Exception e) {
                    result.incrementInvalidRows();
                    logger.warn("Error processing row {}: {}", rowIndex, e.getMessage());
                }
            }

        } catch (Exception e) {
            logger.error("Error reading Excel file for student import", e);
        } finally {
            // Clean up resources
            try {
                if (workbook != null)
                    workbook.close();
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                logger.warn("Error closing file resources: {}", e.getMessage());
            }
        }

        return result;
    }

    /**
     * Helper method to get string value from Excel cell (handles both string and
     * numeric types)
     */
    private String getCellStringValue(Cell cell) {
        if (cell == null)
            return null;

        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cellType == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf((long) cell.getNumericCellValue());
        }
        return null;
    }

    @RequestMapping(value = "/studentManagement/studentInformation/studentTable", method = RequestMethod.GET)
    public String studentTable(Model model, @RequestParam(required = false) String number,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam(defaultValue = "0", required = false) int schoolYear,
            @RequestParam(required = false) String accountStatus) {
        User firstUser = null;
        List<User> userList;
        if (StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0
                && schoolYear == 0 && StringUtils.isBlank(accountStatus)) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            searchable.setSchoolYear(schoolYear);
            searchable.setAccountStatus(accountStatus);
            userList = userMapper.findStudentBy(searchable);

            for (User user : userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/admin/studentInformation/studentTable";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/studentDetail", method = RequestMethod.GET)
    public String studentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);

        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);
        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);

        List<LangCert> langCerts = langCertMapper.findByUserId(studentId);
        model.addAttribute("langCerts", langCerts);
        return "role/admin/studentInformation/studentDetail";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/studentDetailForPrint", method = RequestMethod.GET)
    public String studentDetailForPrint(Model model, @RequestParam int studentId) {
        System.out.println("studentId = " + studentId);
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);

        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);
        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);

        List<LangCert> langCerts = langCertMapper.findByUserId(studentId);
        model.addAttribute("langCerts", langCerts);
        return "role/admin/studentInformation/studentDetailForPrint";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/studentDetail", method = RequestMethod.POST)
    public String studentDetail(@ModelAttribute("studentUser") User studentUser, SessionStatus sessionStatus,
            HttpServletRequest request) throws IOException {
        System.out.println("studentUser = " + studentUser);
        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile f = ((MultipartHttpServletRequest) request).getFile("file");
            if (f == null || f.getSize() == 0) {

            } else {
                String fileName = f.getOriginalFilename();
                String ext = fileService.getExtension(fileName);
                if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")
                        || ext.equalsIgnoreCase("gif")) {
                    uploadedFileMapper.deleteProfileByUser(studentUser);
                    DateTime dt = new DateTime();
                    fileService.processUploadedFile(f, studentUser, Designation.profile, 0, 0, dt.getYear());
                }

            }

        }

        userMapper.update(studentUser);
        userMapper.updateFromSignup(studentUser);
        contactMapper.update(studentUser.getContact());
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/studentInformation?result=success";
    }

    @RequestMapping(value = "/studentManagement/studentInformation", method = RequestMethod.GET)
    public String studentInformation(Model model, @RequestParam(required = false) String result) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("result", result);

        return "role/admin/studentInformation/studentInformation";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/addLangCert", method = RequestMethod.GET)
    public String addLangCert(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);

        LangCert langCert = new LangCert();
        langCert.setUserId(studentId);
        model.addAttribute("langCert", langCert);

        return "role/admin/studentInformation/addLangCert";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/addLangCert", method = RequestMethod.POST)
    public String addLangCert(@ModelAttribute("langCert") LangCert langCert, @RequestParam int studentId,
            SessionStatus sessionStatus) {
        langCert.setUserId(studentId);
        langCert.setApprove(true);
        langCertMapper.insert(langCert);
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/studentInformation";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/deleteLangCert", method = RequestMethod.POST)
    public String deleteLangCert(@RequestParam int langCertId) {
        LangCert langCert = langCertMapper.findOne(langCertId);
        langCertMapper.delete(langCert);
        return "redirect:/admin/studentManagement/studentInformation";
    }

    /**
     * Delete a student from the system.
     * Checks for related records in student_course before deletion.
     * Returns JSON response for AJAX call.
     */
    @RequestMapping(value = "/studentManagement/studentInformation/deleteStudent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteStudent(@RequestParam int studentId) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Find the student
            User student = userMapper.findOne(studentId);
            if (student == null) {
                logger.warn("Delete failed: student not found with id={}", studentId);
                response.put("success", false);
                response.put("message", "Student not found");
                return response;
            }

            // Check for related records in student_course
            List<koreatech.cse.domain.role.student.StudentCourse> courseRecords = studentCourseMapper
                    .findByUserId(studentId);
            if (courseRecords != null && !courseRecords.isEmpty()) {
                logger.info("Delete prevented: student {} has {} course registrations", student.getNumber(),
                        courseRecords.size());
                response.put("success", false);
                response.put("message", "hasRelatedRecords");
                response.put("recordCount", courseRecords.size());
                return response;
            }

            // Safe to delete - no related records
            logger.info("Deleting student: {} ({})", student.getNumber(),
                    student.getContact() != null ? student.getContact().getFullName() : "N/A");
            userMapper.delete(student);

            response.put("success", true);
            response.put("message", "deleted");
            return response;

        } catch (Exception e) {
            logger.error("Error deleting student with id=" + studentId, e);
            response.put("success", false);
            response.put("message", "error");
            return response;
        }
    }

    @RequestMapping(value = "/studentManagement/studentProfile", method = RequestMethod.GET)
    public String studentProfile(Model model) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
        return "role/admin/studentProfile/studentProfile";
    }

    @RequestMapping(value = "/studentManagement/studentProfile/studentTable", method = RequestMethod.GET)
    public String studentProfileStudentTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int schoolYear,
            @RequestParam(defaultValue = "0", required = false) int advisor,
            @RequestParam(defaultValue = "0", required = false) int division) {
        User firstUser = null;
        List<User> userList;

        if (schoolYear == 0 && advisor == 0 && division == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setAdvisor(advisor);
            searchable.setSchoolYear(schoolYear);
            searchable.setDivision(division);
            userList = userMapper.findStudentBy(searchable);

            for (User user : userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/admin/studentProfile/studentTable";
    }

    @RequestMapping(value = "/studentManagement/studentProfile/studentDetail", method = RequestMethod.GET)
    public String studentProfileStudentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);
        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);

        List<LangCert> langCerts = langCertMapper.findByUserId(studentId);
        model.addAttribute("langCerts", langCerts);

        return "role/admin/studentProfile/studentDetail";
    }

    @RequestMapping(value = "/studentManagement/studentProfile/studentDetailForPrint", method = RequestMethod.GET)
    public String studentProfileStudentDetailForPrint(Model model,
            @RequestParam boolean checkAll,
            @RequestParam(defaultValue = "0", required = false) int schoolYear,
            @RequestParam(defaultValue = "0", required = false) int advisor,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam Map<String, String> params) {
        List<Integer> integerIds = new ArrayList<>();
        List<User> studentList;
        if (checkAll) {
            Searchable searchable = new Searchable();
            searchable.setAdvisor(advisor);
            searchable.setSchoolYear(schoolYear);
            searchable.setDivision(division);
            studentList = userMapper.findStudentBy(searchable);

        } else {
            params.entrySet().stream().filter(entry -> entry.getKey().equals("tableCheckbox")).forEach(entry -> {
                String value = entry.getValue();
                String[] split = value.split(",");
                for (String userIdString : split) {
                    if (StringUtils.isNotBlank(userIdString)) {
                        try {
                            integerIds.add(Integer.parseInt(userIdString));
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid student ID in checkbox: {}", sanitizeForLog(userIdString));
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                studentList = new ArrayList<>();
            else
                studentList = userMapper.findByUserIds(integerIds);
        }

        for (User u : studentList) {
            User advisorUser = userMapper.findOne(u.getAdvisorId());
            u.setAdvisor(advisorUser);

            int admissionYear = u.getContact().getAdmissionYear();
            int divisionId = u.getDivisionId();

            GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear,
                    divisionId);
            u.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);

            List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(u.getId());
            u.setStudentCourses(studentCourses);
        }

        model.addAttribute("studentList", studentList);
        return "role/admin/studentProfile/studentDetailForPrint";
    }

    @RequestMapping(value = "/studentManagement/schoolYear", method = RequestMethod.GET)
    public String schoolYear(Model model, @RequestParam(required = false) String result) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
        model.addAttribute("result", result);
        return "role/admin/schoolYear/schoolYear";
    }

    @RequestMapping(value = "/studentManagement/schoolYear/studentTable", method = RequestMethod.GET)
    public String schoolYearStudentTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int schoolYear,
            @RequestParam(defaultValue = "0", required = false) int advisor,
            @RequestParam(defaultValue = "0", required = false) int division) {
        User firstUser = null;
        List<User> userList;

        if (schoolYear == 0 && advisor == 0 && division == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setAdvisor(advisor);
            searchable.setSchoolYear(schoolYear);
            searchable.setDivision(division);
            userList = userMapper.findStudentBy(searchable);

            for (User user : userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/admin/schoolYear/studentTable";
    }

    @RequestMapping(value = "/studentManagement/schoolYear/studentDetail", method = RequestMethod.GET)
    public String schoolYearStudentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);

        User advisor = userMapper.findOne(studentUser.getAdvisorId());
        model.addAttribute("advisor", advisor);
        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
        return "role/admin/schoolYear/studentDetail";
    }

    @RequestMapping(value = "/studentManagement/schoolYear/studentDetail", method = RequestMethod.POST)
    public String schoolYearStudentDetail(@ModelAttribute("studentUser") User studentUser,
            SessionStatus sessionStatus) {
        System.out.println("studentUser = " + studentUser);

        userMapper.update(studentUser);
        userMapper.updateFromSignup(studentUser);
        contactMapper.update(studentUser.getContact());
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/schoolYear?result=success";
    }

    @RequestMapping(value = "/studentManagement/schoolYear/increaseYear", method = RequestMethod.POST)
    @ResponseBody
    public Boolean increaseYear(@RequestParam boolean checkAll,
            @RequestParam(defaultValue = "0", required = false) int schoolYear,
            @RequestParam(defaultValue = "0", required = false) int advisor,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam Map<String, String> params) {
        System.out.println("checkAll = " + checkAll);
        List<Integer> integerIds = new ArrayList<>();
        List<User> studentList;
        if (checkAll) {
            Searchable searchable = new Searchable();
            searchable.setAdvisor(advisor);
            searchable.setSchoolYear(schoolYear);
            searchable.setDivision(division);
            studentList = userMapper.findStudentBy(searchable);

        } else {
            params.entrySet().stream().filter(entry -> entry.getKey().equals("tableCheckbox")).forEach(entry -> {
                String value = entry.getValue();
                String[] split = value.split(",");
                for (String userIdString : split) {
                    if (StringUtils.isNotBlank(userIdString)) {
                        try {
                            integerIds.add(Integer.parseInt(userIdString));
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid student ID in checkbox: {}", sanitizeForLog(userIdString));
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                studentList = new ArrayList<>();
            else
                studentList = userMapper.findByUserIds(integerIds);
        }

        for (User u : studentList) {
            u.setSchoolYear(u.getSchoolYear() + 1);
            userMapper.update(u);
        }

        return true;
    }

    @RequestMapping(value = "/studentManagement/schoolYear/studentDetailForPrint", method = RequestMethod.GET)
    public String schoolYearStudentDetailForPrint(Model model,
            @RequestParam boolean checkAll,
            @RequestParam(defaultValue = "0", required = false) int schoolYear,
            @RequestParam(defaultValue = "0", required = false) int advisor,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam Map<String, String> params) {
        List<Integer> integerIds = new ArrayList<>();
        List<User> studentList;
        if (checkAll) {
            Searchable searchable = new Searchable();
            searchable.setAdvisor(advisor);
            searchable.setSchoolYear(schoolYear);
            searchable.setDivision(division);
            studentList = userMapper.findStudentBy(searchable);

        } else {
            params.entrySet().stream().filter(entry -> entry.getKey().equals("tableCheckbox")).forEach(entry -> {
                String value = entry.getValue();
                String[] split = value.split(",");
                for (String userIdString : split) {
                    if (StringUtils.isNotBlank(userIdString)) {
                        try {
                            integerIds.add(Integer.parseInt(userIdString));
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid student ID in checkbox: {}", sanitizeForLog(userIdString));
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                studentList = new ArrayList<>();
            else
                studentList = userMapper.findByUserIds(integerIds);
        }

        for (User u : studentList) {
            User advisorUser = userMapper.findOne(u.getAdvisorId());
            u.setAdvisor(advisorUser);

            int admissionYear = u.getContact().getAdmissionYear();
            int divisionId = u.getDivisionId();

            GraduationCriteria graduationCriteria = graduationCriteriaMapper.findOneByYearDivision(admissionYear,
                    divisionId);
            u.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);

            List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdValid(u.getId());
            u.setStudentCourses(studentCourses);
        }

        model.addAttribute("studentList", studentList);
        return "role/admin/schoolYear/studentDetailForPrint";
    }

    @RequestMapping(value = "/studentManagement/studentCounseling", method = RequestMethod.GET)
    public String studentCounseling(Model model) {

        model.addAttribute("yearList", getYearList());
        return "role/admin/studentCounseling/studentCounseling";
    }

    @RequestMapping(value = "/studentManagement/studentCounseling/counselingTable", method = RequestMethod.GET)
    public String counselingStudentTable(Model model, @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(required = false) String name) {
        Counseling firstCounseling = null;
        List<Counseling> counselingList;
        if (year == 0 && StringUtils.isBlank(name)) {
            counselingList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setName(name);
            counselingList = counselingMapper.findByCounseling(searchable);

            for (Counseling counseling : counselingList) {
                System.out.println("counseling = " + counseling);
                firstCounseling = counseling;
                break;
            }
        }

        model.addAttribute("counselingList", counselingList);
        model.addAttribute("firstCounseling", firstCounseling);
        return "role/admin/studentCounseling/counselingTable";
    }

    @RequestMapping(value = "/studentManagement/studentCounseling/counselingDetail", method = RequestMethod.GET)
    public String counselingStudentDetail(Model model, @RequestParam int counselingId) {
        Counseling counseling = counselingMapper.findOne(counselingId);
        model.addAttribute("counseling", counseling);
        return "role/admin/studentCounseling/counselingDetail";
    }

    @RequestMapping(value = "/studentManagement/studentCounseling/counselingDetailForPrint", method = RequestMethod.GET)
    public String counselingDetailForPrint(Model model,
            @RequestParam boolean checkAll,
            @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(required = false) String name, @RequestParam Map<String, String> params) {
        System.out.println("checkAll = " + checkAll);
        List<Counseling> counselingList;

        if (checkAll) {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setName(name);
            counselingList = counselingMapper.findByCounseling(searchable);
        } else {
            List<Integer> integerIds = new ArrayList<>();
            params.entrySet().stream().filter(entry -> entry.getKey().equals("tableCheckbox")).forEach(entry -> {
                String value = entry.getValue();
                String[] split = value.split(",");
                for (String integerIdString : split) {
                    if (StringUtils.isNotBlank(integerIdString)) {
                        try {
                            integerIds.add(Integer.parseInt(integerIdString));
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid counseling ID in checkbox: {}", sanitizeForLog(integerIdString));
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                counselingList = new ArrayList<>();
            else
                counselingList = counselingMapper.findByIds(integerIds);
        }
        model.addAttribute("counselingList", counselingList);
        return "role/admin/studentCounseling/counselingDetailForPrint";
    }

    @RequestMapping(value = "/studentManagement/inquiryGrade", method = RequestMethod.GET)
    public String inquiryGrade(Model model) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);

        return "role/admin/inquiryGrade/inquiryGrade";
    }

    @RequestMapping(value = "/studentManagement/inquiryGrade/studentTable", method = RequestMethod.GET)
    public String inquiryGradeStudentTable(Model model, @RequestParam(required = false) String number,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0", required = false) int division) {
        User firstUser = null;
        List<User> userList;
        if (StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            userList = userMapper.findStudentBy(searchable);

            for (User user : userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/admin/inquiryGrade/studentTable";
    }

    @RequestMapping(value = "/studentManagement/inquiryGrade/studentDetail", method = RequestMethod.GET)
    public String inquiryGradeStudentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        Semester firstSemester = null;
        LinkedHashSet<Integer> semesterSet = studentCourseMapper.findSemesterIdByUserIdValid(studentUser.getId());
        for (Integer semesterId : semesterSet) {
            firstSemester = semesterMapper.findOne(semesterId);
            break;
        }
        model.addAttribute("firstSemester", firstSemester);
        Map<Semester, List<StudentCourse>> map = new HashMap<>();
        for (Integer semesterId : semesterSet) {
            Semester semester = semesterMapper.findOne(semesterId);
            Searchable searchable = new Searchable();
            searchable.setYear(semester.getYear());
            searchable.setSemester(semester.getSemester());
            searchable.setUserId(studentUser.getId());
            List<StudentCourse> courses = studentCourseMapper.findByUserIdYearSemester(searchable);
            map.put(semester, courses);
        }

        model.addAttribute("courseMap", map);
        return "role/admin/inquiryGrade/studentDetail";
    }

    @RequestMapping(value = "/studentManagement/inquiryGrade/gradeDetail", method = RequestMethod.GET)
    public String inquiryGradeDetail(Model model, @RequestParam int studentId, @RequestParam int semesterId) {
        User studentUser = userMapper.findOne(studentId);

        List<StudentCourse> studentCourses = studentCourseMapper.findByUserIdSemesterIdValid(studentUser.getId(),
                semesterId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("studentCourses", studentCourses);
        return "role/admin/inquiryGrade/gradeDetail";
    }

    @RequestMapping(value = "/studentManagement/inquiryGrade/gradeDetailForPrint", method = RequestMethod.GET)
    public String inquiryGradeDetailPrint(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);

        Certificate certificate = certificateMapper.findByUserId(studentId);
        if (certificate == null) {
            certificate = new Certificate();
            certificate.setUserId(studentId);
            // Display-only — no insert in GET
        }
        model.addAttribute("certificate", certificate);
        model.addAttribute("today", DateHelper.format(new Date()));
        Map<Semester, List<StudentCourse>> map = profService.getStudentSemesterCourseMap(studentUser.getId());
        model.addAttribute("finalScore", profService.getStudentTotalScore(studentUser.getId()));
        model.addAttribute("courseMap", map);
        return "role/common/grade/gradeCertForPrint";
    }

    @RequestMapping(value = "/profManagement/profRegistration", method = RequestMethod.GET)
    public String profRegistration(Model model, @RequestParam(required = false) String result) {
        List<Division> divisions = divisionMapper.findAll();
        model.addAttribute("divisions", divisions);
        User profUser = new User();
        Contact contact = new Contact();
        profUser.setContact(contact);
        model.addAttribute("profUser", profUser);
        model.addAttribute("result", result);
        return "role/admin/profRegistration/profRegistration";
    }

    @RequestMapping(value = "/profManagement/profRegistration", method = RequestMethod.POST)
    public String profRegistration(@ModelAttribute("profUser") User profUser, SessionStatus sessionStatus) {
        profUser.setEnabled(false);
        profUser.setConfirm(false);
        boolean result = userService.register(profUser);
        sessionStatus.setComplete();
        return "redirect:/admin/profManagement/profRegistration?result=" + (result ? "success" : "fail");
    }

    @RequestMapping(value = "/profManagement/profInformation", method = RequestMethod.GET)
    public String profInformation(Model model, @RequestParam(required = false) String result) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("result", result);
        return "role/admin/profInformation/profInformation";
    }

    @RequestMapping(value = "/profManagement/profInformation/profTable", method = RequestMethod.GET)
    public String profTable(Model model, @RequestParam(required = false) String number,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0", required = false) int division) {
        User firstUser = null;
        List<User> userList;
        if (StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            userList = userMapper.findProfessorBy(searchable);

            for (User user : userList) {
                firstUser = user;
                break;
            }
        }

        model.addAttribute("userList", userList);
        model.addAttribute("firstUser", firstUser);
        return "role/admin/profInformation/profTable";
    }

    @RequestMapping(value = "/profManagement/profInformation/profDetail", method = RequestMethod.GET)
    public String profDetail(Model model, @RequestParam int profId) {
        User profUser = userMapper.findOne(profId);
        model.addAttribute("profUser", profUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);

        return "role/admin/profInformation/profDetail";
    }

    @RequestMapping(value = "/profManagement/profInformation/profDetail", method = RequestMethod.POST)
    public String profDetail(@ModelAttribute("profUser") User profUser, SessionStatus sessionStatus) {

        userMapper.update(profUser);
        userMapper.updateFromSignup(profUser);
        contactMapper.update(profUser.getContact());
        sessionStatus.setComplete();

        return "redirect:/admin/profManagement/profInformation?result=success";
    }

    @RequestMapping(value = "/profManagement/graduationResearch", method = RequestMethod.GET)
    public String graduationResearch(Model model) {

        model.addAttribute("yearList", getYearList());
        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);

        return "role/admin/graduationResearch/graduationResearch";
    }

    @RequestMapping(value = "/profManagement/graduationResearch/researchTable", method = RequestMethod.GET)
    public String graduationResearchPlanTable(Model model,
            @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam(defaultValue = "0", required = false) int advisor,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) String name) {
        GraduationResearchPlan firstOne = null;
        List<GraduationResearchPlan> plans;

        if (year == 0 && division == 0 && advisor == 0 && StringUtils.isBlank(number) && StringUtils.isBlank(name)) {
            plans = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();

            searchable.setYear(year);
            searchable.setDivision(division);
            searchable.setAdvisor(advisor);
            searchable.setNumber(number);
            searchable.setName(name);
            plans = graduationResearchPlanMapper.findBySearchable(searchable);

            for (GraduationResearchPlan graduationResearchPlan : plans) {
                firstOne = graduationResearchPlan;
                break;
            }
        }

        model.addAttribute("plans", plans);
        model.addAttribute("firstOne", firstOne);
        return "role/admin/graduationResearch/researchTable";
    }

    @RequestMapping(value = "/profManagement/graduationResearch/planDetail", method = RequestMethod.GET)
    public String graduationResearchPlanDetail(Model model, @RequestParam int planId) {
        GraduationResearchPlan graduationResearchPlan = graduationResearchPlanMapper.findOne(planId);
        model.addAttribute("stored", graduationResearchPlan);
        model.addAttribute("studentUser", graduationResearchPlan.getUser());
        model.addAttribute("completeSemester",
                userService.getCompleteSemesterCount(graduationResearchPlan.getUser().getId()));
        return "role/admin/graduationResearch/planDetail";
    }

    @RequestMapping(value = "/profManagement/graduationResearch/planDetailForPrint", method = RequestMethod.GET)
    public String graduationResearchPlanDetailForPrint(Model model,
            @RequestParam boolean checkAll,
            @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam(defaultValue = "0", required = false) int advisor,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) String name, @RequestParam Map<String, String> params) {
        System.out.println("checkAll = " + checkAll);
        List<Integer> integerIds = new ArrayList<>();
        List<GraduationResearchPlan> planList = null;
        if (checkAll) {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setDivision(division);
            searchable.setAdvisor(advisor);
            searchable.setNumber(number);
            searchable.setName(name);
            planList = graduationResearchPlanMapper.findBySearchable(searchable);
        } else {
            params.entrySet().stream().filter(entry -> entry.getKey().equals("tableCheckbox")).forEach(entry -> {
                String value = entry.getValue();
                String[] split = value.split(",");
                for (String userIdString : split) {
                    if (StringUtils.isNotBlank(userIdString)) {
                        try {
                            integerIds.add(Integer.parseInt(userIdString));
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid plan ID in checkbox: {}", sanitizeForLog(userIdString));
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                planList = new ArrayList<>();
            else
                planList = graduationResearchPlanMapper.findByIds(integerIds);
        }

        for (GraduationResearchPlan graduationResearchPlan : planList) {
            graduationResearchPlan
                    .setCompleteSemester(userService.getCompleteSemesterCount(graduationResearchPlan.getUserId()));
        }
        model.addAttribute("planList", planList);

        return "role/admin/graduationResearch/planDetailForPrint";
    }

    @RequestMapping(value = "/courseManagement/curriculum", method = RequestMethod.GET)
    public String curriculum(Model model, @RequestParam(defaultValue = "0", required = false) int year) {

        List<Integer> yearList = getYearList();
        if (year == 0) {
            Integer currentYear = yearList.get(0);
            year = currentYear;
        }

        model.addAttribute("year", year);
        model.addAttribute("yearList", yearList);

        List<Division> divisions = divisionMapper.findAll();
        List<koreatech.cse.domain.dto.CurriculumRowDTO> curriculumRows = curriculumService
                .buildCurriculumRows(divisions, year);

        model.addAttribute("curriculumRows", curriculumRows);
        return "role/admin/curriculum/curriculum";
    }

    // =========================================================================
    // Curriculum Mutations — one endpoint per intent
    // =========================================================================

    @RequestMapping(value = "/courseManagement/curriculum/upload", method = RequestMethod.POST)
    public String handleUploadCurriculum(@ModelAttribute UploadedFile uploadedFile,
            @RequestParam int year,
            @RequestParam int divisionId) {
        logger.info("handleUploadCurriculum: divisionId={}, year={}", divisionId, year);
        try {
            User user = User.current();
            MultipartFile file = uploadedFile.getFile();
            curriculumService.uploadCurriculum(file, user, divisionId, year);
            logger.info("handleUploadCurriculum: SUCCESS - divisionId={}, year={}", divisionId, year);
        } catch (IllegalArgumentException e) {
            logger.warn("handleUploadCurriculum: Validation failed - divisionId={}, year={}, error={}",
                    divisionId, year, e.getMessage());
            return "redirect:/admin/courseManagement/curriculum?year=" + year
                    + "&currentPageRole=admin&error=validation";
        } catch (IllegalStateException e) {
            logger.warn("handleUploadCurriculum: Conflict - divisionId={}, year={}, error={}",
                    divisionId, year, e.getMessage());
            return "redirect:/admin/courseManagement/curriculum?year=" + year + "&currentPageRole=admin&error=conflict";
        } catch (Exception e) {
            logger.error("handleUploadCurriculum: Unexpected error - divisionId={}, year={}",
                    divisionId, year, e);
            return "redirect:/admin/courseManagement/curriculum?year=" + year
                    + "&currentPageRole=admin&error=upload_failed";
        }
        return "redirect:/admin/courseManagement/curriculum?year=" + year + "&currentPageRole=admin&success=uploaded";
    }

    @RequestMapping(value = "/courseManagement/curriculum/uploadCurriculum", method = RequestMethod.GET)
    public String showUploadOrUpdateCurriculumPage(
            @RequestParam int year,
            @RequestParam int divisionId,
            Model model) {

        model.addAttribute("divisionId", divisionId);
        model.addAttribute("year", year);

        CurriculumRowDTO row = curriculumService.buildSingleCurriculumRow(divisionId, year);

        boolean curriculumExists = (row != null && row.hasCurriculum());

        model.addAttribute("curriculumExists", curriculumExists);

        if (row != null) {
            model.addAttribute("divisionName", row.getDivisionName());
        }

        if (curriculumExists && row.hasFile()) {
            model.addAttribute("currentFileName", row.getUploadedFileName());
            model.addAttribute("uploaderName", row.getUploaderName());
        }

        return "role/admin/curriculum/uploadCurriculum";
    }

    @RequestMapping(value = "/courseManagement/curriculum/update", method = RequestMethod.POST)
    public String handleUpdateCurriculum(@ModelAttribute UploadedFile uploadedFile,
            @RequestParam int year,
            @RequestParam int divisionId) {
        logger.info("handleUpdateCurriculum: divisionId={}, year={}", divisionId, year);
        try {
            User user = User.current();
            MultipartFile file = uploadedFile.getFile();
            curriculumService.updateCurriculum(file, user, divisionId, year);
            logger.info("handleUpdateCurriculum: SUCCESS - divisionId={}, year={}", divisionId, year);
        } catch (IllegalArgumentException e) {
            logger.warn("handleUpdateCurriculum: Validation failed - divisionId={}, year={}, error={}",
                    divisionId, year, e.getMessage());
            return "redirect:/admin/courseManagement/curriculum?year=" + year
                    + "&currentPageRole=admin&error=validation";
        } catch (IllegalStateException e) {
            logger.warn("handleUpdateCurriculum: Not found - divisionId={}, year={}, error={}",
                    divisionId, year, e.getMessage());
            return "redirect:/admin/courseManagement/curriculum?year=" + year
                    + "&currentPageRole=admin&error=not_found";
        } catch (Exception e) {
            logger.error("handleUpdateCurriculum: Unexpected error - divisionId={}, year={}",
                    divisionId, year, e);
            return "redirect:/admin/courseManagement/curriculum?year=" + year
                    + "&currentPageRole=admin&error=update_failed";
        }
        return "redirect:/admin/courseManagement/curriculum?year=" + year + "&currentPageRole=admin&success=updated";
    }

    @RequestMapping(value = "/courseManagement/course", method = RequestMethod.GET)
    public String course(Model model, @RequestParam(required = false) String result) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("course", new Course());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        return "role/admin/course/course";
    }

    @RequestMapping(value = "/courseManagement/course", method = RequestMethod.POST)
    public String course(@ModelAttribute("course") Course course, SessionStatus sessionStatus) {

        courseMapper.insert(course);

        sessionStatus.setComplete();

        return "redirect:/admin/courseManagement/course?result=success";
    }

    @RequestMapping(value = "/courseManagement/course/editCourse", method = RequestMethod.GET)
    public String editCourse(Model model, @RequestParam int id) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("course", new Course());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        Course course = courseMapper.findOne(id);
        model.addAttribute("course", course);
        return "role/admin/course/editCourse";
    }

    @RequestMapping(value = "/courseManagement/course/editCourse", method = RequestMethod.POST)
    public String editCourse(@ModelAttribute("course") Course course, SessionStatus sessionStatus) {

        courseMapper.update(course);

        sessionStatus.setComplete();

        return "redirect:/admin/courseManagement/course?result=success";
    }

    @RequestMapping(value = "/courseManagement/course/courseTable", method = RequestMethod.GET)
    public String courseTable(Model model,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int division) {
        Course firstCourse = null;
        List<Course> courseList;
        if (StringUtils.isBlank(code) && StringUtils.isBlank(title) && division == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setCode(code);
            searchable.setTitle(title);
            searchable.setDivision(division);

            courseList = courseMapper.findBy(searchable);
            for (Course course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/course/courseTable";
    }

    @RequestMapping(value = "/courseManagement/courseEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean courseEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Course course = courseMapper.findOne(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(course, name, value);
        }
        courseMapper.update(course);

        return true;
    }

    @RequestMapping(value = "/courseManagement/course/deleteCourse", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteCourse(@RequestParam int id) {
        Course course = courseMapper.findOne(id);

        int num = professorCourseMapper.countByCourseId(course.getId());
        if (num == 0) {
            courseMapper.delete(course);
            return true;
        } else {
            course.setEnabled(false);
            courseMapper.update(course);
            return false;
        }
    }

    @RequestMapping(value = "/courseManagement/course/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean changeCourseStatus(@RequestParam int id, @RequestParam boolean status) {
        Course course = courseMapper.findOne(id);
        course.setEnabled(status);
        courseMapper.update(course);
        return true;
    }

    @RequestMapping(value = "/courseManagement/alternative", method = RequestMethod.GET)
    public String alternative(Model model, @RequestParam(required = false) String result) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        return "role/admin/alternative/alternative";
    }

    @RequestMapping(value = "/courseManagement/alternative/manageCourse", method = RequestMethod.GET)
    public String manageCourse(Model model, @RequestParam int id) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("subjCategoryList", SubjCategory.values());
        Course course = courseMapper.findOne(id);
        model.addAttribute("course", course);

        List<AltCourse> altCourses = altCourseMapper.findByTargetCourseId(id);
        model.addAttribute("altCourses", altCourses);
        return "role/admin/alternative/manageCourse";
    }

    @RequestMapping(value = "/courseManagement/alternative/courseTable", method = RequestMethod.GET)
    public String alternativeCourseTable(Model model,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int division) {

        Course firstCourse = null;
        List<Course> courseList;
        if (StringUtils.isBlank(code) && StringUtils.isBlank(title) && division == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setCode(code);
            searchable.setTitle(title);
            searchable.setDivision(division);

            courseList = courseMapper.findBy(searchable);

            for (Course course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/alternative/courseTable";
    }

    @RequestMapping(value = "/courseManagement/alternative/altCourseTable", method = RequestMethod.GET)
    public String altCourseTable(Model model,
            @RequestParam int targetCourseId,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int division) {
        Course firstCourse = null;
        List<Course> courseList;

        if (StringUtils.isBlank(code) && StringUtils.isBlank(title) && division == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setCode(code);
            searchable.setTitle(title);
            searchable.setDivision(division);

            courseList = courseMapper.findBy(searchable);

            for (Course course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        model.addAttribute("targetCourseId", targetCourseId);
        return "role/admin/alternative/altCourseTable";
    }

    @RequestMapping(value = "/courseManagement/alternative/addToAlt", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addToAlt(@RequestParam int id, @RequestParam int targetCourseId, @RequestParam String type) {

        AltCourse stored = altCourseMapper.findByCourseIdTargetCourseIdType(id, targetCourseId, type);
        if (stored == null && id != targetCourseId) {
            AltCourse altCourse = new AltCourse();
            altCourse.setCourseId(id);
            altCourse.setTargetCourseId(targetCourseId);
            altCourse.setType(type);
            altCourseMapper.insert(altCourse);
        }

        return true;
    }

    @RequestMapping(value = "/courseManagement/alternative/deleteAlt", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteAlt(@RequestParam int id) {

        AltCourse stored = altCourseMapper.findOne(id);
        altCourseMapper.delete(stored);

        return true;
    }

    @RequestMapping(value = "/courseManagement/cOpen", method = RequestMethod.GET)
    public String cOpen(Model model, @RequestParam(required = false) String result,
            @RequestParam(defaultValue = "0", required = false) @SuppressWarnings("unused") int year,
            @RequestParam(defaultValue = "0", required = false) @SuppressWarnings("unused") int semester,
            @RequestParam(defaultValue = "0", required = false) @SuppressWarnings("unused") int division) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("course", new Course());
        model.addAttribute("compCategoryList", CompCategory.values());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        model.addAttribute("profCourse", new ProfessorCourse());

        return "role/admin/cOpen/cOpen";
    }

    @RequestMapping(value = "/courseManagement/cOpen/courseTable", method = RequestMethod.GET)
    public String cOpenCourseTable(Model model,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int division) {
        Course firstCourse = null;
        List<Course> courseList;
        if (StringUtils.isBlank(code) && StringUtils.isBlank(title) && division == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setCode(code);
            searchable.setTitle(title);
            searchable.setDivision(division);
            searchable.setEnabled(true);

            courseList = courseMapper.findBy(searchable);
            for (Course course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/cOpen/courseTable";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageTime", method = RequestMethod.GET)
    public String manageTime(Model model, @RequestParam int profCourseId,
            @RequestParam(required = false) String result) {
        ProfessorCourse profCourse = professorCourseMapper.findOne(profCourseId);
        Course course = profCourse.getCourse();
        model.addAttribute("course", course);
        model.addAttribute("profCourse", profCourse);
        List<ClassTime> classTimes = classTimeMapper.findByProfCourseId(profCourseId);
        model.addAttribute("classTimes", classTimes);
        model.addAttribute("classTime", new ClassTime());
        model.addAttribute("result", result);
        return "role/admin/cOpen/manageTime";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageTime", method = RequestMethod.POST)
    public String manageTime(@ModelAttribute ClassTime classTime,
            @RequestParam int profCourseId) {
        System.out.println("profCourseId = " + profCourseId);

        if (classTime.getE() > classTime.getS())
            classTimeMapper.insert(classTime);
        return "redirect:/admin/courseManagement/cOpen/manageTime?profCourseId=" + profCourseId + "&result=success";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageTime/deleteTime", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteTime(@RequestParam int id) {
        ClassTime classTime = classTimeMapper.findOne(id);
        classTimeMapper.delete(classTime);
        return true;
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageDivide", method = RequestMethod.GET)
    public String manageDivide(Model model, @RequestParam int courseId, @RequestParam(required = false) String result) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        List<Division> divisions = divisionMapper.findAll();
        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        List<Semester> semesters = semesterMapper.findAll();
        model.addAttribute("semesters", semesters);
        List<User> professors = userMapper.findProfessorsByDivision(course.getDivisionId());
        model.addAttribute("professors", professors);
        List<ProfessorCourse> professorCourseList = professorCourseMapper.findByCourseId(courseId);
        model.addAttribute("professorCourseList", professorCourseList);
        model.addAttribute("profCourse", new ProfessorCourse());
        List<Classroom> classroomList = classroomMapper.findAllEnabled();
        model.addAttribute("classroomList", classroomList);
        model.addAttribute("result", result);
        return "role/admin/cOpen/manageDivide";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageDivide", method = RequestMethod.POST)
    public String manageDivide(@ModelAttribute ProfessorCourse professorCourse,
            @RequestParam int courseId) {
        professorCourseMapper.insert(professorCourse);
        return "redirect:/admin/courseManagement/cOpen/manageDivide?courseId=" + courseId + "&result=success";
    }

    @RequestMapping(value = "/courseManagement/cOpen/editDivide", method = RequestMethod.GET)
    public String editDivide(Model model, @RequestParam int profCourseId,
            @RequestParam(required = false) String result) {
        ProfessorCourse professorCourse = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("profCourse", professorCourse);
        List<Division> divisions = divisionMapper.findAll();
        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        List<Semester> semesters = semesterMapper.findAll();
        model.addAttribute("semesters", semesters);
        List<User> professors = userMapper.findProfessorsByDivision(professorCourse.getCourse().getDivisionId());
        model.addAttribute("professors", professors);
        List<Classroom> classroomList = classroomMapper.findAllEnabled();
        model.addAttribute("classroomList", classroomList);
        model.addAttribute("result", result);
        return "role/admin/cOpen/editDivide";
    }

    @RequestMapping(value = "/courseManagement/cOpen/editDivide", method = RequestMethod.POST)
    public String editDivide(@ModelAttribute("profCourse") ProfessorCourse profCourse, SessionStatus sessionStatus,
            @RequestParam @SuppressWarnings("unused") int profCourseId) {
        System.out.println("profCourse = " + profCourse);
        professorCourseMapper.update(profCourse);
        sessionStatus.setComplete();
        return "redirect:/admin/courseManagement/cOpen/manageDivide?courseId=" + profCourse.getCourseId()
                + "&result=success";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageDivide/deleteDivide", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteDivide(@RequestParam int id) {
        ProfessorCourse professorCourse = professorCourseMapper.findOne(id);

        int num = studentCourseMapper.countByProfCourseId(professorCourse.getId());
        if (num == 0) {
            professorCourseMapper.delete(professorCourse);
            return true;
        } else {
            professorCourse.setEnabled(false);
            professorCourseMapper.update(professorCourse);
            return false;
        }
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageDivide/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean changeDivideStatus(@RequestParam int id, @RequestParam boolean status) {
        System.out.println("id = " + id);
        ProfessorCourse professorCourse = professorCourseMapper.findOne(id);
        professorCourse.setEnabled(status);
        professorCourseMapper.update(professorCourse);
        return true;
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageStudent", method = RequestMethod.GET)
    public String manageStudent(Model model, @RequestParam int profCourseId,
            @RequestParam(required = false) String result) {
        ProfessorCourse professorCourse = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("profCourse", professorCourse);
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("result", result);
        model.addAttribute("profCourseId", profCourseId);

        UploadedFile uploadedFile = new UploadedFile();
        model.addAttribute("uploadedFile", uploadedFile);

        List<StudentCourse> studentCourseList = studentCourseMapper.findByProfCourseId(profCourseId);
        model.addAttribute("studentCourseList", studentCourseList);
        return "role/admin/cOpen/manageStudent";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageStudent/studentTable", method = RequestMethod.GET)
    public String manageStudentStudentTable(Model model, @RequestParam int profCourseId,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0", required = false) int division) {
        List<User> userList;
        if (StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
            List<Integer> userIds = studentCourseMapper.findUserIdsByCourseId(pc.getCourseId());
            searchable.setUserIds(userIds);
            userList = userMapper.findStudentsByAdvisorSchoolYearDivisionExceptRegistered(userIds, number, name,
                    division);
        }

        model.addAttribute("userList", userList);
        model.addAttribute("profCourseId", profCourseId);
        return "role/admin/cOpen/studentTable";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageStudent/addToDivide", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addToDivide(@RequestParam int id, @RequestParam int profCourseId) {
        StudentCourse studentCourse = new StudentCourse();
        ProfessorCourse professorCourse = professorCourseMapper.findOne(profCourseId);
        studentCourse.setCourseId(professorCourse.getCourseId());
        studentCourse.setProfCourseId(professorCourse.getId());
        studentCourse.setUserId(id);
        studentCourseMapper.insert(studentCourse);
        int num = studentCourseMapper.countByProfCourseId(profCourseId);
        professorCourse.setNumStudent(num);
        professorCourseMapper.update(professorCourse);

        return true;
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageStudent/removeFromDivide", method = RequestMethod.POST)
    @ResponseBody
    public Boolean removeFromDivide(@RequestParam int id, @RequestParam int profCourseId) {
        ProfessorCourse professorCourse = professorCourseMapper.findOne(profCourseId);
        StudentCourse studentCourse = studentCourseMapper.findByUserIdProfCourseId(id, profCourseId);
        studentCourseMapper.delete(studentCourse);
        int num = studentCourseMapper.countByProfCourseId(profCourseId);
        professorCourse.setNumStudent(num);
        professorCourseMapper.update(professorCourse);

        return true;
    }

    /**
     * Load eligible students for course registration.
     * Filters by division + schoolYear, excludes already registered students.
     * Returns JSON response for AJAX.
     */
    @RequestMapping(value = "/courseManagement/cOpen/loadEligibleStudents", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadEligibleStudents(
            @RequestParam int divisionId,
            @RequestParam int schoolYear,
            @RequestParam int profCourseId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Validate parameters
            if (divisionId <= 0 || schoolYear <= 0 || profCourseId <= 0) {
                response.put("success", false);
                response.put("error", "Invalid parameters");
                return response;
            }

            // Get eligible students
            List<User> eligibleUsers = userMapper.findEligibleStudentsForCourseRegistration(
                    divisionId, schoolYear, profCourseId);

            // Transform to simple JSON-friendly format
            List<Map<String, Object>> students = new ArrayList<>();
            for (User user : eligibleUsers) {
                Map<String, Object> studentData = new HashMap<>();
                studentData.put("userId", user.getId());
                studentData.put("number", user.getNumber());
                String fullName = "";
                if (user.getContact() != null) {
                    fullName = user.getContact().getFullName();
                }
                studentData.put("name", fullName);
                studentData.put("enabled", user.isConfirm() ? 1 : 0); // 1=Active, 0=Pending
                students.add(studentData);
            }

            response.put("success", true);
            response.put("students", students);
            logger.debug("Loaded {} eligible students for profCourseId={}, divisionId={}, schoolYear={}",
                    students.size(), profCourseId, divisionId, schoolYear);

        } catch (Exception e) {
            logger.error("Error loading eligible students: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("error", "Failed to load students");
        }

        return response;
    }

    /**
     * Register selected students to a course (batch insert).
     * Handles duplicate protection - skips already registered students.
     * Returns JSON response with inserted count and duplicates count.
     */
    @RequestMapping(value = "/courseManagement/cOpen/registerSelectedStudents", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<Map<String, Object>> registerSelectedStudents(@RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Parse request body
            Integer courseId = (Integer) requestBody.get("courseId");
            Integer profCourseId = (Integer) requestBody.get("profCourseId");
            Integer schoolYear = (Integer) requestBody.get("schoolYear");
            @SuppressWarnings("unchecked")
            List<Integer> studentIds = (List<Integer>) requestBody.get("studentIds");

            // Validation: check required parameters (400 Bad Request)
            if (profCourseId == null) {
                response.put("status", "error");
                response.put("message", "Missing profCourseId");
                return ResponseEntity.badRequest().body(response);
            }
            if (studentIds == null || studentIds.isEmpty()) {
                response.put("status", "error");
                response.put("message", "No students selected");
                return ResponseEntity.badRequest().body(response);
            }

            // Get professor course for validation and capacity check
            ProfessorCourse professorCourse = professorCourseMapper.findOne(profCourseId);
            if (professorCourse == null) {
                response.put("status", "error");
                response.put("message", "Course not found");
                return ResponseEntity.badRequest().body(response);
            }

            // If courseId not provided, get it from professorCourse
            if (courseId == null) {
                courseId = professorCourse.getCourseId();
            }

            // Capacity check: get current count and limit
            int currentCount = studentCourseMapper.countByProfCourseId(profCourseId);
            int limitStudent = professorCourse.getLimitStudent();

            // Filter out duplicates first to get accurate new student count
            List<Integer> newStudentIds = new ArrayList<>();
            int duplicates = 0;
            for (Integer studentId : studentIds) {
                if (studentId == null || studentId <= 0) {
                    continue;
                }
                // Use faster exists check
                if (studentCourseMapper.existsByUserIdAndProfCourseId(studentId, profCourseId)) {
                    duplicates++;
                } else {
                    newStudentIds.add(studentId);
                }
            }

            // Check if adding new students would exceed capacity (409 Conflict)
            if (limitStudent > 0 && (currentCount + newStudentIds.size()) > limitStudent) {
                int availableSeats = Math.max(0, limitStudent - currentCount);
                response.put("status", "error");
                response.put("message", "Course capacity exceeded. Available seats: " + availableSeats);
                response.put("availableSeats", availableSeats);
                response.put("limitStudent", limitStudent);
                response.put("currentCount", currentCount);
                return ResponseEntity.status(409).body(response);
            }

            // Insert new students (no partial inserts - all or nothing)
            int inserted = 0;
            for (Integer studentId : newStudentIds) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setUserId(studentId);
                studentCourse.setCourseId(courseId);
                studentCourse.setProfCourseId(profCourseId);
                if (schoolYear != null) {
                    studentCourse.setSchoolYear(schoolYear);
                }
                // Leave other fields null/default as per schema defaults

                studentCourseMapper.insert(studentCourse);
                inserted++;
                logger.debug("Inserted: userId={} for profCourseId={}", studentId, profCourseId);
            }

            // Update student count on professor course
            int totalCount = studentCourseMapper.countByProfCourseId(profCourseId);
            professorCourse.setNumStudent(totalCount);
            professorCourseMapper.update(professorCourse);

            // Build response
            response.put("status", duplicates > 0 ? "warning" : "success");
            response.put("inserted", inserted);
            response.put("duplicates", duplicates);
            response.put("message", "Successfully registered " + inserted + " student(s)" +
                    (duplicates > 0 ? ". Duplicates skipped: " + duplicates : ""));
            // Keep backward compatible 'success' field
            response.put("success", true);

            logger.info("Registered {} students for profCourseId={} (duplicates skipped: {})",
                    inserted, profCourseId, duplicates);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error registering students: {}", e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Failed to register students: " + e.getMessage());
            response.put("success", false);
            return ResponseEntity.status(500).body(response);
        }
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageStudent/uploadStudent", method = RequestMethod.POST)
    public String uploadStudent(HttpServletRequest request, @ModelAttribute UploadedFile uploadedFile,
            @RequestParam int profCourseId) {

        logger.info("Starting student upload for profCourseId={}", profCourseId);

        MultipartFile multipartFile = uploadedFile.getFile();

        // Validate file is present
        if (multipartFile == null || multipartFile.isEmpty()) {
            logger.warn("Upload failed: empty file for profCourseId={}", profCourseId);
            return "redirect:/admin/courseManagement/cOpen/manageStudent?result=error&errorMsg=empty_file&profCourseId="
                    + profCourseId;
        }

        try {
            String fileName = multipartFile.getOriginalFilename();
            String ext = fileService.getExtension(fileName);

            // Validate file format
            if (!ext.equalsIgnoreCase("xls") && !ext.equalsIgnoreCase("xlsx")) {
                logger.warn("Upload failed: invalid format '{}' for profCourseId={}", sanitizeForLog(ext),
                        profCourseId);
                return "redirect:/admin/courseManagement/cOpen/manageStudent?result=error&errorMsg=invalid_format&profCourseId="
                        + profCourseId;
            }

            // Save file to temp directory via FileService (sanitized + canonical path
            // validated)
            File convFile = fileService.saveMultipartToTempFile(multipartFile, request);
            logger.debug("File saved to temp path: {}", sanitizeForLog(convFile.getAbsolutePath()));

            // Process Excel and get detailed result
            StudentUploadResult result = readStudentExcel(convFile, profCourseId);
            logger.info("Upload result for profCourseId={}: {}", profCourseId, result.getSummary());

            // Save file metadata to uploaded_file table
            try {
                DateTime dt = new DateTime();
                fileService.processUploadedFile(multipartFile, User.current(),
                        Designation.student_registration, 0, profCourseId, dt.getYear());
                logger.debug("File metadata saved to uploaded_file table");
            } catch (Exception e) {
                logger.error("Failed to save file metadata for profCourseId={}: {}", profCourseId, e.getMessage());
                // Continue even if metadata save fails - the main operation succeeded
            }

            // Determine redirect based on result
            if (result.hasInsertions()) {
                logger.info("Upload successful: {} students inserted for profCourseId={}",
                        result.getInsertedCount(), profCourseId);
                return "redirect:/admin/courseManagement/cOpen/manageStudent?result=success"
                        + "&inserted=" + result.getInsertedCount()
                        + "&duplicates=" + result.getSkippedDuplicateCount()
                        + "&notFound=" + result.getSkippedNotFoundCount()
                        + "&profCourseId=" + profCourseId;
            } else if (result.getSkippedNotFoundCount() > 0) {
                logger.warn("Upload completed but no insertions: {} students not found in system for profCourseId={}",
                        result.getSkippedNotFoundCount(), profCourseId);
                return "redirect:/admin/courseManagement/cOpen/manageStudent?result=error&errorMsg=no_match"
                        + "&notFound=" + result.getSkippedNotFoundCount()
                        + "&profCourseId=" + profCourseId;
            } else if (result.getSkippedDuplicateCount() > 0) {
                logger.info("Upload completed: all {} students were already registered for profCourseId={}",
                        result.getSkippedDuplicateCount(), profCourseId);
                return "redirect:/admin/courseManagement/cOpen/manageStudent?result=error&errorMsg=all_duplicates"
                        + "&duplicates=" + result.getSkippedDuplicateCount()
                        + "&profCourseId=" + profCourseId;
            } else {
                logger.warn("Upload completed but no data found in file for profCourseId={}", profCourseId);
                return "redirect:/admin/courseManagement/cOpen/manageStudent?result=error&errorMsg=empty_data&profCourseId="
                        + profCourseId;
            }

        } catch (Exception e) {
            logger.error("Error processing student upload for profCourseId={}", profCourseId, e);
            return "redirect:/admin/courseManagement/cOpen/manageStudent?result=error&errorMsg=processing_error&profCourseId="
                    + profCourseId;
        }
    }

    /**
     * Reads student numbers from Excel file and registers them into student_course
     * table.
     * 
     * @param file         The Excel file containing student numbers in the first
     *                     column
     * @param profCourseId The professor course ID to register students for
     * @return StudentUploadResult containing detailed counts of inserted, skipped,
     *         and invalid records
     */
    public StudentUploadResult readStudentExcel(File file, int profCourseId) {
        StudentUploadResult result = new StudentUploadResult();
        FileInputStream fileInputStream = null;
        XSSFWorkbook workbook = null;

        try {
            // Defense-in-depth: re-validate canonical path at point of use
            String canonicalPath = file.getCanonicalPath();
            String allowedBase = new File(System.getProperty("java.io.tmpdir")
                    + File.separator + "ams_temp").getCanonicalPath();
            if (!canonicalPath.startsWith(allowedBase + File.separator)
                    && !canonicalPath.equals(allowedBase)) {
                throw new SecurityException("Path validation failed: file outside allowed directory");
            }
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            final int RESERVED_SKIP = 1; // Skip header row

            ProfessorCourse professorCourse = professorCourseMapper.findOne(profCourseId);
            if (professorCourse == null) {
                logger.error("ProfessorCourse not found for id={}", profCourseId);
                return result;
            }

            int limitStudent = professorCourse.getLimitStudent();
            logger.debug("Processing {} rows (excluding header) for profCourseId={}, limit={}",
                    rows - RESERVED_SKIP, profCourseId, limitStudent);

            for (int rowIndex = RESERVED_SKIP; rowIndex < rows; rowIndex++) {
                result.incrementTotalRows();

                // Check if we've reached the student limit
                int currentCount = studentCourseMapper.countByProfCourseId(profCourseId);
                if (currentCount >= limitStudent) {
                    logger.warn("Student limit {} reached for profCourseId={}, stopping at row {}",
                            limitStudent, profCourseId, rowIndex);
                    break;
                }

                try {
                    XSSFRow row = sheet.getRow(rowIndex);
                    if (row == null) {
                        result.incrementInvalidRows();
                        continue;
                    }

                    Cell cell0 = row.getCell(0);
                    if (cell0 == null) {
                        result.incrementInvalidRows();
                        continue;
                    }

                    // Get student number - handle both string and numeric cell types
                    String studentNumber = null;
                    int cellType = cell0.getCellType();
                    if (cellType == Cell.CELL_TYPE_STRING) {
                        studentNumber = cell0.getStringCellValue();
                    } else if (cellType == Cell.CELL_TYPE_NUMERIC) {
                        studentNumber = String.valueOf((long) cell0.getNumericCellValue());
                    } else {
                        result.incrementInvalidRows();
                        logger.debug("Invalid cell type at row {}: {}", rowIndex, cellType);
                        continue;
                    }

                    // Validate student number is not empty
                    if (StringUtils.isBlank(studentNumber)) {
                        result.incrementInvalidRows();
                        continue;
                    }

                    studentNumber = studentNumber.trim();

                    // Find student by number
                    User studentUser = userMapper.findStudentByNumber(studentNumber);

                    if (studentUser == null) {
                        result.incrementSkippedNotFound();
                        logger.debug("Student not found in system: {}", studentNumber);
                        continue;
                    }

                    // Check for duplicate registration
                    StudentCourse existing = studentCourseMapper.findByUserIdProfCourseId(
                            studentUser.getId(), profCourseId);

                    if (existing != null) {
                        result.incrementSkippedDuplicate();
                        logger.debug("Student already registered: {} (userId={})", studentNumber, studentUser.getId());
                        continue;
                    }

                    // Insert new student course registration
                    StudentCourse studentCourse = new StudentCourse();
                    studentCourse.setCourseId(professorCourse.getCourseId());
                    studentCourse.setProfCourseId(professorCourse.getId());
                    studentCourse.setUserId(studentUser.getId());
                    studentCourse.setSchoolYear(studentUser.getSchoolYear());
                    studentCourseMapper.insert(studentCourse);
                    result.incrementInserted();
                    logger.debug("Inserted student: {} (userId={}) into profCourseId={}",
                            studentNumber, studentUser.getId(), profCourseId);

                } catch (Exception e) {
                    result.incrementInvalidRows();
                    logger.warn("Error processing row {}: {}", rowIndex, e.getMessage());
                }
            }

            // Update the student count on the professor course
            int finalCount = studentCourseMapper.countByProfCourseId(profCourseId);
            professorCourse.setNumStudent(finalCount);
            professorCourseMapper.update(professorCourse);
            logger.debug("Updated profCourseId={} numStudent to {}", profCourseId, finalCount);

        } catch (Exception e) {
            logger.error("Error reading Excel file for profCourseId={}", profCourseId, e);
        } finally {
            // Clean up resources
            try {
                if (workbook != null)
                    workbook.close();
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                logger.warn("Error closing file resources: {}", e.getMessage());
            }
        }

        return result;
    }

    @RequestMapping(value = "/courseManagement/attendance", method = RequestMethod.GET)
    public String attendance(Model model, @RequestParam(required = false) String result) {

        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("course", new Course());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        return "role/admin/attendance/attendance";
    }

    @RequestMapping(value = "/courseManagement/attendance/courseTable", method = RequestMethod.GET)
    public String attendanceCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester,
            @RequestParam(defaultValue = "0", required = false) int division) {
        ProfessorCourse firstCourse = null;
        List<ProfessorCourse> courseList;

        if (year == 0 && semester == 0 && division == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setDivision(division);
            courseList = professorCourseMapper.findBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/admin/attendance/courseTable";
    }

    @RequestMapping(value = "/courseManagement/syllabus", method = RequestMethod.GET)
    public String syllabus(Model model) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/admin/syllabus/syllabus";
    }

    @RequestMapping(value = "/courseManagement/syllabus/courseDetail", method = RequestMethod.GET)
    public String courseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(pc.getId());
        if (lectureFundamentals == null)
            return "role/common/syllabus/requiredSyllabus";
        model.addAttribute("lectureFundamentals", lectureFundamentals);
        ProfLectureMethod profLectureMethod = profLectureMethodMapper.findByProfCourseId(pc.getId());
        model.addAttribute("profLectureMethod",
                profLectureMethod == null ? new ProfLectureMethod() : profLectureMethod);
        LectureContents lectureContents = lectureContentsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureContents", lectureContents == null ? new LectureContents() : lectureContents);

        List<LectureMethod> lectureMethods = lectureMethodMapper.findAll();
        model.addAttribute("lectureMethods", lectureMethods);

        List<EducationalMedium> educationalMediums = educationalMediumMapper.findAll();
        model.addAttribute("educationalMediums", educationalMediums);

        List<EvaluationMethod> evaluationMethods = evaluationMethodMapper.findAll();
        model.addAttribute("evaluationMethods", evaluationMethods);

        List<Equipment> equipments = equipmentMapper.findAll();
        model.addAttribute("equipments", equipments);
        if (print.equals("true"))
            return "role/common/syllabus/courseDetailForPrint";
        return "role/admin/syllabus/courseDetail";
    }

    @RequestMapping(value = "/courseManagement/syllabus/courseTable", method = RequestMethod.GET)
    public String syllabusCourseTable(Model model,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {
        List<ProfessorCourse> courseList;
        ProfessorCourse firstCourse = null;

        if (StringUtils.isBlank(code) && StringUtils.isBlank(title) && division == 0 && year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setCode(code);
            searchable.setTitle(title);
            searchable.setDivision(division);

            courseList = professorCourseMapper.findBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("profCourseList", courseList);
        return "role/admin/syllabus/courseTable";
    }

    @RequestMapping(value = "/academicManagement/studentGrade", method = RequestMethod.GET)
    public String studentGrade(Model model) {
        model.addAttribute("yearList", getYearList());
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        return "role/admin/studentGrade/studentGrade";
    }

    @RequestMapping(value = "/academicManagement/studentGrade/courseTable", method = RequestMethod.GET)
    public String academicManagementCourseTable(Model model,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {

        ProfessorCourse firstCourse = null;
        List<ProfessorCourse> courseList;
        if (StringUtils.isBlank(code) && StringUtils.isBlank(title) && division == 0 && year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            courseList = professorCourseMapper.findBy(searchable);
            for (ProfessorCourse pc : courseList) {
                firstCourse = pc;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/studentGrade/courseTable";
    }

    @RequestMapping(value = "/academicManagement/studentGrade/courseDetail", method = RequestMethod.GET)
    public String academicManagementCourseDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(profCourseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(profCourseId);
        model.addAttribute("studentCourses", studentCourses);

        return "role/admin/studentGrade/courseDetail";
    }

    @RequestMapping(value = "/academicManagement/studentGrade/ratioDetail", method = RequestMethod.GET)
    public String academicManagementRatioDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(profCourseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);

        return "role/common/grade/ratioDetail";
    }

    @RequestMapping(value = "/academicManagement/studentGrade/courseDetailForPrint", method = RequestMethod.GET)
    public String registerGradeCourseDetailForPrint(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(profCourseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = studentCourseMapper.findByProfCourseId(profCourseId);
        model.addAttribute("studentCourses", studentCourses);

        return "role/common/grade/courseDetailForPrint";
    }

    @RequestMapping(value = "/academicManagement/graduationCriteria", method = RequestMethod.GET)
    public String graduationCriteria(Model model, @RequestParam(required = false) String result) {
        model.addAttribute("yearList", getYearList());
        List<Division> divisions = divisionMapper.findAll();
        model.addAttribute("divisions", divisions);
        GraduationCriteria graduationCriteria = new GraduationCriteria();
        model.addAttribute("graduationCriteria", graduationCriteria);
        model.addAttribute("result", result);
        return "role/admin/graduationCriteria/graduationCriteria";
    }

    @RequestMapping(value = "/academicManagement/graduationCriteria", method = RequestMethod.POST)
    public String graduationCriteria(@ModelAttribute GraduationCriteria graduationCriteria) {
        graduationCriteriaMapper.insert(graduationCriteria);
        return "redirect:/admin/academicManagement/graduationCriteria?result=success";
    }

    @RequestMapping(value = "/academicManagement/graduationCriteria/criteriaTable", method = RequestMethod.GET)
    public String graduationCriteriaStudentTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int division) {
        GraduationCriteria firstOne = null;
        List<GraduationCriteria> list;
        if (division == 0 && year == 0) {
            list = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setDivision(division);

            list = graduationCriteriaMapper.findByYearDivision(searchable);

            for (GraduationCriteria gc : list) {
                firstOne = gc;
                break;
            }
        }

        model.addAttribute("gcList", list);
        model.addAttribute("firstOne", firstOne);
        return "role/admin/graduationCriteria/criteriaTable";
    }

    @RequestMapping(value = "/academicManagement/graduationCriteria/criteriaEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean criteriaEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        GraduationCriteria gc = graduationCriteriaMapper.findOne(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(gc, name, value);
        }
        graduationCriteriaMapper.update(gc);

        return true;
    }

    @RequestMapping(value = "/courseManagement/course/deleteCriteria", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteCriteria(@RequestParam int id) {
        GraduationCriteria gc = graduationCriteriaMapper.findOne(id);
        Searchable searchable = new Searchable();
        searchable.setYear(gc.getYear());
        searchable.setDivision(gc.getDivisionId());
        List<GraduationCriteria> gcList = graduationCriteriaMapper.findByYearDivision(searchable);
        if (!CollectionUtils.isEmpty(gcList) && gcList.size() == 1)
            return false;

        graduationCriteriaMapper.delete(gc);
        return true;
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor", method = RequestMethod.GET)
    public String assessmentFactor(Model model) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/admin/assessmentFactor/assessmentFactor";
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor/courseTable", method = RequestMethod.GET)
    public String assessmentFactorCourseTable(Model model,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int division) {

        Course firstCourse = null;
        List<Course> courseList;

        if (StringUtils.isBlank(code) && StringUtils.isBlank(title) && division == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setCode(code);
            searchable.setTitle(title);
            searchable.setDivision(division);

            courseList = courseMapper.findBy(searchable);

            for (Course course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/assessmentFactor/courseTable";
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor/manageAf", method = RequestMethod.GET)
    public String assessmentFactorCourseDetail(Model model, @RequestParam int courseId) {
        Course course = courseMapper.findOne(courseId);
        model.addAttribute("course", course);
        AssessmentFactor assessmentFactor = new AssessmentFactor();
        assessmentFactor.setCourseId(courseId);
        model.addAttribute("assessmentFactor", assessmentFactor);
        List<AssessmentFactor> assessmentFactors = assessmentFactorMapper.findByCourseId(courseId);
        model.addAttribute("assessmentFactors", assessmentFactors);

        return "role/admin/assessmentFactor/manageAf";
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor/manageAf", method = RequestMethod.POST)
    public String assessmentFactorCourseDetail(@ModelAttribute AssessmentFactor assessmentFactor,
            @RequestParam int courseId) {
        assessmentFactor.setEnabled(true);
        assessmentFactorMapper.insert(assessmentFactor);
        return "redirect:/admin/academicManagement/assessmentFactor/manageAf?courseId=" + courseId;
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor/manageAf/deleteAf", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteAf(@RequestParam int id) {
        AssessmentFactor assessmentFactor = assessmentFactorMapper.findOne(id);

        List<Assessment> assessments = assessmentMapper.findByAssessmentFactorId(id);
        if (CollectionUtils.isEmpty(assessments)) {
            assessmentFactorMapper.delete(assessmentFactor);
            return true;
        } else {
            assessmentFactor.setEnabled(false);
            assessmentFactorMapper.update(assessmentFactor);
            return false;
        }
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor/manageAf/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean changeAfStatus(@RequestParam int id, @RequestParam boolean status) {
        AssessmentFactor assessmentFactor = assessmentFactorMapper.findOne(id);
        assessmentFactor.setEnabled(status);
        assessmentFactorMapper.update(assessmentFactor);
        return true;
    }

    @RequestMapping(value = "/academicManagement/assessmentResult", method = RequestMethod.GET)
    public String assessmentResult(Model model) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        List<User> professors = userMapper.findAllProfessors();
        model.addAttribute("professors", professors);
        return "role/admin/assessmentResult/assessmentResult";
    }

    @RequestMapping(value = "/academicManagement/assessmentResult/courseTable", method = RequestMethod.GET)
    public String assessmentResultCourseTable(Model model,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam(defaultValue = "0", required = false) int advisor) {
        ProfessorCourse firstCourse = null;
        List<ProfessorCourse> courseList;

        if (year == 0 && semester == 0 && division == 0 && advisor == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setDivision(division);
            searchable.setAdvisor(advisor);
            courseList = professorCourseMapper.findBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/assessmentResult/courseTable";
    }

    @RequestMapping(value = "/academicManagement/assessmentResult/courseDetail", method = RequestMethod.GET)
    public String assessmentResultCourseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);
        model.addAttribute("pc", pc);
        List<Assessment> assessments = assessmentMapper.findByProfCourseId(profCourseId);
        model.addAttribute("assessments", assessments);
        List<AssessmentFactor> assessmentFactors = assessmentFactorMapper.findByCourseId(pc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);
        if (print.equals("true"))
            return "role/common/assessment/courseDetailForPrint";
        return "role/admin/assessmentResult/courseDetail";
    }

    @RequestMapping(value = "/academicManagement/cqi", method = RequestMethod.GET)
    public String cqi(Model model) {
        List<Division> divisions = divisionMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/admin/cqi/cqi";
    }

    @RequestMapping(value = "/academicManagement/cqi/courseTable", method = RequestMethod.GET)
    public String cqiReportCourseTable(Model model,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0", required = false) int division,
            @RequestParam(defaultValue = "0", required = false) int year,
            @RequestParam(defaultValue = "0", required = false) int semester) {

        ProfessorCourse firstCourse = null;
        List<ProfessorCourse> courseList;
        if (StringUtils.isBlank(code) && StringUtils.isBlank(title) && division == 0 && year == 0 && semester == 0) {
            courseList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setSemester(semester);
            searchable.setCode(code);
            searchable.setTitle(title);
            searchable.setDivision(division);

            courseList = professorCourseMapper.findBy(searchable);

            for (ProfessorCourse course : courseList) {
                firstCourse = course;
                break;
            }
        }

        model.addAttribute("firstCourse", firstCourse);
        model.addAttribute("courseList", courseList);
        return "role/admin/cqi/courseTable";
    }

    @RequestMapping(value = "/academicManagement/cqi/courseDetail", method = RequestMethod.GET)
    public String cqiReportCourseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = professorCourseMapper.findOne(profCourseId);

        model.addAttribute("myAvg", profService.getAssignedAvg(pc));
        model.addAttribute("pc", pc);
        Searchable s = new Searchable();
        s.setCourseId(pc.getCourseId());
        s.setYear(pc.getSemester().getYear());
        s.setEnabled(true);
        s.setOrderParam("divide");
        s.setOrderDir("asc");
        List<ProfessorCourse> professorCourseList = professorCourseMapper.findBy(s);
        for (ProfessorCourse professorCourse : professorCourseList) {
            List<Assessment> assessments = assessmentMapper.findByProfCourseId(professorCourse.getId());
            professorCourse.setAssessmentList(assessments);
            List<StudentCourse> studentCourseList = studentCourseMapper
                    .findByProfCourseIdValid(professorCourse.getId());
            professorCourse.setStudentCourseList(studentCourseList);
        }
        model.addAttribute("professorCourseList", professorCourseList);

        int currentYear = pc.getSemester().getYear();
        model.addAttribute("currentYear", currentYear);
        LectureFundamentals lectureFundamentals = lectureFundamentalsMapper.findByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        Map<Integer, Double> averageAssignedDivideMap = profService.getAverageAssignedDivideMap(professorCourseList);
        model.addAttribute("averageAssignedDivideMap", averageAssignedDivideMap);

        Map<Integer, Cqi> cqiMap = new LinkedHashMap<>();
        for (int i = (currentYear - 2); i < currentYear; i++) {
            Cqi cqi = cqiMapper.findByYearCourseIdDivide(i, pc.getCourseId(), pc.getDivide());

            if (cqi == null) {
                cqi = new Cqi();
            }
            cqiMap.put(i, cqi);
            if (i == currentYear - 1) {
                model.addAttribute("prevCqi", cqi);
            }
        }
        model.addAttribute("cqiMap", cqiMap);

        Cqi cqi = cqiMapper.findByProfCourseId(pc.getId());
        if (cqi == null) {
            cqi = new Cqi();
            cqi.setSemesterId(pc.getSemesterId());
            cqi.setCourseId(pc.getCourseId());
            cqi.setDivide(pc.getDivide());
            cqi.setProfCourseId(pc.getId());
        }
        model.addAttribute("cqi", cqi);
        Map<Integer, Double> averageAssignedMap = profService.getAverageAssignedMap(pc.getCourseId(), currentYear);
        model.addAttribute("averageAssignedMap", averageAssignedMap);
        List<AssessmentFactor> assessmentFactors = assessmentFactorMapper.findByCourseId(pc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);

        if (print.equals("true")) {

            return "role/common/cqi/courseDetailForPrint";
        }

        return "role/admin/cqi/courseDetail";
    }

    @RequestMapping(value = "/systemManagement/yearSemester", method = RequestMethod.GET)
    public String yearSemester(Model model, @RequestParam(required = false) String result) {
        Semester semester = new Semester();
        model.addAttribute("semester", semester);
        model.addAttribute("result", result);
        return "role/admin/yearSemester/yearSemester";
    }

    @RequestMapping(value = "/systemManagement/yearSemester", method = RequestMethod.POST)
    public String yearSemester(@ModelAttribute("semester") Semester semester, SessionStatus sessionStatus) {

        Semester exist = semesterMapper.findByYearSemester(semester.getYear(), semester.getSemester());
        if (exist == null) {
            List<Semester> semesterAll = semesterMapper.findAll();
            for (Semester s : semesterAll) {
                s.setCurrent(false);
                semesterMapper.update(s);
            }
            semester.setCurrent(true);
            semesterMapper.insert(semester);
        }

        sessionStatus.setComplete();

        return "redirect:/admin/systemManagement/yearSemester?result=success";
    }

    @RequestMapping(value = "/systemManagement/yearSemester/deleteSemester", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteSemester(@RequestParam int id) {
        Semester exist = semesterMapper.findOne(id);
        semesterMapper.delete(exist);

        return true;
    }

    @RequestMapping(value = "/systemManagement/yearSemester/currentSemester", method = RequestMethod.POST)
    @ResponseBody
    public Boolean currentSemester(@RequestParam int id) {
        List<Semester> semesterAll = semesterMapper.findAll();
        for (Semester s : semesterAll) {
            s.setCurrent(false);
            semesterMapper.update(s);
        }
        Semester exist = semesterMapper.findOne(id);
        exist.setCurrent(true);
        semesterMapper.update(exist);

        return true;
    }

    @RequestMapping(value = "/systemManagement/yearSemester/semesterEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean semesterEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Semester semester = semesterMapper.findOne(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(semester, name, value);
        }

        Semester stored = semesterMapper.findByYearSemester(semester.getYear(), semester.getSemester());
        if (stored == null) {

            semesterMapper.update(semester);
            return true;
        } else
            return false;
    }

    @RequestMapping(value = "/systemManagement/yearSemester/semesterTable", method = RequestMethod.GET)
    public String semesterTable(Model model) {

        List<Semester> semesterList = semesterMapper.findAll();

        model.addAttribute("semesterList", semesterList);
        return "role/admin/yearSemester/semesterTable";
    }

    @RequestMapping(value = "/systemManagement/divisionMajor", method = RequestMethod.GET)
    public String divisionMajor(Model model, @RequestParam(required = false) String result) {
        Division division = new Division();
        List<Division> divisions = divisionMapper.findAll();
        model.addAttribute("divisions", divisions);
        model.addAttribute("division", division);
        model.addAttribute("result", result);

        return "role/admin/divisionMajor/divisionMajor";
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/divisionTable", method = RequestMethod.GET)
    public String divisionTable(Model model) {

        List<Division> divisionList = divisionMapper.findAll();

        model.addAttribute("divisionList", divisionList);
        return "role/admin/divisionMajor/divisionTable";
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/divisionEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean divisionEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Division division = divisionMapper.findOne(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(division, name, value);
        }
        divisionMapper.update(division);
        return true;
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/deleteDivision", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteDivision(@RequestParam int id) {
        Division division = divisionMapper.findOne(id);
        List<Course> courses = courseMapper.findByDivision(id);
        if (CollectionUtils.isEmpty(courses)) {
            divisionMapper.delete(division);
            return true;
        } else {
            division.setEnabled(false);
            divisionMapper.update(division);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/enableDivision", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableDivision(@RequestParam int id) {
        Division division = divisionMapper.findOne(id);
        division.setEnabled(true);
        divisionMapper.update(division);
        return true;
    }

    @RequestMapping(value = "/systemManagement/createDivision", method = RequestMethod.POST)
    public String createDivision(@ModelAttribute Division division) {
        divisionMapper.insert(division);
        return "redirect:/admin/systemManagement/divisionMajor?result=success";
    }

    @RequestMapping(value = "/systemManagement/lectureMethod", method = RequestMethod.GET)
    public String lectureMethod(Model model, @RequestParam(required = false) String result) {
        LectureMethod lectureMethod = new LectureMethod();
        model.addAttribute("lectureMethod", lectureMethod);
        model.addAttribute("result", result);
        return "role/admin/lectureMethod/lectureMethod";
    }

    @RequestMapping(value = "/systemManagement/lectureMethod", method = RequestMethod.POST)
    public String lectureMethod(@ModelAttribute LectureMethod lectureMethod) {
        lectureMethodMapper.insert(lectureMethod);
        return "redirect:/admin/systemManagement/lectureMethod?result=success";
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/lectureMethodTable", method = RequestMethod.GET)
    public String lectureMethodTable(Model model) {

        List<LectureMethod> lectureMethodList = lectureMethodMapper.findAll();

        model.addAttribute("lectureMethodList", lectureMethodList);
        return "role/admin/lectureMethod/lectureMethodTable";
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/lectureMethodEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean lectureMethodEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        LectureMethod lectureMethod = lectureMethodMapper.findOne(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(lectureMethod, name, value);
        }
        lectureMethodMapper.update(lectureMethod);
        return true;
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/deleteLectureMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteLectureMethod(@RequestParam int id) {
        LectureMethod lectureMethod = lectureMethodMapper.findOne(id);

        ProfLectureMethod stored = profLectureMethodMapper.findByLectureMethodId(Integer.toString(id));

        if (stored == null) {
            lectureMethodMapper.delete(lectureMethod);
            return true;
        } else {
            lectureMethod.setEnabled(false);
            lectureMethodMapper.update(lectureMethod);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/enableLectureMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableLectureMethod(@RequestParam int id) {
        LectureMethod lectureMethod = lectureMethodMapper.findOne(id);
        lectureMethod.setEnabled(true);
        lectureMethodMapper.update(lectureMethod);
        return true;
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod", method = RequestMethod.GET)
    public String evaluationMethod(Model model, @RequestParam(required = false) String result) {
        EvaluationMethod evaluationMethod = new EvaluationMethod();
        model.addAttribute("evaluationMethod", evaluationMethod);
        model.addAttribute("result", result);
        return "role/admin/evaluationMethod/evaluationMethod";
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod", method = RequestMethod.POST)
    public String evaluationMethod(@ModelAttribute EvaluationMethod evaluationMethod) {
        evaluationMethodMapper.insert(evaluationMethod);
        return "redirect:/admin/systemManagement/evaluationMethod?result=success";
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/evaluationMethodTable", method = RequestMethod.GET)
    public String evaluationMethodTable(Model model) {

        List<EvaluationMethod> evaluationMethodList = evaluationMethodMapper.findAll();

        model.addAttribute("evaluationMethodList", evaluationMethodList);
        return "role/admin/evaluationMethod/evaluationMethodTable";
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/evaluationMethodEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean evaluationMethodEditable(@RequestParam int pk, @RequestParam String name,
            @RequestParam String value) {
        EvaluationMethod evaluationMethod = evaluationMethodMapper.findOne(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(evaluationMethod, name, value);
        }
        evaluationMethodMapper.update(evaluationMethod);
        return true;
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/deleteEvaluationMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteEvaluationMethod(@RequestParam int id) {
        EvaluationMethod evaluationMethod = evaluationMethodMapper.findOne(id);

        ProfLectureMethod stored = profLectureMethodMapper.findByEvaluationMethodId(Integer.toString(id));
        evaluationMethodMapper.delete(evaluationMethod);
        if (stored == null) {
            evaluationMethodMapper.delete(evaluationMethod);
            return true;
        } else {
            evaluationMethod.setEnabled(false);
            evaluationMethodMapper.update(evaluationMethod);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/enableEvaluationMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableEvaluationMethod(@RequestParam int id) {
        EvaluationMethod evaluationMethod = evaluationMethodMapper.findOne(id);
        evaluationMethod.setEnabled(true);
        evaluationMethodMapper.update(evaluationMethod);
        return true;
    }

    @RequestMapping(value = "/systemManagement/educationalMedium", method = RequestMethod.GET)
    public String educationalMedium(Model model) {
        EducationalMedium educationalMedium = new EducationalMedium();
        model.addAttribute("educationalMedium", educationalMedium);
        return "role/admin/educationalMedium/educationalMedium";
    }

    @RequestMapping(value = "/systemManagement/educationalMedium", method = RequestMethod.POST)
    public String educationalMedium(@ModelAttribute EducationalMedium educationalMedium) {
        educationalMediumMapper.insert(educationalMedium);
        return "redirect:/admin/systemManagement/educationalMedium?result=success";
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/educationalMediumTable", method = RequestMethod.GET)
    public String educationalMediumTable(Model model) {

        List<EducationalMedium> educationalMediumList = educationalMediumMapper.findAll();

        model.addAttribute("educationalMediumList", educationalMediumList);
        return "role/admin/educationalMedium/educationalMediumTable";
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/educationalMediumEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean educationalMediumEditable(@RequestParam int pk, @RequestParam String name,
            @RequestParam String value) {
        EducationalMedium educationalMedium = educationalMediumMapper.findOne(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(educationalMedium, name, value);
        }
        educationalMediumMapper.update(educationalMedium);
        return true;
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/deleteEducationalMedium", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteEducationalMedium(@RequestParam int id) {
        EducationalMedium educationalMedium = educationalMediumMapper.findOne(id);

        ProfLectureMethod stored = profLectureMethodMapper.findByEducationalMediumId(Integer.toString(id));

        if (stored == null) {
            educationalMediumMapper.delete(educationalMedium);
            return true;
        } else {
            educationalMedium.setEnabled(false);
            educationalMediumMapper.update(educationalMedium);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/enableEducationalMedium", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableEducationalMedium(@RequestParam int id) {
        EducationalMedium educationalMedium = educationalMediumMapper.findOne(id);
        educationalMedium.setEnabled(true);
        educationalMediumMapper.update(educationalMedium);
        return true;
    }

    @RequestMapping(value = "/systemManagement/equipment", method = RequestMethod.GET)
    public String equipment(Model model, @RequestParam(required = false) String result) {
        Equipment equipment = new Equipment();
        model.addAttribute("equipment", equipment);
        model.addAttribute("result", result);
        return "role/admin/equipment/equipment";
    }

    @RequestMapping(value = "/systemManagement/equipment", method = RequestMethod.POST)
    public String equipment(@ModelAttribute Equipment equipment) {
        equipmentMapper.insert(equipment);
        return "redirect:/admin/systemManagement/equipment?result=success";
    }

    @RequestMapping(value = "/systemManagement/equipment/equipmentTable", method = RequestMethod.GET)
    public String equipmentTable(Model model) {

        List<Equipment> equipmentList = equipmentMapper.findAll();

        model.addAttribute("equipmentList", equipmentList);
        return "role/admin/equipment/equipmentTable";
    }

    @RequestMapping(value = "/systemManagement/equipment/equipmentEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean equipmentEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Equipment equipment = equipmentMapper.findOne(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(equipment, name, value);
        }
        equipmentMapper.update(equipment);
        return true;
    }

    @RequestMapping(value = "/systemManagement/equipment/deleteEquipment", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteEquipment(@RequestParam int id) {
        Equipment equipment = equipmentMapper.findOne(id);

        ProfLectureMethod stored = profLectureMethodMapper.findByEquipmentId(Integer.toString(id));

        if (stored == null) {
            equipmentMapper.delete(equipment);
            return true;
        } else {
            equipment.setEnabled(false);
            equipmentMapper.update(equipment);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/equipment/enableEquipment", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableEquipment(@RequestParam int id) {
        Equipment equipment = equipmentMapper.findOne(id);
        equipment.setEnabled(true);
        equipmentMapper.update(equipment);
        return true;
    }

    @RequestMapping(value = "/systemManagement/classroom", method = RequestMethod.GET)
    public String classroom(Model model, @RequestParam(required = false) String result) {
        Classroom classroom = new Classroom();
        model.addAttribute("classroom", classroom);
        model.addAttribute("result", result);
        return "role/admin/classroom/classroom";
    }

    @RequestMapping(value = "/systemManagement/classroom", method = RequestMethod.POST)
    public String classroom(@ModelAttribute Classroom classroom) {
        classroomMapper.insert(classroom);
        return "redirect:/admin/systemManagement/classroom?result=success";
    }

    @RequestMapping(value = "/systemManagement/classroom/classroomTable", method = RequestMethod.GET)
    public String classroomTable(Model model) {

        List<Classroom> classroomList = classroomMapper.findAll();

        model.addAttribute("classroomList", classroomList);
        return "role/admin/classroom/classroomTable";
    }

    @RequestMapping(value = "/systemManagement/classroom/classroomEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean classroomEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        Classroom classroom = classroomMapper.findOne(pk);

        switch (name) {
            default:
                SystemUtil.setObjectFieldValue(classroom, name, value);
        }
        classroomMapper.update(classroom);
        return true;
    }

    @RequestMapping(value = "/systemManagement/classroom/deleteClassroom", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteClassroom(@RequestParam int id) {
        Classroom classroom = classroomMapper.findOne(id);

        List<ProfessorCourse> stored = professorCourseMapper.findByClassroomId(id);

        if (CollectionUtils.isEmpty(stored)) {
            classroomMapper.delete(classroom);
        } else {
            classroom.setEnabled(false);
            classroomMapper.update(classroom);
        }
        return true;
    }

    @RequestMapping(value = "/systemManagement/classroom/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean changeClassroomStatus(@RequestParam int id, @RequestParam boolean status) {
        Classroom classroom = classroomMapper.findOne(id);
        classroom.setEnabled(status);
        classroomMapper.update(classroom);
        return true;
    }

    @RequestMapping(value = "/systemManagement/menu", method = RequestMethod.GET)
    public String menu(Model model, @RequestParam(required = false) String result) {
        MenuAccess menuAccess = menuAccessMapper.findOne();
        if (menuAccess == null) {
            menuAccess = new MenuAccess();
            // Display-only — no insert in GET
        }

        model.addAttribute("menuAccess", menuAccess);
        model.addAttribute("result", result);
        return "role/admin/menu/menu";
    }

    @RequestMapping(value = "/systemManagement/menu", method = RequestMethod.POST)
    public String menu(@ModelAttribute MenuAccess menuAccess) {
        menuAccessMapper.update(menuAccess);
        return "redirect:/admin/systemManagement/menu?result=success";
    }

    @RequestMapping(value = "/systemManagement/addAdmin", method = RequestMethod.GET)
    public String addAdmin(Model model, @RequestParam(required = false) String result) {

        User adminUser = new User();
        Contact contact = new Contact();
        adminUser.setContact(contact);
        model.addAttribute("adminUser", adminUser);
        model.addAttribute("result", result);
        return "role/admin/addAdmin/addAdmin";
    }

    @RequestMapping(value = "/systemManagement/editAdmin", method = RequestMethod.GET)
    public String editAdmin(Model model, @RequestParam int id) {

        User adminUser = userMapper.findOne(id);
        model.addAttribute("adminUser", adminUser);
        return "role/admin/addAdmin/editAdmin";
    }

    @RequestMapping(value = "/systemManagement/addAdmin", method = RequestMethod.POST)
    public String addAdmin(@ModelAttribute("adminUser") User adminUser, SessionStatus sessionStatus) {

        adminUser.setEnabled(true);
        adminUser.setConfirm(true);
        userService.signupAdmin(adminUser);
        sessionStatus.setComplete();

        return "redirect:/admin/systemManagement/addAdmin?result=success";
    }

    @RequestMapping(value = "/systemManagement/deleteAdmin", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteAdmin(@RequestParam int id) {
        List<User> admins = userMapper.findAllAdmins();
        if (admins.size() > 1) {
            User adminUser = userMapper.findOne(id);
            userMapper.delete(adminUser);
            return true;
        } else
            return false;

    }

    @RequestMapping(value = "/systemManagement/editAdmin", method = RequestMethod.POST)
    public String editAdmin(@ModelAttribute("adminUser") User adminUser, SessionStatus sessionStatus) {
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        userMapper.updateFromSignup(adminUser);
        contactMapper.update(adminUser.getContact());
        sessionStatus.setComplete();

        return "redirect:/admin/systemManagement/addAdmin?result=success";
    }

    @RequestMapping(value = "/systemManagement/addAdmin/adminTable", method = RequestMethod.GET)
    public String adminTable(Model model) {
        List<User> adminList = userMapper.findAllAdmins();
        model.addAttribute("adminList", adminList);
        return "role/admin/addAdmin/adminTable";
    }

    @RequestMapping(value = "/systemManagement/errorReport", method = RequestMethod.GET)
    public String errorReport(Model model) {
        List<Feedback> feedbackList = feedbackMapper.findRecent();
        model.addAttribute("feedbackList", feedbackList);
        return "role/admin/errorReport/errorReport";
    }

    @RequestMapping(value = "/systemManagement/errorReport/deleteEr", method = RequestMethod.POST)
    @ResponseBody
    public String deleteEr(@RequestParam int id) {
        Feedback feedback = feedbackMapper.findOne(id);
        feedbackMapper.delete(feedback);
        return "success";
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteFile(@RequestParam int uploadedFileId) throws IOException {
        UploadedFile uploadedFile = uploadedFileMapper.findOne(uploadedFileId);
        if (uploadedFile != null) {
            File file = new File(uploadedFile.getPath());
            boolean delete = file.delete();
            uploadedFileMapper.delete(uploadedFile);
            return delete;
        }
        return false;
    }

    private List<Integer> getYearList() {
        return semesterMapper.findYears();
    }
}
