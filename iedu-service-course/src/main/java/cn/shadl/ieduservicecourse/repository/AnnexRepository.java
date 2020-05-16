package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Annex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnexRepository extends JpaRepository<Annex, Integer> {
    List<Annex> findByLid(Integer lid);
}
