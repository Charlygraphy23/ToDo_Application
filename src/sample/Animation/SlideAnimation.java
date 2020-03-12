package sample.Animation;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.util.Duration;

public class SlideAnimation {
     TranslateTransition transition;

    public SlideAnimation(Node e,Double width) {
        transition=new TranslateTransition(Duration.millis(600),e);
        transition.setFromX(width);
        transition.setToX(0);
        transition.setInterpolator(Interpolator.EASE_IN);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
    }

    public void getSlider() {
        transition.play();
    }
}
