/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jcodec.api.awt.AWTSequenceEncoder;

/**
 * This is a public class which is the controller of Answer_Script FXML
 *
 * @author Ifrad(180041225)
 * @author Ridwan(180041230)
 */
public class Answer_Script_Controller implements Initializable {

    Connection connection;
    private Question question[] = new Question[105];
    private Answer answer[] = new Answer[105];

    @FXML
    private Label exam_name;

    @FXML
    private Label total_marks;

    @FXML
    private Label total_time;

    @FXML
    private Label time_remaining;

    @FXML
    private ComboBox QuestionNumberBox;

    @FXML
    private Label Question_statement;

    @FXML
    private RadioButton Option_A;

    @FXML
    private RadioButton Option_C;

    @FXML
    private RadioButton Option_B;

    @FXML
    private RadioButton Option_D;

    @FXML
    private Button submit;

    @FXML
    private ToggleGroup option;

    int total_questions, exam_code, student_id, time, temp;
    boolean is_exam_started = false;

    /**
     * This function passes the exam info
     * 
     * @param name This represents the exam name
     * @param marks This represents the exam marks
     * @param t This represents the exam time
     * @param tot_questions This represents the total number of exam questions
     * @param code This represents the exam code
     * @param stu_id This represents the student Id
     * @author Ifrad(180041225)
     * @throws SQLException
     * @throws IOException
     *
     */
    public void pass_exam_info(String name, int marks, int t, int tot_questions, int code, int stu_id) throws SQLException, IOException {
        exam_name.setText(name);
        total_marks.setText(Integer.toString(marks));
        time = t;
        total_time.setText(Integer.toString(t) + " Minutes");
        total_questions = tot_questions;
        exam_code = code;
        student_id = stu_id;
        for (int i = 1; i <= total_questions; i++) {
            QuestionNumberBox.getItems().add(Integer.toString(i));
        }
        get_all_Questions(exam_code);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = SqliteConnection.Connector();
    }

    /**
     * This function stores all the questions of an exam into an array
     * 
     * @param exam_code This represents the exam code
     * @throws SQLException
     * @throws IOException 
     * @author Ifrad(180041225)
     * 
     */
    public void get_all_Questions(int exam_code) throws SQLException, IOException {
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        String query = "Select * from Question_info where Exam_Code = ?";
        try {
            preparedstatement = connection.prepareStatement(query);
            preparedstatement.setString(1, Integer.toString(exam_code));
            resultset = preparedstatement.executeQuery();
            int x = 1;
            while (resultset.next()) {
                String Question_statement = resultset.getString("Question_statement");
                String Option1_A = resultset.getString("Option_a");
                String Option1_B = resultset.getString("Option_b");
                String Option1_C = resultset.getString("Option_c");
                String Option1_D = resultset.getString("Option_d");
                String correct_answer = resultset.getString("Correct_answer");
                question[x] = new Question(Question_statement, Option1_A, Option1_B, Option1_C, Option1_D, correct_answer);
                x++;
            }

        } catch (SQLException e) {
            System.out.println("\nGet all question e problem\n");
            e.printStackTrace();
        } finally {
            preparedstatement.close();
            resultset.close();
        }
    }

