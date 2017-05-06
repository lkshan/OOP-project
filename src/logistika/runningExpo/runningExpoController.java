package logistika.runningExpo;

import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logistika.DBConnection;
import logistika.Main;
import logistika.vehicles.Vehicle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.DoubleSummaryStatistics;

/**
 * Created by lukashanincik on 06/05/2017.
 */
public class runningExpoController {
    @FXML
    private AnchorPane centralAnchorPane;
    private TableView runningExpoTL;
    private ArrayList<ExpeditionStatus> expeditionsAL = new ArrayList<ExpeditionStatus>();

    @FXML
    public void initialize() throws SQLException {
        initializeTable();
        centralAnchorPane.setTopAnchor(runningExpoTL, 10.0); // obviously provide your own constraints
        centralAnchorPane.getChildren().add(runningExpoTL);
    }

    public void initializeTable() throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs;
        rs = connection.getAllExpeditions();
        while (rs.next()){
            ExpeditionStatus expedition = new ExpeditionStatus(rs.getInt("id_expedition"), rs.getInt("totalDistance"), rs.getTimestamp("beginDate"), rs.getDouble("totalTime"), rs.getDouble("costs"), rs.getDouble("profit"));
            expedition.setStatus(createStatus(rs.getTimestamp("beginDate"), rs.getDouble("totalTime")));
            expeditionsAL.add(expedition);
        }

        TableColumn<ExpeditionStatus, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Integer>("id_expedition"));
        TableColumn<ExpeditionStatus, Integer> distColumn = new TableColumn<>("Distance");
        distColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Integer>("distance"));
        TableColumn<ExpeditionStatus, Timestamp> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Timestamp>("date"));
        TableColumn<ExpeditionStatus, Double> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Double>("time"));
        TableColumn<ExpeditionStatus, Double> profitColumn = new TableColumn<>("Profit");
        profitColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Double>("profit"));

        runningExpoTL = new TableView<>();
        runningExpoTL.setPrefWidth(600);
        runningExpoTL.setMaxHeight(280);
        runningExpoTL.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        runningExpoTL.setItems(FXCollections.observableArrayList(expeditionsAL));
        runningExpoTL.getColumns().addAll(idColumn, distColumn, dateColumn, timeColumn, profitColumn);
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
        if ((dateToSec(date)+(time*10)) <= nowSec){
            status = 100.0;
        }
        else{
            Double totalTime = dateToSec(date)+(time*100);
            status = (( nowSec / totalTime ) * 100);
        }
        return status;
    }

    public int dateToSec(Timestamp date){
        int sec, min, hou, day;
        sec = date.getSeconds();
        min = date.getMinutes();
        hou = date.getHours();
        day = date.getDay();
        return (sec + (min * 60) + (hou * 3600) + (day * 86400));
    }


    @FXML
    public void showProgress() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("runningExpo/progress.fxml"));
        BorderPane progress = loader.load();

        Stage progressStage = new Stage();
        progressStage.setTitle("Progress");
        progressStage.initModality(Modality.WINDOW_MODAL);
        progressStage.initOwner(Main.getPrimaryStage());
        Scene scene = new Scene(progress);
        progressStage.setScene(scene);
        progressStage.showAndWait();

        /*final Service thread = new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
               return new Task<Integer>() {
                   @Override
                   protected Integer call() throws Exception {
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
        pbPrimary.setMinSize(400, 60);
        ProgressIndicator piPrimary = new ProgressIndicator();
        piPrimary.setMinSize(60, 60);

        pbPrimary.progressProperty().bind(thread.progressProperty());
        piPrimary.progressProperty().bind(thread.progressProperty());

        progress.setCenter(pbPrimary);

        btnStart.setText("Start");
        btnStart.setOnAction(e -> {
            thread.restart();
            btnStart.setText("Restart");
            btnCancel.setText("Cancel");
            btnCancel.setDisable(false);
        });

        btnCancel.setOnAction(e -> {
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
    }
}
