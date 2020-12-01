
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ASUS
 */
public class Teacher_Login_Controller implements Initializable {

    Connection connection;
    @FXML
    private TextField textfield_username;
    @FXML
    private PasswordField textfield_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = SqliteConnection.Connector();
    }

    public boolean Sign_in(ActionEvent event) throws SQLException, IOException {
        boolean flag = false;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        String query = "SELECT * FROM Teachers_Info";
        try {
            preparedstatement = connection.prepareStatement(query);
            resultset = preparedstatement.executeQuery();
            while (resultset.next()) {
                if (textfield_username.getText().equals(resultset.getString("Username")) && textfield_password.getText().equals(resultset.getString("Password"))) {
                    flag = true;
                    break;
                }
            }
            if (flag == true) {
                System.out.println("\nLogin Successful\n");
                
                
                //Entering the Scene Teacher_main with username and password
                
                Teacher teacher= new Teacher(resultset.getString("Username"),Integer.parseInt(resultset.getString("ID")),resultset.getString("Department"));
                FXMLLoader loader= new FXMLLoader();
                loader.setLocation(getClass().getResource("Teacher_main.fxml"));
                Parent root=loader.load();
                
                Scene teacher_main_scene= new Scene(root);
                Teacher_main_Controller controller= loader.getController();
                controller.pass_teacher_info(teacher);
                ((Node) event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                primaryStage.setScene(teacher_main_scene);
                primaryStage.show();
                
                return false;
            } else {
                System.out.println("\nLogin Unsuccessful\n");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("\nLogin e problem\n");
            e.printStackTrace();
            return false;
        } finally {
            preparedstatement.close();
            resultset.close();
        }
    }

    public void Create_account(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Teacher_create_account.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
