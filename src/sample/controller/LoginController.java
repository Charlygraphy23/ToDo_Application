package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Animation.SlideAnimation;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private JFXTextField usernameTextfield;

    @FXML
    private JFXPasswordField passwardTextfield;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Label registerButton;

    public static int id=0;

    @FXML
    void initialize() {
        registerButton.setOnMouseClicked(e->{
            try {
                getSignUpWindow();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        loginButton.setOnAction(e->{
            try {
                getUser();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void getUser() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        DatabaseHandler handler=new DatabaseHandler();
        User user=new User();
        if(!usernameTextfield.getText().equals("")) {
            user.setUserName(usernameTextfield.getText());
            user.setPassword(passwardTextfield.getText());
            resultSet =handler.getUserdata(user);

        int c=0;
            while (resultSet.next()){
                id=resultSet.getInt("id");
                c++;
            }
       if(c>0){
                loginButton.getScene().getWindow().hide();
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/sample/view/userdetailsview.fxml"));
                Stage stage=new Stage();
           try {
               stage.setScene(new Scene((Pane) loader.load()));
           } catch (IOException e) {
               e.printStackTrace();
           }
           stage.initStyle(StageStyle.DECORATED);
           stage.setResizable(false);
           stage.getIcons().add(new Image("file:C:/Users/Person/Documents/Java/todolisticon.png"));
           stage.show();
       }
       else {
           Alert alert=new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Enter a Valid Username or Password");
           alert.show();
       }
       }
        else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter Details Properly");
            alert.show();
        }
    }



    private void getSignUpWindow() throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("/sample/view/signupview.fxml"));
        SlideAnimation slideAnimation=new SlideAnimation(root,registerButton.getScene().getWidth());
        slideAnimation.getSlider();
        loginPane.getChildren().setAll(root);

    }
}
