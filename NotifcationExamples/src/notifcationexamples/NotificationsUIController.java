/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button button1;
    
    @FXML 
    private Button button2;
    
    @FXML
    private Button button3;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
   
    @FXML
    public void startTask1(ActionEvent event) {
        
        if (task1 == null) {
            button1.setText("Stop Task 1");
            System.out.println("start task 1");
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
        } 
        else {
            if (button1.getText().equals("Start Task 1")) {
                System.out.println("start task 1");
                task1.resumeTask1();
                button1.setText("Stop Task 1");
            } else if (button1.getText().equals("Stop Task 1")) {
                System.out.println("stop task 1");
                task1.suspendTask1();
                button1.setText("Start Task 1");
            }
        }
        
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            button1.setText("Start Task 1");
        }
        textArea.appendText(message + "\n");
    }

    @FXML
    public void startTask2(ActionEvent event) {
        
                
        if (task2 == null) {
            System.out.println("start task 2");
            button2.setText("Stop Task 2");
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                if (message.equals("Task1 done.")) {
                    task1 = null;
                    button1.setText("Start Task 1");
                 }   
                textArea.appendText(message + "\n");
                
            });
            
            task2.start();
        } 
        else {
            if (button2.getText().equals("Start Task 2") 
                     && task2 != null) {
                System.out.println("start task 2");
                task2.resumeTask2();
                button2.setText("Stop Task 2");
            } else if (button2.getText().equals("Stop Task 2") 
                    && task2 != null) {
                /* Start Task 2 Button */
                System.out.println("stop task 2");
                task2.suspendTask2();
                button2.setText("Start Task 2");
            }
        }
    }


    @FXML
    public void startTask3(ActionEvent event) {
        
                  
        if (task3 == null) {
            System.out.println("start task 3");
            button3.setText("Stop Task 3");
            task3 = new Task3(2147483647, 1000000);
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
            });
            
            task3.start();
        } 
        else {

            if (button3.getText().equals("Start Task 3") 
                       && task3 != null) {
                System.out.println("start task 3");
                task3.resumeTask3();
                button3.setText("Stop Task 3");
            } else if (button3.getText().equals("Stop Task 3") 
                    && task3 != null) {
                System.out.println("stop task 3");
                task3.suspendTask3();
                button3.setText("Start Task 3");
            }
        }
    } 
}
