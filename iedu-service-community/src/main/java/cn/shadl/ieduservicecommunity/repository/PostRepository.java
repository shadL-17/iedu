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
    @Query(nativeQuery = true, value = "select * from post where status!='top' and status!='top-recommended' and pid=parent")
    List<Post> findNoneTopThemes();

    //查找主题帖下所有回复
    List<Post> findByParentOrderByFloorAsc(Integer parent);
}
