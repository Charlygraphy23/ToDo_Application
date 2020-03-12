package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import sample.Animation.ReverseHalfSlideAnimation;
import sample.database.DatabaseHandler;
import sample.model.Task;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;


public class UpdateTaskController {

    @FXML
    private AnchorPane updateTAskPane;

    @FXML
    private JFXTextField taskTextField;

    @FXML
    private JFXTextArea descriptionTextField;

    @FXML
    private JFXButton updatetaskButton;

    public static AnchorPane upane;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        upane=new AnchorPane();
        upane=updateTAskPane;
        getTask();

        updatetaskButton.setOnAction(e->{
            if(!taskTextField.getText().equals("") && !descriptionTextField.getText().equals("")){
                Timestamp timestamp=new Timestamp(Calendar.getInstance().getTimeInMillis());
                DatabaseHandler handler=new DatabaseHandler();
                Task task=new Task(taskTextField.getText(),descriptionTextField.getText(),timestamp);
                task.setTaskid(CellController.tskId);
                try {
                    handler.updateTask(task);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                ReverseHalfSlideAnimation reverseHalfSlideAnimation=new ReverseHalfSlideAnimation(updateTAskPane,updateTAskPane.getWidth());
                reverseHalfSlideAnimation.play();
                clearAll();

            }
            else {
                Alert alert=new Alert(Alert.AlertType.ERROR,"Insert Details Properly");
                alert.show();
            }

         });

    }                               // End of Initialize

    private void clearAll() {
        taskTextField.setText("");
        descriptionTextField.setText("");
    }

    private void getTask() throws SQLException, ClassNotFoundException {
        String task=null,description=null;
        ResultSet resultSet=null;
        DatabaseHandler handler=new DatabaseHandler();
        resultSet=handler.getTaskbyId();
        while (resultSet.next()){
            task=resultSet.getString("task");
            description=resultSet.getString("description");
        }
        taskTextField.setText(task);
        descriptionTextField.setText(description);
    }
}
