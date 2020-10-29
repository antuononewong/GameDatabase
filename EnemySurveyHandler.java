package GameDatabase.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import GameDatabase.Connection.DBConnection;

// Database Schema - enemy_surveys
// enemyName (str), area (str), playerLevel (int), encounters (int), deaths (int), abilities (str), diedTo (str)

public class EnemySurveyHandler {
	
	// area, deaths - most dangerous areas
	// enemyName, area, deaths - most dangerous enemies / see if one area is too difficult/scary
	// area, playerLevel, deaths - can reference with quest data to see if player power is balanced across course of the game
	// enemyName, encounters, deaths - how difficult an enemy is to overcome
	// enemyName, diedTo - balancing of mechanics of enemy
	// enemyName, playerLevel, abilities - setup players use when fighting certain enemies
	// enemyName, deathCount, abilities - see if certain setups are weaker
	
	private String _areaDanger = "SELECT area, COUNT(deaths) AS deathCount FROM enemy_surveys;";
	private String _enemyDanger = "SELECT enemyName, area, COUNT(deaths) AS deathCount FROM enemy_surveys;";
	private String _areaBalance = "SELECT area, AVG(playerLevel) AS levelAverage, COUNT(deaths) AS deathCount FROM enemy_surveys;";
	private String _enemyDifficulty = "SELECT enemyName, COUNT(encounters) AS encounterCount, COUNT(deaths) AS deathCount FROM enemy_surveys;";
	private String _enemyAbilityBalance = "SELECT enemyName, COUNT(diedTo) AS dieCount FROM enemy_surveys;";
	private String _playerLoadouts = "SELECT enemyName, AVG(playerLevel) AS levelAverage, COUNT(abilities) AS abilityCount FROM enemy_surveys;";
	private String _loadoutBalance = "SELECT enemyName, COUNT(deaths) AS deathCount, COUNT(abilities) AS abilityCount FROM enemy_surveys;";
	
	public void GetAllEnemySurveyData() {
		String[] areaDangerColumns = {"area", "deathCount"};
		GetEnemySurveyData(_areaDanger, areaDangerColumns);
		
		String[] enemyDangerColumns = {"enemyName", "area", "deathCount"};
		GetEnemySurveyData(_enemyDanger, enemyDangerColumns);
		
		String[] areaBalanceColumns = {"area", "levelAverage", "deathCount"};
		GetEnemySurveyData(_areaBalance, areaBalanceColumns);
		
		String[] enemyDifficultyColumns = {"enemyName", "encounterCount", "deathCount"};
		GetEnemySurveyData(_enemyDifficulty, enemyDifficultyColumns);
		
		String[] enemyAbilityBalanceColumns = {"enemyName", "dieCount"};
		GetEnemySurveyData(_enemyAbilityBalance, enemyAbilityBalanceColumns);
		
		String[] playerLoadoutsColumns = {"enemyName", "levelAverage", "abilityCount"};
		GetEnemySurveyData(_playerLoadouts, playerLoadoutsColumns);
		
		String[] loadoutBalanceColumns = {"enemyName", "levelAverage", "abilityCount"};
		GetEnemySurveyData(_loadoutBalance, loadoutBalanceColumns);
	}
	
	// Adds enemy survey data witH INSERT query into connected database
	public void AddEnemySurvey(String enemyName, String area, int playerLevel, int encounters, 
								int deathCount, String abilities, String diedTo) {
		String query = String.format("INSERT INTO enemy_surveys (enemyName, area, playerLevel, encounters, deathCount, abilities, "
				+ "diedTo) VALUES (%s, %s, %d, %d, %d, %s, %s);", enemyName, area, playerLevel, encounters, deathCount, 
																  abilities, diedTo);
		
		try {
			DBConnection.Insert(query);
		}
		catch (Exception e) {
			System.out.println("---SQLException error from EnemySurveyHandler.AddEnemySurvey().---");
			e.printStackTrace();
		}
	}
	
	private void GetEnemySurveyData(String query, String[] selectColumnNames) {
		try {
			ResultSet set = DBConnection.Select(query);
			if (set != null) {
				QueryContainer queryContainer = new QueryContainer(query, selectColumnNames);
				ParseEnemySurveyData(set, queryContainer);
			}
		}
		catch (SQLException e) {
			System.out.println("---SQLException error from EnemySurveyHandler.GetEnemySurveyData().---");
			e.printStackTrace();
		}
	}
	
	private void ParseEnemySurveyData(ResultSet set, QueryContainer queryContainer) throws SQLException {
		set.next(); // ResultSet has 1 indexing and pointer starts before first row
		
		System.out.println("---Enemy Survey Data---");
		String output = String.format("%s: %s\n", queryContainer.GetColumnName(0), set.getString(1));

		for (int i = 1; i < queryContainer.GetColumnCount(); i++) {
			output += String.format("%s: %.2f\n", queryContainer.GetColumnName(i), set.getFloat(i + 1));
		}
		
		System.out.print(output);
	}
}
