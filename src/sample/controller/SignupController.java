package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import sample.Animation.ReverseAnimation;
import sample.Animation.SlideAnimation;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.*;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Pattern;

public class SignupController {

    @FXML
    private AnchorPane signupPane;

    @FXML
    private JFXTextField firstnameTextfield;

    @FXML
    private JFXTextField lastnameTextfield;

    @FXML
    private JFXTextField usernameTextfield;

    @FXML
    private JFXTextField passwordTextfield;

    @FXML
    private JFXTextField mailTextfield;

    @FXML
    private JFXTextField mobilenoTextfield;

    @FXML
    private ImageView photoImageview;

    @FXML
    private JFXButton uploadButton;

    @FXML
    private JFXButton signupbutton;

    @FXML
    private JFXButton canclebutton;

    @FXML
    private JFXComboBox<String> genderCombobox;

    private String[] gender={"Male","Female","Other"};
    private ObservableList<String> observableList;

    private InputStream inputStream=null;
    private File file;

    @FXML
    void initialize() {
        observableList= FXCollections.observableList(new ArrayList<String>(Arrays.asList(gender)));
        genderCombobox.setItems(observableList);


        firstnameTextfield.setOnKeyReleased(e->{
            if(firstnameTextfield.getText().matches("[a-z[A-Z]]+") || e.getCode().equals(KeyCode.TAB)|| e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.ENTER)){}
            else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("!! Name Does not contain any digit or special Character !!");
                alert.show();
            }
        });             // validate first name

        lastnameTextfield.setOnKeyReleased(e->{
            if(lastnameTextfield.getText().matches("[a-z[A-Z]]+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.ENTER)){}
            else  {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("!! Name Does not contain any digit or special Character !!");
                alert.show();
            }
        });             // validate lastname

        usernameTextfield.setOnKeyReleased(e->{
            if(usernameTextfield.getText().matches("[a-z[A-Z[0-9]]]+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.ENTER)){}
            else  {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("!! Name Does not contain any special Character !!");
                alert.show();
            }});            // validate Username

        mobilenoTextfield.setOnKeyReleased(e->{
            if(mobilenoTextfield.getText().matches("\\d+") || e.getCode().equals(KeyCode.TAB) || e.getCode().equals(KeyCode.BACK_SPACE) || e.getCode().equals(KeyCode.ENTER)){}
            else  {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("!! Only Digits !!");
                alert.show();
            }});                    // validating mobileno


        uploadButton.setOnAction(e->{
            try {
                getImage();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        });


       signupbutton.setOnAction(e->{
           try {
               userSignUp();
           } catch (IOException ex) {
               ex.printStackTrace();
           }
       });            // signup button

        canclebutton.setOnAction(e -> {
            try {
                getLoginWindow();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }                           // End of Initialize


    private void userSignUp() throws IOException {
        if(inputStream!=null)
        if(mobilenoTextfield.getText().matches("^\\d{10}$")){
            if(mailTextfield.getText().matches("\\S+?@\\S+")){
                if(!firstnameTextfield.getText().equals("") && !lastnameTextfield.getText().equals("") && genderCombobox.getSelectionModel() != null && !usernameTextfield.getText().equals("") && !mailTextfield.getText().equals("") && !mobilenoTextfield.getText().equals("") && inputStream!=null){
                    DatabaseHandler handler=new DatabaseHandler();
                    Timestamp timestamp= new Timestamp(Calendar.getInstance().getTimeInMillis());
                    User user=new User(mobilenoTextfield.getText(),firstnameTextfield.getText(),lastnameTextfield.getText(),genderCombobox.getSelectionModel().getSelectedItem(),usernameTextfield.getText(),passwordTextfield.getText(),mailTextfield.getText(),inputStream,timestamp);
                    try {
                        handler.setUserData(user);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/view/loginsample.fxml"));
                    ReverseAnimation reverseAnimation = new ReverseAnimation(root, canclebutton.getScene().getWidth());
                    signupPane.getChildren().setAll(root);
                    reverseAnimation.play();

                }
                else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("!! Insert Data Properly !!");
                    alert.show();
                }
            }
            else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("!! Not A valid Email Id !!");
                alert.show();
            }
        }
        else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("!! Mobile No Should have  10 Digits !!");
            alert.show();
        }
        else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("!! Choose Any Photo !!");
            alert.show();
        }
    }

    private void getImage() throws FileNotFoundException, MalformedURLException {
        FileChooser chooser=new FileChooser();
        file=chooser.showOpenDialog(uploadButton.getScene().getWindow());
        inputStream=new FileInputStream(file);
        if(file != null){
            photoImageview.setImage(new Image(file.toURI().toURL().toString()));
        }
    }

    private void getLoginWindow() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/loginsample.fxml"));
        ReverseAnimation reverseAnimation = new ReverseAnimation(root, canclebutton.getScene().getWidth());
        signupPane.getChildren().setAll(root);
        reverseAnimation.play();

    }
}