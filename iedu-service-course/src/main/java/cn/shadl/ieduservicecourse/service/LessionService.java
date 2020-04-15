package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Exam;
import cn.shadl.ieducommonbeans.domain.Lession;
import cn.shadl.ieduservicecourse.repository.ExamRepository;
import cn.shadl.ieduservicecourse.repository.LessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessionService {

    @Autowired
    private LessionRepository lessionRepository;

    @Autowired
    private ExamRepository examRepository;

    public Lession findByLid(Integer lid) {
        List<Lession> lessions = lessionRepository.findByLid(lid);
        return (lessions!=null&&!lessions.isEmpty()) ? lessions.get(0) : null;
    }

    public List<Lession> findByChid(Integer chid) {
        return lessionRepository.findByChid(chid);
    }

    public List<Lession> findByCid(Integer cid) {
        return lessionRepository.findByCid(cid);
    }

    public Integer getLessionNumInCourse(Integer lid, Integer cid) {//获取课目在课程中的顺序序号（包括测验）
        try{
            Lession currentLession = findByLid(lid);
            List<Lession> lessions = lessionRepository.findByCid(cid);
            List<Exam> exams = examRepository.findByCid(cid);
            Integer lessionNo = 1;
            Integer preExamsNum = 0;
            if(exams!=null && !exams.isEmpty()) {
                for(Exam exam : exams) {
                    if (exam.getChid()<currentLession.getChid()) {
                        preExamsNum++;
                    }
                }
            }
            for(Lession lession : lessions) {
                if(lid==lession.getLid()) {
                    return lessionNo + preExamsNum;
                }
                else {
                    lessionNo++;
                }
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

}
