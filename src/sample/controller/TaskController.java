package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Animation.ReverseAnimation;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

public class TaskController {

    @FXML
    private AnchorPane taskPane;

    @FXML
    private JFXTextField taskTextField;

    @FXML
    private JFXTextArea descriptionTextField;

    @FXML
    private JFXButton addtaskButton;

    @FXML
    private ImageView backButton;

    @FXML
    private JFXListView<Task> listViewTask;

    @FXML
    private ImageView updateList;

    @FXML
    private Label taskLAbel;

    private ObservableList<Task> taskObservableList;
    public static AnchorPane tpane;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        tpane=new AnchorPane();
        tpane=taskPane;
        backButton.setOnMouseClicked(e->{
            try {
                getUserdetailScreen();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        addtaskButton.setOnAction(e->{
            try {
                addtask();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        updateList.setOnMouseClicked(e->{
            try {
                updatelst();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });





        ResultSet resultSet=null;
        DatabaseHandler handler=new DatabaseHandler();
        resultSet=handler.getTask();
        taskObservableList=FXCollections.observableArrayList();
        while (resultSet.next()){
            Task task=new Task();
            task.setTaskid(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDescription(resultSet.getString("description"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            taskObservableList.addAll(task);
        }
        listViewTask.setItems(taskObservableList);
        listViewTask.setCellFactory(CellController->new CellController());



    }                   // End of Initialize



    private void updatelst() throws SQLException, ClassNotFoundException {
        initialize();

    }

    private void addtask() throws SQLException, ClassNotFoundException {
        if(!taskTextField.getText().equals("") && !descriptionTextField.getText().equals("")){
            Timestamp timestamp=new Timestamp(Calendar.getInstance().getTimeInMillis());
            DatabaseHandler handler=new DatabaseHandler();
            Task task=new Task(taskTextField.getText(),descriptionTextField.getText(),timestamp);
            handler.addTask(task);
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Task Added Successful");
            alert.show();
            initialize();
            clearAll();
        }else {
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setContentText("!! Fill It Properly !!");
            alert.show();
        }
    }

    private void clearAll() {
        taskTextField.setText("");
        descriptionTextField.setText("");
    }

    private void getUserdetailScreen() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/sample/view/userdetailsview.fxml"));
        ReverseAnimation reverseAnimation=new ReverseAnimation(root,backButton.getScene().getWidth());
        reverseAnimation.play();
        taskPane.getChildren().setAll(root);
    }

}
