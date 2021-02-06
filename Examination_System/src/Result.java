

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class results of students to be displayed
 * @author Ridwan(180041230)
 */
public class Result {
    private String ID ;
    private String Name;
    private String marks_obtained;

    /**
     * Constructor of Result class
     * @param ID ID of student
     * @param Name name of the student
     * @param marks_obtained obtained marks in the specific exam
     * @author Ridwan(180041230)
     */
    public Result(String ID,String Name, String marks_obtained) {
        this.ID = ID;
        this.Name=Name;
        this.marks_obtained = marks_obtained;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMarks_obtained() {
        return marks_obtained;
    }

    public void setMarks_obtained(String marks_obtained) {
        this.marks_obtained = marks_obtained;
    }
    

    
}
