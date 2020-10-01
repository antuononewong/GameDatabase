package GameDatabase.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// Script for handling connecting/closing of connection to a database
// based on the stored credentials. It has generalized insert/select functions
// that handles the query execution pattern/error checking.

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
			
			String connectionInput = String.format("jdbc:mysql://%s:%s/", serverName, portNumber);
			connection = DriverManager.getConnection(connectionInput, properties);
		}
		
		// Close database connection to release any used database resources
		public void CloseConnection() throws SQLException {
			connection.close();
		}
		
		// Simple print error to the console
		public static void PrintErrorStatement(Exception e) {
			System.out.println("---Error trying to execute SQL query.---");
			e.printStackTrace();
		}
		
		// Runs a string query that includes INSERT with the open database connection
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
		
		// Runs a string query that includes SELECT with the open database connection 
		// and returns the output from the database
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
