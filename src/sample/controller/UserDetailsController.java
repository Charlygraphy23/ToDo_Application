package sample.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Animation.SlideAnimation;
import sample.database.DatabaseHandler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailsController {

    @FXML
    private AnchorPane userdetailsPane;

    @FXML
    private Label loginName;

    @FXML
    private ImageView image;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField mail;

    @FXML
    private JFXTextField mobileno;

    @FXML
    private JFXTextField gender;

    @FXML
    private JFXTextField username;

    @FXML
    private ImageView backButton;

    @FXML
    private Label redDotBackground;

    @FXML
    private ImageView addTask;

    @FXML
    private Label countTask;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        String namee=null,genderr=null,maill=null,usernamee=null,mobileNoo=null;
        InputStream imagee=null;
        DatabaseHandler databaseHandler=new DatabaseHandler();
        resultSet=databaseHandler.getUserdetailsPage();

        while (resultSet.next()){
            namee=resultSet.getString("firstname")+" "+resultSet.getString("lastname");
            genderr=resultSet.getString("gender");
            maill=resultSet.getString("mail");
            usernamee=resultSet.getString("username");
            mobileNoo=resultSet.getString("mobileno");
            imagee=resultSet.getBinaryStream("image");
        }

        id.setText(String.valueOf(LoginController.id));
        loginName.setText(namee);
        name.setText(namee);
        gender.setText(genderr);
        mail.setText(maill);
        username.setText(usernamee);
        mobileno.setText(mobileNoo);
        image.setImage(new Image(imagee));

        backButton.setOnMouseClicked(e->{
            try {
                getBacktoLogin();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        addTask.setOnMouseClicked(e->{
            try {
                getTask();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        int count=0;
        resultSet=databaseHandler.getCountTasks();
        while (resultSet.next()){
            count=resultSet.getInt(1);
        }

        if(count>0){
            countTask.setVisible(true);
            redDotBackground.setVisible(true);
            countTask.setText(String.valueOf(count));
        }
        else {
            countTask.setVisible(false);
            redDotBackground.setVisible(false);
        }

    }                       // End of Initialize

    private void getTask() throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("/sample/view/taskview.fxml"));
        SlideAnimation slideAnimation=new SlideAnimation(root,addTask.getScene().getWidth());
        slideAnimation.getSlider();
        userdetailsPane.getChildren().setAll(root);

    }

    private void getBacktoLogin() throws IOException {
        backButton.getScene().getWindow().hide();
        Parent root= FXMLLoader.load(getClass().getResource("/sample/view/loginsample.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.getIcons().add(new Image("file:C:/Users/Person/Documents/Java/todolisticon.png"));
        stage.show();
    }
}
