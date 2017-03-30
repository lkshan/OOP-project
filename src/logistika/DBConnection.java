package logistika;

import logistika.map.Cities;
import logistika.orderlist.Order;

import java.sql.*;

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
            String query = "INSERT INTO `myOrders`(`id_storage`, `id_city`, `distance`, `specifying`) VALUES (\""+insertNewCity(order.getZdroj())+"\", \""+insertNewStorage(order.getDestinacia())+"\", \""+order.getVzdialenost()+"\", \""+order.getTyp()+"\")";
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
            System.out.println(id);
        }
        return id;
    }

    public int insertNewStorage(Cities storage) throws SQLException {
        try {
            String queryUpdate = "INSERT INTO `storages`(`name`, `specifying`, `position`) VALUES (\"" + storage.getName() + "\", 1, \"" + insertNewCity(storage) + "\")";
            st.executeUpdate(queryUpdate);
            String querySelect = "SELECT `id_storage` FROM `storages` ORDER BY `date_of_modifie` DESC LIMIT 1";
            rs = st.executeQuery(querySelect);
        } catch (SQLException e) {
            System.out.println(e);
        }
        int id=-1;
        while (rs.next()){
            id = rs.getInt("id_storage");
            System.out.println(id);
        }
        return id;
    }
}
