package logistika.orderlist;

/**
 * Created by lukashanincik on 21/03/2017.
 */
import javafx.fxml.FXML;
import logistika.DBConnection;
import logistika.Main;
import logistika.map.Cities;
import logistika.map.FullStorage;
import logistika.map.Storage;
import logistika.map.WorldMap;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * Created by lukashanincik on 09/03/2017.
 */
public class OrderList {

    @FXML
    private javafx.scene.control.TextField selectOrderTextFIeld;
    private Main main;
    private static ArrayList<Order> Objednavky = new ArrayList<Order>();

    public ArrayList<Order> getObjednavky() {
        return Objednavky;
    }

    public void createOrderList() throws IOException, SQLException {
        WorldMap map = new WorldMap();
        int i, rand;
        Storage zdroj = new Storage();
        Cities destinacia = new Cities();
        int typ, vzdialenost;
        for (i = 0; i < 10; i++) {
            rand = randCity();
            Cities mesto = new Cities();
            mesto = map.getCities().get(rand-1);
            destinacia = mesto;
            rand = randInt(getStorageList().size(), 1);
            zdroj = getStorageList().get(rand-1);
            typ = randInt(5, 1);
            //
            int a, b;
            double ab;
            a = Math.abs( mesto.getX() - zdroj.getLocation().getX() +1 );
            b = Math.abs( mesto.getY() - zdroj.getLocation().getY() +1 );
            ab = Math.pow(a, 2) + Math.pow(b, 2);
            vzdialenost = (int) Math.round( Math.sqrt(ab) );
            //
            Order order = new Order(typ, vzdialenost, zdroj, destinacia);
            Objednavky.add(order);
        }
    }

    public ArrayList<Storage> getStorageList() throws SQLException {
        ArrayList<Storage> storageArrayList = new ArrayList<Storage>();
        DBConnection connection = new DBConnection();
        for (FullStorage fullStorage : getFullStorageList()){
            Storage storage = new Storage();
            storage.setType(fullStorage.getType());
            storage.setName(fullStorage.getName());
            Cities city = new Cities();
            city = connection.getCity(fullStorage.getCity_id());
            storage.setLocation(city);
            storageArrayList.add(storage);
        }
        return storageArrayList;
    }

    public ArrayList<FullStorage> getFullStorageList() throws SQLException {
        DBConnection connection = new DBConnection();
        ResultSet rs = connection.getStorageList();
        ArrayList<FullStorage> storageArrayList = new ArrayList<FullStorage>();
        while (rs.next()){
            FullStorage storage = new FullStorage();
            storage.setId_storage(rs.getInt("id_storage"));
            storage.setName(rs.getString("name"));
            storage.setType(rs.getInt("specifying"));
            storage.setCity_id(rs.getInt("position"));
            storageArrayList.add(storage);
        }
        return storageArrayList;
    }

    public static int randCity() throws IOException{
        Random rand  = new Random();
        int max=0, min = 1;
        File f = new File("/Users/lukashanincik/Documents/OOP-project/src/logistika/map/mesta.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line = new String();
        while ((line = br.readLine()) != null){
            max++;
        }
        max /= 3;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        br.close();
        fr.close();
        return randomNum;
    }

    public static int randInt(int max, int min){
        int weight;
        Random rand = new Random();
        weight = rand.nextInt((max-min) + 1 ) + min;
        return weight;
    }

    public void printOrders(){
        int i = 0;
        for (Order objednavka : Objednavky){
            i++;
            System.out.println(i + ".");
            System.out.println("Z  : " + objednavka.getZdroj().getName());
            System.out.println("Do : " + objednavka.getDestinacia().getName());
            System.out.println("Typ : " + objednavka.getTyp());
            System.out.println("Vzdialenost : " + objednavka.getVzdialenost());
            System.out.println();
        }
    }

    public Order getObjednavka(int i){
        return Objednavky.get(i-1);
    }

    public void removeOrder(Order objednavka){
        this.Objednavky.remove(objednavka);
    }

    public void selectOrder() throws SQLException, IOException {
        if (selectOrderTextFIeld.getText().equals("")){
            System.out.println("Zadaj cislo objednavky");
        }
        else {
            DBConnection connection = new DBConnection();
            int id=0;
            for (int i = 0; i < selectOrderTextFIeld.getText().length(); i++){
                char c = selectOrderTextFIeld.getText().charAt(i);
                int tempt=0;
                tempt = (int) c;
                tempt -= 48;
                id += tempt * pow(10, (selectOrderTextFIeld.getText().length() - i)-1);
            }
            if (id <= Objednavky.size()) {
                //System.out.println(id);
                //Order selectedOrder = new Order(Objednavky.get(id-1).getTyp(), Objednavky.get(id-1).getVzdialenost(), Objednavky.get(id-1).getZdroj(), Objednavky.get(id-1).getDestinacia());
                //Objednavky.get(id-1);
                printOrders();
                Order selectedOrder = Objednavky.get(id - 1);
                connection.insertNewOrder(selectedOrder);
                Objednavky.remove(id - 1);
                main.showOrderTableView();
            }
            else System.out.println("Nevhodne cislo objednavky");
        }
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
}

