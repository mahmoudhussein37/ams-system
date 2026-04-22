package koreatech.cse.controller.role;

import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.AccountState;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.dto.AdminProfessorRegistrationRequest;
import koreatech.cse.domain.dto.AdminStudentRegistrationRequest;
import koreatech.cse.domain.dto.StudentUploadResult;
import koreatech.cse.service.AdminService;
import koreatech.cse.service.UserService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.support.SimpleSessionStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerPhase2CHardeningTest {

    @Mock
    private AdminService adminService;
    @Mock
    private UserService userService;

    private AdminController adminController;

    @Before
    public void setUp() {
        adminController = new AdminController();
        ReflectionTestUtils.setField(adminController, "adminService", adminService);
        ReflectionTestUtils.setField(adminController, "userService", userService);
    }

    @Test
    public void profRegistrationRejectsMissingName() {
        AdminProfessorRegistrationRequest req = new AdminProfessorRegistrationRequest();
        req.setNumber("P1001");
        req.setDivisionId(1);
        req.setFirstName(" ");
        req.setLastName("Hassan");

        String viewName = adminController.profRegistration(req, new SimpleSessionStatus());

        assertEquals("redirect:/admin/profManagement/profRegistration?result=validation_error", viewName);
        verify(userService, never()).register(any(User.class), eq(Role.professor));
        verifyNoInteractions(adminService);
    }

    @Test
    public void studentRegistrationDoesNotAutoAssignAdvisor() {
        AdminStudentRegistrationRequest req = createStudentRequest("20243001", "Mona", "Ali", 2);
        req.setAdvisorId(0);

        when(userService.register(any(User.class), eq(Role.student))).thenReturn(true);

        String viewName = adminController.basic(req, new SimpleSessionStatus());

        assertEquals("redirect:/admin/studentManagement/studentRegistration?result=success", viewName);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).register(userCaptor.capture(), eq(Role.student));

        User submittedUser = userCaptor.getValue();
        assertEquals(0, submittedUser.getAdvisorId());
        assertEquals(AccountState.PENDING, submittedUser.getAccountState());
        verify(adminService, never()).findUserById(anyInt());
    }

    @Test
    public void advisorNormalizationInvalidToUnassignedForCrossDivisionAdvisor() {
        AdminStudentRegistrationRequest req = createStudentRequest("20243002", "Nour", "Samir", 1);
        req.setAdvisorId(99);

        when(userService.register(any(User.class), eq(Role.student))).thenReturn(true);

        String viewName = adminController.basic(req, new SimpleSessionStatus());

        assertEquals("redirect:/admin/studentManagement/studentRegistration?result=success", viewName);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).register(userCaptor.capture(), eq(Role.student));
        // Advisor not found in service → normalized to unassigned
        assertEquals(0, userCaptor.getValue().getAdvisorId());
        verify(adminService).findUserById(99);
    }

    @Test
    public void importCreatesPendingUsersWithNoAdvisor() throws Exception {
        File workbookFile = createStudentImportWorkbook("2024999", "Laila", "Sayed");
        try {
            when(adminService.findUserByNumber("2024999")).thenReturn(null);
            when(userService.register(any(User.class), eq(Role.student))).thenReturn(true);

            StudentUploadResult result = ReflectionTestUtils.invokeMethod(
                    adminController,
                    "readStudentImportExcel",
                    workbookFile,
                    4,
                    2
            );

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userService).register(userCaptor.capture(), eq(Role.student));

            User importedUser = userCaptor.getValue();
            assertEquals(AccountState.PENDING, importedUser.getAccountState());
            assertEquals(0, importedUser.getAdvisorId());
            assertEquals(4, importedUser.getDivisionId());
            assertEquals(2, importedUser.getSchoolYear());
            assertEquals("2024999", importedUser.getNumber());
            assertNotNull(result);
            assertEquals(1, result.getInsertedCount());
        } finally {
            if (workbookFile.exists()) {
                workbookFile.delete();
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void classroomEditableRejectsArbitraryFieldName() {
        adminController.classroomEditable(1, "UNKNOWN_FIELD", "malicious");
    }

    private AdminStudentRegistrationRequest createStudentRequest(String number, String firstName, String lastName,
            int divisionId) {
        AdminStudentRegistrationRequest req = new AdminStudentRegistrationRequest();
        req.setNumber(number);
        req.setFirstName(firstName);
        req.setLastName(lastName);
        req.setDivisionId(divisionId);
        req.setSchoolYear(1);
        return req;
    }

    private File createStudentImportWorkbook(String studentNumber, String firstName, String lastName) throws IOException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "ams_temp");
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            throw new IOException("Unable to create temp import directory");
        }

        File file = File.createTempFile("phase2c-import-", ".xlsx", tempDir);
        try (XSSFWorkbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream(file)) {
            XSSFSheet sheet = workbook.createSheet("Students");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("Student Number");
            header.createCell(1).setCellValue("First Name");
            header.createCell(2).setCellValue("Last Name");

            XSSFRow row = sheet.createRow(1);
            row.createCell(0).setCellValue(studentNumber);
            row.createCell(1).setCellValue(firstName);
            row.createCell(2).setCellValue(lastName);

            workbook.write(outputStream);
        }

        return file;
    }
}
