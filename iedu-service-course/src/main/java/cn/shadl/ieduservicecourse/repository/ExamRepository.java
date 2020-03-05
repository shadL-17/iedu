package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByChid(Integer chid);
}
