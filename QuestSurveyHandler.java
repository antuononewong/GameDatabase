package GameDatabase.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import GameDatabase.Connection.DBConnection;

/* Script that handles the surveys shown to players upon completion of a quest. The survey will
 * note basic statistics about the player/location. Survey will be used to gather data that
 * can be analyzed for improvements upon gameplay/gameplay systems.
 */

// Database Schema - quest_surveys
// id (Primary key - int), area (str), quest (str), questType (str), playerLevel (int) difficulty (int), 
// clearInstructions (int), questLength (int)

public class QuestSurveyHandler {
	
	// area, quest - most done quests (shows general pathing of player)
	// questType, quest - most frequent/enjoyable quests
	// quest, clearInstrctions - quest comprehension
	// area, playerLevel, difficulty - general feel of a given area
	// quest, questLength - general game time on quest
	// quest, difficulty, clearInstructions, questLength - general feel of a quest
	// area, playerLevel - completion rate of previous areas
	
	private String _pathing = "SELECT area, COUNT(quest) AS questCount FROM quest_surveys;";
	private String _questFrequency = "SELECT questType, COUNT(quest) AS questCount FROM quest_surveys;";
	private String _questComprehension = "SELECT quest, AVG(clearInstructions) AS clearAverage FROM quest_surveys;";
	private String _areaFeel = "SELECT area, AVG(playerLevel) AS levelAverage, AVG(difficulty) AS difficultyAverage FROM quest_surveys;";
	private String _questTime = "SELECT quest, AVG(questLength) AS lengthAverage FROM quest_surveys;";
	private String _questFeel = "SELET quest, AVG(difficulty) AS difficultyAverage, AVG(clearInstructions) AS clearAverage, "
								+ "AVG(questLength) AS lengthAverage FROM quest_surveys;";
	private String _areaCompletion  = "SELECT area, AVG(playerLevel) AS levelAverage FROM quest_surveys;";
	
	// Run each defined query and print out the data received from database
	public void GetAllQuestSurveyData() {
		String[] pathingColumns = {"area", "questCount"};
		GetQuestSurveyData(_pathing, pathingColumns);
		
		String[] questFrequencyColumns = {"questType", "questCount"};
		GetQuestSurveyData(_questFrequency, questFrequencyColumns);
		
		String[] questComprehensionColumns = {"quest", "clearAverage"};
		GetQuestSurveyData(_questComprehension, questComprehensionColumns);
		
		String[] areaFeelColumns = {"area", "levelAverage" , "difficultyAverage"};
		GetQuestSurveyData(_areaFeel, areaFeelColumns);
		
		String[] questTimeColumns = {"quest", "lengthAverage"};
		GetQuestSurveyData(_questTime, questTimeColumns);
		
		String[] questFeelColumns = {"quest", "difficultyAverage", "clearAverage", "lengthAverage"};
		GetQuestSurveyData(_questFeel, questFeelColumns);
		
		String[] areaCompletionColumns = {"area", "levelAverage"};
		GetQuestSurveyData(_areaCompletion, areaCompletionColumns);
	}
	
	// Adds user-inputed survey data to the database
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
	
	// Retrieve data from database with SELECT query
	private void GetQuestSurveyData(String query, String[] selectColumnNames) {
		try {
			ResultSet set = DBConnection.Select(query);
			if (set != null) {
				QueryContainer queryContainer = new QueryContainer(query, selectColumnNames);
				ParseQuestSurveyData(set, queryContainer);
			}
		}
		catch (SQLException e) {
			System.out.println("---SQLException error from QuestSurveyHandler.GetQuestSurveyData().---");
			e.printStackTrace();
		}
		
	}
	
	// Parse ResultSet from database into a easy to read format
	private void ParseQuestSurveyData(ResultSet set, QueryContainer queryContainer) throws SQLException {
		set.next();
		// ResultSet/SQL queries uses 1 indexing
		String output = String.format("%s: %s\n", queryContainer.GetColumnName(0), set.getString(1));
	
		for (int i = 1; i < queryContainer.GetColumnCount(); i++) {
			output += String.format("%s: %.2f\n", queryContainer.GetColumnName(i), set.getFloat(i + 1));
		}
		
		System.out.print(output);
	}
	
}
