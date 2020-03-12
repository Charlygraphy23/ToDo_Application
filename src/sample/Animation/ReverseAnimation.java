package sample.Animation;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;



public class ReverseAnimation {
    TranslateTransition translateTransition;

    public ReverseAnimation(Node e,Double width) {
       translateTransition=new TranslateTransition(Duration.millis(600),e);
       translateTransition.setFromX(-width);
       translateTransition.setToX(0);
       translateTransition.setAutoReverse(false);
       translateTransition.setCycleCount(1);
       translateTransition.setInterpolator(Interpolator.EASE_OUT);

    }

    public void play(){
        translateTransition.play();
    }
}
