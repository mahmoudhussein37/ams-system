package koreatech.cse.controller.role;

import koreatech.cse.domain.User;
import koreatech.cse.repository.UserMapper;
import koreatech.cse.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_PROFESSOR')")
@RequestMapping("/professor")
public class ProfessorController {
    @Inject
    private UserMapper userMapper;
    @Inject
    private UserService userService;



    @RequestMapping("/studentLookup")
    public String studentLookup(Model model) {
        List<User> userList = userMapper.findAll();
        model.addAttribute("userList", userList);
        return "role/professor/studentLookup";
    }



}
