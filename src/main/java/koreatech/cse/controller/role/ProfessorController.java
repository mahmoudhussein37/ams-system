package koreatech.cse.controller.role;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.univ.Division;
import koreatech.cse.domain.univ.Major;
import koreatech.cse.repository.DivisionMapper;
import koreatech.cse.repository.MajorMapper;
import koreatech.cse.repository.UserMapper;
import koreatech.cse.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_PROFESSOR')")
@RequestMapping("/professor")
public class ProfessorController {
    @Inject
    private UserMapper userMapper;
    @Inject
    private DivisionMapper divisionMapper;
    @Inject
    private MajorMapper majorMapper;
    @Inject
    private UserService userService;



    @RequestMapping("/studentLookup")
    public String studentLookup(Model model) {

        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);







        return "role/professor/student-lookup";
    }

    @RequestMapping("/studentTable")
    public String studentTable(Model model, @RequestParam(required=false) String number,
                               @RequestParam(required=false) String name,
                               @RequestParam(defaultValue = "0", required=false) int division,
                               @RequestParam(defaultValue = "0", required=false) int major) {

        Searchable searchable = new Searchable();
        searchable.setNumber(number);
        searchable.setName(name);
        searchable.setDivision(division);
        searchable.setMajor(major);
        List<User> userList = userMapper.findByStudentLookup(searchable);
        model.addAttribute("userList", userList);
        User firstUser = null;
        for(User user: userList) {
            firstUser = user;
            break;
        }

        model.addAttribute("firstUser", firstUser);
        return "role/professor/student-table";
    }

    @RequestMapping("/studentDetail")
    public String studentDetail(Model model, @RequestParam int studentId) {
        User studentUser = userMapper.findOne(studentId);
        model.addAttribute("studentUser", studentUser);
        return "role/professor/student-detail";
    }

    @RequestMapping("/counseling")
    public String counseling(Model model) {
        List<Division> divisions = divisionMapper.findAll();
        List<Major> majors = majorMapper.findAll();

        model.addAttribute("divisions", divisions);
        model.addAttribute("majors", majors);

        return "role/professor/counseling";
    }

}
