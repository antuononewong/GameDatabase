package GameDatabase.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import GameDatabase.Connection.DBConnection;

/* Script that establishes a database connection, adds/removes user 
 * save files from the database, and closes the same connection.
 */

// Database Schema - save_files
// id (Primary key - int), username (str), hp (int), mana (int), credits (int), checkpoint (str), quest (str)

public class SaveFileHandler {
	
	// Replace old username with new user-inputed username in database
	public void UpdateUsername(String oldUsername, String newUsername) {
		String query = String.format("UPDATE save_files SET username = %s WHERE username = %s;", newUsername, oldUsername);
		
		try {
			DBConnection.Update(query);
		}
		catch (SQLException e) {
			System.out.println("---SQLException error from SaveFileHandler.UpdateUsername().---");
			e.printStackTrace();
		}
	}
	
	// Add save file to database based on parameters.
	// Sample query - INSERT INTO save_files (username, hp, mana, credits, checkpoint, quest) 
	//				  VALUES (username, hp, mana, credits, checkpoint, quest);
	public void AddSaveFile(String username, int hp, int mana, int credits, 
							String checkpoint, String quest) {
		String query = String.format("INSERT INTO save_files (username, hp, mana, credits, checkpoint, quest) "
									+ "VALUES (%s, %d, %d, %d, %s, %s);", username, hp, mana, credits, checkpoint, quest);
		try {
			DBConnection.Insert(query);
		}
		catch (SQLException e) {
			System.out.println("---SQLException error from SaveFileHandler.AddSaveFile().---");
			e.printStackTrace();
		}
	}
	
	// Retrieve save file from database for appropriate user.
	// Sample query - SELECT * FROM save_files WHERE username = username;
	public void GetSaveFile(String username) {
		String query = String.format("SELECT * FROM save_files WHERE username = %s;", username);
		
		try {
			ResultSet set = DBConnection.Select(query);
			if (set != null) {
				ProcessSaveFileSet(set);
			}
		}
		catch (SQLException e) {
			System.out.println("---SQLException error from SaveFileHandler.GetSaveFile().---");
			e.printStackTrace();
		}
	}
	
	// Parse output from database when sending SELECT query 
	// to grab save file for specific user
	// ResultSet includes id, username, hp, mana, credits, checkpoint, quest
	private void ProcessSaveFileSet(ResultSet set) throws SQLException{
		while (set.next()) {
			String username = set.getString("username");
			int hp = set.getInt("hp");
			int mana = set.getInt("mana");
			int credits = set.getInt("credits");
			String checkpoint = set.getString("checkpoint");
			String quest = set.getString("quest");
			System.out.println(String.format("%s @ %s with quest: %s. \nResources:\n HP: %d | Mana: %d | Credits: %d.", 
							   username, checkpoint, quest, hp, mana, credits));
		}
	}
	
}
