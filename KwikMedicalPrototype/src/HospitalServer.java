import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HospitalServer {

	public static void main(String args[]) {
		try {
			// Start the registry on the default RMI port
			Registry registry = LocateRegistry.createRegistry(1099);

			// Instantiate the concrete class
			KwikMedical acc = new KwikMedical();

			// Export the stub using the interface specification
			IKwikMedical stub = (IKwikMedical) UnicastRemoteObject.exportObject(acc, 0);

			// Bind the stub to the name "Hospital" in the registry
			registry.bind("Hospital", stub);

			System.out.println("Connecting to server..." + "\n");
			System.out.println("Successfully connected to KwikMedical");

		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}