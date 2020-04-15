package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.*;
import cn.shadl.ieduservicecourse.repository.ExamRepository;
import cn.shadl.ieduservicecourse.repository.LessionRepository;
import cn.shadl.ieduservicecourse.repository.QuestionRepository;
import cn.shadl.ieduservicecourse.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private LessionRepository lessionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private ChapterService chapterService;

    public Exam findByEid(Integer eid) {
        List<Exam> exams = examRepository.findByEid(eid);
        return (exams!=null&&!exams.isEmpty()) ? exams.get(0) : null;
    }

    public List<Exam> findByChid(Integer chid) {
        return examRepository.findByChid(chid);
    }

    public Integer getCourseIdByEid(Integer eid) {
        Exam exam = findByEid(eid);
        Chapter chapter = chapterService.findByChid(exam.getChid());
        return chapter.getCid();
    }

    public Integer getExamNumInCourse(Integer eid, Integer cid) {//获取测验在课程中的顺序序号（包括测验）
        try{
            Exam currentExam = findByEid(eid);
            List<Lession> lessions = lessionRepository.findByCid(cid);
            List<Exam> exams = examRepository.findByCid(cid);
            int examNo = 1;
            int preExamsNum = 0;
            int preLessionNum = 0;
            if(exams!=null && !exams.isEmpty()) {
                for(Exam exam : exams) {
                    if (exam.getChid()<currentExam.getChid() || (exam.getChid()==currentExam.getChid()&&exam.getEid()<currentExam.getEid())) {
                        preExamsNum++;
                    }
                }
            }
            for(Lession lession : lessions) {
                if (lession.getChid()<=currentExam.getChid()) {
                    preLessionNum++;
                }
            }
            return preExamsNum+preLessionNum+examNo;
        } catch (Exception e) {
            return -1;
        }
    }

    public Integer getQuestionsNumByEid(Integer eid) {
        Integer num;
        try{
            num = questionRepository.findByEid(eid).size();
        } catch (NullPointerException e) {
            num = 0;
        }
        return num;
    }

    public Integer countScoreByAnswer(Integer uid, Integer eid, Map<String, String[]> data) {
        Map<String, String> answers = formatAnswers(data);
        if(answers==null || answers.isEmpty()) {
            return 0;
        }
        Integer summary = 0;
        Integer questionNum = getQuestionsNumByEid(eid);
        Integer answerNum = answers.size();
        List<Question> questions = questionRepository.findByEid(eid);
        System.out.println("----------测验评分系统开始匹配答案----------");
        for (int i=1; i<=questionNum; i++) {
            Question question = questions.get(i-1);
            String key = "question"+i;
            System.out.println("["+key+"] 分值："+question.getValue());
            String userAnswer = answers.get(key);
            String correctAnswer = question.getAnswer();
            System.out.println();
            if (userAnswer==null) {
                System.out.println("用户没有答题");
                continue;
            }
            else {
                if(correctAnswer.contains("&")) {//多选题的答案匹配逻辑
                    switch (checkMultiAnswer(userAnswer, correctAnswer)) {
                        case 0:
                            System.out.println("【错误】你的答案："+userAnswer+"，标准答案："+correctAnswer);
                            break;
                        case 1:
                            System.out.println("【漏答】你的答案："+userAnswer+"，标准答案："+correctAnswer);
                            summary += questions.get(i-1).getValue()/2;
                            break;
                        case 2:
                            System.out.println("【正确】你的答案："+userAnswer+"，标准答案："+correctAnswer);
                            summary += questions.get(i-1).getValue();
                            break;
                    }
                }
                else {//单选题的答案匹配逻辑
                    if(userAnswer.trim().equals(correctAnswer.trim())) {
                        System.out.println("【正确】你的答案："+userAnswer+"，标准答案："+correctAnswer);
                        summary += questions.get(i-1).getValue();
                    }
                    else {
                        System.out.println("【错误】你的答案："+userAnswer+"，标准答案："+correctAnswer);
                    }
                }
            }
        }
        System.out.println("----------批卷结束，总分："+summary+"----------");
        Integer cid = getCourseIdByEid(eid);
        studentCourseService.addScore(uid, cid, summary);
        studentCourseService.upgradeProgress(uid, cid);
        return summary;
    }

    //遇到一个玄学问题，Map<String, String[]>对象根据Key取出value后编译器识别为String[]对象，但实际上却取出了ArrayList对象，故写此方法转换
    public Map<String, String> formatAnswers(Map<String, String[]> data) {
        Map<String, String> answers = new HashMap<>();
        Set<String> keys = data.keySet();
        for (String key : keys) {
            System.out.println("data: "+data.get(key));
            Object obj = data.get(key);
            ArrayList<String> answer = (ArrayList<String>) obj;
            if(answer.size()==1) {
                answers.put(key, answer.get(0));
            }
            else if(answer.size()>1) {
                String multiAnswer = "";
                for (String str : answer) {
                    multiAnswer += (str+"&");
                }
                multiAnswer = multiAnswer.substring(0,multiAnswer.length()-1);
                answers.put(key, multiAnswer);
            }
        }
        return answers;
    }

    public int checkMultiAnswer(String userAnswer, String correctAnswer) {//多选题答案匹配，0表示错答，1表示漏答，2表示完全正确
        String[] userAnswers = userAnswer.split("&");
        String[] correctAnswers = correctAnswer.split("&");
        if (userAnswer==null) {//没答
            return 0;
        }
        int correctNum = 0;
        int answerNum = correctAnswers.length;
        for (String a : userAnswers) {
            if(!correctAnswer.contains(a)) {//错答
                return 0;
            }
            else {
                correctNum++;
            }
        }
        if (correctNum>=answerNum) {
            return 2;//全对
        }
        else {
            return 1;//漏答
        }
    }

}
