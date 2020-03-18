package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Chapter;
import cn.shadl.ieduservicecourse.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    public Chapter findByChid(Integer chid) {
        List<Chapter> chapters = chapterRepository.findByChid(chid);
        return (chapters!=null&&!chapters.isEmpty()) ? chapters.get(0) : null;
    }

    public List<Chapter> findByCid(Integer cid) {
        return chapterRepository.findByCid(cid);
    }
}
