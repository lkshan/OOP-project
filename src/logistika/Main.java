package logistika;/**
 * Created by lukashanincik on 17/03/2017.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logistika.map.Cities;
import logistika.map.Storage;
import logistika.orderlist.Order;
import logistika.orderlist.OrderList;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    private static Stage primaryStage;
    private static BorderPane mainLayout;

    private static TableView<echoOrder> OLTV;

    private static int firstStart = 0;
    private static int cash = 10000;

    public static int getCash() {
        return cash;
    }

    public static void setCash(int cash) {
        Main.cash = cash;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Welcome to my world");

        showMainView();
        showMainItems();
        showFirstStorage();
    }

    public void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainView.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMainItems() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainItems.fxml"));
        BorderPane mainItems = loader.load();
        mainLayout.setCenter(mainItems);
    }

    public static void showStoragesScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("storage/Storage.fxml"));
        BorderPane storage = loader.load();
        mainLayout.setCenter(storage);
    }

    public static void showOrderlistScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("orderlist/Orderlist.fxml"));
        BorderPane orderlist = loader.load();
        mainLayout.setCenter(orderlist);
    }

    public static void showAddStorage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/AddNewStorage.fxml"));
        BorderPane addNewStorage = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("New storage");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(addNewStorage);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    public static void showFirstStorage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/newStorage.fxml"));
        BorderPane firstStorage = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("First storage");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(firstStorage);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    public static void showOrderTableView() throws IOException, SQLException {

        TableColumn<echoOrder, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<echoOrder, Integer>("id"));

        TableColumn<echoOrder, String> DelFromColumn = new TableColumn<>("Delivery From");
        DelFromColumn.setCellValueFactory(new PropertyValueFactory<echoOrder, String>("from"));

        TableColumn<echoOrder, String> DelToColumn = new TableColumn<>("Delivery To");
        DelToColumn.setCellValueFactory(new PropertyValueFactory<echoOrder, String>("to"));

        TableColumn<echoOrder, Integer> DistColumn = new TableColumn<>("Distance");
        DistColumn.setCellValueFactory(new PropertyValueFactory<echoOrder, Integer>("dist"));

        TableColumn<echoOrder, Integer> WeightColumn = new TableColumn<>("Type");
        WeightColumn.setCellValueFactory(new PropertyValueFactory<echoOrder, Integer>("type"));

        OLTV = new TableView<>();
        OLTV.setPrefWidth(500);
        OLTV.setMaxHeight(280);
        OLTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        OLTV.setItems(getOrders());
        OLTV.getColumns().addAll(idColumn, DelFromColumn, DelToColumn, DistColumn, WeightColumn);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("orderlist/Orderlist.fxml"));
        BorderPane orderlist = loader.load();
        orderlist.setCenter(OLTV);
        mainLayout.setCenter(orderlist);
    }
    public static class echoOrder{
        private int id;
        private String from = new String();
        private String to = new String();
        private int dist;
        private String type;
        private Storage source;
        private Cities destination;

        public int getId() {
            return id;
        }

        public void setId(int ID) {
            this.id = ID;
        }

        public Storage getSource() {
            return source;
        }

        public void setSource(Storage source) {
            this.source = source;
        }

        public Cities getDestination() {
            return destination;
        }

        public void setDestination(Cities destination) {
            this.destination = destination;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public int getDist() {
            return dist;
        }

        public void setDist(int dist) {
            this.dist = dist;
        }

        public String  getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public echoOrder(int id, String from, String to, int dist, String type, Storage source, Cities destination) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.dist = dist;
            this.type = type;
            this.source = source;
            this.destination = destination;
        }
    }

    public static ObservableList<echoOrder> getOrders() throws IOException, SQLException {
        OrderList orderList = new OrderList();
        Cities city = new Cities();
        city.setName("Svidnik");
        city.setX(1);
        city.setY(8);
        //orderList.createOrderList(city);
        if (firstStart == 0) {
            orderList.createOrderList();
            firstStart = 1;
        }
            else {
            orderList.getObjednavky();
        }
        ObservableList<echoOrder> OrderListOBS = FXCollections.observableArrayList();
        int i = 0;

        for (Order order : orderList.getObjednavky()){
            String typ = new String();
            switch (order.getTyp()){
                case 1: typ = "Freezers"; break;
                case 2: typ = "Fuel"; break;
                case 3: typ = "Chemicals"; break;
                case 4: typ = "Pallets"; break;
                case 5: typ = "Other";
            }
            echoOrder newOrder = new echoOrder(++i, order.getZdroj().getName(), order.getDestinacia().getName(), order.getVzdialenost(), typ, order.getZdroj(), order.getDestinacia());
            OrderListOBS.add(newOrder);
        }
        return OrderListOBS;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
