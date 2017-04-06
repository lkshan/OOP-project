package logistika;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logistika.map.Cities;
import logistika.map.Storage;
import logistika.orderlist.DBOrder;
import logistika.orderlist.Order;

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
        String query = "SELECT DISTINCT `id_storage`, `name`, `specifying`, `position` FROM `storages`";
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
}
