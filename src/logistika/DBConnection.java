package logistika;

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

    public void insertData(String name, String address, String city){
        try{
            String query = "INSERT INTO `employee`(`name`, `address`, `city`) VALUES ('"+name+"', '"+address+"', '"+city+"')";
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
