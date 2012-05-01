package acme.procedures;

import org.voltdb.ProcInfo;
import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

@ProcInfo (
	singlePartition = false // Table is replicated across all partitions
)
public class CreateDepartment extends VoltProcedure {

	public final SQLStmt createdepartment = new SQLStmt(
		"INSERT INTO DEPARTMENT VALUES (?, ?)"
	);
	
	public VoltTable[] run(int id, String name) 
		throws VoltAbortException {
		
		voltQueueSQL(createdepartment, id, name);
		voltExecuteSQL(true);
		return null;
	}
}
