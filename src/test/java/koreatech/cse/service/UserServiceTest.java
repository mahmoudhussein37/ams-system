package koreatech.cse.service;

import koreatech.cse.domain.Authority;
import koreatech.cse.domain.Contact;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.constant.SignupResult;
import koreatech.cse.domain.dto.AdminProfessorProfileUpdateDTO;
import koreatech.cse.domain.dto.AdminStudentProfileUpdateDTO;
import koreatech.cse.domain.dto.StudentProfileUpdateDTO;
import koreatech.cse.repository.AuthorityMapper;
import koreatech.cse.repository.ContactMapper;
import koreatech.cse.repository.SemesterMapper;
import koreatech.cse.repository.StudentCourseMapper;
import koreatech.cse.repository.UploadedFileMapper;
import koreatech.cse.repository.UserMapper;
import koreatech.cse.service.FileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private AuthorityMapper authorityMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ContactMapper contactMapper;
    @Mock
    private StudentCourseMapper studentCourseMapper;
    @Mock
    private SemesterMapper semesterMapper;
    @Mock
    private UploadedFileMapper uploadedFileMapper;
    @Mock
    private FileService fileService;

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
        ReflectionTestUtils.setField(userService, "userMapper", userMapper);
        ReflectionTestUtils.setField(userService, "authorityMapper", authorityMapper);
        ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
        ReflectionTestUtils.setField(userService, "contactMapper", contactMapper);
        ReflectionTestUtils.setField(userService, "studentCourseMapper", studentCourseMapper);
        ReflectionTestUtils.setField(userService, "semesterMapper", semesterMapper);
        ReflectionTestUtils.setField(userService, "uploadedFileMapper", uploadedFileMapper);
        ReflectionTestUtils.setField(userService, "fileService", fileService);
    }

    @Test
    public void signupActivatesPendingAccountWithoutOverwritingIdentity() {
        User signupUser = createSignupUser("2024001", " Student@Example.COM ", "secret", " alice ", " SMITH ");
        User storedUser = createStoredUser(7, "2024001", false);
        Contact storedContact = createContact("Alice", "Smith");

        when(userMapper.findByNumber("2024001")).thenReturn(storedUser);
        when(contactMapper.findByUserId(7)).thenReturn(storedContact);
        when(userMapper.findByUsername("student@example.com")).thenReturn(null);
        when(passwordEncoder.encode("secret")).thenReturn("encoded-secret");
        when(authorityMapper.findByUserIdAndRole(7, Role.user.toCode())).thenReturn(null);
        when(authorityMapper.findByUserIdAndRole(7, Role.student.toCode())).thenReturn(null);

        SignupResult result = userService.signup(signupUser, Role.student, "secret");

        assertEquals(SignupResult.SUCCESS, result);
        assertEquals("student@example.com", storedUser.getUsername());
        assertEquals("encoded-secret", storedUser.getPassword());
        assertTrue(storedUser.isEnabled());
        assertTrue(storedUser.isConfirm());
        assertEquals("Alice", storedContact.getFirstName());
        assertEquals("Smith", storedContact.getLastName());
        verify(userMapper).updateFromSignup(storedUser);
        verify(contactMapper, never()).update(any(Contact.class));

        ArgumentCaptor<Authority> authorityCaptor = ArgumentCaptor.forClass(Authority.class);
        verify(authorityMapper, times(2)).insert(authorityCaptor.capture());
        List<Authority> insertedAuthorities = authorityCaptor.getAllValues();
        assertEquals(Role.user, insertedAuthorities.get(0).getRole());
        assertEquals(Role.student, insertedAuthorities.get(1).getRole());
        verify(userMapper).findByUsername("student@example.com");
    }

    @Test
    public void signupDoesNotMutateStoredIdentity() {
        User signupUser = createSignupUser("2024010", "student10@example.com", "secret", "Mina", "Khaled");
        User storedUser = createStoredUser(10, "2024010", false);
        Contact storedContact = createContact("Mina", "Khaled");

        when(userMapper.findByNumber("2024010")).thenReturn(storedUser);
        when(contactMapper.findByUserId(10)).thenReturn(storedContact);
        when(userMapper.findByUsername("student10@example.com")).thenReturn(null);
        when(passwordEncoder.encode("secret")).thenReturn("encoded-secret");
        when(authorityMapper.findByUserIdAndRole(10, Role.user.toCode())).thenReturn(new Authority());
        when(authorityMapper.findByUserIdAndRole(10, Role.student.toCode())).thenReturn(new Authority());

        SignupResult result = userService.signup(signupUser, Role.student, "secret");

        assertEquals(SignupResult.SUCCESS, result);
        assertEquals("Mina", storedContact.getFirstName());
        assertEquals("Khaled", storedContact.getLastName());
        verify(contactMapper, never()).update(any(Contact.class));
    }

    @Test
    public void signupRejectsIdentityMismatch() {
        User signupUser = createSignupUser("2024001", "student@example.com", "secret", "Alice", "Walker");
        User storedUser = createStoredUser(7, "2024001", false);

        when(userMapper.findByNumber("2024001")).thenReturn(storedUser);
        when(contactMapper.findByUserId(7)).thenReturn(createContact("Alice", "Smith"));

        SignupResult result = userService.signup(signupUser, Role.student, "secret");

        assertEquals(SignupResult.IDENTITY_MISMATCH, result);
        verify(userMapper, never()).updateFromSignup(any(User.class));
        verify(authorityMapper, never()).insert(any(Authority.class));
    }

    @Test
    public void signupRejectsAlreadyActivatedAccount() {
        User signupUser = createSignupUser("2024001", "student@example.com", "secret", "Alice", "Smith");
        User storedUser = createStoredUser(7, "2024001", true);

        when(userMapper.findByNumber("2024001")).thenReturn(storedUser);
        when(contactMapper.findByUserId(7)).thenReturn(createContact("Alice", "Smith"));
        when(userMapper.findByUsername("student@example.com")).thenReturn(null);

        SignupResult result = userService.signup(signupUser, Role.student, "secret");

        assertEquals(SignupResult.ALREADY_ACTIVE, result);
        verify(userMapper, never()).updateFromSignup(any(User.class));
        verify(authorityMapper, never()).insert(any(Authority.class));
    }

    @Test
    public void signupRejectsDuplicateUsername() {
        User signupUser = createSignupUser("2024001", "student@example.com", "secret", "Alice", "Smith");
        User storedUser = createStoredUser(7, "2024001", false);
        User otherUser = createStoredUser(9, "2024002", true);

        when(userMapper.findByNumber("2024001")).thenReturn(storedUser);
        when(contactMapper.findByUserId(7)).thenReturn(createContact("Alice", "Smith"));
        when(userMapper.findByUsername("student@example.com")).thenReturn(otherUser);

        SignupResult result = userService.signup(signupUser, Role.student, "secret");

        assertEquals(SignupResult.USERNAME_TAKEN, result);
        verify(userMapper, never()).updateFromSignup(any(User.class));
    }

    @Test
    public void signupRejectsPasswordConfirmationMismatch() {
        User signupUser = createSignupUser("2024001", "student@example.com", "secret", "Alice", "Smith");

        SignupResult result = userService.signup(signupUser, Role.student, "different-secret");

        assertEquals(SignupResult.INVALID_INPUT, result);
        verifyNoInteractions(userMapper, contactMapper, authorityMapper, passwordEncoder);
    }

    @Test
    public void signupRejectsMissingContactSafely() {
        User signupUser = new User();
        signupUser.setNumber("2024001");
        signupUser.setUsername("student@example.com");
        signupUser.setPassword("secret");

        SignupResult result = userService.signup(signupUser, Role.student, "secret");

        assertEquals(SignupResult.INVALID_INPUT, result);
        verifyNoInteractions(userMapper, contactMapper, authorityMapper, passwordEncoder);
    }

    @Test
    public void signupRejectsWhitespaceOnlyIdentityFieldsSafely() {
        User signupUser = createSignupUser("   ", "   ", "secret", "   ", "   ");

        SignupResult result = userService.signup(signupUser, Role.student, "secret");

        assertEquals(SignupResult.INVALID_INPUT, result);
        verifyNoInteractions(userMapper, contactMapper, authorityMapper, passwordEncoder);
    }

    @Test
    public void signupMethodIsTransactional() throws Exception {
        Method signupMethod = UserService.class.getMethod("signup", User.class, Role.class, String.class);
        Transactional transactional = signupMethod.getAnnotation(Transactional.class);

        assertTrue(signupMethod.isAnnotationPresent(Transactional.class));
        assertTrue(Arrays.asList(transactional.rollbackFor()).contains(Exception.class));
    }

    @Test
    public void registerAssignsProfessorRole() {
        User professor = createStoredUser(0, "P001", false);
        professor.setContact(createContact("Mina", "Nabil"));
        professor.setDivisionId(1);

        when(userMapper.findByNumber("P001")).thenReturn(null);
        when(authorityMapper.findByUserIdAndRole(0, Role.professor.toCode())).thenReturn(null);

        boolean result = userService.register(professor, Role.professor);

        assertTrue(result);
        verify(userMapper).insert(professor);
        verify(contactMapper).insert(professor.getContact());
        ArgumentCaptor<Authority> authorityCaptor = ArgumentCaptor.forClass(Authority.class);
        verify(authorityMapper).insert(authorityCaptor.capture());
        assertEquals(Role.professor, authorityCaptor.getValue().getRole());
    }

    @Test
    public void registerAssignsStudentRole() {
        User student = createStoredUser(0, "S001", false);
        student.setContact(createContact("Mariam", "Sami"));

        when(userMapper.findByNumber("S001")).thenReturn(null);
        when(authorityMapper.findByUserIdAndRole(0, Role.student.toCode())).thenReturn(null);

        boolean result = userService.register(student, Role.student);

        assertTrue(result);
        ArgumentCaptor<Authority> authorityCaptor = ArgumentCaptor.forClass(Authority.class);
        verify(authorityMapper).insert(authorityCaptor.capture());
        assertEquals(Role.student, authorityCaptor.getValue().getRole());
    }

    @Test
    public void registerRejectsNullRole() {
        User student = createStoredUser(0, "S001", false);
        student.setContact(createContact("Mariam", "Sami"));

        boolean result = userService.register(student, null);

        assertFalse(result);
        verifyNoInteractions(userMapper, contactMapper, authorityMapper);
    }

    @Test
    public void updateAdminStudentProfileOnlyTouchesWhitelistedFields() throws Exception {
        User storedUser = createStoredUser(15, "2024015", true);
        storedUser.setDivisionId(1);
        storedUser.setSchoolYear(1);
        storedUser.setStatus("ENROLLED");

        Contact storedContact = createContact("Aya", "Saleh");
        storedContact.setUserId(15);
        storedUser.setContact(storedContact);

        AdminStudentProfileUpdateDTO dto = new AdminStudentProfileUpdateDTO();
        dto.setId(15);
        dto.setDivisionId(2);
        dto.setAdvisorId(0);
        dto.setSchoolYear(3);
        dto.setStatus("GRADUATED");
        Contact submittedContact = new Contact();
        submittedContact.setFirstName("Mina");
        submittedContact.setLastName("Nabil");
        submittedContact.setGradDegree("BSc");
        dto.setContact(submittedContact);

        when(userMapper.findOne(15)).thenReturn(storedUser);
        when(contactMapper.findByUserId(15)).thenReturn(storedContact);

        assertTrue(userService.updateAdminStudentProfile(dto, null));
        verify(userMapper).update(storedUser);
        verify(userMapper, never()).updateFromSignup(any(User.class));
        verify(contactMapper).update(storedContact);
        assertEquals(2, storedUser.getDivisionId());
        assertEquals(3, storedUser.getSchoolYear());
        assertEquals("GRADUATED", storedUser.getStatus());
        assertEquals("Mina", storedContact.getFirstName());
        assertEquals("Nabil", storedContact.getLastName());
        assertEquals("BSc", storedContact.getGradDegree());
    }

    @Test
    public void updateAdminProfessorProfileUsesDedicatedAccountStateWrite() {
        User storedUser = createStoredUser(21, "P0021", true);
        storedUser.setDivisionId(1);
        Contact storedContact = createContact("Mona", "Khaled");
        storedContact.setUserId(21);
        storedUser.setContact(storedContact);

        AdminProfessorProfileUpdateDTO dto = new AdminProfessorProfileUpdateDTO();
        dto.setId(21);
        dto.setDivisionId(4);
        dto.setEnabled(false);
        Contact submittedContact = new Contact();
        submittedContact.setFirstName("Mona");
        submittedContact.setLastName("Ibrahim");
        dto.setContact(submittedContact);

        when(userMapper.findOne(21)).thenReturn(storedUser);
        when(contactMapper.findByUserId(21)).thenReturn(storedContact);

        assertTrue(userService.updateAdminProfessorProfile(dto));
        verify(userMapper).update(storedUser);
        verify(userMapper).updateAccountState(storedUser);
        verify(userMapper, never()).updateFromSignup(any(User.class));
        assertEquals(4, storedUser.getDivisionId());
        assertFalse(storedUser.isEnabled());
        assertTrue(storedUser.isConfirm());
        assertEquals("Ibrahim", storedContact.getLastName());
    }

    @Test
    public void updateStudentSelfProfilePersistsOnlyContactFields() throws Exception {
        User storedUser = createStoredUser(33, "S0033", true);
        Contact storedContact = createContact("Nadia", "Fathy");
        storedContact.setUserId(33);
        storedUser.setContact(storedContact);

        StudentProfileUpdateDTO dto = new StudentProfileUpdateDTO();
        dto.setFirstName("Nadia");
        dto.setLastName("Fouad");
        Contact submittedContact = new Contact();
        submittedContact.setCellPhone("0100");
        submittedContact.setAddress("Beni Suef");
        dto.setContact(submittedContact);

        when(userMapper.findOne(33)).thenReturn(storedUser);
        when(contactMapper.findByUserId(33)).thenReturn(storedContact);

        assertTrue(userService.updateStudentSelfProfile(33, dto, null));
        verify(contactMapper).update(storedContact);
        verify(uploadedFileMapper, never()).deleteProfileByUser(any(User.class));
        assertEquals("Fouad", storedContact.getLastName());
        assertEquals("0100", storedContact.getCellPhone());
        assertEquals("Beni Suef", storedContact.getAddress());
    }

    private User createSignupUser(String number, String username, String password, String firstName, String lastName) {
        User user = new User();
        user.setNumber(number);
        user.setUsername(username);
        user.setPassword(password);
        user.setContact(createContact(firstName, lastName));
        return user;
    }

    private User createStoredUser(int id, String number, boolean confirm) {
        User user = new User();
        user.setId(id);
        user.setNumber(number);
        user.setConfirm(confirm);
        user.setEnabled(confirm);
        return user;
    }

    private Contact createContact(String firstName, String lastName) {
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        return contact;
    }
}
