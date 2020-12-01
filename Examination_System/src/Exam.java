/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Exam {
    private String exam_name;
    private int exam_code,exam_marks,exam_time,total_questions;

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public int getExam_code() {
        return exam_code;
    }

    public void setExam_code(int exam_code) {
        this.exam_code = exam_code;
    }

    public int getExam_marks() {
        return exam_marks;
    }

    public void setExam_marks(int exam_marks) {
        this.exam_marks = exam_marks;
    }

    public int getExam_time() {
        return exam_time;
    }

    public void setExam_time(int exam_time) {
        this.exam_time = exam_time;
    }

    public int getTotal_questions() {
        return total_questions;
    }

    public void setTotal_questions(int total_questions) {
        this.total_questions = total_questions;
    }

    public Exam(String exam_name, int exam_code, int exam_marks, int exam_time, int total_questions) {
        this.exam_name = exam_name;
        this.exam_code = exam_code;
        this.exam_marks = exam_marks;
        this.exam_time = exam_time;
        this.total_questions = total_questions;
    }

    
    
    
}
