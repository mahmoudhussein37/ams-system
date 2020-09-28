package koreatech.cse.service;

import koreatech.cse.domain.Authority;
import koreatech.cse.domain.Contact;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.repository.AuthorityMapper;
import koreatech.cse.repository.ContactMapper;
import koreatech.cse.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
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


    public Boolean signup(User user) {

        if(user.getUsername() == null || user.getPassword() ==  null)
            return false;

        if(isUniqueUsername(user.getUsername().trim())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setConfirm(true);
            user.setDivisionId(1);
            user.setNumber(UUID.randomUUID().toString().substring(9, 28));
            user.setMajorId(1);


            userMapper.insert(user);
            user.getContact().setUserId(user.getId());
            Contact contact = user.getContact();
            contactMapper.insert(contact);

            Authority authority = new Authority();
            authority.setUserId(user.getId());
            authority.setRole(Role.user);
            authorityMapper.insert(authority);

            //TODO: remove
            if(user.getUsername().contains("admin")) {
                Authority adminAuthority = new Authority();
                adminAuthority.setUserId(user.getId());
                adminAuthority.setRole(Role.admin);
                authorityMapper.insert(adminAuthority);
            } else if(user.getUsername().contains("student")) {
                Authority adminAuthority = new Authority();
                adminAuthority.setUserId(user.getId());
                adminAuthority.setRole(Role.student);
                authorityMapper.insert(adminAuthority);
            } else if(user.getUsername().contains("professor")) {
                Authority adminAuthority = new Authority();
                adminAuthority.setUserId(user.getId());
                adminAuthority.setRole(Role.professor);
                authorityMapper.insert(adminAuthority);
            }

            System.out.println("user created :" + new Date());
            return true;
        }
        return false;


    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username/password.");
        }
        List<Authority> authorities = authorityMapper.findByUserId(user.getId());
        user.setAuthorities(authorities);
        System.out.println("user = " + user);
        return user;
    }

    public boolean isUniqueUsername(String username) {
        return userMapper.findByUsername(username) == null;
    }

}
