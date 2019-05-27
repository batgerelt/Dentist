package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;

//https://www.youtube.com/watch?v=8WJ5p3T9Iss эндээс бичлэг үзээрэй
public class ConnectionClass {
    public Connection connection;

    public Connection getConnection() {
        String hostname = "localhost";
        String dbname = "dental";
        String userName = "root";
        String password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbname + "?autoReconnect=true&useSSL=false", userName, password);

        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

}