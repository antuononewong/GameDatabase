package GameDatabase.Data;

// commit

/* Script will hold a SQL SELECT query along with other data related
 * to the defined query. Class is used to parse ResultSet returned from 
 * database.
 */

public class QueryContainer {
	
	private String query;
	private String[] columnNames;

	public QueryContainer (String query, String[] selectColumnNames) {
		this.query = query;
		columnNames = selectColumnNames;
	}
	
	public String GetQueryString() {
		return query;
	}
	
	public String GetColumnName(int i) {
		return columnNames[i];
	}
	
	public int GetColumnCount() {
		return columnNames.length;
	}
	
}
