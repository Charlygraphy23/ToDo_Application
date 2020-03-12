package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/loginsample.fxml"));
        primaryStage.setTitle("Todo Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.getIcons().add(new Image("file:C:/Users/Person/Documents/Java/todolisticon.png"));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
