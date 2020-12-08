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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Student_main_Controller implements Initializable {

    Connection connection;
    private Student selected_student;
    @FXML
    private Label student_name;
    @FXML
    private Label student_username;
    @FXML
    private Label student_id;
    @FXML
    private TextField exam_code;
    
    String exam_name;
    int exam_marks,exam_time,exam_total_questions,stu_id; 
    
    public void pass_student_info(Student student) {
        this.selected_student = student;
        student_username.setText("Username: " + selected_student.getUsername());
        student_id.setText("ID: " + Integer.toString(selected_student.getID()));
        stu_id = selected_student.getID();
        student_name.setText("Name: " + selected_student.getName());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SqliteConnection.Connector();
    }    
    
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
    
    public void Back(ActionEvent event) throws IOException
    { 
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Student_Login.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
