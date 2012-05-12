package acme.procedures;

import org.voltdb.*;

/**
 * Adds an entry to the identifier table.
 * 
 * The alternative would be to add a check to the
 * run procedure in GenerateUniqueIdentifier so that if
 * the table entry doesn't exist, it gets created automatically.
 */
@ProcInfo (
	partitionInfo = "IDENTIFIER.TABLE_NAME: 0",
	singlePartition = true
)
public class AddTableIdentifier extends VoltProcedure {

	public final SQLStmt insert = new SQLStmt(
		"INSERT INTO IDENTIFIER (TABLE_NAME) VALUES (?)"
	);
	
	public VoltTable[] run (String tableName)
		throws VoltAbortException {
		
		voltQueueSQL(insert, tableName);
		voltExecuteSQL(true);
		return null;
	}
}
