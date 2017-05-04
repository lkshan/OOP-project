package logistika.createExpo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logistika.DBConnection;
import logistika.orderlist.DBOrder;
import logistika.orderlist.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by lukashanincik on 02/05/2017.
 */
public class createExpoController {
    @FXML
    private ChoiceBox storageChoiceBox;
    @FXML
    private ComboBox vehicleComboBox;
    @FXML
    private ChoiceBox orderChoiceBox;
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private Label infoLabel;

    @FXML
    private void initialize() throws SQLException {
        storageChoiceBox.setItems(getStorageNames());
        vehicleComboBox.setItems(getVehicleNames());
    }

    @FXML
    private void addToTable(){

    }

    @FXML
    private void createExpedition(){

    }

    private ObservableList<String> getStorageNames() throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs;
        ArrayList<String> storageNames = new ArrayList<String>();
        rs = connection.getStorageNameList();
        while (rs.next()){
            storageNames.add(rs.getString("name"));
        }
        return FXCollections.observableArrayList(storageNames);
    }

    @FXML
    private ObservableList<String> getVehicleNames() throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs;
        ArrayList<String> vehicleNames = new ArrayList<String>();
        rs = connection.getVehicles();
        while (rs.next()){
            vehicleNames.add(rs.getString("name"));
        }
        return FXCollections.observableArrayList(vehicleNames);
    }

    private ObservableList<String> createOrderChoiceBox() throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs;
        String orderType = new String(vehicleComboBox.getValue().toString());
        ArrayList<String> specifiedOrderAL = new ArrayList<String>();
        int i = 0;
        if (orderType.contains("Freezer")) i = 1;
            else if (orderType.contains("Fuel")) i = 2;
                else if (orderType.contains("Chemical")) i = 3;
                    else if (orderType.contains("Pallet")) i = 4;
        if (i != 0){
            //ordinary vehicle
            rs = connection.getOrders(storageChoiceBox.getValue().toString(), i);
        }
        else {
            //super vehicle
            rs = connection.getOrders(storageChoiceBox.getValue().toString());
        }
        while (rs.next()){
            String item = new String(rs.getString("storages.name") + " -> " + rs.getString("cities.name") + ": " + rs.getInt("myOrders.distance"));
            specifiedOrderAL.add(item);
        }
        return FXCollections.observableArrayList(specifiedOrderAL);
    }

    @FXML
    private void setOrderChoiceBox() throws SQLException {
        orderChoiceBox.setItems(createOrderChoiceBox());
    }
}
