package acme;

import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;

public class ShutdownClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage: Must specify host.");
			System.exit(-1);
		}
		
        Client client = ClientFactory.createClient();
        client.createConnection(args[0]);

        try {
        	/**
        	 * Shutdown the database.
        	 */
			client.callProcedure("@Shutdown");
		} catch (Exception e) {
			/**
			 * An exception is expected here as when the database server is shut down
			 * it breaks the database connection to the client.
			 */
			System.out.println("Database shutdown request has been sent.");
		}
	}
}
