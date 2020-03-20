package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTargetTypeAndTargetId(String targetType, Integer targetId);
    List<Comment> findByReplyTo(Integer cmid);
}
