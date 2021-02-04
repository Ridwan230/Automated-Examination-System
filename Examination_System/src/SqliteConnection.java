
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class establishes the sqlite database connection
 * 
 * @author Ifrad(180041225)
 */
import java.sql.*;
public class SqliteConnection {
    public static Connection Connector()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            //Connection conn = DriverManager.getConnection("jdbc:sqlite:E:\\4th Semester\\Visual Programming Lab\\Project\\Database\\ExamSysDB.db");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:ExamSysDB.db");
            return conn;
        }catch (Exception e){
            System.out.println("DB Connection Problem");
            System.out.println(e);
            return null;
        }
    }
}
