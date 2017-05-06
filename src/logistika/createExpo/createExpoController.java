package logistika.createExpo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logistika.DBConnection;
import logistika.map.Cities;
import logistika.orderlist.DBOrder;
import logistika.orderlist.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.Math.pow;
import static jdk.nashorn.internal.objects.NativeMath.round;

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
    int dist = 0, firstclick = 1;
    double time = 0, costs = 0, profit = 0;
    Cities startingCity = new Cities();

    ArrayList<DBOrder> orders = new ArrayList<DBOrder>();

    @FXML
    private void initialize() throws SQLException {
        storageChoiceBox.setItems(getStorageNames());
        vehicleComboBox.setItems(getVehicleNames());
    }

    @FXML
    private void addToTable() throws SQLException {
        if (orders.size() < 5){
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
            if (firstclick == 1){
                rs = connection.getStorageByName(dbOrder.getStorageName());
                while (rs.next()){
                    startingCity.setName(rs.getString("cities.name"));
                    startingCity.setX(rs.getInt("cities.x"));
                    startingCity.setY(rs.getInt("cities.y"));
                }
                firstclick = 0;
            }
            initializeLabel(dbOrder);
            initializeTable();
        }
        else System.out.println("Maximalne 5 objednavok");
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
        orderTableView = new TableView<DBOrder>();
        orderTableView.setItems(FXCollections.observableArrayList(orders));
        orderTableView.getColumns().addAll(storageName, destinationName, distance);
        orderTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        orderTableView.setMaxHeight(155);
        orderTableView.setPrefWidth(500);
        tableAnchorPane.setTopAnchor(orderTableView, 10.0);
        tableAnchorPane.getChildren().add(orderTableView);
        //
        //
        //
        //verifying specifying of orders
        //
        //
        //
        //initializing infolabel
        //total distance, time, costs, profit
    }

    private void initializeLabel(DBOrder dbOrder) throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs;
        rs = connection.getCityByName(dbOrder.getCityName());
        Cities nextCity = new Cities();
        while (rs.next()){
            nextCity.setName(dbOrder.getCityName());
            nextCity.setX(rs.getInt("x"));
            nextCity.setY(rs.getInt("y"));
        }
        int a, b;
        double ab;
        a = Math.abs( startingCity.getX() - nextCity.getX() ) +1 ;
        b = Math.abs( startingCity.getY() - nextCity.getY() ) +1 ;
        ab = Math.pow(a, 2) + Math.pow(b, 2);
        dist += (int) Math.round( Math.sqrt(ab) );

        startingCity.setName(nextCity.getName());
        startingCity.setX(nextCity.getX());
        startingCity.setY(nextCity.getY());
        rs = connection.getVehicleSpeedByName(vehicleComboBox.getValue().toString());
        while (rs.next()){
           time = dist / rs.getDouble("speed");
        }

        costs = ( ( dist * 0.2 ) * ( 1 - ( 0.1 * ( orders.size() - 1 ) ) ) ) * 1000;

        profit = ( dist * 1000 ) - costs;

        infoLabel.setText("Total distance: "+dist+" | Total time: "+String.format( "%.2f", time )+" | Total costs: "+String.format( "%.2f", costs )+" | Profit: "+String.format( "%.2f", profit ));
        //dist = 10
        //dist = 8
        //dist = 20

        //costs = ( dist * 0.2 ) * ( 1 - ( 0.1 * ( n - 1 ) ) )
        // ( 10 * 0.2 ) * ( 1 - ( 0.1 * ( 1 - 1 ) ) ) = 2.00
        // ( 18 * 0.2 ) * ( 1 - ( 0.1 * ( 2 - 1 ) ) ) = 3.24
        // ( 38 * 0.2 ) * ( 1 - ( 0.1 * ( 3 - 1 ) ) ) = 6.08

        //profit = dist - costs
        // 10 - 2.00 = 8.00
        // 18 - 3.24 = 14.76
        // 38 - 6.08 = 31.92

        //dist = 10
        //dist = 10
        //dist = 10
        //dist = 10

        //costs = ( dist * 0.2 ) * ( 1 - ( 0.1 * ( n - 1 ) ) )
        // ( 10 * 0.2 ) * ( 1 - ( 0.1 * ( 1 - 1 ) ) ) = 2.00
        // ( 20 * 0.2 ) * ( 1 - ( 0.1 * ( 2 - 1 ) ) ) = 3.60
        // ( 30 * 0.2 ) * ( 1 - ( 0.1 * ( 3 - 1 ) ) ) = 4.80
        // ( 40 * 0.2 ) * ( 1 - ( 0.1 * ( 4 - 1 ) ) ) = 5.60

        //profit = dist - costs
        // 10 - 2.00 = 8.00
                    // 8.40
        // 20 - 3.60 = 16.40
                    // 8.80
        // 30 - 4.80 = 25.20
                    // 9.20
        // 40 - 5.60 = 34.40
    }

    @FXML
    private void eraseTable(){
        orders = new ArrayList<>();
        firstclick = 1;
        initializeTable();
        infoLabel.setText("");
        dist = 0;
    }

    @FXML
    private void createExpedition() throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs = connection.createExpedition(dist, time, costs, profit);
        int id_expedition = 999;
        int idReplacedOrder = 999;
        while (rs.next()){
            id_expedition = rs.getInt("LAST_INSERT_ID()");
        }
        for (DBOrder dbOrder : orders){
            rs = connection.replaceOrder(dbOrder.getId_order());
            while (rs.next()){
                idReplacedOrder = rs.getInt("LAST_INSERT_ID()");
            }
            connection.setExpeditionId(id_expedition, idReplacedOrder);
        }
        for (DBOrder dbOrder : orders){
            connection.removeOrderById(dbOrder.getId_order());
        }
        eraseTable();
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
