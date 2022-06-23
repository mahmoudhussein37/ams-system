package koreatech.cse.service;

import koreatech.cse.domain.Authority;
import koreatech.cse.domain.Contact;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.role.student.StudentCourse;
import koreatech.cse.domain.univ.Semester;
import koreatech.cse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Inject
    private UserMapper userMapper;
    @Inject
    private AuthorityMapper authorityMapper;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private ContactMapper contactMapper;
    @Inject
    private StudentCourseMapper studentCourseMapper;
    @Inject
    private SemesterMapper semesterMapper;


    public Boolean signup(User user, Role role) {

        if(user.getUsername() == null || user.getPassword() ==  null)
            return false;

        if(isUniqueUsername(user.getUsername().trim())) {

            User stored = userMapper.findByNumber(user.getNumber());
            if(stored == null) {
                stored = user;
                stored.setUsername(user.getUsername());
                stored.setPassword(passwordEncoder.encode(user.getPassword()));
                stored.setEnabled(true);
                stored.setConfirm(true);

                stored.setStatus("registered");
                userMapper.insert(stored);
                Contact contact = user.getContact();
                contact.setUserId(stored.getId());

                contactMapper.insert(contact);

                Authority authority = new Authority();
                authority.setUserId(stored.getId());
                authority.setRole(Role.user);
                authorityMapper.insert(authority);

                //TODO: remove
                if(role == Role.admin) {
                    Authority adminAuthority = new Authority();
                    adminAuthority.setUserId(stored.getId());
                    adminAuthority.setRole(Role.admin);
                    authorityMapper.insert(adminAuthority);
                } else if(role == Role.student) {
                    Authority studentAuthority = new Authority();
                    studentAuthority.setUserId(stored.getId());
                    studentAuthority.setRole(Role.student);
                    authorityMapper.insert(studentAuthority);
                } else if(role == Role.professor) {
                    Authority profAuthority = new Authority();
                    profAuthority.setUserId(stored.getId());
                    profAuthority.setRole(Role.professor);
                    authorityMapper.insert(profAuthority);
                }
            }


            System.out.println("user created :" + new Date());
            return true;
        }
        return false;
    }

    public Boolean signupAdmin(User user) {

        if(user.getUsername() == null || user.getPassword() ==  null)
            return false;

        if(isUniqueUsername(user.getUsername().trim())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userMapper.insert(user);
            user.getContact().setUserId(user.getId());
            Contact contact = user.getContact();
            contactMapper.insert(contact);

            Authority authority = new Authority();
            authority.setUserId(user.getId());
            authority.setRole(Role.user);
            authorityMapper.insert(authority);

            Authority adminAuthority = new Authority();
            adminAuthority.setUserId(user.getId());
            adminAuthority.setRole(Role.admin);
            authorityMapper.insert(adminAuthority);

            System.out.println("user created :" + new Date());
            return true;
        }
        return false;
    }

    public Boolean register(User user) {
        String number = user.getNumber();
        User stored = userMapper.findByNumber(number);
        if(stored != null)
            return false;
        userMapper.insert(user);
        user.getContact().setUserId(user.getId());
        Contact contact = user.getContact();
        contactMapper.insert(contact);
        return true;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username/password.");
        }
        List<Authority> authorities = authorityMapper.findByUserId(user.getId());
        user.setAuthorities(authorities);
        return user;
    }

    public boolean isUniqueUsername(String username) {
        return userMapper.findByUsername(username) == null;
    }

    public int getCompleteSemesterCount(int userId) {
        LinkedHashSet<Integer> semesterSet = studentCourseMapper.findSemesterIdByUserIdValid(userId);
        if(semesterSet == null) return 0;

        int completeSemesterCount = 0;
        for(Integer semesterId: semesterSet) {
            List<StudentCourse> validCourses = studentCourseMapper.findByUserIdSemesterIdValid(userId, semesterId);
            List<StudentCourse> allCourses = studentCourseMapper.findByUserIdSemesterId(userId, semesterId);
            if(validCourses == null || allCourses == null) continue;
            if(validCourses.size() == allCourses.size())
                completeSemesterCount++;
        }
        return completeSemesterCount;
    }

}
