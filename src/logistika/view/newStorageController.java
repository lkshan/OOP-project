package logistika.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logistika.DBConnection;
import logistika.Main;
import logistika.map.Cities;
import logistika.map.Storage;
import logistika.map.WorldMap;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by lukashanincik on 02/04/2017.
 */
public class newStorageController {

    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox locationChoiceBox;
    @FXML
    private ChoiceBox specifyingChoiceBox;
    ObservableList<String> specifyingList = FXCollections.observableArrayList("Freezers", "Fuel", "Chemicals", "Pallets", "Other");
    @FXML
    private Button closeButton;

    @FXML
    private Label priceLabel;

    @FXML
    public void initialize() throws IOException {
        //specifyingChoiceBox.setValue("Choose one");
        specifyingChoiceBox.setItems(specifyingList);
        locationChoiceBox.setItems(createCityList().sorted());
        priceLabel.setText("10 000$");
    }

    @FXML
    private ObservableList<String> createCityList() throws IOException {
        WorldMap cities = new WorldMap();
        ArrayList<String> cityNamesList = new ArrayList<String>();
        for (Cities city : cities.getCities()){
            cityNamesList.add(city.getName());
        }
        ObservableList<String> cityList = FXCollections.observableArrayList(cityNamesList);
        return cityList;
    }
    @FXML
    private void closeStage(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addNewStorage() throws SQLException, IOException {
        DBConnection connection = new DBConnection();
        int specId=0;
        String spec = specifyingChoiceBox.getValue().toString();
        switch (spec){
            case "Freezers" : specId = 1; break;
            case "Fuel" : specId = 2; break;
            case "Chemicals" : specId = 3; break;
            case "Pallets" : specId = 4; break;
            case "Other" : specId = 5; break;
        }
        //System.out.println(spec);
        WorldMap cities = new WorldMap();
        Cities location = new Cities();
        for (Cities city : cities.getCities()){
            if (city.getName().equals(locationChoiceBox.getValue().toString())){
                location = city;
                break;
            }
        }
        //System.out.println(location.getName());
        if(Main.getCash() >= 10000) {
            Storage storage = new Storage(nameTextField.getText(), specId, location);
            connection.insertNewStorage(storage);
            Main.setCash(Main.getCash()-10000);
        }
        else System.out.println("Nedostatok financii");
        closeStage();
        Main main = new Main();
        main.showMainView();
        main.showMainItems();
        //System.out.println(Main.getCash());
    }
}
