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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * This is the controller class of Teacher_create_account FXML 
 * 
 * @author Ifrad(180041225)
 */
public class Teacher_create_account_Controller implements Initializable{
    Connection connection;
    @FXML
    private TextField name;
    @FXML
    private TextField department;
    @FXML
    private TextField id;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirm_password;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = SqliteConnection.Connector();
    }
    
    /**
     * This function checks if the provided credentials for new account creation is valid or not
     * 
     * @return Returns True if the credentials provided for new account creation is valid and False otherwise
     * @throws SQLException 
     * @author Ifrad(180041225)
     */
    public boolean Teacher_isLogin() throws SQLException{
        boolean flag=false;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        String query="SELECT Username FROM Teachers_Info";
        try{
            preparedstatement = connection.prepareStatement(query);
            resultset = preparedstatement.executeQuery();
            while(resultset.next())
            {
                if(username.getText().equals(resultset.getString("Username")))    
                {
                    flag=true;
                    break;
                }
            }
            if(flag==true)
            {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setTitle("ERROR");
                a1.setContentText("Username Already Taken!");
                a1.setHeaderText("Try Again.");
                a1.showAndWait();
                System.out.println("\nUsername Already Taken\n");
                return false;
            }
            else
            {
                if(confirm_password.getText().equals(password.getText()))
                {
                    String query1="Insert into Teachers_Info(name,ID,Department,Username,password) values (?,?,?,?,?)";
                    preparedstatement = connection.prepareStatement(query1);
                    preparedstatement.setString(1,name.getText());
                    preparedstatement.setString(2,id.getText());
                    preparedstatement.setString(3,department.getText());
                    preparedstatement.setString(4,username.getText());
                    preparedstatement.setString(5,password.getText());
                    
                    preparedstatement.executeUpdate();
                    return true;
                }
                else {
                    Alert a1 = new Alert(Alert.AlertType.ERROR);
                    a1.setTitle("ERROR");
                    a1.setContentText("Passwords Not Matched!!!");
                    a1.setHeaderText("Check Password");
                    a1.showAndWait();
                    return false;
                }
            }
        }
        catch (Exception e){
            System.out.println("\nLogin e problem\n");
            e.printStackTrace();
            return false;
        }finally{
            preparedstatement.close();
            resultset.close();
        }
    }
    
    /**
     * This function when triggered creates the account
     * 
     * @param event
     * @throws IOException 
     * @author Ifrad(180041225)
     */
    public void Create(ActionEvent event) throws IOException
    { 
        try{
            if (Teacher_isLogin() == true) {
                Alert a1 = new Alert(Alert.AlertType.INFORMATION);
                a1.setTitle("Success");
                a1.setContentText("Account Created Successfully");
                a1.setHeaderText(null);
                a1.showAndWait();
                System.out.println("\nAccount Created\n");
                ((Node) event.getSource()).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("Teacher_Login.fxml"));
                Scene scene = new Scene(root);
                Stage primaryStage = new Stage();
                primaryStage.setScene(scene);
                primaryStage.show();
            }
            else
            {
                System.out.println("Account not created");
            }
           }catch (SQLException e)
        {
            System.out.println("\ncreated e problem\n");
        }
    }
    
    /**
     * This function when triggered opens the Teacher Login window
     * 
     * @param event
     * @throws IOException 
     * @author Ifrad(180041225)
     */
    public void Back(ActionEvent event) throws IOException
    { 
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Teacher_Login.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

