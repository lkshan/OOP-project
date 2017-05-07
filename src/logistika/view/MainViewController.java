package logistika.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import logistika.Main;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lukashanincik on 17/03/2017.
 */
public class MainViewController {

    private Main main;
    @FXML
    private Label cashLabel;

    final Timeline timeline = new Timeline(
            new KeyFrame(
                    Duration.millis( 1000 ),
                    event -> {
                        cashLabel.setText(Main.getCash()+"$");
                    }
            )
    );

    @FXML
    public void initialize(){
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }

    @FXML
    private void goHome() throws IOException {
        main.showMainItems();
    }

    @FXML
    private void addStorage() throws IOException {
        main.showFirstStorage();
    }

}
