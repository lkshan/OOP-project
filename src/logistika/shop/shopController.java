package logistika.shop;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import logistika.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import logistika.Main;
import logistika.map.Cities;
import logistika.map.Storage;

import static java.lang.Math.pow;

/**
 * Created by lukashanincik on 19/04/2017.
 */
public class shopController{
    @FXML
    private TextField selectGoodTextField;

    @FXML
    private void purchaseItem() throws SQLException, IOException {
        if (selectGoodTextField.getText().equals("")){
            System.out.println("Zadaj cislo objednavky");
        }
        else {
            DBConnection connection = new DBConnection();
            ResultSet rs;
            int id = intToStr(selectGoodTextField.getText());
            if (id <= Main.getShopGoodArrayList().size()){
                if (Main.getShopGoodArrayList().get(id - 1) instanceof StorageExtensions){
                    rs = connection.getStoragePosition(((StorageExtensions) Main.getShopGoodArrayList().get(id - 1)).getStorage());
                    int position = 0;
                    while (rs.next()){
                        position = rs.getInt("position");
                    }
                    Cities city = connection.getCity(position);
                    int specifying = 0;
                    switch (((StorageExtensions) Main.getShopGoodArrayList().get(id - 1)).getExtension()){
                        case "Freezers": specifying = 1; break;
                        case "Fuel": specifying = 2; break;
                        case "Chemicals": specifying = 3; break;
                        case "Pallets": specifying = 4; break;
                        case "Other": specifying = 5; break;
                    }
                    Storage storage = new Storage();
                    storage.setName(((StorageExtensions) Main.getShopGoodArrayList().get(id - 1)).getStorage());
                    storage.setType(specifying);
                    storage.setLocation(city);
                    connection.insertNewStorage(storage);
                    Main.getShopGoodArrayList().remove(id - 1);
                    Main.showShopTableList();
                }else {
                    if (Main.getShopGoodArrayList().get(id-1).getType().equals("Vehicle")){
                        connection.insertNewVehicle(Main.getShopGoodArrayList().get(id - 1));
                        Main.getShopGoodArrayList().remove(id - 1);
                        Main.showShopTableList();
                    }
                }
            }
        }
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
