
package application;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection{
 public static Connection getConnection() {
   Connection connection = null;
	 try {
		connection = DriverManager.getConnection("jdbc:mysql://localhost/dentist","root","");
	 } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return connection;
 }
}

