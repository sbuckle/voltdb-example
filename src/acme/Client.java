package acme;

// VoltTable is VoltDB's table representation.
import org.voltdb.VoltTable;
import org.voltdb.VoltTableRow;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientResponse;

public class Client {
    
    public static void main(String[] args) throws Exception {
        System.out.println("Client started");
        
        ClientConfig clientConfig = new ClientConfig("program", "none");
        org.voltdb.client.Client client =
            org.voltdb.client.ClientFactory.createClient(clientConfig);
        
        // Client instance connected to the database running on
        // the specified IP address, in this case 127.0.0.1. The
        // database always runs on TCP/IP port 21212.
        client.createConnection("localhost");
        
        
        // Example of executing a dynamic SQL query
        String tableName = "EMPLOYEE";
        VoltTable[] count = client.callProcedure("@AdHoc", "SELECT COUNT(*) FROM " + tableName).getResults();
        System.out.printf("Found %d employees.\n", count[0].fetchRow(0).getLong(0));
        
        /**
         * Initialize the database.
         */
        try {
        	client.callProcedure("CreateDepartment", 1, "Accounts");
        	client.callProcedure("CreateDepartment", 2, "Sales");
        	client.callProcedure("CreateDepartment", 3, "IT");
        	
        	client.callProcedure("AddEmployee", "wile@acme.com", "Wile", "Coyote", 1);
        	client.callProcedure("AddEmployee", "john@acme.com", "John", "Ledger", 1);
        	client.callProcedure("AddEmployee", "alice@acme.com", "Alice", "Smith", 3);
        	client.callProcedure("AddEmployee", "bob@acme.com", "Bob", "Parker", 3);
        	client.callProcedure("AddEmployee", "larry@acme.com", "Larry", "Merchant", 2);
        	
        } catch (Exception e) {
        	// Ignore constraint violations when we try and load the table(s) more than once.
        }
        
        // Synchronous call to find out what department Larry Merchant works in
        ClientResponse response = client.callProcedure("EmployeeDetails", "bob@acme.com");
        if (response.getStatus() != ClientResponse.SUCCESS) {
        	System.err.println(response.getStatusString());
        	System.exit(-1);
        }
        
        final VoltTable results[] = response.getResults();
       
        VoltTable result = results[0];
        VoltTableRow row = result.fetchRow(0);
        System.out.printf("%s works in %s\n", row.getString("FIRSTNAME"),
        		row.getString("NAME"));
    }

}
