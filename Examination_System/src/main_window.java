
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * This is a public class which represents the main window 
 * 
 * @author Ifrad(180041225)
 */
public class main_window implements Initializable{
    
    public DB_Model dbmodel = new DB_Model();
    @FXML
    private Button teac;
    @FXML
    private Button stud;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if(dbmodel.isDbConnected()){
            System.out.println("\nConnected\n");
        }else{
            System.out.println("\nNot Connected\n");
        }
    }

    /**
     * This function when triggered opens the Login window of Teacher
     * 
     * @param event
     * @throws IOException 
     * @author Ifrad(180041225)
     */
    @FXML
    public void Teacher_login(ActionEvent event) throws IOException
    {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Teacher_Login.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * This function when triggered opens the Login window of Student
     * 
     * @param event
     * @throws IOException 
     * @author Ifrad(180041225)
     */
    @FXML
    public void Student_login(ActionEvent event) throws IOException
    {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Student_Login.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
