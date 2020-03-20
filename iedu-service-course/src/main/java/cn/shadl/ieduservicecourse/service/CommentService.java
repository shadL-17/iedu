package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Comment;
import cn.shadl.ieducommonbeans.domain.dto.CommentFloorDTO;
import cn.shadl.ieduservicecourse.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    public List<Comment> findByTargetTypeAndTargetId(String targetType, Integer targetId) {
        return commentRepository.findByTargetTypeAndTargetId(targetType, targetId);
    }

    public List<CommentFloorDTO> transformToFloor(List<Comment> comments) {
        List<CommentFloorDTO> floors = new ArrayList<>();
        for (Comment comment : comments) {
            CommentFloorDTO floor = new CommentFloorDTO();
            floor.setUser(userService.findByUid(comment.getCreator()));
            floor.setContent(comment.getContent());
            floor.setDate(comment.getCreateDate());
            floors.add(floor);
        }
        return floors;
    }

    public List<CommentFloorDTO> getCommentFloor(List<Comment> comments) {
        List<CommentFloorDTO> floors = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getReplyTo()==null) {
                CommentFloorDTO floor = new CommentFloorDTO();
                floor.setUser(userService.findByUid(comment.getCreator()));
                floor.setContent(comment.getContent());
                floor.setDate(comment.getCreateDate());
                floor.setReplies(transformToFloor(commentRepository.findByReplyTo(comment.getCmid())));
                floors.add(floor);
            }
        }
        return floors;
    }
}
