package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Question;
import cn.shadl.ieducommonbeans.domain.dto.ExamQuestionDTO;
import cn.shadl.ieduservicecourse.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> findByQid(Integer qid) {
        return questionRepository.findByQid(qid);
    }

    public List<ExamQuestionDTO> findByEid(Integer eid) {
        return getQuestionList(questionRepository.findByEid(eid));
    }

    private List<ExamQuestionDTO> getQuestionList(List<Question> questions) {
        if(questions==null || questions.isEmpty()) {
            return null;
        }
        List<ExamQuestionDTO> list = new ArrayList<>();
        for (int i=0; i<questions.size();i++) {
            Question question = questions.get(i);
            ExamQuestionDTO qt = new ExamQuestionDTO();
            qt.setQid(question.getQid());
            qt.setQuestion(question.getQuestion());
            if (question.getOption()==null || question.getOption().trim().equals("")) {
                qt.setType("blank");
                qt.setOptions(null);
                list.add(qt);
                continue;
            }
            else if(question.getAnswer().contains("&")) {
                qt.setType("multiple");
            }
            else {
                qt.setType("single");
            }
            Set<String> optionSet = new HashSet<>();
            String[] optionArray = question.getOption().split("\\|");
            for (String o : optionArray) {
                optionSet.add(o);
            }
            qt.setOptions(optionSet);
            list.add(qt);
        }
        return list;
    }
}
