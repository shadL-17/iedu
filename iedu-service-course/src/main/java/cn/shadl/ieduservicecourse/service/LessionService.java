package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Lession;
import cn.shadl.ieduservicecourse.repository.LessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessionService {

    @Autowired
    private LessionRepository lessionRepository;

    public Lession findByLid(Integer lid) {
        List<Lession> lessions = lessionRepository.findByLid(lid);
        return (lessions!=null&&!lessions.isEmpty()) ? lessions.get(0) : null;
    }

    public List<Lession> findByChid(Integer chid) {
        return lessionRepository.findByChid(chid);
    }
}
