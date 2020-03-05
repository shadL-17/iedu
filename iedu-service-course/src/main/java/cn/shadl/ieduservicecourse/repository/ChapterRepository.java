package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByCid(Integer cid);
}
