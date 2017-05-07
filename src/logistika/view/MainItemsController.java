package logistika.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logistika.DBConnection;
import logistika.Main;
import logistika.orderlist.OrderList;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by lukashanincik on 17/03/2017.
 */
public class MainItemsController {

    private Main main;

    @FXML
    private void goStorages() throws IOException {
        main.showStoragesScene();
    }

    @FXML
    private void goOrderlist() throws IOException, SQLException {
        main.showOrderlistScene();
        main.showOrderTableView();
    }

    @FXML
    private void exit() throws SQLException {
        DBConnection connection = new DBConnection();
        connection.eraseDB();
        closeStage();
    }
    @FXML
    private Button exitButton;
    private void closeStage(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void goShop() throws IOException, SQLException {
        main.showShopScene();
        main.showShopTableList();
    }
    @FXML
    private void goVehicles() throws IOException, SQLException {
        main.showVehicleListScene();
        main.showVehicleTableList();
    }
    @FXML
    private void goCreateExpo() throws IOException {
        main.showCreateExpoScene();
    }
    @FXML
    private void goRunningExpo() throws IOException{
        main.showRunningExpoScene();
    }
    @FXML
    private void goDoneOrders() throws IOException {
        main.showDoneExpeditionScene();
    }
}
