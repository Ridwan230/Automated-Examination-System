/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.*;

/**
 * This class represents the connection of the database
 * 
 * @author Ifrad(180041225)
 */
public class DB_Model {
    Connection connection;
    
    /**
     * Constructor of the DB_Model class which establishes the database connection
     */
    public DB_Model(){
        connection = SqliteConnection.Connector();
        if(connection==null) {
            System.out.println("NOT SUCCESSFUL");
            System.exit(1);
        }
    }
    
    /**
     * 
     * @return Returns True if the database is connected and False otherwise
     * @author Ifrad(180041225)
     */
    public boolean isDbConnected(){
        try{
            return !connection.isClosed();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
}
