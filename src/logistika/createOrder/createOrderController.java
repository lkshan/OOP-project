package logistika.createOrder;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import logistika.DBConnection;
import logistika.map.Cities;
import logistika.map.Storage;
import logistika.orderlist.Order;
import logistika.orderlist.OrderList;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lukashanincik on 07/05/2017.
 */
public class createOrderController {
    @FXML
    private ComboBox storageComboBox;
    @FXML
    private ChoiceBox cityChoiceBox;
    @FXML
    private ChoiceBox specChoiceBox;

    private ArrayList<Cities> citiesAL = new ArrayList<>();
    private ArrayList<Storage> storageAL = new ArrayList<>();
    private ArrayList<String> specAL = new ArrayList<String>();

    @FXML
    public void initialize() throws IOException, SQLException {
        ArrayList<String> citiesNameAL = new ArrayList<>();
        ArrayList<String> storageNameAL = new ArrayList<>();
        initializeCitiesAL();
        initializeStorageAL();
        for (Cities city : citiesAL){
            citiesNameAL.add(city.getName());
        }
        DBConnection connection = new DBConnection();
        ResultSet rs = connection.getDistinctStorageNames();
        while (rs.next()){
            storageNameAL.add(rs.getString("name"));
        }
        storageComboBox.setItems(FXCollections.observableArrayList(storageNameAL));
        cityChoiceBox.setItems(FXCollections.observableArrayList(citiesNameAL));

    }

    @FXML
    private void initializeSpec(){
        specAL.clear();
        String storageName = storageComboBox.getValue().toString();
        String spec = new String();
        for (Storage storage : storageAL){
            if (storageName.equals(storage.getName())){
                switch (storage.getType()){
                    case 1: spec = "Freezers"; break;
                    case 2: spec = "Fuel"; break;
                    case 3: spec = "Chemicals"; break;
                    case 4: spec = "Pallets"; break;
                    case 5: spec = "Other";
                }
                specAL.add(spec);
            }
        }
        specChoiceBox.setItems(FXCollections.observableArrayList(specAL));
    }

    private void initializeCitiesAL() throws IOException {
        File f = new File("/Users/lukashanincik/Documents/Projekt_Logistika/src/logistikaCore/fiit/stuba/sk/Lukas/Hanincik/mesta.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int i=0, m=0,n=0;
        String name = new String();
        String x = new String();
        String y = new String();
        while((line = br.readLine()) != null){
            switch (i){
                case 0: name = line;
                    //System.out.println(name);
                    break;
                case 1: x = line;
                    //System.out.println(x);
                    break;
                case 2: y = line;
                    //System.out.println(y);
                    break;
            }
            if (i == 2)fillCities(name, x, y);
            i++;
            if (i == 3)i=0;
        }
        br.close();
        fr.close();
        m=0;
        n=0;
    }

    public void fillCities(String name, String x, String y){
        int X=0, i, Y=0;
        char c;
        for (i=0; i<x.length(); i++){
            c = x.charAt(i);
            String hex = String.format("%04x", (int) c);
            X += (h2d(hex)-64);
            //System.out.println(hex);
        }
        X += (x.length()-1)*25;
        Y = Integer.parseInt(y);
        Cities mesto = new Cities();
        mesto.setName(name);
        mesto.setX(X);
        mesto.setY(Y);
        citiesAL.add(mesto);
    }

    public static int h2d(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

    private void initializeStorageAL() throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs = connection.getAllStoragesWithCities();
        while (rs.next()){
            Cities city = new Cities();
            city.setName(rs.getString("cities.name"));
            city.setX(rs.getInt("cities.x"));
            city.setY(rs.getInt("cities.y"));
            Storage storage = new Storage();
            storage.setName(rs.getString("storages.name"));
            storage.setType(rs.getInt("storages.specifying"));
            storage.setLocation(city);
            storageAL.add(storage);
        }
    }

    @FXML
    private void updateOrder(){
        Storage orderStorage = new Storage();
        Cities orderCities = new Cities();
        int spec = 0, dist = 0;
        for (Storage storage : storageAL){
            if (storage.getName().equals(storageComboBox.getValue().toString())){
                orderStorage.setName(storage.getName());
                orderStorage.setLocation(storage.getLocation());
                orderStorage.setType(storage.getType());
            }
        }
        for (Cities city : citiesAL){
            if (city.getName().equals(cityChoiceBox.getValue().toString())){
                orderCities.setName(city.getName());
                orderCities.setX(city.getX());
                orderCities.setY(city.getY());
            }
        }
        switch (specChoiceBox.getValue().toString()){
            case "Freezers": spec = 1; break;
            case "Fuel": spec = 2; break;
            case "Chemicals": spec = 3; break;
            case "Pallets": spec = 4; break;
            case "Other": spec = 5; break;
        }
        int a, b;
        double ab;
        a = Math.abs( orderStorage.getLocation().getX() - orderCities.getX() ) +1 ;
        b = Math.abs( orderStorage.getLocation().getY() - orderCities.getY() ) +1 ;
        ab = Math.pow(a, 2) + Math.pow(b, 2);
        dist += (int) Math.round( Math.sqrt(ab) );
        Order order = new Order();
        order.setZdroj(orderStorage);
        order.setDestinacia(orderCities);
        order.setVzdialenost(dist);
        order.setTyp(spec);
        OrderList.Objednavky.add(order);
    }
}
