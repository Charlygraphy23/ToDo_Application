package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.Animation.HalfSlideAnimation;
import sample.Animation.SlideAnimation;
import sample.database.DatabaseHandler;
import sample.model.Task;
import java.io.IOException;
import java.sql.SQLException;

public class CellController extends JFXListCell<Task> {
    @FXML
    private AnchorPane cellPane;

    @FXML
    private Label taskId;

    @FXML
    private Label task;

    @FXML
    private Label description;

    @FXML
    private Label time;

    @FXML
    private ImageView taskDeleteButton;

    @FXML
    private ImageView taskUpdateButton;


    private FXMLLoader loader;
    public static int tskId;

    @FXML
    void initialize() {

    }


    @Override
    protected void updateItem(Task tsk, boolean empty) {
        super.updateItem(tsk, empty);

        if(empty || tsk==null)
        {
            setText(null);
            setGraphic(null);
        }
        else {
            if(loader==null){
                loader=new FXMLLoader(getClass().getResource("/sample/view/cellview.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tskId=tsk.getTaskid();
            taskId.setText(String.valueOf(tsk.getTaskid()));
            task.setText(tsk.getTask());
            description.setText(tsk.getDescription());
            time.setText(tsk.getDatecreated().toString());

            taskDeleteButton.setOnMouseClicked(e->{
                DatabaseHandler handler=new DatabaseHandler();
                try {
                    handler.deleteTask(tsk);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                getListView().getItems().remove(getItem());
            });

            taskUpdateButton.setOnMouseClicked(e->{
                Parent root= null;
                try {
                    root =FXMLLoader.load(getClass().getResource("/sample/view/updatetaskview.fxml"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                HalfSlideAnimation halfSlideAnimation=new HalfSlideAnimation(root,TaskController.tpane.getWidth());
                halfSlideAnimation.play();
                TaskController.tpane.getChildren().addAll(root);
                root.setLayoutY(80);
            });

            setText(null);
            setGraphic(cellPane);
        }

    }
}
