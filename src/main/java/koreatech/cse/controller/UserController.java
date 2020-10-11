package koreatech.cse.controller;

import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.User;
import koreatech.cse.repository.UserMapper;
import koreatech.cse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Controller

@RequestMapping("/user")
public class UserController {
    @Inject
    private UserMapper userMapper;
    @Inject
    private UserService userService;




    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam int id, Model model) {
        model.addAttribute("user", userMapper.findOne(id));
        return "edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute User user) {
        System.out.println("user = " + user);
        userMapper.update(user);

        return "redirect:/user/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam int id) {
        userMapper.delete(id);
        return "redirect:/user/list";
    }


    @RequestMapping("/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping(value="/signinSuccess")
    public String signinSuccess() {
        System.out.println("signin Success");
        return "redirect:/";
    }

    @RequestMapping(value="/signinFailed")
    public String signinFailed() {
        System.out.println("signin Failed");
        return "redirect:/";
    }


}
