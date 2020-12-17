package koreatech.cse.controller;

import koreatech.cse.domain.*;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.univ.Article;
import koreatech.cse.repository.BoardMapper;
import koreatech.cse.repository.FeedbackMapper;
import koreatech.cse.repository.UploadedFileMapper;
import koreatech.cse.repository.UserMapper;
import koreatech.cse.service.UserService;
import koreatech.cse.util.SystemUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/")
public class HomeController {
    @Inject
    private UserService userService;
    @Inject
    private UserMapper userMapper;
    @Inject
    private FeedbackMapper feedbackMapper;
    @Inject
    private UploadedFileMapper uploadedFileMapper;
    @Inject
    private BoardMapper boardMapper;


    @RequestMapping
    public String home(Model model) {

        String[] boardTableNames = {"notice", "de", "hire", "schedule"};
        for(String b: boardTableNames) {
            List<Article> articleList = boardMapper.findArticleList("board_" + b, 6);
            model.addAttribute(b + "List", articleList);
        }
        model.addAttribute("home", true);

        return "index";
    }
    @RequestMapping("/profList")
    public String profList(Model model, @RequestParam(required=false, defaultValue = "0") int divisionId, @RequestParam(required=false, defaultValue = "0") int defaultSelected) {
        Searchable searchable = new Searchable();
        searchable.setDivision(divisionId);

        model.addAttribute("profList", userMapper.findProfessorBy(searchable));
        model.addAttribute("defaultSelected", defaultSelected);

        return "include/profOptions";
    }

    @RequestMapping("/registerAdmin")
    @ResponseBody
    public String registerAdmin(@RequestParam String code) {
        if(code.equals("c57c-496e-b71a-05e6")) {
            User user = new User();
            Contact contact = new Contact();
            contact.setFirstName("Admin");
            contact.setLastName("Admin");
            user.setUsername("admin@admin.org");
            user.setPassword("testadmin1234!");
            user.setConfirm(true);
            user.setEnabled(true);
            user.setContact(contact);
            userService.signupAdmin(user);

        }

        return "success";
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

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback(Model model) {
        Feedback feedback = new Feedback();
        model.addAttribute("feedback", feedback);

        return "feedback";
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public String feedback(@ModelAttribute Feedback feedback, SessionStatus status) {
        feedbackMapper.insert(feedback);
        status.setComplete();

        return "redirect:/";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam int uploadedFileId) throws IOException {
        UploadedFile uploadedFile = uploadedFileMapper.findOne(uploadedFileId);
        if (uploadedFile != null) {
            File file = new File(uploadedFile.getPath());
            this.handleDownloadFile(request, response, file, uploadedFile.getName());
        }
    }

    private void handleDownloadFile(HttpServletRequest request, HttpServletResponse response, File file, String downloadFileName) {
        response.setContentLength((int) file.length());
        response.setHeader("Content-type", "application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        FileInputStream fis = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + SystemUtil.getDocName(request, downloadFileName));
            OutputStream out = response.getOutputStream();
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

}
