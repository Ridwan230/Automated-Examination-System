/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * A public class which is the controller of Question_setting FXML file
 *
 * @author Ridwan(180041230)
 */
public class Question_setting_Controller implements Initializable {
    
    Connection connection;
    private Exam selected_exam;
    private Question question[]=new Question[105];
    
    @FXML
    private Label exam_name;
    
    @FXML
    private Label exam_code;
    
    @FXML
    private Label exam_marks;
    
    @FXML
    private Label exam_time;
    
    @FXML
    private Label total_questions;
    
    @FXML
    private ComboBox QuestionNumberBox;
    
    @FXML
    private TextArea Question_statement;
    
    @FXML
    private TextArea Option_A;
    
    @FXML
    private TextArea Option_B;
    
    @FXML
    private TextArea Option_C;
    
    @FXML
    private TextArea Option_D;
    
    @FXML
    private TextField correct_answer;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SqliteConnection.Connector();
    }    

    /**
     * Passes the information regarding exam to the current scene
     * @param exam 
     * @author Ridwan(180041230)
     */
    public void pass_exam_info(Exam exam){
        this.selected_exam=exam;
        exam_name.setText("Exam Name: " +selected_exam.getExam_name());
        exam_code.setText("Exam Code: " + Integer.toString(selected_exam.getExam_code()));
        exam_marks.setText("Exam Marks: " +Integer.toString(selected_exam.getExam_marks()));
        exam_time.setText("Exam Time: " +Integer.toString(selected_exam.getExam_time()));
        total_questions.setText("Total Questions: "+Integer.toString(selected_exam.getTotal_questions()));
        for(int i=1;i<=selected_exam.getTotal_questions();i++ ){
            QuestionNumberBox.getItems().add(Integer.toString(i));
        }
    }
    /**
     * Loads questions already saved
     */
    public void change_question_number(){
        Question_statement.clear();
        Option_A.clear();
        Option_B.clear();
        Option_C.clear();
        Option_D.clear();
        correct_answer.clear();
        
        int x=Integer.parseInt(QuestionNumberBox.getValue().toString());
        if(question[x]!=null){
            Question_statement.setText(question[x].getQues_statement());
            Option_A.setText(question[x].getOption_a());
            Option_B.setText(question[x].getOption_b());
            Option_C.setText(question[x].getOption_c());
            Option_D.setText(question[x].getOption_d());
            correct_answer.setText(question[x].getCorrect_ans());
        }
    }
    /**
     * Saves changes made 
     * @author Ridwan(180041230)
     */
    public void save_changes(){
        int x=Integer.parseInt(QuestionNumberBox.getValue().toString());
        if(question[x]!=null){
            question[x].setQues_statement(Question_statement.getText());
            question[x].setOption_a(Option_A.getText());
            question[x].setOption_b(Option_B.getText());
            question[x].setOption_c(Option_C.getText());
            question[x].setOption_d(Option_D.getText());
            question[x].setCorrect_ans(correct_answer.getText());
        }
        else{
            question[x]=new Question(Question_statement.getText(), Option_A.getText(), Option_B.getText(), Option_C.getText(), Option_D.getText(), correct_answer.getText());
        }
    }
    /**
     * Confirms the exam with specified questions
     * @throws SQLException 
     * @author Ridwan(180041230)
     */
    public void Confirm() throws SQLException {
        PreparedStatement preparedstatement = null;
        try {
            Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
            a1.setTitle(null);
            a1.setContentText("Confirm Submission?");
            a1.setHeaderText(null);
            Optional<ButtonType> result1 = a1.showAndWait();

            if (result1.get() == ButtonType.OK) {
                for (int i = 1; i <= selected_exam.getTotal_questions(); i++) {

                    if (question[i] != null) {
                        String query1 = "Insert into Question_info(Question_number,Exam_Code,Question_statement,Option_a,Option_b,Option_c,Option_d,Correct_answer) values (?,?,?,?,?,?,?,?)";
                        preparedstatement = connection.prepareStatement(query1);
                        preparedstatement.setString(1, Integer.toString(i));
                        preparedstatement.setString(2, Integer.toString(selected_exam.getExam_code()));
                        preparedstatement.setString(3, question[i].getQues_statement());
                        preparedstatement.setString(4, question[i].getOption_a());
                        preparedstatement.setString(5, question[i].getOption_b());
                        preparedstatement.setString(6, question[i].getOption_c());
                        preparedstatement.setString(7, question[i].getOption_d());
                        preparedstatement.setString(8, question[i].getCorrect_ans());
                        preparedstatement.executeUpdate();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("\nQuestion database e problem\n");
            e.printStackTrace();
        } finally {
            preparedstatement.close();          
        }
    }
    
    /**
     * This function when triggered opens the Teacher main window
     * 
     * @param event
     * @throws IOException 
     * @author Ifrad(180041225)
     */
    public void Back(ActionEvent event) throws IOException
    { 
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Teacher_main.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}



