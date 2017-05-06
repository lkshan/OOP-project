package logistika.runningExpo;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logistika.Main;

import java.io.IOException;

/**
 * Created by lukashanincik on 07/05/2017.
 */
public class progressController {

    @FXML
    private Button btnClose;
    @FXML
    private Label titleLabel;
    @FXML
    private AnchorPane centralAnchorPane;

    public void initialize() throws IOException {

        final Service thread = new Service<Integer>() {
            @Override
            public Task createTask() {
               return new Task<Integer>() {
                   @Override
                   public Integer call() throws InterruptedException {
                       int i;
                       for (i = 0; i < 1000; i++){
                           updateProgress(i, 1000);
                           Thread.sleep(10);
                       }
                       return i;
                   }
               };
            }
        };

        ProgressBar pbPrimary = new ProgressBar();
        pbPrimary.setMinSize(350, 60);

        pbPrimary.progressProperty().bind(thread.progressProperty());

        centralAnchorPane.setTopAnchor(pbPrimary, 10.0);
        centralAnchorPane.getChildren().add(pbPrimary);

        thread.restart();
        /*btnStart.setText("Start");
        btnStart.setOnAction(e -> {
            thread.restart();
            btnStart.setText("Restart");
            btnCancel.setText("Cancel");
            btnCancel.setDisable(false);
        });*/

        /*btnCancel.setOnAction(e -> {
            if (thread.isRunning()){
                thread.cancel();
                btnCancel.setText("Reset");
            }
            else if(btnCancel.getText().equals("Reset")){
                thread.reset();
                btnCancel.setDisable(true);
                btnCancel.setText("Cancel");
                btnStart.setText("Start");
            }
        });*/
        btnClose.setOnAction(e -> {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }
}
