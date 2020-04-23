package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Lession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessionRepository extends JpaRepository<Lession, Long> {

    List<Lession> findByLid(Integer lid);

    List<Lession> findByChid(Integer chid);

    @Query(nativeQuery = true, value = "SELECT * FROM lession WHERE chid IN (SELECT chid FROM chapter WHERE cid=:#{#cid})")
    List<Lession> findByCid(Integer cid);

    @Query(nativeQuery = true, value = "SELECT cid FROM chapter WHERE chid IN (SELECT chid FROM lession WHERE lid=:#{#lid})")
    Integer getCidBelong(Integer lid);
}
