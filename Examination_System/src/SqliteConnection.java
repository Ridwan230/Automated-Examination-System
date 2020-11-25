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
