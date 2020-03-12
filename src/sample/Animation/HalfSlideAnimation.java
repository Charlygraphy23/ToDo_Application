package sample.Animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;


public class HalfSlideAnimation {
    private TranslateTransition translateTransition;

    public HalfSlideAnimation(Node e,double width){
        translateTransition=new TranslateTransition(Duration.millis(600),e);
        translateTransition.setFromX(width);
        translateTransition.setToX(width/2);
        translateTransition.setCycleCount(1);
       // translateTransition.setByY(85.0);
        translateTransition.setAutoReverse(false);
    }
    public void play(){
        translateTransition.play();
    }
}
