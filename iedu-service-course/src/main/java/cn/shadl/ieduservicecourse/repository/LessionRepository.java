package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Lession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessionRepository extends JpaRepository<Lession, Long> {
    List<Lession> findByChid(Integer chid);
}
