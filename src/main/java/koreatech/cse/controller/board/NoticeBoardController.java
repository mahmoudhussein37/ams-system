package koreatech.cse.controller.board;


import koreatech.cse.domain.univ.Article;
import koreatech.cse.repository.BoardMapper;
import koreatech.cse.service.board.BoardService;
import koreatech.cse.service.board.NoticeBoardService;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Controller
@Transactional
@RequestMapping(value = "/admin/board/notice")
public class NoticeBoardController {
    @Inject
    private NoticeBoardService noticeBoardService;
    @Inject
    private BoardService boardService;
    @Inject
    private BoardMapper boardMapper;

    final static String boardFilePrefix = "notice";
    final static String boardTableName = "board_notice";
    final static String boardUrl = "/board/notice";

    @RequestMapping(value = "/form")
    public String form(Model model) {
        model.addAttribute("article", new Article());
        return "board/notice/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String write(Article article, BindingResult bindingResult, SessionStatus sessionStatus) throws IOException {
        if (bindingResult.hasErrors()) {
            return "/board/notice/form";
        } else {
            noticeBoardService.insert(article);
            sessionStatus.setComplete();
        }

        return "redirect:/board/notice";
    }


    @RequestMapping(value = "")
    public String list(Model model, @RequestParam(value = "s_type", required = false) String searchType, @RequestParam(value = "s_word", required = false) String searchWord,
            @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        List<Article> result;
        if (page < 1) {
            page = 1;
        }

        if (StringUtils.isNotBlank(searchType) && StringUtils.isNotBlank(searchWord)) {
            result = boardMapper.findArticleListByLike(boardTableName, searchType, searchWord, (page - 1) * size, size);
            model.addAttribute("s_type", searchType);
            model.addAttribute("s_word", searchWord);
        } else {
            result = boardMapper.findArticleList(boardTableName, (page - 1) * size, size);
        }
        model.addAttribute("articles", result);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("firstPager", boardService.makeFirstPager(boardUrl, size, searchType, searchWord));
        model.addAttribute("listPager", boardService.makeListPager(boardTableName, boardUrl, 0, size, 0, searchType, searchWord));
        model.addAttribute("lastPager", boardService.makeLastPager(boardTableName, boardUrl, size, searchType, searchWord));
        return "board/notice/list";
    }

    @RequestMapping(value = "/{articleId}")
    public String read(Model model, @PathVariable int articleId) {
        model.addAttribute("article", noticeBoardService.read(articleId));
        return "board/notice/article";
    }



    @RequestMapping(value = "/{articleId}/edit")
    public String editForm(Model model, @PathVariable int articleId) {
        Article article = boardMapper.findOne(boardTableName, articleId);
        model.addAttribute("article", article);
        return "board/notice/form";
    }

    @RequestMapping(value = "/{articleId}/edit", method = RequestMethod.POST)
    public String update(@PathVariable int articleId, Article article, BindingResult bindingResult, SessionStatus sessionStatus) throws IOException {
        if (bindingResult.hasErrors()) {
            return "/board/notice/form";
        } else {
            noticeBoardService.update(article);
            sessionStatus.setComplete();
        }
        return "redirect:/board/notice";
    }

    @RequestMapping(value = "/{articleId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable int articleId) {
        boardMapper.deleteById(boardTableName, articleId);
        return "redirect:/board/notice";
    }
}
