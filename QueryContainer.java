package GameDatabase.Data;

/* Script will hold a SQL SELECT query along with other data related
 * to the defined query. Class is used to parse ResultSet returned from 
 * database.
 */

public class QueryContainer {
	
	private String _query;
	private String[] _columnNames;

	public QueryContainer(String query, String[] selectColumnNames) {
		this._query = query;
		_columnNames = selectColumnNames;
	}
	
	public String GetQueryString() {
		return _query;
	}
	
	public String GetColumnName(int i) {
		return _columnNames[i];
	}
	
	public int GetColumnCount() {
		return _columnNames.length;
	}
	
}
