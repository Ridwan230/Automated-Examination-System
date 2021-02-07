/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This is a public class which is the controller class of Student_main FXML
 *
 * @author Ifrad(180041225)
 */
public class Student_main_Controller implements Initializable {

    Connection connection;
    String exam_code_for_combobox;
    int total_marks;
    int marks_obtained;
    
    private Student selected_student;
    @FXML
    private Label student_name;
    @FXML
    private Label student_username;
    @FXML
    private Label student_id;
    @FXML
    private TextField exam_code;
    @FXML
    private ComboBox comboBox;
    
    @FXML
    private Label Result_display_Label;
    
    
    String exam_name;
    int exam_marks,exam_time,exam_total_questions,stu_id; 
    
    /**
     * 
     * @param student
     * @throws SQLException 
     * @author Ridwan(180041230)
     * @author Abrar(180041235)
     */
    public void pass_student_info(Student student) throws SQLException {
        this.selected_student = student;
        student_username.setText("Username: " + selected_student.getUsername());
        student_id.setText("ID: " + Integer.toString(selected_student.getID()));
        stu_id = selected_student.getID();
        student_name.setText(selected_student.getName());
        
        
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        String query="SELECT DISTINCT Exam_code FROM Answer_Info where Student_id=" + Integer.toString(selected_student.getID());
        
        try{
            preparedstatement = connection.prepareStatement(query);
            resultset = preparedstatement.executeQuery();
            
            while(resultset.next())
            {
                exam_code_for_combobox=Integer.toString(resultset.getInt("Exam_code"));
                comboBox.getItems().add(exam_code_for_combobox);
            }
            
        }catch (SQLException e){
            System.out.println("\nResult Check e rpoblem\n");
            e.printStackTrace();
        }finally {
            preparedstatement.close();
            resultset.close();
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SqliteConnection.Connector();
    }    
    
    /**
     * This function checks the credentials provided by the user to enter an exam and if successful opens the Answer Script
     * 
     * @param event
     * @throws IOException
     * @throws SQLException 
     * 
     * @author Ifrad(180041225)
     */
    public void Enter(ActionEvent event) throws IOException, SQLException
    {
        boolean flag=false;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        String query="SELECT * FROM Exam_Info";
        
        try{
            preparedstatement = connection.prepareStatement(query);
            resultset = preparedstatement.executeQuery();
            
            while(resultset.next())
            {
                if(exam_code.getText().equals(resultset.getString("Code")))    
                {
                    exam_name=resultset.getString("Name");
                    exam_marks=resultset.getInt("Marks");  
                    exam_time=resultset.getInt("Time");
                    exam_total_questions=resultset.getInt("Total_Questions");
                    flag=true;
                    break;
                }
            }
            if(flag==true)
            {
                //Entering Answer Script scene
                
                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(getClass().getResource("Answer_Script.fxml"));
                Parent root = loader.load();
                Scene answer_scriptScene = new Scene(root);
                Answer_Script_Controller controller = loader.getController();
                controller.pass_exam_info(exam_name,exam_marks,exam_time,exam_total_questions,Integer.parseInt(exam_code.getText()),stu_id);

               
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                primaryStage.setScene(answer_scriptScene);
                primaryStage.show();
            }
            else
            {
                System.out.println("Exam Not Found!!!");
            }
            
        }catch (SQLException e){
            System.out.println("\nEnter e problem\n");
            e.printStackTrace();
        }finally {
            preparedstatement.close();
            resultset.close();
        }
    }
    
    /**
     * This function when triggered opens the Student Login window
     * 
     * @param event
     * @throws IOException 
     * @author Ifrad(180041225)
     */
    public void Back(ActionEvent event) throws IOException
    { 
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Student_Login.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * 
     * 
     * @param event
     * @throws IOException
     * @throws SQLException 
     * @author Ridwan(180041230)
     * @author Abrar(180041235)
     */
    public void check_result(ActionEvent event) throws IOException, SQLException
    {
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        exam_code_for_combobox=comboBox.getValue().toString();
        String query_1="SELECT Count(Exam_code)as total_marks FROM Answer_Info where Student_id=" 
                        + Integer.toString(selected_student.getID())+" and Answer_Info.Exam_code=" +exam_code_for_combobox;
        String query_2="SELECT Count(Answer_Info.Exam_code)as obtained_marks FROM Answer_Info,Question_info where Student_id=" 
                        + Integer.toString(selected_student.getID())+" and Question_number=Question_no and Answer_Info.Exam_code="
                        + "Question_info.Exam_code and answer=correct_answer"+" and Answer_Info.Exam_code=" +exam_code_for_combobox;
        
        try{
            preparedstatement = connection.prepareStatement(query_1);
            resultset = preparedstatement.executeQuery();
            total_marks=resultset.getInt("total_marks");
            
            preparedstatement = connection.prepareStatement(query_2);
            resultset = preparedstatement.executeQuery();
            marks_obtained=resultset.getInt("obtained_marks");
            
            int percentage= (marks_obtained*100)/total_marks;
            Result_display_Label.setText("Marks: " + marks_obtained+"/"+ total_marks+" -> "+"Percentage: "+percentage +"%");
            
            
        }catch (SQLException e){
            System.out.println("\nResult Check Problem\n");
            e.printStackTrace();
        }finally {
            preparedstatement.close();
            resultset.close();
        }
    }
}
