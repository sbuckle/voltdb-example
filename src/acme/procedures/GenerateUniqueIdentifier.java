package acme.procedures;

import org.voltdb.*;

@ProcInfo (
	partitionInfo = "IDENTIFIER.TABLE_NAME: 0",
	singlePartition = true
)
public class GenerateUniqueIdentifier extends VoltProcedure {

	public final SQLStmt select = new SQLStmt(
		"SELECT CURRENT_VALUE FROM IDENTIFIER WHERE TABLE_NAME = ?"
	);

	public final SQLStmt update = new SQLStmt(
		"UPDATE IDENTIFIER SET CURRENT_VALUE = CURRENT_VALUE + 1 " +
		"WHERE TABLE_NAME = ?"
	);
	
	public VoltTable[] run(String tableName)
		throws VoltAbortException {
		
		voltQueueSQL(select, tableName);
		VoltTable[] idResult = voltExecuteSQL();
		
		voltQueueSQL(update, tableName);
		voltExecuteSQL(true);
		
		return idResult; // Return the current value for the table
	}
}

