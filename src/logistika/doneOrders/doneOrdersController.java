package logistika.doneOrders;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import logistika.DBConnection;
import logistika.Main;
import logistika.orderlist.finalOrder;
import logistika.runningExpo.Expedition;
import logistika.runningExpo.ExpeditionStatus;
import logistika.runningExpo.ExpeditionStatusEndTime;
import logistika.vehicles.Vehicle;
import logistika.view.MainViewController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Math.pow;

/**
 * Created by lukashanincik on 07/05/2017.
 */
public class doneOrdersController {
    @FXML
    private TextField selectOrderTextField;
    @FXML
    private AnchorPane centralAnchorPane;
    private TableView ordersTableList;
    private ArrayList<ExpeditionStatus> expeditionsArrayList = new ArrayList<>();
    private ArrayList<finalOrder> ordersArrayList = new ArrayList<finalOrder>();

    @FXML
    public void initialize() throws SQLException {
        createTable();
        centralAnchorPane.setTopAnchor(ordersTableList, 10.0); // obviously provide your own constraints
        centralAnchorPane.getChildren().add(ordersTableList);
    }

    @FXML
    public void invoiceOrder() throws SQLException {
        int id = intToStr(selectOrderTextField.getText());
        int found = 0;
        Double profit = 0.0;
        for (finalOrder order : ordersArrayList){
            if (order.getId_order() == id) {
                profit = order.getProfit();
                found = 1;
            }
        }
        if (found == 1){
            DBConnection connection = new DBConnection();
            //connection.removeRunningOrderById(id);
            Main.setCash(Main.getCash() + profit.intValue());
            //System.out.println(Main.getCash());
            //MainViewController.setCashLabelText();
            initialize();
        }
        else System.out.println("Vybrana objednavka neexistuje");

    }

    public void createTable() throws SQLException {
        ordersArrayList.clear();
        expeditionsArrayList.clear();
        DBConnection connection = new DBConnection();
        ResultSet rs = connection.getAllExpeditions();
        while (rs.next()){
            ExpeditionStatus expedition = new ExpeditionStatus(rs.getInt("id_expedition"), rs.getInt("totalDistance"), rs.getTimestamp("beginDate"), rs.getDouble("totalTime"), rs.getDouble("costs"), rs.getDouble("profit"));
            expedition.setStatus(createStatus(expedition.getDate(), expedition.getTime()));
            if (expedition.getStatus() == 1.0){
                expeditionsArrayList.add(expedition);
            }
        }
        for (ExpeditionStatus expedition : expeditionsArrayList){
            ResultSet rs1 = connection.getOrdersByExpId(expedition.getId_expedition());
            while (rs1.next()){
                finalOrder order = new finalOrder();
                order.setId_order(rs1.getInt("runningOrders.id_order"));
                order.setStorageName(rs1.getString("storages.name"));
                order.setCityName(rs1.getString("cities.name"));
                order.setVzdialenost(rs1.getInt("runningOrders.distance"));
                String spec = new String();
                switch (rs1.getInt("runningOrders.specifying")){
                    case 1: spec = "Freezers"; break;
                    case 2: spec = "Fuel"; break;
                    case 3: spec = "Chemicals"; break;
                    case 4: spec = "Pallets"; break;
                    case 5: spec = "Other"; break;
                }
                order.setTyp_str(spec);
                order.setId_expedition(expedition.getId_expedition());
                order.setCosts(expedition.getCosts());
                ordersArrayList.add(order);
            }
        }
        for (finalOrder order : ordersArrayList){
            for (ExpeditionStatus expedition : expeditionsArrayList){
                if (order.getId_expedition() == expedition.getId_expedition()){
                    ResultSet rs2 = connection.getCountOfRowsById(expedition.getId_expedition());
                    int count = 1;
                    while (rs2.next()){
                        count = rs2.getInt("COUNT(*)");
                    }
                    Double costs = expedition.getCosts()/count;
                    order.setCosts((double)Math.round(costs*100)/100);
                    Double profit = (order.getVzdialenost()*1000) - costs;
                    order.setProfit((double)Math.round(profit*100)/100);
                }
            }
            /*ResultSet rs2 = connection.getCountOfRowsById(expedition.getId_expedition());
            int count = 1;
            while (rs2.next()){
                count = rs2.getInt("COUNT(*)");
            }
            Double costs = expedition.getCosts()/count;
            order.setCosts(costs);
            Double profit = (order.getVzdialenost()*1000) - costs;
            order.setProfit(profit);*/
        }
        TableColumn<finalOrder, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<finalOrder, Integer>("id_order"));
        TableColumn<finalOrder, String> storageColumn = new TableColumn<>("Storage Name");
        storageColumn.setCellValueFactory(new PropertyValueFactory<finalOrder, String>("storageName"));
        TableColumn<finalOrder, String> cityColumn = new TableColumn<>("Destination");
        cityColumn.setCellValueFactory(new PropertyValueFactory<finalOrder, String>("cityName"));
        TableColumn<finalOrder, Integer> distColumn = new TableColumn<>("Distance");
        distColumn.setCellValueFactory(new PropertyValueFactory<finalOrder, Integer>("vzdialenost"));
        TableColumn<finalOrder, String> specColumn = new TableColumn<>("Specifying");
        specColumn.setCellValueFactory(new PropertyValueFactory<finalOrder, String>("typ_str"));
        TableColumn<finalOrder, Double> costsColumn = new TableColumn<>("Costs");
        costsColumn.setCellValueFactory(new PropertyValueFactory<finalOrder, Double>("costs"));
        TableColumn<finalOrder, Double> profitColumn = new TableColumn<>("Profit");
        profitColumn.setCellValueFactory(new PropertyValueFactory<finalOrder, Double>("profit"));

        /*TableColumn<ExpeditionStatus, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Integer>("id_expedition"));
        TableColumn<ExpeditionStatus, Integer> distColumn = new TableColumn<>("Distance");
        distColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Integer>("distance"));
        TableColumn<ExpeditionStatus, Double> profitColumn = new TableColumn<>("Profit");
        profitColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Double>("profit"));
        TableColumn<ExpeditionStatus, Timestamp> beginTimeColumn = new TableColumn<>("Begin time");
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Timestamp>("date"));
        TableColumn<ExpeditionStatus, Double> timeColumn = new TableColumn<>("Duration");
        timeColumn.setCellValueFactory(new PropertyValueFactory<ExpeditionStatus, Double>("time"));*/

        ordersTableList = new TableView<>();
        ordersTableList.setPrefWidth(600);
        ordersTableList.setMaxHeight(280);
        ordersTableList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ordersTableList.setItems(FXCollections.observableArrayList(ordersArrayList));
        ordersTableList.getColumns().addAll(idColumn, storageColumn, cityColumn, distColumn, specColumn, costsColumn, profitColumn);
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

    public static int intToStr(String str){
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
