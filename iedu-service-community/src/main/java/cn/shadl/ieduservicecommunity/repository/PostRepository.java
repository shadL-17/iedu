package cn.shadl.ieduservicecommunity.repository;

import cn.shadl.ieducommonbeans.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPid(Integer pid);

    //查找所有置顶主题帖
    @Query(nativeQuery = true, value = "select * from post where (status='top' or status='top-recommended') and pid=parent")
    List<Post> findTopThemes();

    //查找所有非置顶主题帖
    @Query(nativeQuery = true, value = "select * from post where status!='top' and status!='top-recommended' and pid=parent order by last_update_date desc")
    List<Post> findNoneTopThemes();

    //查找主题帖下所有回复
    List<Post> findByParentOrderByFloorAsc(Integer parent);

    //查询主题帖的回复数
    @Query(nativeQuery = true, value = "select count(pid) from post where parent=:#{#parent}")
    Integer findAmountsOfReplies(Integer parent);

    //查找用户所有帖子
    List<Post> findByCreator(Integer creator);

}
