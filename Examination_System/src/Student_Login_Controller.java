
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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class Student_Login_Controller implements Initializable {
    
    Connection connection;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = SqliteConnection.Connector();
    }
    
    public void Sign_in(ActionEvent event) throws SQLException, IOException
    {
        boolean flag = false;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        String query = "SELECT * FROM Students_Info";
        try {
            preparedstatement = connection.prepareStatement(query);
            resultset = preparedstatement.executeQuery();
            while (resultset.next()) {
                if (username.getText().equals(resultset.getString("Username")) && password.getText().equals(resultset.getString("Password"))) {
                    flag = true;
                    break;
                }
            }
            if (flag == true) {
                System.out.println("\nLogin Successful\n");
                
                
                //Entering the Scene Student_main with username and password
                
                Student student= new Student(resultset.getString("Username"),Integer.parseInt(resultset.getString("ID")),resultset.getString("Name"));
                FXMLLoader loader= new FXMLLoader();
                loader.setLocation(getClass().getResource("Student_main.fxml"));
                Parent root=loader.load();
                
                Scene student_main_scene= new Scene(root);
                Student_main_Controller controller= loader.getController();
                controller.pass_student_info(student);
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                primaryStage.setScene(student_main_scene);
                primaryStage.show();
                
            } else {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setTitle("ERROR");
                a1.setContentText("Wrong Username or Password!");
                a1.setHeaderText("Try Again.");
                a1.showAndWait();
                System.out.println("\nLogin Unsuccessful\n");
            }
        } catch (SQLException e) {
            System.out.println("\nLogin e problem\n");
            e.printStackTrace();
        } finally {
            preparedstatement.close();
            resultset.close();
        }        
    }
    
    
    public void Create_account(ActionEvent event) throws IOException
    {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Student_create_account.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void Back(ActionEvent event) throws IOException
    { 
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
