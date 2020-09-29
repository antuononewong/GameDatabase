package GameDatabase.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
	
		// Database credentials
		private String userName = "root";
		private String password = "pw";
		private String serverName = "localhost";
		private String portNumber = "3306";
		
		// Database connection
		private static Connection connection;
		
		// Attempt to connect to database based on stored credentials.
		public void GetConnection() throws SQLException {
			Properties properties = new Properties();
			properties.put("user", userName);
			properties.put("password", password);
			
			String connectionInput = "jdbc:mysql://" + serverName + ":" + portNumber + "/";
			connection = DriverManager.getConnection(connectionInput, properties);
		}
		
		// Close database connection to release any used database resources
		public void CloseConnection() throws SQLException {
			connection.close();
		}
		
		public static void PrintErrorStatement(Exception e) {
			System.out.println("---Error trying to execute SQL query.---");
			e.printStackTrace();
		}
		
		public static void Insert(String query) throws SQLException {
			Statement statement = null;
			
			try {
				statement = connection.createStatement();
				statement.executeUpdate(query);
			}
			catch (Exception e) {
				DBConnection.PrintErrorStatement(e);
			}
			finally {
				if (statement != null) {
					statement.close();
				}
			}
		}
		
		public static ResultSet Select(String query) throws SQLException {
			Statement statement = null;
			
			try {
				statement = connection.createStatement();
				return statement.executeQuery(query);
			}
			catch (Exception e) {
				DBConnection.PrintErrorStatement(e);
			}
			finally {
				if (statement != null) {
					statement.close();
				}
			}
			return null;
		}

}
