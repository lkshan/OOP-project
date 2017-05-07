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
import logistika.DBConnection;
import logistika.Main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
                   public Integer call() throws InterruptedException, SQLException {
                            int id = runningExpoController.getSelectedExpedition();
                            Double status = 0.0, time = 0.0;
                            for(ExpeditionStatus expeditionStatus : runningExpoController.getExpeditionsAL()){
                                if (expeditionStatus.getId_expedition() == id){
                                    time = expeditionStatus.getTime();
                                    status = createStatus(expeditionStatus.getDate(), time);
                                }
                            }
                            int i;
                            Double begin = time*status, percentage;
                            begin *= 10000;
                            //System.out.println(status+" | " + time + " | " + begin + " | " +begin.intValue());
                            if (1 > status){
                                for (i = begin.intValue(); i < time*10000; i += 11){
                                    //percentage = i / (time*10000);
                                    //titleLabel.setText(percentage.intValue()+"%");
                                    updateProgress(i, time*10000);
                                    Thread.sleep(10);
                                }
                                System.out.println("Incomplete");
                            }
                            else {
                                //titleLabel.setText("100%");
                                for (i = 999; i < 1000; i++){
                                    updateProgress(i, 100);
                                    Thread.sleep(10);
                                }
                                System.out.println("Complete");
                            }
                            return i;
                            /*for (i = 0; i < 1000; i++){
                                updateProgress(i, 1000);
                                Thread.sleep(10);
                            }
                            return i;*/
                   }
               };
            }
        };

        ProgressBar pbPrimary = new ProgressBar();
        pbPrimary.setMinSize(350, 60);

        pbPrimary.progressProperty().bind(thread.progressProperty());

        centralAnchorPane.setTopAnchor(pbPrimary, 10.0);
        centralAnchorPane.getChildren().add(pbPrimary);
        titleLabel.setText("Selected expedition: "+runningExpoController.getSelectedExpedition());
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

    public Double createStatus(Timestamp date, Double time) throws SQLException {
        DBConnection connection = new DBConnection();
        Double status = 0.0;
        int nowSec = 0;
        ResultSet rs = connection.getNow();
        while (rs.next()){
            Timestamp now = rs.getTimestamp("NOW()");
            nowSec = dateToSec(now);
        }
        int beginTime = dateToSec(date);
        if ((beginTime+(time*10)) <= nowSec){
            status = 1.0;
        }
        else{
            int totalTime = beginTime+(time.intValue()*100);
            int a = nowSec - beginTime;
            int b = totalTime - beginTime;
            Double a1 = a*0.1*10;
            Double b1 = b*0.1;
            status = a1 / b1;
        }
        return status;
    }

    public int dateToSec(Timestamp date){
        int sec, min, hou, day;
        Date date1 = date;
        sec = date.getSeconds();
        min = date.getMinutes();
        hou = date.getHours();
        day = date.getDate();
        return (sec + (min * 60) + (hou * 3600) + (day * 86400));
    }
}
