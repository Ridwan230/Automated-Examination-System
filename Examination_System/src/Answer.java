/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class Answer {
    private String ans;
    private int question_no,exam_code;

    public Answer (String ans, int question_no, int exam_code)
    {
        this.ans = ans;
        this.question_no=question_no;
        this.exam_code=exam_code;
    }
    
    public int getQuestion_no() {
        return question_no;
    }

    public void setQuestion_no(int question_no) {
        this.question_no = question_no;
    }

    public int getExam_code() {
        return exam_code;
    }

    public void setExam_code(int exam_code) {
        this.exam_code = exam_code;
    }
    
    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
