package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConnectionClass {
    public Connection connection;

    public Connection getConnection() {
        String hostname = "localhost";
        String dbname = "dentistmedical";
        String userName = "root";
        String password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbname + "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false", userName, password);

        } catch (Exception e){
        	Alert alert = new Alert(AlertType.WARNING, ""); 
        	alert.setTitle("");
        	alert.setHeaderText("Өгөгдлийн сантай холбогдох үед алдаа гарлаа");
        	alert.showAndWait();
        }

         return connection;
    }

}