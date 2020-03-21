package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQid(Integer qid);
    List<Question> findByEid(Integer eid);
}
