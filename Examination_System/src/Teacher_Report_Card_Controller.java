/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * Controller for teacher report card class
 *
 * @author Ridwan(180041230)
 */
public class Teacher_Report_Card_Controller implements Initializable {
    Teacher selected_teacher;
    String Exam_Code_String;
    Connection connection;
    String marks_obtained;
    String id;
    String Student_name;
    
    Result result[]=new Result[105];
    
    @FXML
    private TextField Exam_Label;
    @FXML
    private TableView<Result> tableView;
    @FXML
    private TableColumn<Result,String> firstColumn;
    @FXML
    private TableColumn<Result,String> secondColumn;
    @FXML
    private TableColumn<Result,String> thirdColumn;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SqliteConnection.Connector();
        // TODO
    }    
    public void pass_teacher_info(Teacher teacher) {
        this.selected_teacher = teacher;
    }
    /**
     * takes back to the previous scene
     * @param event
     * @throws IOException 
     * @author Ridwan(180041230)
     */
    public void back(ActionEvent event) throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("Teacher_main.fxml"));
        Parent root=loader.load();
        Scene teacher_main_scene= new Scene(root);
        Teacher_main_Controller controller= loader.getController();
        controller.pass_teacher_info(selected_teacher);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        primaryStage.setScene(teacher_main_scene);
        primaryStage.show();
    }
    
    /**
     * Displays the result in tabular form
     * @param event
     * @throws IOException
     * @throws SQLException 
     * @author Ridwan(180041230)
     */
    public void check_result(ActionEvent event) throws IOException, SQLException
    {
        PreparedStatement preparedstatement = null;
        PreparedStatement preparedstatement_2 = null;
        ResultSet resultset = null;
        Exam_Code_String=Exam_Label.getText();
        String query_getStudent="Select Distinct Student_id as ID,Students_Info.Name from Answer_info,Students_Info where Student_id=ID and Exam_code="+Exam_Code_String;
        
        String query_Exam_marks="SELECT Count(Answer_Info.Exam_code)as marks FROM Answer_Info,Question_info where Student_id=? and Question_number=Question_no and Answer_Info.Exam_code=Question_info.Exam_code and answer=correct_answer and Answer_Info.Exam_code="+Exam_Code_String;
        
        try{
            preparedstatement = connection.prepareStatement(query_getStudent);
            resultset = preparedstatement.executeQuery();
            int total_student_this_exam=0;
            while(resultset.next())
            {
                id=Integer.toString(resultset.getInt("ID"));
                Student_name=resultset.getString("Name");
                preparedstatement = connection.prepareStatement(query_Exam_marks);
                preparedstatement.setString(1,id);
                ResultSet resultset_2 = preparedstatement.executeQuery();
                marks_obtained=Integer.toString(resultset_2.getInt("marks"));
                result[total_student_this_exam]=new Result(id,Student_name,marks_obtained);
                total_student_this_exam++;
            }
            ObservableList<Result> result_list=FXCollections.observableArrayList();
            for(int i=0;i<total_student_this_exam;i++){
                
                result_list.add(result[i]);
                
            }
            firstColumn.setCellValueFactory(new PropertyValueFactory<Result,String>("ID"));
            secondColumn.setCellValueFactory(new PropertyValueFactory<Result,String>("Name"));
            thirdColumn.setCellValueFactory(new PropertyValueFactory<Result,String>("marks_obtained"));
            
            tableView.setItems(result_list);
            
            
        }catch (SQLException e){
            System.out.println("\nResult Check e rpoblem\n");
            e.printStackTrace();
        }finally {
            preparedstatement.close();
            resultset.close();
        }
    }
}