/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class represents a student
 * 
 * @author Ifrad(180041225)
 */
public class Student {
    private String name,username;
    int ID;
 
    public Student(String username, int ID, String name){
        this.ID=ID;
        this.name=name;
        this.username=username;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
