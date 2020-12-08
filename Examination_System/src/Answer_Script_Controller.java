/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Answer_Script_Controller implements Initializable {

    Connection connection;
    private Question question[]=new Question[105];
    private Answer answer[]=new Answer[105];
    
    @FXML
    private Label exam_name;

    @FXML
    private Label total_marks;

    @FXML
    private Label total_time;
    
    @FXML
    private ComboBox QuestionNumberBox;
    
    @FXML
    private Label Question_statement;

    @FXML
    private RadioButton Option_A;

    @FXML
    private RadioButton Option_C;

    @FXML
    private RadioButton Option_B;

    @FXML
    private RadioButton Option_D;
    
    @FXML
    private ToggleGroup option;
    
    int total_questions, exam_code, student_id;
    
    public void pass_exam_info(String name, int marks, int time, int tot_questions, int code, int stu_id) throws SQLException, IOException {
        exam_name.setText(name);
        total_marks.setText(Integer.toString(marks));
        total_time.setText(Integer.toString(time)+" Minutes");
        total_questions=tot_questions;
        exam_code = code;
        student_id = stu_id;
        for(int i=1;i<=total_questions;i++ ){
            QuestionNumberBox.getItems().add(Integer.toString(i));
        }
        get_all_Questions(exam_code);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SqliteConnection.Connector();
    }

    
    public void get_all_Questions(int exam_code) throws SQLException, IOException
    {
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        String query = "Select * from Question_info where Exam_Code = ?";
        try {
            preparedstatement = connection.prepareStatement(query);
            preparedstatement.setString(1,Integer.toString(exam_code));
            resultset = preparedstatement.executeQuery();
            int x=1;
            while (resultset.next()) {
                String Question_statement = resultset.getString("Question_statement");
                String Option1_A = resultset.getString("Option_a");
                String Option1_B = resultset.getString("Option_b");
                String Option1_C = resultset.getString("Option_c");
                String Option1_D = resultset.getString("Option_d");
                String correct_answer = resultset.getString("Correct_answer");
                question[x]=new Question(Question_statement, Option1_A, Option1_B, Option1_C, Option1_D, correct_answer);
                x++;
            }
            
        } catch (SQLException e) {
            System.out.println("\nGet all question e problem\n");
            e.printStackTrace();
        } finally {
            preparedstatement.close();
            resultset.close();
        }
    }
    
    
    public void select_question(ActionEvent event){
        try {
            int x = Integer.parseInt(QuestionNumberBox.getValue().toString());
            Question_statement.setText(question[x].getQues_statement());
            Option_A.setText(question[x].getOption_a());
            Option_B.setText(question[x].getOption_b());
            Option_C.setText(question[x].getOption_c());
            Option_D.setText(question[x].getOption_d());

            Option_A.setSelected(false);
            Option_B.setSelected(false);
            Option_C.setSelected(false);
            Option_D.setSelected(false);

            if (answer[x] != null) {
                if (answer[x].getAns().equals(Option_A.getText())) {
                    Option_A.setSelected(true);
                    Option_B.setSelected(false);
                    Option_C.setSelected(false);
                    Option_D.setSelected(false);
                } else if (answer[x].getAns().equals(Option_B.getText())) {
                    Option_B.setSelected(true);
                    Option_A.setSelected(false);
                    Option_C.setSelected(false);
                    Option_D.setSelected(false);
                } else if (answer[x].getAns().equals(Option_C.getText())) {
                    Option_C.setSelected(true);
                    Option_B.setSelected(false);
                    Option_A.setSelected(false);
                    Option_D.setSelected(false);
                } else if (answer[x].getAns().equals(Option_D.getText())) {
                    Option_D.setSelected(true);
                    Option_B.setSelected(false);
                    Option_C.setSelected(false);
                    Option_A.setSelected(false);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("\nSelect question e problem\n");
            e.printStackTrace();
        }
    }
    
    public void previous_question(ActionEvent event) {
        try {
            int x = Integer.parseInt(QuestionNumberBox.getValue().toString());
            if (x - 1 >= 1) {
                x--;
                Question_statement.setText(question[x].getQues_statement());
                Option_A.setText(question[x].getOption_a());
                Option_B.setText(question[x].getOption_b());
                Option_C.setText(question[x].getOption_c());
                Option_D.setText(question[x].getOption_d());
                QuestionNumberBox.setValue(Integer.toString(x));
                if(answer[x]==null)
                {
                    Option_A.setSelected(false);
                    Option_B.setSelected(false);
                    Option_C.setSelected(false);
                    Option_D.setSelected(false);
                }
                else
                {
                    if(answer[x].getAns().equals(Option_A.getText()))
                    {
                        Option_A.setSelected(true);
                        Option_B.setSelected(false);
                        Option_C.setSelected(false);
                        Option_D.setSelected(false);
                    }
                    else if(answer[x].getAns().equals(Option_B.getText()))
                    {
                        Option_B.setSelected(true);
                        Option_A.setSelected(false);
                        Option_C.setSelected(false);
                        Option_D.setSelected(false);
                    }
                    else if(answer[x].getAns().equals(Option_C.getText()))
                    {
                        Option_C.setSelected(true);
                        Option_B.setSelected(false);
                        Option_A.setSelected(false);
                        Option_D.setSelected(false);
                    }
                    else if(answer[x].getAns().equals(Option_D.getText()))
                    {
                        Option_D.setSelected(true);
                        Option_B.setSelected(false);
                        Option_C.setSelected(false);
                        Option_A.setSelected(false);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\nPrevious question e problem\n");
            e.printStackTrace();
        }
    }
    
    public void next_question(ActionEvent event) {
        try {
            int x = Integer.parseInt(QuestionNumberBox.getValue().toString());
            if (x + 1 <= total_questions) {
                x++;
                Question_statement.setText(question[x].getQues_statement());
                Option_A.setText(question[x].getOption_a());
                Option_B.setText(question[x].getOption_b());
                Option_C.setText(question[x].getOption_c());
                Option_D.setText(question[x].getOption_d());
                QuestionNumberBox.setValue(Integer.toString(x));
                
                if(answer[x]==null)
                {
                    Option_A.setSelected(false);
                    Option_B.setSelected(false);
                    Option_C.setSelected(false);
                    Option_D.setSelected(false);
                }
                else
                {
                    if(answer[x].getAns().equals(Option_A.getText()))
                    {
                        Option_A.setSelected(true);
                        Option_B.setSelected(false);
                        Option_C.setSelected(false);
                        Option_D.setSelected(false);
                    }
                    else if(answer[x].getAns().equals(Option_B.getText()))
                    {
                        Option_B.setSelected(true);
                        Option_A.setSelected(false);
                        Option_C.setSelected(false);
                        Option_D.setSelected(false);
                    }
                    else if(answer[x].getAns().equals(Option_C.getText()))
                    {
                        Option_C.setSelected(true);
                        Option_B.setSelected(false);
                        Option_A.setSelected(false);
                        Option_D.setSelected(false);
                    }
                    else if(answer[x].getAns().equals(Option_D.getText()))
                    {
                        Option_D.setSelected(true);
                        Option_B.setSelected(false);
                        Option_C.setSelected(false);
                        Option_A.setSelected(false);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\nPrevious question e problem\n");
            e.printStackTrace();
        }
    }
    
    public void save_changes(ActionEvent event){
        try {
            int x = Integer.parseInt(QuestionNumberBox.getValue().toString());
            if (answer[x] != null) {
                
                if (Option_A.isSelected()) {
                    System.out.println("Saved A");
                    answer[x].setAns(Option_A.getText());
                } else if (Option_B.isSelected()) {
                    System.out.println("Saved B");
                    answer[x].setAns(Option_B.getText());
                } else if (Option_C.isSelected()) {
                    System.out.println("Saved C");
                    answer[x].setAns(Option_C.getText());
                } else if (Option_D.isSelected()) {
                    System.out.println("Saved D");
                    answer[x].setAns(Option_D.getText());
                }
                
                answer[x].setQuestion_no(x);
                answer[x].setExam_code(exam_code);
                
            } else {
                if (Option_A.isSelected()) {
                    System.out.println("New Saved A");
                    answer[x] = new Answer(Option_A.getText(), x, exam_code);
                } else if (Option_B.isSelected()) {
                    System.out.println("New Saved B");
                    answer[x] = new Answer(Option_B.getText(), x, exam_code);
                } else if (Option_C.isSelected()) {
                    System.out.println("New Saved C");
                    answer[x] = new Answer(Option_C.getText(), x, exam_code);
                } else if (Option_D.isSelected()) {
                    System.out.println("New Saved D");
                    answer[x] = new Answer(Option_D.getText(), x, exam_code);
                }              
            }
        } catch (Exception e) {
            System.out.println("\nSave changes e problem\n");
            e.printStackTrace();
        }
        
    }
    
    public void Submit(ActionEvent event) throws SQLException {
        PreparedStatement preparedstatement = null;
        try {
            for(int i=1;i<=total_questions;i++) {
             
                if(answer[i]!=null){
                    
                    String query1 = "Insert into Answer_Info(Exam_code,Question_no,Student_id,Answer) values (?,?,?,?)";
                    preparedstatement = connection.prepareStatement(query1);
                    preparedstatement.setString(1, Integer.toString(exam_code));
                    preparedstatement.setString(2, Integer.toString(i));
                    preparedstatement.setString(3, Integer.toString(student_id));
                    
                    if(answer[i].getAns().equals(question[i].getOption_a()))
                    {
                        preparedstatement.setString(4, "a");
                    }
                    else if(answer[i].getAns().equals(question[i].getOption_b()))
                    {
                        preparedstatement.setString(4, "b");
                    }
                    else if(answer[i].getAns().equals(question[i].getOption_c()))
                    {
                        preparedstatement.setString(4, "c");
                    }
                    else if(answer[i].getAns().equals(question[i].getOption_d()))
                    {
                        preparedstatement.setString(4, "d");
                    }

                    preparedstatement.executeUpdate();
                }           
            }
            System.out.println("\nSubmitted\n");
            ((Node) event.getSource()).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            System.out.println("\nSubmit e problem\n");
            e.printStackTrace();
        } finally {
            preparedstatement.close();
        }
    }
}
