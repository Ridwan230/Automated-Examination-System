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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Teacher_main_Controller implements Initializable {

    Connection connection;
    private Teacher selected_teacher;

    @FXML
    private TextField exam_code;
    @FXML
    private TextField exam_name;
    @FXML
    private TextField exam_marks;
    @FXML
    private TextField exam_time;
    @FXML
    private TextField total_questions;

    @FXML
    private Label teacher_username;
    @FXML
    private Label teacher_id;
    @FXML
    private Label teacher_Department;

    public void pass_teacher_info(Teacher teacher) {
        this.selected_teacher = teacher;
        teacher_username.setText("Username: " + selected_teacher.getUsername());
        teacher_id.setText("ID: " + Integer.toString(selected_teacher.getID()));
        teacher_Department.setText("Department: " + selected_teacher.getDepartmant());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SqliteConnection.Connector();
    }

    public boolean Confirm(ActionEvent event) throws SQLException, IOException {
        boolean flag = false;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        String query = "SELECT * FROM Exam_Info";
        try {
            preparedstatement = connection.prepareStatement(query);
            resultset = preparedstatement.executeQuery();
            while (resultset.next()) {
                if (Integer.parseInt(exam_code.getText()) == resultset.getInt("Code")) {
                    flag = true;
                    break;
                }
            }
            if (flag == true) {
                System.out.println("\nCode Already Taken\n");
                return false;
            } else {

                String query1 = "Insert into Exam_Info(Name,Code,Marks,Time,Total_Questions) values (?,?,?,?,?)";
                preparedstatement = connection.prepareStatement(query1);
                preparedstatement.setString(1, exam_name.getText());
                preparedstatement.setString(2, exam_code.getText());
                preparedstatement.setString(3, exam_marks.getText());
                preparedstatement.setString(4, exam_time.getText());
                preparedstatement.setString(5, total_questions.getText());
                preparedstatement.executeUpdate();

                Exam exam = new Exam(exam_name.getText(), Integer.parseInt(exam_code.getText()), Integer.parseInt(exam_marks.getText()), Integer.parseInt(exam_time.getText()), Integer.parseInt(total_questions.getText()));

                //Entering Question setting scene
                
                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(getClass().getResource("Question_setting.fxml"));
                Parent root = loader.load();

                Scene question_settingScene = new Scene(root);
                Question_setting_Controller controller = loader.getController();
                controller.pass_exam_info(exam);

               

                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();

                primaryStage.setScene(question_settingScene);

                primaryStage.show();
                return true;
            }
        } catch (Exception e) {
            System.out.println("\nExam Code e problem\n");
            e.printStackTrace();
            return false;
        } finally {
            preparedstatement.close();
            resultset.close();
        }
    }

}
