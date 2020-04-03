package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByEid(Integer eid);
    List<Exam> findByChid(Integer chid);
    @Query(nativeQuery = true, value = "SELECT * FROM exam WHERE chid IN (SELECT chid FROM chapter WHERE cid=:#{#cid})")
    List<Exam> findByCid(Integer cid);
}