    /**
     * This function when triggered it starts the timer of the exam and screen recording through multi threading
     * This also lets the user select the questions with the help of combo box 
     * @author Ifrad(180041225)
     * @author Ridwan(180041230)
     * @param event 
     */
    public void select_question(ActionEvent event) {
        try {
            if (is_exam_started == false) {
                //time_remaining.setText(Integer.toString(time * 60));
                if((time * 60)/60 == 0 && (time * 60)%60 == 0)
                {
                    time_remaining.setText("00:00");
                }
                else if((time * 60)/60 == 0)
                {
                    time_remaining.setText("00:"+Integer.toString((time * 60)%60));
                }
                else if((time * 60)%60 == 0)
                {
                    time_remaining.setText(Integer.toString((time * 60)/60)+":00");
                }
                else
                {
                    time_remaining.setText(Integer.toString((time * 60)/60)+":"+Integer.toString((time * 60)%60));
                }
                
                Runnable obj1 = new Runnable() {
                    public void run() {
                        temp = time * 60;
                        for (int i = 1; i <= time * 60; i++) {
                            try {
                                temp--;
                                int temp1 = temp;
                                //Platform.runLater(() -> time_remaining.setText(Integer.toString(temp1)));
                                if(temp1/60 == 0 && temp1%60 == 0)
                                {
                                    Platform.runLater(() -> time_remaining.setText("00:00"));
                                }
                                else if(temp1/60 == 0)
                                {
                                    if(temp1%60 < 10)
                                    {
                                        Platform.runLater(() -> time_remaining.setText("00:0"+Integer.toString(temp1%60)));
                                    }
                                    else
                                    {
                                        Platform.runLater(() -> time_remaining.setText("00:"+Integer.toString(temp1%60)));
                                    }
                                }
                                else if(temp1%60 == 0)
                                {
                                    Platform.runLater(() -> time_remaining.setText(Integer.toString(temp1/60)+":00"));
                                }
                                else if(temp1%60 < 10)
                                {
                                    Platform.runLater(() -> time_remaining.setText(Integer.toString(temp1/60)+":0"+Integer.toString(temp1%60)));
                                }
                                else
                                {
                                    Platform.runLater(() -> time_remaining.setText(Integer.toString(temp1/60)+":"+Integer.toString(temp1%60)));
                                }
                                
                                Thread.sleep(1000);
                                if (temp == 0) {
                                    Platform.runLater(() -> submit.fire());
                                }
                            } catch (Exception e) {
                                System.out.println("Time class e problem");
                                e.printStackTrace();
                            }
                        }
                    }
                };
                Runnable obj2 = new Runnable() {
                    public void run() {
                        try {
                            int temp_recording = time * 60;
                            List<BufferedImage> imageList = new ArrayList<>();
                            
                            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                            Robot robot;
                            
                            robot = new Robot();
                            
                            File file = new File("Recording.mp4");
                            
                            System.out.println("getting screen images...");
                            
                            int count = 0;
                            
                            while (count < temp_recording) {
                                
                                BufferedImage image = robot.createScreenCapture(screenRect);
                                imageList.add(image);
                                
                                count++;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Answer_Script_Controller.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            
                            
                            //Making a video with captured screen shots with fps = 1
                            
                            
                            AWTSequenceEncoder sequenceEncoder = AWTSequenceEncoder.createSequenceEncoder(file, 1);
                            for (int i = 0; i < imageList.size(); i++) {
                                sequenceEncoder.encodeImage(imageList.get(i));
                                System.out.println("list encode " + i);
                                
                            }
                            sequenceEncoder.finish();
                        } catch (AWTException ex) {
                            Logger.getLogger(Answer_Script_Controller.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(Answer_Script_Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
                Thread t1 = new Thread(obj1);
                Thread t2 = new Thread(obj2);
                t1.start();
                t2.start();
                is_exam_started = true;
            }
            int x = Integer.parseInt(QuestionNumberBox.getValue().toString());
            Question_statement.setText(question[x].getQues_statement());
            Option_A.setText(question[x].getOption_a());
            Option_B.setText(question[x].getOption_b());
            Option_C.setText(question[x].getOption_c());
            Option_D.setText(question[x].getOption_d());

            Option_A.setSelected(false);
            Option_B.setSelected(false);
            Option_C.setSelected(false);
            Option_D.setSelected(false);

            if (answer[x] != null) {
                if (answer[x].getAns().equals(Option_A.getText())) {
                    Option_A.setSelected(true);
                    Option_B.setSelected(false);
                    Option_C.setSelected(false);
                    Option_D.setSelected(false);
                } else if (answer[x].getAns().equals(Option_B.getText())) {
                    Option_B.setSelected(true);
                    Option_A.setSelected(false);
                    Option_C.setSelected(false);
                    Option_D.setSelected(false);
                } else if (answer[x].getAns().equals(Option_C.getText())) {
                    Option_C.setSelected(true);
                    Option_B.setSelected(false);
                    Option_A.setSelected(false);
                    Option_D.setSelected(false);
                } else if (answer[x].getAns().equals(Option_D.getText())) {
                    Option_D.setSelected(true);
                    Option_B.setSelected(false);
                    Option_C.setSelected(false);
                    Option_A.setSelected(false);
                }
            }
        } catch (Exception e) {
            System.out.println("\nSelect question e problem\n");
            e.printStackTrace();
        }
    }

    /**
     * This function when triggered displays the previous question
     * @author Ifrad(180041225)
     * @param event 
     */
    public void previous_question(ActionEvent event) {
        try {
            int x = Integer.parseInt(QuestionNumberBox.getValue().toString());
            if (x - 1 >= 1) {
                x--;
                Question_statement.setText(question[x].getQues_statement());
                Option_A.setText(question[x].getOption_a());
                Option_B.setText(question[x].getOption_b());
                Option_C.setText(question[x].getOption_c());
                Option_D.setText(question[x].getOption_d());
                QuestionNumberBox.setValue(Integer.toString(x));
                if (answer[x] == null) {
                    Option_A.setSelected(false);
                    Option_B.setSelected(false);
                    Option_C.setSelected(false);
                    Option_D.setSelected(false);
                } else {
                    if (answer[x].getAns().equals(Option_A.getText())) {
                        Option_A.setSelected(true);
                        Option_B.setSelected(false);
                        Option_C.setSelected(false);
                        Option_D.setSelected(false);
                    } else if (answer[x].getAns().equals(Option_B.getText())) {
                        Option_B.setSelected(true);
                        Option_A.setSelected(false);
                        Option_C.setSelected(false);
                        Option_D.setSelected(false);
                    } else if (answer[x].getAns().equals(Option_C.getText())) {
                        Option_C.setSelected(true);
                        Option_B.setSelected(false);
                        Option_A.setSelected(false);
                        Option_D.setSelected(false);
                    } else if (answer[x].getAns().equals(Option_D.getText())) {
                        Option_D.setSelected(true);
                        Option_B.setSelected(false);
                        Option_C.setSelected(false);
                        Option_A.setSelected(false);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\nPrevious question e problem\n");
            e.printStackTrace();
        }
    }

    
    /**
     * This function when triggered displays the next question
     * 
     * @param event 
     * @author Ifrad(180041225)
     */
    public void next_question(ActionEvent event) {
        try {
            int x = Integer.parseInt(QuestionNumberBox.getValue().toString());
            if (x + 1 <= total_questions) {
                x++;
                Question_statement.setText(question[x].getQues_statement());
                Option_A.setText(question[x].getOption_a());
                Option_B.setText(question[x].getOption_b());
                Option_C.setText(question[x].getOption_c());
                Option_D.setText(question[x].getOption_d());
                QuestionNumberBox.setValue(Integer.toString(x));

                if (answer[x] == null) {
                    Option_A.setSelected(false);
                    Option_B.setSelected(false);
                    Option_C.setSelected(false);
                    Option_D.setSelected(false);
                } else {
                    if (answer[x].getAns().equals(Option_A.getText())) {
                        Option_A.setSelected(true);
                        Option_B.setSelected(false);
                        Option_C.setSelected(false);
                        Option_D.setSelected(false);
                    } else if (answer[x].getAns().equals(Option_B.getText())) {
                        Option_B.setSelected(true);
                        Option_A.setSelected(false);
                        Option_C.setSelected(false);
                        Option_D.setSelected(false);
                    } else if (answer[x].getAns().equals(Option_C.getText())) {
                        Option_C.setSelected(true);
                        Option_B.setSelected(false);
                        Option_A.setSelected(false);
                        Option_D.setSelected(false);
                    } else if (answer[x].getAns().equals(Option_D.getText())) {
                        Option_D.setSelected(true);
                        Option_B.setSelected(false);
                        Option_C.setSelected(false);
                        Option_A.setSelected(false);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\nPrevious question e problem\n");
            e.printStackTrace();
        }
    }


    /**
     * This function when triggered save the answer into an array
     * 
     * @param event 
     * @author Ifrad(180041225)
     */
    public void save_changes(ActionEvent event) {
        try {
            int x = Integer.parseInt(QuestionNumberBox.getValue().toString());
            if (answer[x] != null) {

                if (Option_A.isSelected()) {
                    System.out.println("Saved A");
                    answer[x].setAns(Option_A.getText());
                } else if (Option_B.isSelected()) {
                    System.out.println("Saved B");
                    answer[x].setAns(Option_B.getText());
                } else if (Option_C.isSelected()) {
                    System.out.println("Saved C");
                    answer[x].setAns(Option_C.getText());
                } else if (Option_D.isSelected()) {
                    System.out.println("Saved D");
                    answer[x].setAns(Option_D.getText());
                }

                answer[x].setQuestion_no(x);
                answer[x].setExam_code(exam_code);

            } else {
                if (Option_A.isSelected()) {
                    System.out.println("New Saved A");
                    answer[x] = new Answer(Option_A.getText(), x, exam_code);
                } else if (Option_B.isSelected()) {
                    System.out.println("New Saved B");
                    answer[x] = new Answer(Option_B.getText(), x, exam_code);
                } else if (Option_C.isSelected()) {
                    System.out.println("New Saved C");
                    answer[x] = new Answer(Option_C.getText(), x, exam_code);
                } else if (Option_D.isSelected()) {
                    System.out.println("New Saved D");
                    answer[x] = new Answer(Option_D.getText(), x, exam_code);
                }
            }
        } catch (Exception e) {
            System.out.println("\nSave changes e problem\n");
            e.printStackTrace();
        }

    }

    /**
     * This function when triggered save all the answers into the database and completes the submission
     * 
     * @param event pressing pushbutton submit
     * @throws SQLException 
     * 
     * @author Ifrad(180041225)
     */
    public void Submit(ActionEvent event) throws SQLException {
        PreparedStatement preparedstatement = null;
        try {
            for (int i = 1; i <= total_questions; i++) {

                if (answer[i] != null) {

                    String query1 = "Insert into Answer_Info(Exam_code,Question_no,Student_id,Answer) values (?,?,?,?)";
                    preparedstatement = connection.prepareStatement(query1);
                    preparedstatement.setString(1, Integer.toString(exam_code));
                    preparedstatement.setString(2, Integer.toString(i));
                    preparedstatement.setString(3, Integer.toString(student_id));

                    if (answer[i].getAns().equals(question[i].getOption_a())) {
                        preparedstatement.setString(4, "a");
                    } else if (answer[i].getAns().equals(question[i].getOption_b())) {
                        preparedstatement.setString(4, "b");
                    } else if (answer[i].getAns().equals(question[i].getOption_c())) {
                        preparedstatement.setString(4, "c");
                    } else if (answer[i].getAns().equals(question[i].getOption_d())) {
                        preparedstatement.setString(4, "d");
                    }

                    preparedstatement.executeUpdate();
                }
            }
            System.out.println("\nSubmitted\n");
            ((Node) event.getSource()).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("\nSubmit e problem\n");
            e.printStackTrace();
        } finally {
            preparedstatement.close();
        }
    }

}
