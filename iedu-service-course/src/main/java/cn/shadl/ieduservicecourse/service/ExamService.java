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

    public Exam findByEid(Integer eid) {
        List<Exam> exams = examRepository.findByEid(eid);
        return (exams!=null&&!exams.isEmpty()) ? exams.get(0) : null;
    }

    public List<Exam> findByChid(Integer chid) {
        return examRepository.findByChid(chid);
    }
}
