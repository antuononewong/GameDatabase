module GameDatabase {
	exports GameDatabase.Connection;
	exports GameDatabase.Data;
	requires transitive java.sql;
}

	//connect
	// create query
	// execute query
	// process results from query
	// close connection