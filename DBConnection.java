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
		private String _userName = "root";
		private String _password = "pw";
		private String _serverName = "localhost";
		private String _portNumber = "3306";
		
		// Database connection
		private static Connection _connection;
		
		// Attempt to connect to database based on stored credentials.
		public void GetConnection() throws SQLException {
			Properties properties = new Properties();
			properties.put("user", _userName);
			properties.put("password", _password);
			
			String connectionInput = String.format("jdbc:mysql://%s:%s/", _serverName, _portNumber);
			_connection = DriverManager.getConnection(connectionInput, properties);
		}
		
		// Close database connection to release any used database resources
		public void CloseConnection() throws SQLException {
			_connection.close();
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
				statement = _connection.createStatement();
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
				statement = _connection.createStatement();
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
		
		// Runs a string query that includes UPDATE with the open database connection
		// Functionally the same as Insert(), but naming this update to coincide
		// with appropriate queries
		public static void Update(String query) throws SQLException {
			Statement statement = null;
			
			try {
				statement = _connection.createStatement();
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

}
