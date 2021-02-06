/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class for storing teacher information
 * @author Ridwan(180041230)
 */
public class Teacher {
    private String username,departmant;
    int ID;
    
    /**
     * COnstructor for teacher class
     * @param username username of teacher
     * @param ID id of teacher
     * @param department department the teacher belongs to
     * @author Ridwan(180041230)
     */
    public Teacher(String username, int ID, String department){
        this.ID=ID;
        this.departmant=department;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartmant() {
        return departmant;
    }

    public void setDepartmant(String departmant) {
        this.departmant = departmant;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
   
}
