package koreatech.cse.service.board;


import koreatech.cse.domain.univ.Article;
import koreatech.cse.repository.BoardMapper;
import koreatech.cse.util.mybatis.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Service
public class BoardService {
    @Inject
    private BoardMapper boardMapper;

    public Article read(int articleId, String boardTableName) {
        return boardMapper.findOne(boardTableName, articleId);
    }

    public void incrementHit(int articleId, String boardTableName) {
        boardMapper.updateHit(boardTableName, articleId);
    }

    @Transactional
    public void insert(Article article, String boardTableName) throws IOException {
        boardMapper.insert(boardTableName, article);
    }





    @Transactional
    public void update(Article article, String boardTableName) throws IOException {
        boardMapper.update(boardTableName, article);
        //noticeBoardFileUpload(article);
    }

    @Transactional
    public void delete(int articleId, String boardTableName) {
        boardMapper.delete(boardTableName, articleId);
    }

    public List<Article> findArticleList(String boardTableName, int limit) {
        return boardMapper.findArticleList(boardTableName, limit);
    }

    public List<Article> findArticleAll(String boardTableName) {
        return boardMapper.findArticleAll(boardTableName);
    }

    public List<Article> findArticleListAjax(Pageable pageable) {
        return boardMapper.findArticleListAjax(pageable);
    }

    public int countArticleListAjax(Pageable pageable) {
        return boardMapper.countArticleListAjax(pageable);
    }
}
