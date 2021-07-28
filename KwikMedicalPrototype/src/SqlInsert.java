import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInsert {

	public static void insertPatient(String NHSno, String name, String address, String condition,
			String recommendedTreatment, String region, String assigned) {
		try {
			// Load the driver
			Class.forName("com.mysql.jdbc.Driver");
			// First we need to establish a connection to the database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospital?user=Java&password=Java");

			// Create a new SQL statement
			Statement statement = conn.createStatement();
			// Build the INSERT statement
			String update = "INSERT INTO patient_records (PatientNHSNo, PatientName, PatientAddress, PatientCondition, RecommendedTreatment, Region, AmbulanceAssigned) "
					+ "VALUES ('" + NHSno + "', '" + name + "', '" + address + "', '" + condition + "' , '"
					+ recommendedTreatment + "', '" + region + "', '" + assigned + "')";
			// Execute the statement
			statement.executeUpdate(update);

			// Close
			conn.close();
			statement.close();
			System.out.println("Patient record inserted");
		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
			// System.exit(-1);
		}

		catch (SQLException sqe) {
			System.err.println("Error performing SQL Update");
			System.err.println(sqe.getMessage());
			// System.exit(-1);
		}
	}

	public static void insertCallout(String NHSno, String name, String condition, String date, String time,
			String location, String action, String timeOnCall) {
		try {
			// Load the driver
			Class.forName("com.mysql.jdbc.Driver");
			// First we need to establish a connection to the database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospital?user=Java&password=Java");

			// Create a new SQL statement
			Statement statement = conn.createStatement();
			// Build the INSERT statement
			String update = "INSERT INTO callouts (NHSNo, PatientName, PatientCondition, Date, Time, LocationOfAccident, ActionTaken, TimeSpent) "
					+ "VALUES ('" + NHSno + "', '" + name + "', '" + condition + "', '" + date + "' , '" + time + "', '"
					+ location + "', '" + action + "', '" + timeOnCall + "')";

			// Execute the statement
			statement.executeUpdate(update);

			// Close
			conn.close();
			statement.close();
			System.out.println("Callout details inserted");
		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
			System.exit(-1);
		}

		catch (SQLException sqe) {
			System.err.println("Error performing SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
	}
}
