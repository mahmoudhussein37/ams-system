package koreatech.cse.controller;

import koreatech.cse.domain.Feedback;
import koreatech.cse.domain.Searchable;
import koreatech.cse.domain.UploadedFile;
import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Role;
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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    private FeedbackMapper feedbackMapper;
    @Inject
    private UploadedFileMapper uploadedFileMapper;


    @RequestMapping
    public String home(HttpSession session) {

        return "index";
    }

    /*@RequestMapping("/majorList")
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
    }*/

    @RequestMapping("/profList")
    public String profList(Model model, @RequestParam(required=false, defaultValue = "0") int divisionId, @RequestParam(required=false, defaultValue = "0") int defaultSelected) {
        Searchable searchable = new Searchable();
        searchable.setDivision(divisionId);

        model.addAttribute("profList", userMapper.findProfessorBy(searchable));
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
