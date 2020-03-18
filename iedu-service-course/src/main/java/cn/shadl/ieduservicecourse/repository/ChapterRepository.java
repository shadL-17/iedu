package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByCid(Integer cid);
    List<Chapter> findByChid(Integer chid);

    @Query(nativeQuery = true, value = "select count(*) from lession where chid=:#{#chid}")
    Integer getNumberOfLessions(Integer chid);
}
