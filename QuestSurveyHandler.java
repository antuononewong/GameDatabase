package GameDatabase.Data;

import java.sql.SQLException;
import java.sql.Statement;
import GameDatabase.Connection.DBConnection;

public class QuestSurveyHandler {
	
	// Database Schema
	// (area (str), quest (str), questTtype (str), playerLevel (int) difficulty (int), 
	// clearInstructions (int), questLength (int))
	// %d - integer, %s string
	public void AddQuestSurvey(String area, String quest, String questType, int playerLevel,
							   int difficulty, int clearInstructions, int questLength) {
		String query = String.format("INSERT INTO quest_surveys (area, quest, questType, playerLevel, "
									+ "difficulty, clearInstructions, questLength) VALUES ("
									+ "%s, %s, %s, %d, %d, %d, %d);", area, quest, questType, playerLevel,
									difficulty, clearInstructions, questLength);

		try {
			DBConnection.Insert(query);
		}
		catch (SQLException e) {
			System.out.println("---SQLException error from QuestSurveyHandler.AddQuestSurvey().---");
			e.printStackTrace();
		}
	}
	
	public void ParseQuestSurveyData() {
		
	}
	
	
}
