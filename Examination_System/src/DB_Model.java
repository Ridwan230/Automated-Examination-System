/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
import java.sql.*;
public class DB_Model {
    Connection connection;
    public DB_Model(){
        connection = SqliteConnection.Connector();
        if(connection==null) {
            System.out.println("NOT SUCCESSFUL");
            System.exit(1);
        }
    }
    
    public boolean isDbConnected(){
        try{
            return !connection.isClosed();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
}
