package sample.Animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ReverseHalfSlideAnimation {
    private TranslateTransition translateTransition;

    public ReverseHalfSlideAnimation(Node e,double width) {
        translateTransition=new TranslateTransition(Duration.millis(600),e);
        translateTransition.setToX(width);
        translateTransition.setToX((width*2)+180);


    }
    public void play(){
        translateTransition.play();
    }
}
