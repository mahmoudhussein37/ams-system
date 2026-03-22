package koreatech.cse.service.board;


import koreatech.cse.domain.univ.Article;
import koreatech.cse.repository.BoardMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;

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
}
