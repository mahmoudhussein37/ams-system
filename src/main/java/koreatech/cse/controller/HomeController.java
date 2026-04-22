package koreatech.cse.controller;

import koreatech.cse.domain.*;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.dto.SignupRequest;
import koreatech.cse.domain.constant.SecurityEventType;
import koreatech.cse.domain.constant.SigninMessage;
import koreatech.cse.domain.constant.SignupResult;
import koreatech.cse.domain.univ.Article;
import koreatech.cse.service.AdminService;
import koreatech.cse.service.AuthenticationAttemptService;
import koreatech.cse.service.board.BoardService;
import koreatech.cse.service.AuthenticationAuditService;
import koreatech.cse.service.FileAccessService;
import koreatech.cse.service.UserService;
import koreatech.cse.util.SystemUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.List;

@Controller
@Transactional
@RequestMapping("/")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Inject
    private UserService userService;
    @Inject
    private AuthenticationAttemptService authenticationAttemptService;
    @Inject
    private AuthenticationAuditService authenticationAuditService;
    @Inject
    private FileAccessService fileAccessService;
    @Inject
    private AdminService adminService;
    @Inject
    private BoardService boardService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {

        String[] boardTableNames = { "notice", "de", "hire", "schedule" };
        for (String b : boardTableNames) {
            List<Article> articleList = boardService.findArticleList("board_" + b, 6);
            model.addAttribute(b + "List", articleList);
        }
        model.addAttribute("home", true);

        return "index";
    }

    @RequestMapping(value = "/profList", method = RequestMethod.GET)
    public String profList(Model model, @RequestParam(required = false, defaultValue = "0") int divisionId,
            @RequestParam(required = false, defaultValue = "0") int defaultSelected) {
        Searchable searchable = new Searchable();
        searchable.setDivision(divisionId);

        model.addAttribute("profList", adminService.findProfessorBy(searchable));
        model.addAttribute("defaultSelected", defaultSelected);

        return "include/profOptions";
    }

    /*
     * /registerAdmin — disabled in production
     */
    @RequestMapping(value = "/registerAdmin", method = RequestMethod.GET)
    @ResponseBody
    public String registerAdmin(@RequestParam @SuppressWarnings("unused") String code) {
        // Suppress CodeQL unused-parameter: required by framework contract
        Objects.toString(code); // no-op reference
        return "disabled in production";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Model model, @RequestParam(required = false) String msg) {
        if (User.current() != null) {
            return "redirect:/";
        }
        SigninMessage signinMessage = SigninMessage.fromCode(StringUtils.trimToNull(msg));
        if (signinMessage != null) {
            model.addAttribute("msgKey", signinMessage.getSafeMessageKey());
        }

        return "signin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        if (User.current() != null) {
            return "redirect:/";
        }
        User signupUser = new User();
        model.addAttribute("signupUser", signupUser);

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute SignupRequest req,
            @RequestParam(value = "signupUser.passwordConfirm", required = false) String confirmPassword,
            SessionStatus status, HttpServletRequest request) {
        String number = StringUtils.trimToNull(req.getNumber());

        if (!authenticationAttemptService.checkSignupAllowed(request, number)) {
            status.setComplete();
            return "redirect:/signin?msg=" + SignupResult.TOO_MANY_ATTEMPTS.getCode();
        }

        User signupUser = new User();
        signupUser.setNumber(req.getNumber());
        signupUser.setUsername(req.getUsername());
        signupUser.setPassword(req.getPassword());
        Contact contact = new Contact();
        contact.setFirstName(req.getContact() != null ? req.getContact().getFirstName() : null);
        contact.setLastName(req.getContact() != null ? req.getContact().getLastName() : null);
        signupUser.setContact(contact);

        SignupResult result = userService.signup(signupUser, confirmPassword);
        authenticationAttemptService.recordSignupResult(request, number, result);
        authenticationAuditService.log(result == SignupResult.SUCCESS ? SecurityEventType.SIGNUP_SUCCESS : SecurityEventType.SIGNUP_FAILED,
                number, request, result.name());
        status.setComplete();

        return "redirect:/signin?msg=" + toSafeSigninMessageCode(result);
    }

    private String toSafeSigninMessageCode(SignupResult result) {
        if (result == SignupResult.SUCCESS) {
            return SignupResult.SUCCESS.getCode();
        }
        if (result == SignupResult.TOO_MANY_ATTEMPTS) {
            return SignupResult.TOO_MANY_ATTEMPTS.getCode();
        }
        return SigninMessage.LOGIN_FAILED_GENERIC.getCode();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback(Model model) {
        Feedback feedback = new Feedback();
        model.addAttribute("feedback", feedback);

        return "feedback";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public String feedback(@ModelAttribute Feedback feedback, SessionStatus status) {
        adminService.insertFeedback(feedback);
        status.setComplete();

        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_PROFESSOR', 'ROLE_ADMIN')")
    @Transactional(readOnly = true)
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam int uploadedFileId)
            throws IOException {
        UploadedFile uploadedFile = adminService.findUploadedFileById(uploadedFileId);
        if (uploadedFile != null) {
            fileAccessService.assertUserCanAccessFile(User.current(), uploadedFile);
            File file = new File(uploadedFile.getPath());
            if (!file.exists() || !file.isFile()) {
                logger.error("DOWNLOAD FAILED — file missing on disk | fileId={} | path={}",
                        uploadedFile.getId(), uploadedFile.getPath());
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "Requested file is no longer available.");
                return;
            }
            this.handleDownloadFile(request, response, file, uploadedFile.getName());
        }
    }

    private void handleDownloadFile(HttpServletRequest request, HttpServletResponse response, File file,
            String downloadFileName) {
        response.setContentLength((int) file.length());
        response.setHeader("Content-type", "application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setIntHeader("Expires", 0);
        FileInputStream fis = null;
        try {
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + SystemUtil.getDocName(request, downloadFileName));
            OutputStream out = response.getOutputStream();
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
            out.flush();
        } catch (IOException e) {
            logger.error("DOWNLOAD FAILED - error streaming file | filename={}", downloadFileName, e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        return "redirect:/signout";
    }

}
