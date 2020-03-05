package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Exam;
import cn.shadl.ieduservicecourse.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    ExamRepository examRepository;

    public List<Exam> findByChid(Integer chid) {
        return examRepository.findByChid(chid);
    }
}
