package koreatech.cse.controller.board;


import koreatech.cse.domain.User;
import koreatech.cse.domain.univ.Article;
import koreatech.cse.repository.BoardMapper;
import koreatech.cse.service.board.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes({"article"})
@Transactional
@RequestMapping(value = "/admin/board/de")
public class DepartmentNoticeBoardController {
    @Inject
    private BoardService boardService;
    @Inject
    private BoardMapper boardMapper;

    final static String boardName = "de";
    final static String boardTableName = "board_de";

    @RequestMapping(value = "/form")
    public String form(Model model) {
        model.addAttribute("article", new Article());
        return "role/common/board/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String write(Article article, SessionStatus sessionStatus) throws IOException {
        article.setUserId(User.current().getId());
        System.out.println("article = " + article);
        boardService.insert(article, boardTableName);
        sessionStatus.setComplete();

        return "redirect:/admin/board/" + boardName + "/list";
    }


    @RequestMapping(value = "/list")
    public String list(Model model) {
        List<Article> articleList = boardMapper.findArticleList(boardTableName, 50);
        model.addAttribute("articleList", articleList);
        return "role/common/board/list";
    }

    @RequestMapping(value = "/{articleId}")
    public String read(Model model, @PathVariable int articleId) {
        model.addAttribute("article", boardService.read(articleId, boardTableName));
        return "role/common/board/article";
    }



    @RequestMapping(value = "/edit/{articleId}")
    public String editForm(Model model, @PathVariable int articleId) {
        Article article = boardMapper.findOne(boardTableName, articleId);
        model.addAttribute("article", article);
        model.addAttribute("boardName", boardName);
        return "role/common/board/edit";
    }

    @RequestMapping(value = "/edit/{articleId}", method = RequestMethod.POST)
    public String update(@PathVariable int articleId, Article article, BindingResult bindingResult, SessionStatus sessionStatus) throws IOException {
        boardService.update(article, boardTableName);
        sessionStatus.setComplete();
        return "redirect:/admin/board/" + boardName + "/list";
    }

    @RequestMapping(value = "/delete/{articleId}", method = RequestMethod.POST)
    public String delete(@PathVariable int articleId) {
        boardMapper.delete(boardTableName, articleId);
        return "redirect:/admin/board/" + boardName + "/list";
    }
}
