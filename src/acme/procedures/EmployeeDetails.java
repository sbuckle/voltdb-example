package acme.procedures;

import org.voltdb.ProcInfo;
import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;

@ProcInfo(
	partitionInfo = "EMPLOYEE.EMAIL: 0",
	singlePartition = true
)
public class EmployeeDetails extends VoltProcedure {

	public final SQLStmt getemployee = new SQLStmt(
		"SELECT E.EMAIL, E.FIRSTNAME, E.LASTNAME, D.NAME " +
	    "FROM EMPLOYEE AS E, DEPARTMENT AS D " +
	    "WHERE EMAIL = ? AND E.DEPARTMENT_ID = D.DEPARTMENT_ID;"
	);
	
	public VoltTable[] run(String email) throws VoltAbortException {
        // Add a SQL statement to the current execution queue
        voltQueueSQL(getemployee, email);

        // Run all queued queries.
        // Passing true parameter since this is the last voltExecuteSQL for this procedure.
        return voltExecuteSQL(true);
    }
}
