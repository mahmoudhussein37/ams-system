package koreatech.cse.controller.role;

import koreatech.cse.domain.*;
import koreatech.cse.domain.constant.CompCategory;
import koreatech.cse.domain.constant.Designation;
import koreatech.cse.domain.constant.AccountState;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.constant.StudentStatus;
import koreatech.cse.domain.constant.SubjCategory;
import koreatech.cse.domain.dto.AdminCreateRequest;
import koreatech.cse.domain.dto.AdminProfessorProfileUpdateDTO;
import koreatech.cse.domain.dto.AdminProfessorRegistrationRequest;
import koreatech.cse.domain.dto.AdminStudentProfileUpdateDTO;
import koreatech.cse.domain.dto.AdminStudentRegistrationRequest;
import koreatech.cse.domain.dto.AdminUpdateRequest;
import koreatech.cse.domain.dto.CurriculumRowDTO;
import koreatech.cse.domain.dto.MenuSettingsRequest;
import koreatech.cse.domain.dto.StudentUploadResult;
import koreatech.cse.domain.role.professor.*;
import koreatech.cse.domain.role.student.GraduationResearchPlan;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import koreatech.cse.service.AdminService;
import koreatech.cse.service.AuthorityService;
import koreatech.cse.service.CurriculumService;
import koreatech.cse.service.FileService;
import koreatech.cse.service.ProfService;
import koreatech.cse.service.UserService;
import koreatech.cse.util.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import java.util.Objects;
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
@SessionAttributes({ "studentUser", "profUser", "adminUser", "menuAccess" })
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Inject
    private UserService userService;
    @Inject
    private AuthorityService authorityService;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private FileService fileService;
    @Inject
    private ProfService profService;
    @Inject
    private CurriculumService curriculumService;
    @Inject
    private AdminService adminService;

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

    private User resolveAdvisor(int advisorId) {
        if (advisorId <= 0) {
            return null;
        }
        return adminService.findUserById(advisorId);
    }

    private void normalizeStudentAdvisorAssignment(User studentUser) {
        if (studentUser == null) {
            return;
        }

        int advisorId = studentUser.getAdvisorId();
        if (advisorId <= 0) {
            studentUser.setAdvisorId(0);
            return;
        }

        User advisor = resolveAdvisor(advisorId);
        if (advisor == null || adminService.findAuthorityByUserIdAndRole(advisorId, Role.professor.toCode()) == null) {
            logger.warn("Invalid advisor assignment normalized to unassigned: advisorId={}", advisorId);
            studentUser.setAdvisorId(0);
            return;
        }

        int studentDivisionId = studentUser.getDivisionId();
        int advisorDivisionId = advisor.getDivisionId();
        if (studentDivisionId > 0 && advisorDivisionId > 0 && studentDivisionId != advisorDivisionId) {
            logger.warn("Invalid cross-division advisor assignment normalized to unassigned: studentDivision={} advisorDivision={} advisorId={}",
                    studentDivisionId, advisorDivisionId, advisorId);
            studentUser.setAdvisorId(0);
        }
    }

    @RequestMapping(value = "/studentManagement/studentRegistration", method = RequestMethod.GET)
    public String studentRegistration(Model model, @RequestParam(required = false) String result) {

        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        User studentUser = new User();
        Contact contact = new Contact();
        studentUser.setContact(contact);
        studentUser.setAdvisorId(0);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("result", result);
        model.addAttribute("statusList", StudentStatus.values());

        List<User> professors = adminService.findAllProfessors();
        model.addAttribute("professors", professors);
        return "role/admin/studentRegistration/studentRegistration";
    }

    @RequestMapping(value = "/studentManagement/studentRegistration", method = RequestMethod.POST)
    public String basic(@ModelAttribute AdminStudentRegistrationRequest req, SessionStatus sessionStatus) {

        String studentNumber = StringUtils.trimToNull(req.getNumber());
        String firstName = StringUtils.trimToNull(req.getFirstName());
        String lastName = StringUtils.trimToNull(req.getLastName());

        if (StringUtils.isBlank(studentNumber) || StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
            logger.warn("Manual registration failed: missing required fields in registration request");
            sessionStatus.setComplete();
            return "redirect:/admin/studentManagement/studentRegistration?result=validation_error";
        }

        User studentUser = new User();
        studentUser.setNumber(studentNumber);
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        studentUser.setContact(contact);
        studentUser.setDivisionId(req.getDivisionId());
        studentUser.setAdvisorId(req.getAdvisorId());
        studentUser.setSchoolYear(req.getSchoolYear());
        studentUser.setStatus(req.getStatus());

        normalizeStudentAdvisorAssignment(studentUser);
        studentUser.setAccountState(AccountState.PENDING);
        boolean result = userService.register(studentUser, Role.student);
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/studentRegistration?result=" + (result ? "success" : "fail");
    }

    @RequestMapping(value = "/studentManagement/studentRegistration/importStudents", method = RequestMethod.POST)
    public String importStudents(HttpServletRequest request,
            @RequestParam("file") MultipartFile file,
            @RequestParam("divisionId") int divisionId,
            @RequestParam("schoolYear") int schoolYear,
            @SuppressWarnings("unused") Model model) {
        // Suppress CodeQL unused-parameter: required by framework contract
        Objects.toString(model); // no-op reference

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
            logger.warn("Import failed: invalid file extension received");
            return "redirect:/admin/studentManagement/studentRegistration?importResult=error&errorMessage=invalid_format";
        }

        try {
            // Save file to temp directory via FileService (sanitized + canonical path
            // validated)
            File convFile = fileService.saveMultipartToTempFile(file, request);
            logger.debug("Import file saved to temp directory successfully");

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
            // Complete taint break: extract path string, validate, create new File from string only
            String rawPath = file.getAbsolutePath();
            String allowedBase = new File(
                System.getProperty("java.io.tmpdir") + File.separator + "ams_temp"
            ).getCanonicalPath();

            File resolvedFile = new File(rawPath).getCanonicalFile();
            String resolvedPath = resolvedFile.getCanonicalPath();

            if (!resolvedPath.startsWith(allowedBase + File.separator)
                    && !resolvedPath.equals(allowedBase)) {
                throw new SecurityException(
                    "Path validation failed: resolved path escapes temp directory");
            }

            // Create FileInputStream from path string only — breaks CodeQL taint chain
            fileInputStream = new FileInputStream(resolvedPath);
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
                    User existingUser = adminService.findUserByNumber(studentNumber);
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
                    newStudent.setAccountState(AccountState.PENDING); // Pending - not activated
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
                    boolean registered = userService.register(newStudent, Role.student);
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
        String normalizedAccountStatus = StringUtils.lowerCase(StringUtils.trimToNull(accountStatus));
        if (normalizedAccountStatus != null
                && !"active".equals(normalizedAccountStatus)
                && !"pending".equals(normalizedAccountStatus)
                && !"disabled".equals(normalizedAccountStatus)) {
            normalizedAccountStatus = null;
        }

        User firstUser = null;
        List<User> userList;
        if (StringUtils.isBlank(number) && StringUtils.isBlank(name) && division == 0
                && schoolYear == 0 && StringUtils.isBlank(normalizedAccountStatus)) {
            userList = new ArrayList<>();
        } else {
            Searchable searchable = new Searchable();
            searchable.setNumber(number);
            searchable.setName(name);
            searchable.setDivision(division);
            searchable.setSchoolYear(schoolYear);
            searchable.setAccountStatus(normalizedAccountStatus);
            userList = adminService.findStudentBy(searchable);

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
        User studentUser = adminService.findUserById(studentId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);

        User advisor = resolveAdvisor(studentUser.getAdvisorId());
        studentUser.setAdvisor(advisor);
        model.addAttribute("advisor", advisor);
        List<User> professors = adminService.findAllProfessors();
        model.addAttribute("professors", professors);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = adminService.findGraduationCriteriaByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);
        List<StudentCourse> studentCourses = adminService.findStudentCoursesByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);

        List<LangCert> langCerts = adminService.findLangCertsByUserId(studentId);
        model.addAttribute("langCerts", langCerts);
        return "role/admin/studentInformation/studentDetail";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/studentDetailForPrint", method = RequestMethod.GET)
    public String studentDetailForPrint(Model model, @RequestParam int studentId) {
        User studentUser = adminService.findUserById(studentId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);

        User advisor = resolveAdvisor(studentUser.getAdvisorId());
        studentUser.setAdvisor(advisor);
        model.addAttribute("advisor", advisor);
        List<User> professors = adminService.findAllProfessors();
        model.addAttribute("professors", professors);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = adminService.findGraduationCriteriaByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);
        List<StudentCourse> studentCourses = adminService.findStudentCoursesByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);

        List<LangCert> langCerts = adminService.findLangCertsByUserId(studentId);
        model.addAttribute("langCerts", langCerts);
        return "role/admin/studentInformation/studentDetailForPrint";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/studentDetail", method = RequestMethod.POST)
    public String studentDetail(@ModelAttribute AdminStudentProfileUpdateDTO req, SessionStatus sessionStatus,
            HttpServletRequest request) throws IOException {
        User studentUser = adminService.findUserById(req.getId());
        if (studentUser == null) {
            sessionStatus.setComplete();
            return "redirect:/admin/studentManagement/studentInformation?result=fail";
        }

        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile f = ((MultipartHttpServletRequest) request).getFile("file");
            if (f != null && f.getSize() > 0) {
                String fileName = f.getOriginalFilename();
                String ext = fileService.getExtension(fileName);
                if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")
                        || ext.equalsIgnoreCase("gif")) {
                    adminService.deleteProfileByUser(studentUser);
                    DateTime dt = new DateTime();
                    fileService.processUploadedFile(f, studentUser, Designation.profile, 0, 0, dt.getYear());
                }
            }
        }

        studentUser.setDivisionId(req.getDivisionId());
        studentUser.setAdvisorId(req.getAdvisorId());
        studentUser.setSchoolYear(req.getSchoolYear());
        studentUser.setStatus(req.getStatus());

        if (req.getContact() != null && studentUser.getContact() != null) {
            Contact c = studentUser.getContact();
            c.setFirstName(req.getContact().getFirstName());
            c.setLastName(req.getContact().getLastName());
            c.setGradYear(req.getContact().getGradYear());
            c.setGradSemester(req.getContact().getGradSemester());
            c.setGradDate(req.getContact().getGradDate());
            c.setGradDegree(req.getContact().getGradDegree());
            c.setDegreeNumber(req.getContact().getDegreeNumber());
            c.setCertNumber(req.getContact().getCertNumber());
        }

        normalizeStudentAdvisorAssignment(studentUser);
        adminService.updateUser(studentUser);
        adminService.updateUserFromSignup(studentUser);
        adminService.updateContact(studentUser.getContact());
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/studentInformation?result=success";
    }

    @RequestMapping(value = "/studentManagement/studentInformation", method = RequestMethod.GET)
    public String studentInformation(Model model, @RequestParam(required = false) String result) {
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        model.addAttribute("result", result);

        return "role/admin/studentInformation/studentInformation";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/addLangCert", method = RequestMethod.GET)
    public String addLangCert(Model model, @RequestParam int studentId) {
        User studentUser = adminService.findUserById(studentId);
        model.addAttribute("studentUser", studentUser);
        User advisor = resolveAdvisor(studentUser.getAdvisorId());
        studentUser.setAdvisor(advisor);
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
        adminService.insertLangCert(langCert);
        sessionStatus.setComplete();

        return "redirect:/admin/studentManagement/studentInformation";
    }

    @RequestMapping(value = "/studentManagement/studentInformation/deleteLangCert", method = RequestMethod.POST)
    public String deleteLangCert(@RequestParam int langCertId) {
        LangCert langCert = adminService.findLangCertById(langCertId);
        adminService.deleteLangCert(langCert);
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
            User student = adminService.findUserById(studentId);
            if (student == null) {
                logger.warn("Delete failed: student not found with id={}", studentId);
                response.put("success", false);
                response.put("message", "Student not found");
                return response;
            }

            // Check for related records in student_course
            List<koreatech.cse.domain.role.student.StudentCourse> courseRecords = adminService.findStudentCoursesByUserId(studentId);
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
            adminService.deleteUser(student);

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

        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        List<User> professors = adminService.findAllProfessors();
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
            userList = adminService.findStudentBy(searchable);

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
        User studentUser = adminService.findUserById(studentId);
        model.addAttribute("studentUser", studentUser);
        User advisor = resolveAdvisor(studentUser.getAdvisorId());
        studentUser.setAdvisor(advisor);
        model.addAttribute("advisor", advisor);
        int admissionYear = studentUser.getContact().getAdmissionYear();
        int divisionId = studentUser.getDivisionId();

        GraduationCriteria graduationCriteria = adminService.findGraduationCriteriaByYearDivision(admissionYear,
                divisionId);
        studentUser.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);
        List<StudentCourse> studentCourses = adminService.findStudentCoursesByUserIdValid(studentId);
        studentUser.setStudentCourses(studentCourses);

        List<LangCert> langCerts = adminService.findLangCertsByUserId(studentId);
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
            studentList = adminService.findStudentBy(searchable);

        } else {
            params.entrySet().stream().filter(entry -> entry.getKey().equals("tableCheckbox")).forEach(entry -> {
                String value = entry.getValue();
                String[] split = value.split(",");
                for (String userIdString : split) {
                    if (StringUtils.isNotBlank(userIdString)) {
                        try {
                            integerIds.add(Integer.parseInt(userIdString));
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid non-numeric student ID in checkbox selection");
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                studentList = new ArrayList<>();
            else
                studentList = adminService.findUsersByIds(integerIds);
        }

        for (User u : studentList) {
            User advisorUser = resolveAdvisor(u.getAdvisorId());
            u.setAdvisor(advisorUser);

            int admissionYear = u.getContact().getAdmissionYear();
            int divisionId = u.getDivisionId();

            GraduationCriteria graduationCriteria = adminService.findGraduationCriteriaByYearDivision(admissionYear,
                    divisionId);
            u.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);

            List<StudentCourse> studentCourses = adminService.findStudentCoursesByUserIdValid(u.getId());
            u.setStudentCourses(studentCourses);
        }

        model.addAttribute("studentList", studentList);
        return "role/admin/studentProfile/studentDetailForPrint";
    }

    @RequestMapping(value = "/studentManagement/schoolYear", method = RequestMethod.GET)
    public String schoolYear(Model model, @RequestParam(required = false) String result) {

        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        List<User> professors = adminService.findAllProfessors();
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
            userList = adminService.findStudentBy(searchable);

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
        User studentUser = adminService.findUserById(studentId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);

        User advisor = resolveAdvisor(studentUser.getAdvisorId());
        studentUser.setAdvisor(advisor);
        model.addAttribute("advisor", advisor);
        List<User> professors = adminService.findAllProfessors();
        model.addAttribute("professors", professors);
        return "role/admin/schoolYear/studentDetail";
    }

    @RequestMapping(value = "/studentManagement/schoolYear/studentDetail", method = RequestMethod.POST)
    public String schoolYearStudentDetail(@ModelAttribute AdminStudentProfileUpdateDTO req,
            SessionStatus sessionStatus) {
        User studentUser = adminService.findUserById(req.getId());
        if (studentUser == null) {
            sessionStatus.setComplete();
            return "redirect:/admin/studentManagement/schoolYear?result=fail";
        }

        studentUser.setDivisionId(req.getDivisionId());
        studentUser.setAdvisorId(req.getAdvisorId());
        studentUser.setSchoolYear(req.getSchoolYear());
        studentUser.setStatus(req.getStatus());

        if (req.getContact() != null && studentUser.getContact() != null) {
            Contact c = studentUser.getContact();
            c.setFirstName(req.getContact().getFirstName());
            c.setLastName(req.getContact().getLastName());
            c.setGradYear(req.getContact().getGradYear());
            c.setGradSemester(req.getContact().getGradSemester());
            c.setGradDate(req.getContact().getGradDate());
            c.setGradDegree(req.getContact().getGradDegree());
            c.setDegreeNumber(req.getContact().getDegreeNumber());
            c.setCertNumber(req.getContact().getCertNumber());
        }

        normalizeStudentAdvisorAssignment(studentUser);
        adminService.updateUser(studentUser);
        adminService.updateUserFromSignup(studentUser);
        adminService.updateContact(studentUser.getContact());
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
        List<Integer> integerIds = new ArrayList<>();
        List<User> studentList;
        if (checkAll) {
            Searchable searchable = new Searchable();
            searchable.setAdvisor(advisor);
            searchable.setSchoolYear(schoolYear);
            searchable.setDivision(division);
            studentList = adminService.findStudentBy(searchable);

        } else {
            params.entrySet().stream().filter(entry -> entry.getKey().equals("tableCheckbox")).forEach(entry -> {
                String value = entry.getValue();
                String[] split = value.split(",");
                for (String userIdString : split) {
                    if (StringUtils.isNotBlank(userIdString)) {
                        try {
                            integerIds.add(Integer.parseInt(userIdString));
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid non-numeric student ID in checkbox selection");
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                studentList = new ArrayList<>();
            else
                studentList = adminService.findUsersByIds(integerIds);
        }

        for (User u : studentList) {
            u.setSchoolYear(u.getSchoolYear() + 1);
            adminService.updateUser(u);
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
            studentList = adminService.findStudentBy(searchable);

        } else {
            params.entrySet().stream().filter(entry -> entry.getKey().equals("tableCheckbox")).forEach(entry -> {
                String value = entry.getValue();
                String[] split = value.split(",");
                for (String userIdString : split) {
                    if (StringUtils.isNotBlank(userIdString)) {
                        try {
                            integerIds.add(Integer.parseInt(userIdString));
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid non-numeric student ID in checkbox selection");
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                studentList = new ArrayList<>();
            else
                studentList = adminService.findUsersByIds(integerIds);
        }

        for (User u : studentList) {
            User advisorUser = resolveAdvisor(u.getAdvisorId());
            u.setAdvisor(advisorUser);

            int admissionYear = u.getContact().getAdmissionYear();
            int divisionId = u.getDivisionId();

            GraduationCriteria graduationCriteria = adminService.findGraduationCriteriaByYearDivision(admissionYear,
                    divisionId);
            u.setGraduationCriteria(graduationCriteria == null ? new GraduationCriteria() : graduationCriteria);

            List<StudentCourse> studentCourses = adminService.findStudentCoursesByUserIdValid(u.getId());
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
            counselingList = adminService.findCounselingBy(searchable);

            for (Counseling counseling : counselingList) {
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
        Counseling counseling = adminService.findCounselingById(counselingId);
        model.addAttribute("counseling", counseling);
        return "role/admin/studentCounseling/counselingDetail";
    }

    @RequestMapping(value = "/studentManagement/studentCounseling/counselingDetailForPrint", method = RequestMethod.GET)
    public String counselingDetailForPrint(Model model,
            @RequestParam boolean checkAll,
            @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(required = false) String name, @RequestParam Map<String, String> params) {
        List<Counseling> counselingList;

        if (checkAll) {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setName(name);
            counselingList = adminService.findCounselingBy(searchable);
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
                            logger.warn("Invalid non-numeric counseling ID in checkbox selection");
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                counselingList = new ArrayList<>();
            else
                counselingList = adminService.findCounselingsByIds(integerIds);
        }
        model.addAttribute("counselingList", counselingList);
        return "role/admin/studentCounseling/counselingDetailForPrint";
    }

    @RequestMapping(value = "/studentManagement/inquiryGrade", method = RequestMethod.GET)
    public String inquiryGrade(Model model) {
        List<Division> divisions = adminService.findAllDivisions();

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
            userList = adminService.findStudentBy(searchable);

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
        User studentUser = adminService.findUserById(studentId);
        model.addAttribute("studentUser", studentUser);
        Semester firstSemester = null;
        LinkedHashSet<Integer> semesterSet = adminService.findSemesterIdsByUserIdValid(studentUser.getId());
        for (Integer semesterId : semesterSet) {
            firstSemester = adminService.findSemesterById(semesterId);
            break;
        }
        model.addAttribute("firstSemester", firstSemester);
        Map<Semester, List<StudentCourse>> map = new HashMap<>();
        for (Integer semesterId : semesterSet) {
            Semester semester = adminService.findSemesterById(semesterId);
            Searchable searchable = new Searchable();
            searchable.setYear(semester.getYear());
            searchable.setSemester(semester.getSemester());
            searchable.setUserId(studentUser.getId());
            List<StudentCourse> courses = adminService.findStudentCoursesByUserIdYearSemester(searchable);
            map.put(semester, courses);
        }

        model.addAttribute("courseMap", map);
        return "role/admin/inquiryGrade/studentDetail";
    }

    @RequestMapping(value = "/studentManagement/inquiryGrade/gradeDetail", method = RequestMethod.GET)
    public String inquiryGradeDetail(Model model, @RequestParam int studentId, @RequestParam int semesterId) {
        User studentUser = adminService.findUserById(studentId);

        List<StudentCourse> studentCourses = adminService.findStudentCoursesByUserIdSemesterIdValid(studentUser.getId(),
                semesterId);
        model.addAttribute("studentUser", studentUser);
        model.addAttribute("studentCourses", studentCourses);
        return "role/admin/inquiryGrade/gradeDetail";
    }

    @RequestMapping(value = "/studentManagement/inquiryGrade/gradeDetailForPrint", method = RequestMethod.GET)
    public String inquiryGradeDetailPrint(Model model, @RequestParam int studentId) {
        User studentUser = adminService.findUserById(studentId);
        model.addAttribute("studentUser", studentUser);

        Certificate certificate = adminService.findCertificateByUserId(studentId);
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
        List<Division> divisions = adminService.findAllDivisions();
        model.addAttribute("divisions", divisions);
        User profUser = new User();
        Contact contact = new Contact();
        profUser.setContact(contact);
        model.addAttribute("profUser", profUser);
        model.addAttribute("result", result);
        return "role/admin/profRegistration/profRegistration";
    }

    @RequestMapping(value = "/profManagement/profRegistration", method = RequestMethod.POST)
    public String profRegistration(@ModelAttribute AdminProfessorRegistrationRequest req, SessionStatus sessionStatus) {
        String firstName = StringUtils.trimToNull(req.getFirstName());
        String lastName = StringUtils.trimToNull(req.getLastName());
        String normalizedProfessorNumber = StringUtils.trimToNull(req.getNumber());

        if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)
                || normalizedProfessorNumber == null || req.getDivisionId() <= 0) {
            sessionStatus.setComplete();
            return "redirect:/admin/profManagement/profRegistration?result=validation_error";
        }

        if (adminService.findUserByNumber(normalizedProfessorNumber) != null) {
            logger.warn("Professor registration rejected: duplicated professor number={}", normalizedProfessorNumber);
            sessionStatus.setComplete();
            return "redirect:/admin/profManagement/profRegistration?result=duplicate_professor";
        }

        User profUser = new User();
        profUser.setNumber(normalizedProfessorNumber);
        profUser.setDivisionId(req.getDivisionId());
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        profUser.setContact(contact);

        profUser.setAccountState(AccountState.PENDING);
        boolean result = userService.register(profUser, Role.professor);
        sessionStatus.setComplete();
        if (!result && adminService.findUserByNumber(normalizedProfessorNumber) != null) {
            return "redirect:/admin/profManagement/profRegistration?result=duplicate_professor";
        }
        return "redirect:/admin/profManagement/profRegistration?result=" + (result ? "success" : "fail");
    }

    @RequestMapping(value = "/profManagement/profInformation", method = RequestMethod.GET)
    public String profInformation(Model model, @RequestParam(required = false) String result) {

        List<Division> divisions = adminService.findAllDivisions();

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
            userList = adminService.findProfessorBy(searchable);

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
        User profUser = adminService.findUserById(profId);
        model.addAttribute("profUser", profUser);
        model.addAttribute("statusList", StudentStatus.values());
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);

        return "role/admin/profInformation/profDetail";
    }

    @RequestMapping(value = "/profManagement/profInformation/profDetail", method = RequestMethod.POST)
    public String profDetail(@ModelAttribute AdminProfessorProfileUpdateDTO req, SessionStatus sessionStatus) {
        User profUser = adminService.findUserById(req.getId());
        if (profUser == null) {
            sessionStatus.setComplete();
            return "redirect:/admin/profManagement/profInformation?result=fail";
        }

        profUser.setDivisionId(req.getDivisionId());
        if (req.isEnabled()) {
            profUser.setAccountState(AccountState.ACTIVE);
        } else {
            AccountState currentState = profUser.getAccountState();
            profUser.setAccountState(currentState == AccountState.PENDING ? AccountState.PENDING : AccountState.DISABLED);
        }

        if (req.getContact() != null && profUser.getContact() != null) {
            profUser.getContact().setFirstName(req.getContact().getFirstName());
            profUser.getContact().setLastName(req.getContact().getLastName());
        }

        adminService.updateUser(profUser);
        adminService.updateUserFromSignup(profUser);
        adminService.updateContact(profUser.getContact());
        sessionStatus.setComplete();

        return "redirect:/admin/profManagement/profInformation?result=success";
    }

    @RequestMapping(value = "/profManagement/graduationResearch", method = RequestMethod.GET)
    public String graduationResearch(Model model) {

        model.addAttribute("yearList", getYearList());
        List<User> professors = adminService.findAllProfessors();
        model.addAttribute("professors", professors);
        List<Division> divisions = adminService.findAllDivisions();

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
            plans = adminService.findGraduationResearchPlansBySearchable(searchable);

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
        GraduationResearchPlan graduationResearchPlan = adminService.findGraduationResearchPlanById(planId);
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
        List<Integer> integerIds = new ArrayList<>();
        List<GraduationResearchPlan> planList = null;
        if (checkAll) {
            Searchable searchable = new Searchable();
            searchable.setYear(year);
            searchable.setDivision(division);
            searchable.setAdvisor(advisor);
            searchable.setNumber(number);
            searchable.setName(name);
            planList = adminService.findGraduationResearchPlansBySearchable(searchable);
        } else {
            params.entrySet().stream().filter(entry -> entry.getKey().equals("tableCheckbox")).forEach(entry -> {
                String value = entry.getValue();
                String[] split = value.split(",");
                for (String userIdString : split) {
                    if (StringUtils.isNotBlank(userIdString)) {
                        try {
                            integerIds.add(Integer.parseInt(userIdString));
                        } catch (NumberFormatException e) {
                            logger.warn("Invalid non-numeric plan ID in checkbox selection");
                        }
                    }
                }
            });
            if (CollectionUtils.isEmpty(integerIds))
                planList = new ArrayList<>();
            else
                planList = adminService.findGraduationResearchPlansByIds(integerIds);
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

        List<Division> divisions = adminService.findAllDivisions();
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
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        model.addAttribute("course", new Course());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        return "role/admin/course/course";
    }

    @RequestMapping(value = "/courseManagement/course", method = RequestMethod.POST)
    public String course(@ModelAttribute("course") Course course, SessionStatus sessionStatus) {

        adminService.insertCourse(course);

        sessionStatus.setComplete();

        return "redirect:/admin/courseManagement/course?result=success";
    }

    @RequestMapping(value = "/courseManagement/course/editCourse", method = RequestMethod.GET)
    public String editCourse(Model model, @RequestParam int id) {
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        model.addAttribute("course", new Course());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        Course course = adminService.findCourseById(id);
        model.addAttribute("course", course);
        return "role/admin/course/editCourse";
    }

    @RequestMapping(value = "/courseManagement/course/editCourse", method = RequestMethod.POST)
    public String editCourse(@ModelAttribute("course") Course courseForm, SessionStatus sessionStatus) {
        Course course = adminService.findCourseById(courseForm.getId());
        course.setCode(courseForm.getCode());
        course.setTitle(courseForm.getTitle());
        course.setSubjCategory(courseForm.getSubjCategory());
        course.setDivisionId(courseForm.getDivisionId());
        course.setOverview(courseForm.getOverview());
        course.setLearningObjective(courseForm.getLearningObjective());
        course.setCredit(courseForm.getCredit());
        course.setLec(courseForm.getLec());
        course.setTut(courseForm.getTut());
        course.setLab(courseForm.getLab());
        course.setWs(courseForm.getWs());
        course.setSchoolYear(courseForm.getSchoolYear());
        course.setAchieve1(courseForm.isAchieve1());
        course.setAchieve2(courseForm.isAchieve2());
        course.setAchieve3(courseForm.isAchieve3());
        course.setAchieve4(courseForm.isAchieve4());
        course.setAchieve5(courseForm.isAchieve5());
        course.setAchieve6(courseForm.isAchieve6());
        course.setAchieve7(courseForm.isAchieve7());
        course.setAchieve8(courseForm.isAchieve8());
        course.setAchieve9(courseForm.isAchieve9());
        adminService.updateCourse(course);
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

            courseList = adminService.findCoursesBy(searchable);
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
        switch (name) {
            case "title":             adminService.updateCourseTitle(pk, value);                                    break;
            case "code":              adminService.updateCourseCode(pk, value);                                     break;
            case "subjCategory":      adminService.updateCourseSubjCategory(pk, value);                             break;
            case "overview":          adminService.updateCourseOverview(pk, value);                                 break;
            case "learningObjective": adminService.updateCourseLearningObjective(pk, value);                        break;
            case "credit":            adminService.updateCourseCredit(pk, Integer.parseInt(value));                 break;
            case "lec":               adminService.updateCourseLec(pk, Integer.parseInt(value));                    break;
            case "tut":               adminService.updateCourseTut(pk, Integer.parseInt(value));                    break;
            case "lab":               adminService.updateCourseLab(pk, Integer.parseInt(value));                    break;
            case "ws":                adminService.updateCourseWs(pk, Integer.parseInt(value));                     break;
            case "schoolYear":        adminService.updateCourseSchoolYear(pk, Integer.parseInt(value));             break;
            case "achieve1":          adminService.updateCourseAchieve(pk, 1,  Boolean.parseBoolean(value));        break;
            case "achieve2":          adminService.updateCourseAchieve(pk, 2,  Boolean.parseBoolean(value));        break;
            case "achieve3":          adminService.updateCourseAchieve(pk, 3,  Boolean.parseBoolean(value));        break;
            case "achieve4":          adminService.updateCourseAchieve(pk, 4,  Boolean.parseBoolean(value));        break;
            case "achieve5":          adminService.updateCourseAchieve(pk, 5,  Boolean.parseBoolean(value));        break;
            case "achieve6":          adminService.updateCourseAchieve(pk, 6,  Boolean.parseBoolean(value));        break;
            case "achieve7":          adminService.updateCourseAchieve(pk, 7,  Boolean.parseBoolean(value));        break;
            case "achieve8":          adminService.updateCourseAchieve(pk, 8,  Boolean.parseBoolean(value));        break;
            case "achieve9":          adminService.updateCourseAchieve(pk, 9,  Boolean.parseBoolean(value));        break;
            case "achieve10":         adminService.updateCourseAchieve(pk, 10, Boolean.parseBoolean(value));        break;
            default: throw new IllegalArgumentException("Unknown course field: " + name);
        }
        return true;
    }

    @RequestMapping(value = "/courseManagement/course/deleteCourse", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteCourse(@RequestParam int id) {
        Course course = adminService.findCourseById(id);

        int num = adminService.countProfessorCoursesByCourseId(course.getId());
        if (num == 0) {
            adminService.deleteCourse(course);
            return true;
        } else {
            course.setEnabled(false);
            adminService.updateCourse(course);
            return false;
        }
    }

    @RequestMapping(value = "/courseManagement/course/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean changeCourseStatus(@RequestParam int id, @RequestParam boolean status) {
        Course course = adminService.findCourseById(id);
        course.setEnabled(status);
        adminService.updateCourse(course);
        return true;
    }

    @RequestMapping(value = "/courseManagement/alternative", method = RequestMethod.GET)
    public String alternative(Model model, @RequestParam(required = false) String result) {

        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        model.addAttribute("subjCategoryList", SubjCategory.values());
        model.addAttribute("result", result);
        return "role/admin/alternative/alternative";
    }

    @RequestMapping(value = "/courseManagement/alternative/manageCourse", method = RequestMethod.GET)
    public String manageCourse(Model model, @RequestParam int id) {
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        model.addAttribute("subjCategoryList", SubjCategory.values());
        Course course = adminService.findCourseById(id);
        model.addAttribute("course", course);

        List<AltCourse> altCourses = adminService.findAltCoursesByTargetCourseId(id);
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

            courseList = adminService.findCoursesBy(searchable);

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

            courseList = adminService.findCoursesBy(searchable);

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

        AltCourse stored = adminService.findAltCourseByCourseIdTargetCourseIdType(id, targetCourseId, type);
        if (stored == null && id != targetCourseId) {
            AltCourse altCourse = new AltCourse();
            altCourse.setCourseId(id);
            altCourse.setTargetCourseId(targetCourseId);
            altCourse.setType(type);
            adminService.insertAltCourse(altCourse);
        }

        return true;
    }

    @RequestMapping(value = "/courseManagement/alternative/deleteAlt", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteAlt(@RequestParam int id) {

        AltCourse stored = adminService.findAltCourseById(id);
        adminService.deleteAltCourse(stored);

        return true;
    }

    @RequestMapping(value = "/courseManagement/cOpen", method = RequestMethod.GET)
    public String cOpen(Model model, @RequestParam(required = false) String result,
            @RequestParam(defaultValue = "0", required = false) @SuppressWarnings("unused") int year,
            @RequestParam(defaultValue = "0", required = false) @SuppressWarnings("unused") int semester,
            @RequestParam(defaultValue = "0", required = false) @SuppressWarnings("unused") int division) {
        // Suppress CodeQL unused-parameter: required by framework contract
        Objects.toString(year); Objects.toString(semester); Objects.toString(division); // no-op references
        List<Division> divisions = adminService.findAllDivisions();

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

            courseList = adminService.findCoursesBy(searchable);
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
        ProfessorCourse profCourse = adminService.findProfessorCourseById(profCourseId);
        Course course = profCourse.getCourse();
        model.addAttribute("course", course);
        model.addAttribute("profCourse", profCourse);
        List<ClassTime> classTimes = adminService.findClassTimesByProfCourseId(profCourseId);
        model.addAttribute("classTimes", classTimes);
        model.addAttribute("classTime", new ClassTime());
        model.addAttribute("result", result);
        return "role/admin/cOpen/manageTime";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageTime", method = RequestMethod.POST)
    public String manageTime(@ModelAttribute ClassTime classTime,
            @RequestParam int profCourseId) {
        if (classTime.getE() > classTime.getS())
            adminService.insertClassTime(classTime);
        return "redirect:/admin/courseManagement/cOpen/manageTime?profCourseId=" + profCourseId + "&result=success";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageTime/deleteTime", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteTime(@RequestParam int id) {
        ClassTime classTime = adminService.findClassTimeById(id);
        adminService.deleteClassTime(classTime);
        return true;
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageDivide", method = RequestMethod.GET)
    public String manageDivide(Model model, @RequestParam int courseId, @RequestParam(required = false) String result) {
        Course course = adminService.findCourseById(courseId);
        model.addAttribute("course", course);
        List<Division> divisions = adminService.findAllDivisions();
        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        List<Semester> semesters = adminService.findAllSemesters();
        model.addAttribute("semesters", semesters);
        List<User> professors = adminService.findProfessorsByDivision(course.getDivisionId());
        model.addAttribute("professors", professors);
        List<ProfessorCourse> professorCourseList = adminService.findProfessorCoursesByCourseId(courseId);
        model.addAttribute("professorCourseList", professorCourseList);
        model.addAttribute("profCourse", new ProfessorCourse());
        List<Classroom> classroomList = adminService.findAllEnabledClassrooms();
        model.addAttribute("classroomList", classroomList);
        model.addAttribute("result", result);
        return "role/admin/cOpen/manageDivide";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageDivide", method = RequestMethod.POST)
    public String manageDivide(@ModelAttribute ProfessorCourse professorCourse,
            @RequestParam int courseId) {
        adminService.insertProfessorCourse(professorCourse);
        return "redirect:/admin/courseManagement/cOpen/manageDivide?courseId=" + courseId + "&result=success";
    }

    @RequestMapping(value = "/courseManagement/cOpen/editDivide", method = RequestMethod.GET)
    public String editDivide(Model model, @RequestParam int profCourseId,
            @RequestParam(required = false) String result) {
        ProfessorCourse professorCourse = adminService.findProfessorCourseById(profCourseId);
        model.addAttribute("profCourse", professorCourse);
        List<Division> divisions = adminService.findAllDivisions();
        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        model.addAttribute("subjCategoryList", SubjCategory.values());
        List<Semester> semesters = adminService.findAllSemesters();
        model.addAttribute("semesters", semesters);
        List<User> professors = adminService.findProfessorsByDivision(professorCourse.getCourse().getDivisionId());
        model.addAttribute("professors", professors);
        List<Classroom> classroomList = adminService.findAllEnabledClassrooms();
        model.addAttribute("classroomList", classroomList);
        model.addAttribute("result", result);
        return "role/admin/cOpen/editDivide";
    }

    @RequestMapping(value = "/courseManagement/cOpen/editDivide", method = RequestMethod.POST)
    public String editDivide(@ModelAttribute("profCourse") ProfessorCourse profCourseForm, SessionStatus sessionStatus,
            @RequestParam int profCourseId) {
        ProfessorCourse profCourse = adminService.findProfessorCourseById(profCourseId);
        profCourse.setSemesterId(profCourseForm.getSemesterId());
        profCourse.setDivide(profCourseForm.getDivide());
        profCourse.setUserId(profCourseForm.getUserId());
        profCourse.setSchoolYear(profCourseForm.getSchoolYear());
        profCourse.setLimitStudent(profCourseForm.getLimitStudent());
        profCourse.setClassroom(profCourseForm.getClassroom());
        profCourse.setLanguage(profCourseForm.getLanguage());
        profCourse.setEngAccreditation(profCourseForm.isEngAccreditation());
        adminService.updateProfessorCourse(profCourse);
        sessionStatus.setComplete();
        return "redirect:/admin/courseManagement/cOpen/manageDivide?courseId=" + profCourse.getCourseId()
                + "&result=success";
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageDivide/deleteDivide", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteDivide(@RequestParam int id) {
        ProfessorCourse professorCourse = adminService.findProfessorCourseById(id);

        int num = adminService.countStudentCoursesByProfCourseId(professorCourse.getId());
        if (num == 0) {
            adminService.deleteProfessorCourse(professorCourse);
            return true;
        } else {
            professorCourse.setEnabled(false);
            adminService.updateProfessorCourse(professorCourse);
            return false;
        }
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageDivide/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean changeDivideStatus(@RequestParam int id, @RequestParam boolean status) {
        ProfessorCourse professorCourse = adminService.findProfessorCourseById(id);
        professorCourse.setEnabled(status);
        adminService.updateProfessorCourse(professorCourse);
        return true;
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageStudent", method = RequestMethod.GET)
    public String manageStudent(Model model, @RequestParam int profCourseId,
            @RequestParam(required = false) String result) {
        ProfessorCourse professorCourse = adminService.findProfessorCourseById(profCourseId);
        model.addAttribute("profCourse", professorCourse);
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        model.addAttribute("result", result);
        model.addAttribute("profCourseId", profCourseId);

        UploadedFile uploadedFile = new UploadedFile();
        model.addAttribute("uploadedFile", uploadedFile);

        List<StudentCourse> studentCourseList = adminService.findStudentCoursesByProfCourseId(profCourseId);
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
            ProfessorCourse pc = adminService.findProfessorCourseById(profCourseId);
            List<Integer> userIds = adminService.findStudentUserIdsByCourseId(pc.getCourseId());
            searchable.setUserIds(userIds);
            userList = adminService.findStudentsByAdvisorSchoolYearDivisionExceptRegistered(userIds, number, name,
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
        ProfessorCourse professorCourse = adminService.findProfessorCourseById(profCourseId);
        studentCourse.setCourseId(professorCourse.getCourseId());
        studentCourse.setProfCourseId(professorCourse.getId());
        studentCourse.setUserId(id);
        adminService.insertStudentCourse(studentCourse);
        int num = adminService.countStudentCoursesByProfCourseId(profCourseId);
        professorCourse.setNumStudent(num);
        adminService.updateProfessorCourse(professorCourse);

        return true;
    }

    @RequestMapping(value = "/courseManagement/cOpen/manageStudent/removeFromDivide", method = RequestMethod.POST)
    @ResponseBody
    public Boolean removeFromDivide(@RequestParam int id, @RequestParam int profCourseId) {
        ProfessorCourse professorCourse = adminService.findProfessorCourseById(profCourseId);
        StudentCourse studentCourse = adminService.findStudentCourseByUserIdProfCourseId(id, profCourseId);
        adminService.deleteStudentCourse(studentCourse);
        int num = adminService.countStudentCoursesByProfCourseId(profCourseId);
        professorCourse.setNumStudent(num);
        adminService.updateProfessorCourse(professorCourse);

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
            List<User> eligibleUsers = adminService.findEligibleStudentsForCourseRegistration(
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
                AccountState accountState = user.getAccountState();
                studentData.put("accountState", accountState.name());
                studentData.put("enabled", accountState == AccountState.ACTIVE ? 1 : 0); // 1=Active, 0=Non-active
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
            ProfessorCourse professorCourse = adminService.findProfessorCourseById(profCourseId);
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
            int currentCount = adminService.countStudentCoursesByProfCourseId(profCourseId);
            int limitStudent = professorCourse.getLimitStudent();

            // Filter out duplicates first to get accurate new student count
            List<Integer> newStudentIds = new ArrayList<>();
            int duplicates = 0;
            for (Integer studentId : studentIds) {
                if (studentId == null || studentId <= 0) {
                    continue;
                }
                // Use faster exists check
                if (adminService.existsStudentCourseByUserIdAndProfCourseId(studentId, profCourseId)) {
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

                adminService.insertStudentCourse(studentCourse);
                inserted++;
                logger.debug("Inserted: userId={} for profCourseId={}", studentId, profCourseId);
            }

            // Update student count on professor course
            int totalCount = adminService.countStudentCoursesByProfCourseId(profCourseId);
            professorCourse.setNumStudent(totalCount);
            adminService.updateProfessorCourse(professorCourse);

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
                logger.warn("Upload failed: invalid file format for profCourseId={}", profCourseId);
                return "redirect:/admin/courseManagement/cOpen/manageStudent?result=error&errorMsg=invalid_format&profCourseId="
                        + profCourseId;
            }

            // Save file to temp directory via FileService (sanitized + canonical path
            // validated)
            File convFile = fileService.saveMultipartToTempFile(multipartFile, request);
            logger.debug("Upload file saved to temp directory successfully");

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
            // Complete taint break: extract path string, validate, create new File from string only
            String rawPath = file.getAbsolutePath();
            String allowedBase = new File(
                System.getProperty("java.io.tmpdir") + File.separator + "ams_temp"
            ).getCanonicalPath();

            File resolvedFile = new File(rawPath).getCanonicalFile();
            String resolvedPath = resolvedFile.getCanonicalPath();

            if (!resolvedPath.startsWith(allowedBase + File.separator)
                    && !resolvedPath.equals(allowedBase)) {
                throw new SecurityException(
                    "Path validation failed: resolved path escapes temp directory");
            }

            // Create FileInputStream from path string only — breaks CodeQL taint chain
            fileInputStream = new FileInputStream(resolvedPath);
            workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            final int RESERVED_SKIP = 1; // Skip header row

            ProfessorCourse professorCourse = adminService.findProfessorCourseById(profCourseId);
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
                int currentCount = adminService.countStudentCoursesByProfCourseId(profCourseId);
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
                    User studentUser = adminService.findStudentByNumber(studentNumber);

                    if (studentUser == null) {
                        result.incrementSkippedNotFound();
                        logger.debug("Student not found in system: {}", studentNumber);
                        continue;
                    }

                    // Check for duplicate registration
                    StudentCourse existing = adminService.findStudentCourseByUserIdProfCourseId(
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
                    adminService.insertStudentCourse(studentCourse);
                    result.incrementInserted();
                    logger.debug("Inserted student: {} (userId={}) into profCourseId={}",
                            studentNumber, studentUser.getId(), profCourseId);

                } catch (Exception e) {
                    result.incrementInvalidRows();
                    logger.warn("Error processing row {}: {}", rowIndex, e.getMessage());
                }
            }

            // Update the student count on the professor course
            int finalCount = adminService.countStudentCoursesByProfCourseId(profCourseId);
            professorCourse.setNumStudent(finalCount);
            adminService.updateProfessorCourse(professorCourse);
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

        List<Division> divisions = adminService.findAllDivisions();

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
            courseList = adminService.findProfessorCoursesBy(searchable);

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
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        return "role/admin/syllabus/syllabus";
    }

    @RequestMapping(value = "/courseManagement/syllabus/courseDetail", method = RequestMethod.GET)
    public String courseDetail(Model model, @RequestParam int profCourseId,
            @RequestParam(defaultValue = "false", required = false) String print) {
        ProfessorCourse pc = adminService.findProfessorCourseById(profCourseId);
        model.addAttribute("pc", pc);
        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(pc.getId());
        if (lectureFundamentals == null)
            return "role/common/syllabus/requiredSyllabus";
        model.addAttribute("lectureFundamentals", lectureFundamentals);
        ProfLectureMethod profLectureMethod = adminService.findProfLectureMethodByProfCourseId(pc.getId());
        model.addAttribute("profLectureMethod",
                profLectureMethod == null ? new ProfLectureMethod() : profLectureMethod);
        LectureContents lectureContents = adminService.findLectureContentsByProfCourseId(pc.getId());
        model.addAttribute("lectureContents", lectureContents == null ? new LectureContents() : lectureContents);

        List<LectureMethod> lectureMethods = adminService.findAllLectureMethods();
        model.addAttribute("lectureMethods", lectureMethods);

        List<EducationalMedium> educationalMediums = adminService.findAllEducationalMediums();
        model.addAttribute("educationalMediums", educationalMediums);

        List<EvaluationMethod> evaluationMethods = adminService.findAllEvaluationMethods();
        model.addAttribute("evaluationMethods", evaluationMethods);

        List<Equipment> equipments = adminService.findAllEquipments();
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

            courseList = adminService.findProfessorCoursesBy(searchable);

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
        List<Division> divisions = adminService.findAllDivisions();

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
            courseList = adminService.findProfessorCoursesBy(searchable);
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
        ProfessorCourse pc = adminService.findProfessorCourseById(profCourseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(profCourseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = adminService.findStudentCoursesByProfCourseId(profCourseId);
        model.addAttribute("studentCourses", studentCourses);

        return "role/admin/studentGrade/courseDetail";
    }

    @RequestMapping(value = "/academicManagement/studentGrade/ratioDetail", method = RequestMethod.GET)
    public String academicManagementRatioDetail(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = adminService.findProfessorCourseById(profCourseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(profCourseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = adminService.findStudentCoursesByProfCourseId(pc.getId());
        model.addAttribute("studentCourses", studentCourses);

        return "role/common/grade/ratioDetail";
    }

    @RequestMapping(value = "/academicManagement/studentGrade/courseDetailForPrint", method = RequestMethod.GET)
    public String registerGradeCourseDetailForPrint(Model model, @RequestParam int profCourseId) {
        ProfessorCourse pc = adminService.findProfessorCourseById(profCourseId);
        model.addAttribute("pc", pc);

        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(profCourseId);
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        List<StudentCourse> studentCourses = adminService.findStudentCoursesByProfCourseId(profCourseId);
        model.addAttribute("studentCourses", studentCourses);

        return "role/common/grade/courseDetailForPrint";
    }

    @RequestMapping(value = "/academicManagement/graduationCriteria", method = RequestMethod.GET)
    public String graduationCriteria(Model model, @RequestParam(required = false) String result) {
        model.addAttribute("yearList", getYearList());
        List<Division> divisions = adminService.findAllDivisions();
        model.addAttribute("divisions", divisions);
        GraduationCriteria graduationCriteria = new GraduationCriteria();
        model.addAttribute("graduationCriteria", graduationCriteria);
        model.addAttribute("result", result);
        return "role/admin/graduationCriteria/graduationCriteria";
    }

    @RequestMapping(value = "/academicManagement/graduationCriteria", method = RequestMethod.POST)
    public String graduationCriteria(@ModelAttribute GraduationCriteria graduationCriteria) {
        adminService.insertGraduationCriteria(graduationCriteria);
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

            list = adminService.findGraduationCriteriaListByYearDivision(searchable);

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
        switch (name) {
            case "year":    adminService.updateGraduationCriteriaYear(pk,    Integer.parseInt(value)); break;
            case "msc":     adminService.updateGraduationCriteriaMsc(pk,     Integer.parseInt(value)); break;
            case "liberal": adminService.updateGraduationCriteriaLiberal(pk, Integer.parseInt(value)); break;
            case "major":   adminService.updateGraduationCriteriaMajor(pk,   Integer.parseInt(value)); break;
            default: throw new IllegalArgumentException("Unknown graduationCriteria field: " + name);
        }
        return true;
    }

    @RequestMapping(value = "/courseManagement/course/deleteCriteria", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteCriteria(@RequestParam int id) {
        GraduationCriteria gc = adminService.findGraduationCriteriaById(id);
        Searchable searchable = new Searchable();
        searchable.setYear(gc.getYear());
        searchable.setDivision(gc.getDivisionId());
        List<GraduationCriteria> gcList = adminService.findGraduationCriteriaListByYearDivision(searchable);
        if (!CollectionUtils.isEmpty(gcList) && gcList.size() == 1)
            return false;

        adminService.deleteGraduationCriteria(gc);
        return true;
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor", method = RequestMethod.GET)
    public String assessmentFactor(Model model) {
        List<Division> divisions = adminService.findAllDivisions();

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

            courseList = adminService.findCoursesBy(searchable);

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
        Course course = adminService.findCourseById(courseId);
        model.addAttribute("course", course);
        AssessmentFactor assessmentFactor = new AssessmentFactor();
        assessmentFactor.setCourseId(courseId);
        model.addAttribute("assessmentFactor", assessmentFactor);
        List<AssessmentFactor> assessmentFactors = adminService.findAssessmentFactorsByCourseId(courseId);
        model.addAttribute("assessmentFactors", assessmentFactors);

        return "role/admin/assessmentFactor/manageAf";
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor/manageAf", method = RequestMethod.POST)
    public String assessmentFactorCourseDetail(@ModelAttribute AssessmentFactor assessmentFactor,
            @RequestParam int courseId) {
        assessmentFactor.setEnabled(true);
        adminService.insertAssessmentFactor(assessmentFactor);
        return "redirect:/admin/academicManagement/assessmentFactor/manageAf?courseId=" + courseId;
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor/manageAf/deleteAf", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteAf(@RequestParam int id) {
        AssessmentFactor assessmentFactor = adminService.findAssessmentFactorById(id);

        List<Assessment> assessments = adminService.findAssessmentsByAssessmentFactorId(id);
        if (CollectionUtils.isEmpty(assessments)) {
            adminService.deleteAssessmentFactor(assessmentFactor);
            return true;
        } else {
            assessmentFactor.setEnabled(false);
            adminService.updateAssessmentFactor(assessmentFactor);
            return false;
        }
    }

    @RequestMapping(value = "/academicManagement/assessmentFactor/manageAf/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean changeAfStatus(@RequestParam int id, @RequestParam boolean status) {
        AssessmentFactor assessmentFactor = adminService.findAssessmentFactorById(id);
        assessmentFactor.setEnabled(status);
        adminService.updateAssessmentFactor(assessmentFactor);
        return true;
    }

    @RequestMapping(value = "/academicManagement/assessmentResult", method = RequestMethod.GET)
    public String assessmentResult(Model model) {
        List<Division> divisions = adminService.findAllDivisions();

        model.addAttribute("divisions", divisions);
        model.addAttribute("yearList", getYearList());
        List<User> professors = adminService.findAllProfessors();
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
            courseList = adminService.findProfessorCoursesBy(searchable);

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
        ProfessorCourse pc = adminService.findProfessorCourseById(profCourseId);
        model.addAttribute("pc", pc);
        List<Assessment> assessments = adminService.findAssessmentsByProfCourseId(profCourseId);
        model.addAttribute("assessments", assessments);
        List<AssessmentFactor> assessmentFactors = adminService.findAssessmentFactorsByCourseId(pc.getCourseId());
        model.addAttribute("assessmentFactors", assessmentFactors);
        if (print.equals("true"))
            return "role/common/assessment/courseDetailForPrint";
        return "role/admin/assessmentResult/courseDetail";
    }

    @RequestMapping(value = "/academicManagement/cqi", method = RequestMethod.GET)
    public String cqi(Model model) {
        List<Division> divisions = adminService.findAllDivisions();

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

            courseList = adminService.findProfessorCoursesBy(searchable);

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
        ProfessorCourse pc = adminService.findProfessorCourseById(profCourseId);

        model.addAttribute("myAvg", profService.getAssignedAvg(pc));
        model.addAttribute("pc", pc);
        Searchable s = new Searchable();
        s.setCourseId(pc.getCourseId());
        s.setYear(pc.getSemester().getYear());
        s.setEnabled(true);
        s.setOrderParam("divide");
        s.setOrderDir("asc");
        List<ProfessorCourse> professorCourseList = adminService.findProfessorCoursesBy(s);
        for (ProfessorCourse professorCourse : professorCourseList) {
            List<Assessment> assessments = adminService.findAssessmentsByProfCourseId(professorCourse.getId());
            professorCourse.setAssessmentList(assessments);
            List<StudentCourse> studentCourseList = adminService.findStudentCoursesByProfCourseIdValid(professorCourse.getId());
            professorCourse.setStudentCourseList(studentCourseList);
        }
        model.addAttribute("professorCourseList", professorCourseList);

        int currentYear = pc.getSemester().getYear();
        model.addAttribute("currentYear", currentYear);
        LectureFundamentals lectureFundamentals = adminService.findLectureFundamentalsByProfCourseId(pc.getId());
        model.addAttribute("lectureFundamentals", lectureFundamentals);

        Map<Integer, Double> averageAssignedDivideMap = profService.getAverageAssignedDivideMap(professorCourseList);
        model.addAttribute("averageAssignedDivideMap", averageAssignedDivideMap);

        Map<Integer, Cqi> cqiMap = new LinkedHashMap<>();
        for (int i = (currentYear - 2); i < currentYear; i++) {
            Cqi cqi = adminService.findCqiByYearCourseIdDivide(i, pc.getCourseId(), pc.getDivide());

            if (cqi == null) {
                cqi = new Cqi();
            }
            cqiMap.put(i, cqi);
            if (i == currentYear - 1) {
                model.addAttribute("prevCqi", cqi);
            }
        }
        model.addAttribute("cqiMap", cqiMap);

        Cqi cqi = adminService.findCqiByProfCourseId(pc.getId());
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
        List<AssessmentFactor> assessmentFactors = adminService.findAssessmentFactorsByCourseId(pc.getCourseId());
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

        Semester exist = adminService.findSemesterByYearSemester(semester.getYear(), semester.getSemester());
        if (exist == null) {
            List<Semester> semesterAll = adminService.findAllSemesters();
            for (Semester s : semesterAll) {
                s.setCurrent(false);
                adminService.updateSemesterDirect(s);
            }
            semester.setCurrent(true);
            adminService.insertSemester(semester);
        }

        sessionStatus.setComplete();

        return "redirect:/admin/systemManagement/yearSemester?result=success";
    }

    @RequestMapping(value = "/systemManagement/yearSemester/deleteSemester", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteSemester(@RequestParam int id) {
        Semester exist = adminService.findSemesterById(id);
        adminService.deleteSemester(exist);

        return true;
    }

    @RequestMapping(value = "/systemManagement/yearSemester/currentSemester", method = RequestMethod.POST)
    @ResponseBody
    public Boolean currentSemester(@RequestParam int id) {
        List<Semester> semesterAll = adminService.findAllSemesters();
        for (Semester s : semesterAll) {
            s.setCurrent(false);
            adminService.updateSemesterDirect(s);
        }
        Semester exist = adminService.findSemesterById(id);
        exist.setCurrent(true);
        adminService.updateSemesterDirect(exist);

        return true;
    }

    @RequestMapping(value = "/systemManagement/yearSemester/semesterEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean semesterEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        switch (name) {
            case "year":     return adminService.updateSemesterYear(pk,   Integer.parseInt(value));
            case "semester": return adminService.updateSemesterNumber(pk, Integer.parseInt(value));
            default: throw new IllegalArgumentException("Unknown semester field: " + name);
        }
    }

    @RequestMapping(value = "/systemManagement/yearSemester/semesterTable", method = RequestMethod.GET)
    public String semesterTable(Model model) {

        List<Semester> semesterList = adminService.findAllSemesters();

        model.addAttribute("semesterList", semesterList);
        return "role/admin/yearSemester/semesterTable";
    }

    @RequestMapping(value = "/systemManagement/divisionMajor", method = RequestMethod.GET)
    public String divisionMajor(Model model, @RequestParam(required = false) String result) {
        Division division = new Division();
        List<Division> divisions = adminService.findAllDivisions();
        model.addAttribute("divisions", divisions);
        model.addAttribute("division", division);
        model.addAttribute("result", result);

        return "role/admin/divisionMajor/divisionMajor";
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/divisionTable", method = RequestMethod.GET)
    public String divisionTable(Model model) {

        List<Division> divisionList = adminService.findAllDivisions();

        model.addAttribute("divisionList", divisionList);
        return "role/admin/divisionMajor/divisionTable";
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/divisionEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean divisionEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        switch (name) {
            case "name": adminService.updateDivisionName(pk, value); break;
            default: throw new IllegalArgumentException("Unknown division field: " + name);
        }
        return true;
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/deleteDivision", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteDivision(@RequestParam int id) {
        Division division = adminService.findDivisionById(id);
        List<Course> courses = adminService.findCoursesByDivision(id);
        if (CollectionUtils.isEmpty(courses)) {
            adminService.deleteDivision(division);
            return true;
        } else {
            division.setEnabled(false);
            adminService.updateDivision(division);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/divisionMajor/enableDivision", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableDivision(@RequestParam int id) {
        Division division = adminService.findDivisionById(id);
        division.setEnabled(true);
        adminService.updateDivision(division);
        return true;
    }

    @RequestMapping(value = "/systemManagement/createDivision", method = RequestMethod.POST)
    public String createDivision(@ModelAttribute Division division) {
        adminService.insertDivision(division);
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
        adminService.insertLectureMethod(lectureMethod);
        return "redirect:/admin/systemManagement/lectureMethod?result=success";
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/lectureMethodTable", method = RequestMethod.GET)
    public String lectureMethodTable(Model model) {

        List<LectureMethod> lectureMethodList = adminService.findAllLectureMethods();

        model.addAttribute("lectureMethodList", lectureMethodList);
        return "role/admin/lectureMethod/lectureMethodTable";
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/lectureMethodEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean lectureMethodEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        switch (name) {
            case "name": adminService.updateLectureMethodName(pk, value); break;
            default: throw new IllegalArgumentException("Unknown lectureMethod field: " + name);
        }
        return true;
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/deleteLectureMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteLectureMethod(@RequestParam int id) {
        LectureMethod lectureMethod = adminService.findLectureMethodById(id);

        ProfLectureMethod stored = adminService.findProfLectureMethodByLectureMethodId(Integer.toString(id));

        if (stored == null) {
            adminService.deleteLectureMethod(lectureMethod);
            return true;
        } else {
            lectureMethod.setEnabled(false);
            adminService.updateLectureMethod(lectureMethod);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/lectureMethod/enableLectureMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableLectureMethod(@RequestParam int id) {
        LectureMethod lectureMethod = adminService.findLectureMethodById(id);
        lectureMethod.setEnabled(true);
        adminService.updateLectureMethod(lectureMethod);
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
        adminService.insertEvaluationMethod(evaluationMethod);
        return "redirect:/admin/systemManagement/evaluationMethod?result=success";
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/evaluationMethodTable", method = RequestMethod.GET)
    public String evaluationMethodTable(Model model) {

        List<EvaluationMethod> evaluationMethodList = adminService.findAllEvaluationMethods();

        model.addAttribute("evaluationMethodList", evaluationMethodList);
        return "role/admin/evaluationMethod/evaluationMethodTable";
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/evaluationMethodEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean evaluationMethodEditable(@RequestParam int pk, @RequestParam String name,
            @RequestParam String value) {
        switch (name) {
            case "name": adminService.updateEvaluationMethodName(pk, value); break;
            default: throw new IllegalArgumentException("Unknown evaluationMethod field: " + name);
        }
        return true;
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/deleteEvaluationMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteEvaluationMethod(@RequestParam int id) {
        EvaluationMethod evaluationMethod = adminService.findEvaluationMethodById(id);

        ProfLectureMethod stored = adminService.findProfLectureMethodByEvaluationMethodId(Integer.toString(id));
        adminService.deleteEvaluationMethod(evaluationMethod);
        if (stored == null) {
            adminService.deleteEvaluationMethod(evaluationMethod);
            return true;
        } else {
            evaluationMethod.setEnabled(false);
            adminService.updateEvaluationMethod(evaluationMethod);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/evaluationMethod/enableEvaluationMethod", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableEvaluationMethod(@RequestParam int id) {
        EvaluationMethod evaluationMethod = adminService.findEvaluationMethodById(id);
        evaluationMethod.setEnabled(true);
        adminService.updateEvaluationMethod(evaluationMethod);
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
        adminService.insertEducationalMedium(educationalMedium);
        return "redirect:/admin/systemManagement/educationalMedium?result=success";
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/educationalMediumTable", method = RequestMethod.GET)
    public String educationalMediumTable(Model model) {

        List<EducationalMedium> educationalMediumList = adminService.findAllEducationalMediums();

        model.addAttribute("educationalMediumList", educationalMediumList);
        return "role/admin/educationalMedium/educationalMediumTable";
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/educationalMediumEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean educationalMediumEditable(@RequestParam int pk, @RequestParam String name,
            @RequestParam String value) {
        switch (name) {
            case "name": adminService.updateEducationalMediumName(pk, value); break;
            default: throw new IllegalArgumentException("Unknown educationalMedium field: " + name);
        }
        return true;
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/deleteEducationalMedium", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteEducationalMedium(@RequestParam int id) {
        EducationalMedium educationalMedium = adminService.findEducationalMediumById(id);

        ProfLectureMethod stored = adminService.findProfLectureMethodByEducationalMediumId(Integer.toString(id));

        if (stored == null) {
            adminService.deleteEducationalMedium(educationalMedium);
            return true;
        } else {
            educationalMedium.setEnabled(false);
            adminService.updateEducationalMedium(educationalMedium);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/educationalMedium/enableEducationalMedium", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableEducationalMedium(@RequestParam int id) {
        EducationalMedium educationalMedium = adminService.findEducationalMediumById(id);
        educationalMedium.setEnabled(true);
        adminService.updateEducationalMedium(educationalMedium);
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
        adminService.insertEquipment(equipment);
        return "redirect:/admin/systemManagement/equipment?result=success";
    }

    @RequestMapping(value = "/systemManagement/equipment/equipmentTable", method = RequestMethod.GET)
    public String equipmentTable(Model model) {

        List<Equipment> equipmentList = adminService.findAllEquipments();

        model.addAttribute("equipmentList", equipmentList);
        return "role/admin/equipment/equipmentTable";
    }

    @RequestMapping(value = "/systemManagement/equipment/equipmentEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean equipmentEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        switch (name) {
            case "name": adminService.updateEquipmentName(pk, value); break;
            case "code": adminService.updateEquipmentCode(pk, value); break;
            default: throw new IllegalArgumentException("Unknown equipment field: " + name);
        }
        return true;
    }

    @RequestMapping(value = "/systemManagement/equipment/deleteEquipment", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteEquipment(@RequestParam int id) {
        Equipment equipment = adminService.findEquipmentById(id);

        ProfLectureMethod stored = adminService.findProfLectureMethodByEquipmentId(Integer.toString(id));

        if (stored == null) {
            adminService.deleteEquipment(equipment);
            return true;
        } else {
            equipment.setEnabled(false);
            adminService.updateEquipment(equipment);
            return false;
        }
    }

    @RequestMapping(value = "/systemManagement/equipment/enableEquipment", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableEquipment(@RequestParam int id) {
        Equipment equipment = adminService.findEquipmentById(id);
        equipment.setEnabled(true);
        adminService.updateEquipment(equipment);
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
    public String classroom(@ModelAttribute koreatech.cse.domain.dto.ClassroomCreateRequest req) {
        Classroom classroom = new Classroom();
        classroom.setCode(req.getCode());
        classroom.setName(req.getName());
        adminService.insertClassroom(classroom);
        return "redirect:/admin/systemManagement/classroom?result=success";
    }

    @RequestMapping(value = "/systemManagement/classroom/classroomTable", method = RequestMethod.GET)
    public String classroomTable(Model model) {

        List<Classroom> classroomList = adminService.findAllClassrooms();

        model.addAttribute("classroomList", classroomList);
        return "role/admin/classroom/classroomTable";
    }

    @RequestMapping(value = "/systemManagement/classroom/classroomEditable", method = RequestMethod.POST)
    @ResponseBody
    public Boolean classroomEditable(@RequestParam int pk, @RequestParam String name, @RequestParam String value) {
        switch (name) {
            case "name": adminService.updateClassroomName(pk, value); break;
            case "code": adminService.updateClassroomCode(pk, value); break;
            default: throw new IllegalArgumentException("Unknown classroom field: " + name);
        }
        return true;
    }

    @RequestMapping(value = "/systemManagement/classroom/deleteClassroom", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteClassroom(@RequestParam int id) {
        Classroom classroom = adminService.findClassroomById(id);

        List<ProfessorCourse> stored = adminService.findProfessorCoursesByClassroomId(id);

        if (CollectionUtils.isEmpty(stored)) {
            adminService.deleteClassroom(classroom);
        } else {
            classroom.setEnabled(false);
            adminService.updateClassroom(classroom);
        }
        return true;
    }

    @RequestMapping(value = "/systemManagement/classroom/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean changeClassroomStatus(@RequestParam int id, @RequestParam boolean status) {
        Classroom classroom = adminService.findClassroomById(id);
        classroom.setEnabled(status);
        adminService.updateClassroom(classroom);
        return true;
    }

    @RequestMapping(value = "/systemManagement/menu", method = RequestMethod.GET)
    public String menu(Model model, @RequestParam(required = false) String result) {
        MenuAccess menuAccess = adminService.findMenuAccess();
        if (menuAccess == null) {
            menuAccess = new MenuAccess();
            // Display-only — no insert in GET
        }

        model.addAttribute("menuAccess", menuAccess);
        model.addAttribute("result", result);
        return "role/admin/menu/menu";
    }

    @RequestMapping(value = "/systemManagement/menu", method = RequestMethod.POST)
    public String menu(@ModelAttribute MenuSettingsRequest req) {
        MenuAccess menuAccess = adminService.findMenuAccess();
        if (menuAccess == null) {
            menuAccess = new MenuAccess();
        }
        menuAccess.setAssessment(req.isAssessment());
        menuAccess.setGrade(req.isGrade());
        menuAccess.setSyllabus(req.isSyllabus());
        menuAccess.setCqi(req.isCqi());
        adminService.updateMenuAccess(menuAccess);
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

        User adminUser = adminService.findUserById(id);
        model.addAttribute("adminUser", adminUser);
        return "role/admin/addAdmin/editAdmin";
    }

    @RequestMapping(value = "/systemManagement/addAdmin", method = RequestMethod.POST)
    public String addAdmin(@ModelAttribute AdminCreateRequest req, SessionStatus sessionStatus) {

        userService.signupAdmin(req);
        sessionStatus.setComplete();

        return "redirect:/admin/systemManagement/addAdmin?result=success";
    }

    @RequestMapping(value = "/systemManagement/deleteAdmin", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteAdmin(@RequestParam int id) {
        List<User> admins = adminService.findAllAdmins();
        if (admins.size() > 1) {
            User adminUser = adminService.findUserById(id);
            adminService.deleteUser(adminUser);
            return true;
        } else
            return false;

    }

    @RequestMapping(value = "/systemManagement/editAdmin", method = RequestMethod.POST)
    public String editAdmin(@ModelAttribute AdminUpdateRequest req, SessionStatus sessionStatus) {
        User adminUser = adminService.findUserById(req.getId());
        if (adminUser == null) {
            sessionStatus.setComplete();
            return "redirect:/admin/systemManagement/addAdmin?result=fail";
        }
        if (StringUtils.isNotBlank(req.getPassword())) {
            adminUser.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        if (adminUser.getContact() != null) {
            adminUser.getContact().setFirstName(req.getFirstName());
            adminUser.getContact().setLastName(req.getLastName());
        }
        adminService.updateUserFromSignup(adminUser);
        adminService.updateContact(adminUser.getContact());
        sessionStatus.setComplete();

        return "redirect:/admin/systemManagement/addAdmin?result=success";
    }

    @RequestMapping(value = "/systemManagement/addAdmin/adminTable", method = RequestMethod.GET)
    public String adminTable(Model model) {
        List<User> adminList = adminService.findAllAdmins();
        model.addAttribute("adminList", adminList);
        return "role/admin/addAdmin/adminTable";
    }

    @RequestMapping(value = "/systemManagement/errorReport", method = RequestMethod.GET)
    public String errorReport(Model model) {
        List<Feedback> feedbackList = adminService.findRecentFeedback();
        model.addAttribute("feedbackList", feedbackList);
        return "role/admin/errorReport/errorReport";
    }

    @RequestMapping(value = "/systemManagement/errorReport/deleteEr", method = RequestMethod.POST)
    @ResponseBody
    public String deleteEr(@RequestParam int id) {
        Feedback feedback = adminService.findFeedbackById(id);
        adminService.deleteFeedback(feedback);
        return "success";
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public Boolean deleteFile(@RequestParam int uploadedFileId) throws IOException {
        UploadedFile uploadedFile = adminService.findUploadedFileById(uploadedFileId);
        if (uploadedFile != null) {
            File file = new File(uploadedFile.getPath());
            boolean delete = file.delete();
            adminService.deleteUploadedFile(uploadedFile);
            return delete;
        }
        return false;
    }

    private List<Integer> getYearList() {
        return adminService.findSemesterYears();
    }
}
