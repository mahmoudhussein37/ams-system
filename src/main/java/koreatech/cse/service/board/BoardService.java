package koreatech.cse.service.board;

import koreatech.cse.domain.univ.Pager;
import koreatech.cse.repository.BoardMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Inject
    BoardMapper boardMapper;

    public Pager makeFirstPager(String boardUrl, int size, String searchType, String searchWord) {
        Pager firstPager = new Pager();
        firstPager.setFirstText();
        if (StringUtils.isNotBlank(searchType) && StringUtils.isNotBlank(searchWord)) {
            firstPager.setLink(String.format("%s?page=%d&size=%d&", boardUrl, 1, size) + "&s_type=" + searchType + "&s_word=" + searchWord);
        } else

            firstPager.setLink(String.format("%s?page=%d&size=%d", boardUrl, 1, size));
        return firstPager;
    }

    public List<Pager> makeListPager(String boardTableName, String boardUrl, int currentOffset, int pageSize, int pagerSize, String searchType, String searchWord) {
        int countAll;
        if (StringUtils.isNotBlank(searchType) && StringUtils.isNotBlank(searchWord)) {
            countAll = boardMapper.countAllByLike(boardTableName, searchType, searchWord);
        } else {
            countAll = boardMapper.countAll(boardTableName);
        }

        List<Pager> result = new ArrayList<>();
        for (int page = 1; (page - 1) * pageSize < countAll; page++) {
            Pager pager = new Pager();
            pager.setText(Integer.toString(page));
            if (StringUtils.isNotBlank(searchType) && StringUtils.isNotBlank(searchWord)) {
                pager.setLink(String.format("%s?page=%d&size=%d", boardUrl, page, pageSize) + "&s_type=" + searchType + "&s_word=" + searchWord);
            } else
                pager.setLink(String.format("%s?page=%d&size=%d", boardUrl, page, pageSize));
            result.add(pager);
        }
        return result;
    }

    public Pager makeLastPager(String boardTableName, String boardUrl, int size, String searchType, String searchWord) {
        int countAll;
        if (StringUtils.isNotBlank(searchType) && StringUtils.isNotBlank(searchWord)) {
            countAll = boardMapper.countAllByLike(boardTableName, searchType, searchWord);
        } else {
            countAll = boardMapper.countAll(boardTableName);
        }
        Pager lastPager = new Pager();
        lastPager.setLastText();
        int page = 1;
        while ((page - 1) * size + size < countAll) {
            page++;
        }
        if (StringUtils.isNotBlank(searchType) && StringUtils.isNotBlank(searchWord)) {
            lastPager.setLink(String.format("%s?page=%d&size=%d", boardUrl, page, size) + "&s_type=" + searchType + "&s_word=" + searchWord);
        } else
            lastPager.setLink(String.format("%s?page=%d&size=%d", boardUrl, page, size));
        return lastPager;
    }
}
