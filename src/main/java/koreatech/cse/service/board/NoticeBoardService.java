package koreatech.cse.service.board;


import koreatech.cse.domain.univ.Article;
import koreatech.cse.repository.BoardMapper;
import koreatech.cse.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class NoticeBoardService {
    @Inject
    FileService fileService;

    @Inject
    BoardMapper boardMapper;


    final static String boardFilePrefix = "notice";
    final static String boardTableName = "board_notice";
    final static String boardUrl = "/board/notice";
    @Value("${file.uploadPath}")
    private String originPath;

    public Article read(int articleId) {
        Article article = boardMapper.findOne(boardTableName, articleId);
        boardMapper.updateHit(boardTableName, articleId);
        return article;
    }

    @Transactional
    public void insert(Article article) throws IOException {
        boardMapper.insert(boardTableName, article);
        //noticeBoardFileUpload(article);
    }

    /*private void noticeBoardFileUpload(Article article) throws IOException {
        int articleId = article.getId();
        List<MultipartFile> files = article.getFiles();
        for (MultipartFile file : files) {
            if (file.getBytes().length == 0) {
                continue;
            }
            StringBuilder pathBuilder = new StringBuilder(originPath);
            pathBuilder.append(boardFilePrefix);
            pathBuilder.append(File.separator);
            pathBuilder.append(articleId);
            File originDir = new File(pathBuilder.toString());
            if (!originDir.exists()) {
                originDir.mkdirs();
            }

            File newFile = new File(originDir + File.separator + file.getOriginalFilename());
            file.transferTo(newFile);

            BoardFile boardFile = new BoardFile();
            boardFile.setArticleId(articleId);
            boardFile.setName(file.getOriginalFilename());
            boardFile.setPath(newFile.getAbsolutePath());
            boardFile.setUploaderId(User.current().getUserId());

            noticeBoardFileMapper.insert(boardFile);
        }
    }

    public Path getPathByArticleIdAndFileName(int articleId, int fileId) {
        String pathString = noticeBoardFileMapper.findPathByArticleIdAndFileId(articleId, fileId);
        return Paths.get(pathString);
    }

     */


    @Transactional
    public void update(Article article) throws IOException {
        boardMapper.update(boardTableName, article);
        //noticeBoardFileUpload(article);
    }
}
