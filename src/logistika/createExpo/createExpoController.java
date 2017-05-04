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

import static java.lang.Math.pow;

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
    private AnchorPane tableAnchorPane;
    private TableView<DBOrder> orderTableView;
    @FXML
    private Label infoLabel;

    ArrayList<DBOrder> orders = new ArrayList<DBOrder>();

    @FXML
    private void initialize() throws SQLException {
        storageChoiceBox.setItems(getStorageNames());
        vehicleComboBox.setItems(getVehicleNames());
    }

    @FXML
    private void addToTable() throws SQLException {
        int id = getId(orderChoiceBox.getValue().toString());
        DBConnection connection = new DBConnection();
        ResultSet rs = connection.getOrderById(id);
        DBOrder dbOrder = new DBOrder();
        while (rs.next()){
            dbOrder.setId_order(id);
            dbOrder.setStorageName(rs.getString("storages.name"));
            dbOrder.setCityName(rs.getString("cities.name"));
            dbOrder.setVzdialenost(rs.getInt("myOrders.distance"));
        }
        orders.add(dbOrder);
        initializeTable();
    }

    private int getId(String str){
        int id, i=0;
        char c = str.charAt(0);
        String substr = new String();
        while (c != '.'){
            substr += c;
            i++;
            c = str.charAt(i);
        }
        id = intToStr(substr);
        return id;
    }

    private void initializeTable(){
        TableColumn<DBOrder, String> storageName = new TableColumn<>("Storage");
        storageName.setCellValueFactory(new PropertyValueFactory<DBOrder, String>("storageName"));
        TableColumn<DBOrder, Integer> destinationName = new TableColumn<>("Destination");
        destinationName.setCellValueFactory(new PropertyValueFactory<DBOrder, Integer>("cityName"));
        TableColumn<DBOrder, Integer> distance = new TableColumn<>("Distance");
        distance.setCellValueFactory(new PropertyValueFactory<DBOrder, Integer>("vzdialenost"));
        for (DBOrder dbOrder1 : orders){
            System.out.println(dbOrder1.getStorageName() + " " + dbOrder1.getCityName());
        }
        orderTableView = new TableView<DBOrder>();
        orderTableView.setItems(FXCollections.observableArrayList(orders));
        orderTableView.getColumns().addAll(storageName, destinationName, distance);
        orderTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        orderTableView.setMaxHeight(200);
        orderTableView.setPrefWidth(500);
        tableAnchorPane.setTopAnchor(orderTableView, 10.0);
        tableAnchorPane.getChildren().add(orderTableView);
        //infoLabel.setText();
        //initializing infolabel
        //total distance, time, costs, profit
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
            String item = new String(rs.getInt("myOrders.id_order") + ". " + rs.getString("storages.name") + " -> " + rs.getString("cities.name") + ": " + rs.getInt("myOrders.distance"));
            specifiedOrderAL.add(item);
        }
        return FXCollections.observableArrayList(specifiedOrderAL);
    }

    @FXML
    private void setOrderChoiceBox() throws SQLException {
        orderChoiceBox.setItems(createOrderChoiceBox());
    }

    public int intToStr(String str){
        int id = 0;
        for (int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            int tempt=0;
            tempt = (int) c;
            tempt -= 48;
            id += tempt * pow(10, (str.length() - i)-1);
        }
        return id;
    }
}
