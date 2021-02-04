
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
 * This is a public class which is the controller class of Student_Login FXML
 * 
 * @author Ifrad(180041225)
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
    
    /**
     * This function checks the signIN info of the student and if successful passes the info of that student to the student's home page
     * 
     * @param event
     * @throws SQLException
     * @throws IOException 
     * 
     * @author Ifrad(180041225)
     * @author Ridwan(180041230)
     */
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
    
    /**
     * This function when triggered opens the Student Account Creation window
     * 
     * @param event
     * @throws IOException 
     * @author Ifrad(180041225)
     */
    public void Create_account(ActionEvent event) throws IOException
    {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Student_create_account.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * This function when triggered opens the Main window
     * 
     * @param event
     * @throws IOException 
     * @author Ifrad(180041225)
     */
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
