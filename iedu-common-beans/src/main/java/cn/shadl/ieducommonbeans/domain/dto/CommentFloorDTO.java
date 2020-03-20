package cn.shadl.ieducommonbeans.domain.dto;

import cn.shadl.ieducommonbeans.domain.User;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class CommentFloorDTO {
    private User user;
    private String content;
    private Date date;
    private List<CommentFloorDTO> replies;
}
