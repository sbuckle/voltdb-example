package acme.procedures;

import org.voltdb.ProcInfo;
import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;

@ProcInfo (
	partitionInfo = "EMPLOYEE.EMAIL: 0",
	singlePartition = true
)
public class AddEmployee extends VoltProcedure {

	public final SQLStmt addemployee = new SQLStmt(
		"INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?);"
	);
	
	public VoltTable[] run(String email, String firstname,
			String lastname, int department) 
	throws VoltAbortException {
	
	voltQueueSQL(addemployee, email, firstname, lastname, department);
	voltExecuteSQL(true);
	return null;
}
}
