package koreatech.cse.controller;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.repository.MajorMapper;
import koreatech.cse.repository.UserMapper;
import koreatech.cse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@Transactional
@RequestMapping("/")
public class HomeController {
    @Inject
    private UserService userService;
    @Inject
    private UserMapper userMapper;
    @Inject
    private MajorMapper majorMapper;


    @RequestMapping
    public String home(HttpSession session, @RequestParam(required = false) String rtl) {
        session.setAttribute("rtl", rtl);

        return "index";
    }

    @RequestMapping("/majorList")
    public String majorList(Model model, @RequestParam(required=false, defaultValue = "0") int divisionId,
                            @RequestParam(required=false, defaultValue = "true") boolean enabled,
                            @RequestParam(required=false, defaultValue = "0") int defaultSelected) {
        if(enabled) {
            if (divisionId == 0) {
                model.addAttribute("majorList", majorMapper.findAllEnabled());
            } else {
                model.addAttribute("majorList", majorMapper.findAllEnabledByDivisionId(divisionId));
            }
        } else {
            if (divisionId == 0) {
                model.addAttribute("majorList", majorMapper.findAll());
            } else {
                model.addAttribute("majorList", majorMapper.findAllByDivisionId(divisionId));
            }
        }
        model.addAttribute("defaultSelected", defaultSelected);
        return "include/majorOptions";
    }

    @RequestMapping("/profList")
    public String profList(Model model, @RequestParam(required=false, defaultValue = "0") int divisionId, @RequestParam(required=false, defaultValue = "0") int defaultSelected) {
        Searchable searchable = new Searchable();
        searchable.setDivision(divisionId);

        model.addAttribute("profList", userMapper.findByProfLookup(searchable));
        model.addAttribute("defaultSelected", defaultSelected);

        return "include/profOptions";
    }

    @RequestMapping("/jstlTest")
    public String emptyTest(Model model) {
        String a = null;
        String b = "";
        String c = "hello";
        List<String> d = new ArrayList<String>();
        List<String> e = new ArrayList<String>();
        e.add(a);
        e.add(b);

        model.addAttribute("a", a);
        model.addAttribute("b", b);
        model.addAttribute("c", c);
        model.addAttribute("d", d);
        model.addAttribute("e", e);

        List<String> stringArray = new ArrayList<String>();
        stringArray.add("one");
        stringArray.add("two");
        stringArray.add("three");
        model.addAttribute("stringArray", stringArray);

        Map<Integer, String> stringMap = new HashMap<Integer, String>();
        stringMap.put(1, "one");
        stringMap.put(2, "two");
        stringMap.put(3, "three");
        model.addAttribute("stringMap", stringMap);

        return "jstlTest";
    }

    @RequestMapping("/signin")
    public String signin(Model model, @RequestParam(required=false) String msg) {
        model.addAttribute("msg", msg);
        return "signin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        User signupUser = new User();
        model.addAttribute("signupUser", signupUser);

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute User signupUser, @RequestParam Role role, SessionStatus status) {

        System.out.println("signupUser = " + signupUser);
        System.out.println("role = " + role);
        userService.signup(signupUser, role);
        status.setComplete();

        return "redirect:/signin?msg=signupSuccess";
    }

}
