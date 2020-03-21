package cn.shadl.ieducommonbeans.domain.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ExamQuestionDTO {
    public static String[] marks = {"A. ","B. ","C. ","D. ","E. ","F. ","G. ","H. ","I. ","J. ","K. "};
    private Integer qid;
    private String type;//single(单选题), multiple(多选题), blank(填空题)
    private String question;
    private Set<String> options;
}
