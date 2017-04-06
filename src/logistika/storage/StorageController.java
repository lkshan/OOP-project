package logistika.storage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import logistika.DBConnection;
import logistika.Main;
import logistika.map.FullStorage;
import logistika.orderlist.DBOrder;
import logistika.orderlist.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by lukashanincik on 04/04/2017.
 */
public class StorageController {
    @FXML
    private SplitPane StorageSplitPane;
    @FXML
    private AnchorPane AnchorPaneLeft;
    @FXML
    private AnchorPane AnchorPaneRight;
    @FXML
    private TabPane StorageTabPane;
    @FXML
    private Tab WaitingTab;
    @FXML
    private Button GoButton;

    @FXML
    private AnchorPane AnchorPaneInside;

    private static ListView<String> StorageLV;

    private static TableView<DBOrder> StorageTV;

    private ListView<String> setNameStorages() throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs;
        ArrayList<String> storageNameArrayList = new ArrayList<String>();
        //ArrayList<Integer> storageIdArrayList = new ArrayList<Integer>();
        ObservableList<String> storageNameObservableList;
        ObservableList<String> storageIdObservableList;
        rs = connection.getStorageNameList();
        while (rs.next()){
            storageNameArrayList.add(rs.getString("name"));
            //storageIdArrayList.add(rs.getInt("id_storage"));
        }
        storageNameObservableList = FXCollections.observableArrayList(storageNameArrayList);

        //TableColumn<FullStorage, String> nameColumn = new TableColumn<>("Storages");
        //nameColumn.setCellValueFactory(new PropertyValueFactory<FullStorage, String>("name"));
        StorageLV = new ListView<String>();
        StorageLV.setMaxHeight(300);
        StorageLV.setItems(storageNameObservableList);
        //StorageLV.getColumns().addAll(nameColumn);
        return StorageLV;
    }

    private ArrayList<String> getStorageList() throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs;
        ArrayList<String> storageArrayList = new ArrayList<String>();
        rs = connection.getStorageNameList();
        while (rs.next()){
            storageArrayList.add(rs.getString("name"));
            //storageNameArrayList.add(rs.getString("name"));
        }
        return storageArrayList;
    }

    public void goToStorage() throws SQLException {
        int id_select = StorageLV.getSelectionModel().getSelectedIndex();
        String storageName = getStorageList().get(id_select);
        DBConnection connection = new DBConnection();
        ObservableList<DBOrder> orderObservableList = connection.selectOrdersByStorage(storageName);
        //TableColumn<FullStorage, String> nameColumn = new TableColumn<>("Storages");
        //nameColumn.setCellValueFactory(new PropertyValueFactory<FullStorage, String>("name"));
        TableColumn<DBOrder, String> source = new TableColumn<>("Deliver from");
        source.setCellValueFactory(new PropertyValueFactory<DBOrder, String>("storageName"));
        TableColumn<DBOrder, String> destination = new TableColumn<>("Deliver to");
        destination.setCellValueFactory(new PropertyValueFactory<DBOrder, String>("cityName"));
        TableColumn<DBOrder, Integer> distance = new TableColumn<>("Distance");
        distance.setCellValueFactory(new PropertyValueFactory<DBOrder, Integer>("vzdialenost"));
        TableColumn<DBOrder, String> type = new TableColumn<>("Specifying");
        type.setCellValueFactory(new PropertyValueFactory<DBOrder, String>("typ_str"));
        StorageTV = new TableView<>();
        StorageTV.setItems(orderObservableList);
        StorageTV.getColumns().addAll(source, destination, distance, type);
        StorageTV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        AnchorPaneInside.setTopAnchor(StorageTV, 0.0);
        AnchorPaneInside.setBottomAnchor(StorageTV, 0.0);
        AnchorPaneInside.setLeftAnchor(StorageTV, 0.0);
        AnchorPaneInside.setRightAnchor(StorageTV, 0.0);
        AnchorPaneInside.getChildren().add(StorageTV);
    }

    public void initialize() throws SQLException {
        AnchorPaneLeft.setTopAnchor(setNameStorages(), 10.0); // obviously provide your own constraints
        AnchorPaneLeft.getChildren().add(setNameStorages());
    }


}
