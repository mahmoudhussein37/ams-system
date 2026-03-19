package koreatech.cse.controller.board;


import koreatech.cse.datatables.DataTablesRequest;
import koreatech.cse.datatables.DataTablesResponse;
import koreatech.cse.domain.User;
import koreatech.cse.domain.univ.Article;
import koreatech.cse.repository.BoardMapper;
import koreatech.cse.service.board.BoardService;
import koreatech.cse.util.mybatis.Pageable;
import koreatech.cse.util.mybatis.PaginationHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes({"article"})
@Transactional
@RequestMapping(value = "/board/{boardName}")
public class BoardController {
    @Inject
    private BoardService boardService;
    @Inject
    private BoardMapper boardMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form(Model model) {
        model.addAttribute("article", new Article());
        return "role/common/board/form";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String write(Article article, SessionStatus sessionStatus, @PathVariable String boardName) throws IOException {
        article.setUserId(User.current().getId());
        System.out.println("article = " + article);
        boardService.insert(article, getBoardTableName(boardName));
        sessionStatus.setComplete();

        return "redirect:/board/" + boardName + "/list";
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, @PathVariable String boardName, @RequestParam(required = false) boolean all) {
        List<Article> articleList;
        if(all) {
            articleList = boardMapper.findArticleAll(getBoardTableName(boardName));
        } else {
            articleList = boardMapper.findArticleList(getBoardTableName(boardName), 50);
        }

        model.addAttribute("articleList", articleList);
        return "role/common/board/list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/listAjax", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public DataTablesResponse getList(@RequestBody DataTablesRequest datatablesRequest, @PathVariable String boardName) {
        int recordsTotal;
        int recordsFiltered;
        Pageable pageable = PaginationHelper.pageable(datatablesRequest);
        pageable.setTableName(getBoardTableName(boardName));
        List<Article> articleList = boardMapper.findArticleListAjax(pageable);
        recordsTotal = recordsFiltered = boardMapper.countArticleListAjax(pageable);
        DataTablesResponse datatablesResponse = new DataTablesResponse();
        datatablesResponse.setDraw(datatablesRequest.getDraw());
        datatablesResponse.setRecordsTotal(recordsTotal);
        datatablesResponse.setRecordsFiltered(recordsFiltered);
        datatablesResponse.setData(articleList);
        return datatablesResponse;
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public String read(Model model, @PathVariable int articleId, @PathVariable String boardName) {
        model.addAttribute("article", boardService.read(articleId, getBoardTableName(boardName)));
        return "role/common/board/article";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/edit/{articleId}", method = RequestMethod.GET)
    public String editForm(Model model, @PathVariable int articleId, @PathVariable String boardName) {
        Article article = boardMapper.findOne(getBoardTableName(boardName), articleId);
        model.addAttribute("article", article);
        model.addAttribute("boardName", boardName);
        return "role/common/board/edit";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/edit/{articleId}", method = RequestMethod.POST)
    public String update(@PathVariable @SuppressWarnings("unused") int articleId, Article article, SessionStatus sessionStatus, @PathVariable String boardName) throws IOException {
        boardService.update(article, getBoardTableName(boardName));
        sessionStatus.setComplete();
        return "redirect:/board/" + boardName + "/list";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete/{articleId}", method = RequestMethod.POST)
    public String delete(@PathVariable int articleId, @PathVariable String boardName) {
        boardMapper.delete(getBoardTableName(boardName), articleId);
        return "redirect:/board/" + boardName + "/list";
    }

    public String getBoardTableName(String boardName) {
        return "board_" + boardName;
    }
}
