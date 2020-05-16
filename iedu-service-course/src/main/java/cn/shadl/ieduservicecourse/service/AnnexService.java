package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Annex;
import cn.shadl.ieduservicecourse.repository.AnnexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnexService {

    @Autowired
    private AnnexRepository annexRepository;

    public List<Annex> findByLid(Integer lid) {
        return annexRepository.findByLid(lid);
    }
}
