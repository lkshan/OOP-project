package logistika;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logistika.map.Cities;
import logistika.map.Storage;
import logistika.orderlist.DBOrder;
import logistika.orderlist.Order;
import logistika.shop.ShopGood;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by lukashanincik on 18/03/2017.
 */
public class DBConnection {
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBConnection() throws SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_project", "root", "");
            st = con.createStatement();
        } catch (Exception er) {
            System.out.println(er);
        }
    }

    public void insertNewOrder(Order order){
        try{
            String query = "INSERT INTO `myOrders`(`id_storage`, `id_city`, `distance`, `specifying`) VALUES (\""+insertStorage(order.getZdroj())+"\", \""+insertNewCity(order.getDestinacia())+"\", \""+order.getVzdialenost()+"\", \""+order.getTyp()+"\")";
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int insertNewCity(Cities city) throws SQLException {
        try {
            String queryUpdate = "INSERT INTO `cities`(`name`, `x`, `y`) VALUES (\"" + city.getName() + "\", \"" + city.getX() + "\", \"" + city.getY() + "\")";
            st.executeUpdate(queryUpdate);
            String querySelect = "SELECT `id_city` FROM `cities` ORDER BY `date_of_modifie` DESC LIMIT 1";
            rs = st.executeQuery(querySelect);
        } catch (SQLException e) {
            System.out.println(e);
        }
        int id=-1;
        while(rs.next()){
            id = rs.getInt("id_city");
            //System.out.println(id);
        }
        return id;
    }

    public int insertStorage(Storage storage) throws SQLException {
        try {
            String queryUpdate = "INSERT INTO `storages`(`name`, `specifying`, `position`) VALUES (\"" + storage.getName() + "\", \"" + storage.getType() + "\", \"" + insertNewCity(storage.getLocation()) + "\")";
            st.executeUpdate(queryUpdate);
            String querySelect = "SELECT `id_storage` FROM `storages` ORDER BY `date_of_modifie` DESC LIMIT 1";
            rs = st.executeQuery(querySelect);
        } catch (SQLException e) {
            System.out.println(e);
        }
        int id=-1;
        while (rs.next()){
            id = rs.getInt("id_storage");
            //System.out.println(id);
        }
        return id;
    }

    //@Override
    public void insertNewStorage(Storage storage){
        try{
            String queryInsert = "INSERT INTO `storages`(`name`, `specifying`, `position`) VALUES (\""+ storage.getName() +"\", \""+ storage.getType() +"\", \""+ insertNewCity(storage.getLocation()) +"\")";
            st.executeUpdate(queryInsert);
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public ResultSet getStorageList() throws SQLException {
        String query = "SELECT `id_storage`, `name`, `specifying`, `position` FROM `storages`";
        rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet getStorageNameList() throws SQLException {
        String query = "SELECT DISTINCT `name` FROM `storages`";
        rs = st.executeQuery(query);
        return rs;
    }

    public ResultSet getDistinctStorageList() throws SQLException {
        String query = "SELECT DISTINCT `name`, `specifying` FROM `storages`";
        rs = st.executeQuery(query);
        return rs;
    }

    public Cities getCity(int id) throws SQLException {
        Cities city = new Cities();
        try{
            String querySelectCity = "SELECT `name`, `x`, `y` FROM cities WHERE id_city = "+id;
            rs = st.executeQuery(querySelectCity);
        }catch (SQLException e){
            System.out.println(e);
        }
        while (rs.next()){
            city.setName(rs.getString("name"));
            city.setX(rs.getInt("x"));
            city.setY(rs.getInt("y"));
        }
        return city;
    }

    public void eraseDB() throws SQLException {
        String query = "DELETE FROM `myOrders`";
        st.executeUpdate(query);
        query = "DELETE FROM `storages`";
        st.executeUpdate(query);
        query = "DELETE FROM `cities`";
        st.executeUpdate(query);
    }

    public ObservableList<DBOrder> selectOrdersByStorage(String storageName) throws SQLException {
        try{
            String querySelectCity = "SELECT myOrders.id_order, cities.name, storages.name, myOrders.distance, myOrders.specifying FROM myOrders INNER JOIN cities ON myOrders.id_city = cities.id_city INNER JOIN storages ON myOrders.id_storage = storages.id_storage WHERE storages.name = \""+storageName+"\"";
            rs = st.executeQuery(querySelectCity);
        }catch (SQLException e){
            System.out.println(e);
        }
        ArrayList<DBOrder> orderArrayList = new ArrayList<>();
        while (rs.next()){
            DBOrder order = new DBOrder();
            order.setId_order(rs.getInt("myOrders.id_order"));
            order.setCityName(rs.getString("cities.name"));
            order.setStorageName(rs.getString("storages.name"));
            order.setVzdialenost(rs.getInt("myOrders.distance"));
            switch (rs.getInt("specifying")){
                case 1 : order.setTyp_str("Freezers"); break;
                case 2 : order.setTyp_str("Fuel"); break;
                case 3 : order.setTyp_str("Chemicals"); break;
                case 4 : order.setTyp_str("Pallets"); break;
                case 5 : order.setTyp_str("Other"); break;
            }
            orderArrayList.add(order);
        }
        ObservableList<DBOrder> orderObservableList = FXCollections.observableArrayList(orderArrayList);
        return orderObservableList;
    }

    public ResultSet getStorageSpecifyingByName(String name) throws SQLException {
        String query = "SELECT DISTINCT `specifying` FROM `storages` WHERE `name` = \""+name+"\"";
        return rs = st.executeQuery(query);
    }

    public ResultSet getStoragePosition(String name) throws SQLException {
        String query = "SELECT DISTINCT `position` FROM `storages` WHERE `name` = \""+name+"\" LIMIT 1";
        return rs = st.executeQuery(query);
    }

    public void insertNewVehicle(ShopGood vehicle) throws SQLException {
        //String type = new String();
        int type = 0;
        String ability = new String();
        if(vehicle.getName().contains("Freezer")) type = 1;
        if(vehicle.getName().contains("Fuel")) type = 2;
        if(vehicle.getName().contains("Chemical")) type = 3;
        if(vehicle.getName().contains("Pallet")) type = 4;
        if(vehicle.getName().contains("Super")) type = 5;
        double speed = 1;
        //if(vehicle.getName().contains("Truck")) speed = 1;
        if(vehicle.getName().contains("Ship")) speed = 0.5;
        if(vehicle.getName().contains("Plane")) speed = 2;
        if (type == 5) speed++;

        String query = "INSERT INTO `vehicles`(`name`, `type`, `speed`) VALUES (\""+vehicle.getName()+"\", \""+type+"\", \""+speed+"\")";
        st.executeUpdate(query);
    }

    public ResultSet getVehicles() throws SQLException {
        String query = "SELECT `name`, `type`, `speed` FROM `vehicles`";
        return rs = st.executeQuery(query);
    }

    public ResultSet getOrders(String storage) throws SQLException {
        //              SELECT cities.name, storages.name, myOrders.distance, myOrders.specifying FROM myOrders INNER JOIN cities ON myOrders.id_city = cities.id_city INNER JOIN storages ON myOrders.id_storage = storages.id_storage WHERE storages.name = "CAW"
        String query = "SELECT myOrders.id_order, cities.name, storages.name, myOrders.distance, myOrders.specifying FROM myOrders INNER JOIN cities ON myOrders.id_city = cities.id_city INNER JOIN storages ON myOrders.id_storage = storages.id_storage WHERE storages.name = \""+storage+"\"";
        return rs = st.executeQuery(query);
    }

    public ResultSet getOrders(String storage, int spec) throws SQLException {
        String query = "SELECT myOrders.id_order, cities.name, storages.name, myOrders.distance, myOrders.specifying FROM myOrders INNER JOIN cities ON myOrders.id_city = cities.id_city INNER JOIN storages ON myOrders.id_storage = storages.id_storage WHERE storages.name = \""+storage+"\" AND myOrders.specifying = "+spec;
        return rs = st.executeQuery(query);
    }

    public ResultSet getOrderById(int id) throws SQLException {
        String query = "SELECT cities.name, storages.name, myOrders.distance FROM myOrders INNER JOIN cities ON myOrders.id_city = cities.id_city INNER JOIN storages ON myOrders.id_storage = storages.id_storage WHERE myOrders.id_order = "+id;
        return rs = st.executeQuery(query);
    }
    public ResultSet getStorageByName(String storageName) throws SQLException {
        String query = "SELECT cities.name, cities.x, cities.y FROM cities INNER JOIN storages ON cities.id_city = storages.position WHERE storages.name = \""+storageName+"\" LIMIT 1";
        return rs = st.executeQuery(query);
    }
    public ResultSet getCityByName(String cityName) throws SQLException {
        String query = "SELECT `x`, `y` FROM `cities` WHERE `name` = \""+cityName+"\" LIMIT 1";
        return rs = st.executeQuery(query);
    }
    public ResultSet getVehicleSpeedByName(String vehicleName) throws SQLException {
        String query = "SELECT `speed` FROM `vehicles` WHERE `name` = \""+vehicleName+"\" LIMIT 1";
        return rs = st.executeQuery(query);
    }

    public ResultSet createExpedition(int dist, double time, double costs, double profit) throws SQLException {
        String query = "INSERT INTO `expeditions`(`totalDistance`, `totalTime`, `costs`, `profit`) VALUES ("+dist+", "+time+", "+costs+", "+profit+")";
        st.executeUpdate(query);
        query = "SELECT LAST_INSERT_ID()";
        return rs = st.executeQuery(query);
    }

    public ResultSet replaceOrder(int id) throws SQLException {
        String query = "INSERT INTO runningOrders (id_storage, id_city, distance, specifying) SELECT id_storage, id_city, distance, specifying FROM `myOrders` WHERE `id_order` = "+id;
        st.executeUpdate(query);
        query = "SELECT LAST_INSERT_ID()";
        return rs = st.executeQuery(query);
    }

    public void setExpeditionId(int id_exp, int id_order) throws SQLException {
        String query = "UPDATE `runningOrders` SET `id_expedition`= "+id_exp+" WHERE id_order = "+id_order;
        st.executeUpdate(query);
    }

    public void removeOrderById(int id) throws SQLException {
        String query = "DELETE FROM `myOrders` WHERE id_order = " +id;
        st.executeUpdate(query);
    }

    public ResultSet getAllExpeditions() throws SQLException {
        String query = "SELECT `id_expedition`, `totalDistance`, `beginDate`, `totalTime`, `costs`, `profit` FROM `expeditions`";
        return rs = st.executeQuery(query);
    }

    public ResultSet getNow() throws SQLException {
        String query = "SELECT NOW()";
        return rs = st.executeQuery(query);
    }

    public ResultSet getOrdersByExpId(int id) throws SQLException {
        String query = "SELECT runningOrders.id_order, cities.name, storages.name, runningOrders.distance, runningOrders.specifying FROM runningOrders INNER JOIN cities ON runningOrders.id_city = cities.id_city INNER JOIN storages ON runningOrders.id_storage = storages.id_storage WHERE runningOrders.id_expedition = "+id;
        return rs = st.executeQuery(query);
    }

    public ResultSet getCountOfRowsById(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM `runningOrders` WHERE `id_expedition` = "+id;
        return rs = st.executeQuery(query);
    }

    public void removeRunningOrderById(int id) throws SQLException {
        String query = "DELETE FROM `runningOrders` WHERE `id_order` = "+id;
        st.executeUpdate(query);
    }

    public ResultSet getAllStoragesWithCities() throws SQLException {
        String query = "SELECT DISTINCT storages.name, storages.specifying, cities.name, cities.x, cities.y FROM storages INNER JOIN cities ON storages.position = cities.id_city";
        return rs = st.executeQuery(query);
    }
    public ResultSet getDistinctStorageNames() throws SQLException {
        String query = "SELECT DISTINCT `name` FROM `storages`";
        return rs = st.executeQuery(query);
    }
}
