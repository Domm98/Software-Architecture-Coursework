import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlUpdate {
	public static void updatePatient(String NHSno, String name, String address, String condition, String treatment,
			String region, String assigned) {
		try {

			// Load drivers
			Class.forName("com.mysql.jdbc.Driver");

			// Establish connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospital?user=Java&password=Java");

			// Create new SQL statement
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// Generate query
			String query = "SELECT * FROM patient_records WHERE PatientNHSNo = '" + NHSno + "'";

			// Execute query
			ResultSet results = statement.executeQuery(query);

			if (results.next()) {
				results.first();
				// Update data
				results.updateString("PatientNHSno", NHSno);
				results.updateString("PatientName", name);
				results.updateString("PatientAddress", address);
				results.updateString("PatientCondition", condition);
				results.updateString("RecommendedTreatment", treatment);
				results.updateString("Region", region);
				results.updateString("AmbulanceAssigned", assigned);
				results.updateRow();
				System.out.println("Patient updated successfully");
			} else {

				System.out.println("Record does not exist");
			}

			// Free resources
			statement.close();
			conn.close();
		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
			System.exit(-1);
		} catch (SQLException sqe) {
			System.err.println("Error in SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
	}

	public static void updateAmbulance(String NHSno, String assigned) {
		try {

			// Load drivers
			Class.forName("com.mysql.jdbc.Driver");

			// Establish connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospital?user=Java&password=Java");

			// Create new SQL statement
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// Generate query
			String query = "SELECT * FROM patient_records WHERE PatientNHSNo = '" + NHSno + "'";

			// Execute query
			ResultSet results = statement.executeQuery(query);

			if (results.next()) {
				results.first();
				// Update data
				results.updateString("AmbulanceAssigned", assigned);
				results.updateRow();
				System.out.println("Request closed successfully");
			} else {

				System.out.println("Record does not exist");
			}

			// Free resources
			statement.close();
			conn.close();
		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
			System.exit(-1);
		} catch (SQLException sqe) {
			System.err.println("Error in SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
	}

	public static void updateCallout(String NHSno, String name, String condition, String date, String time,
			String location, String action, String timeOnCall) {
		try {

			// Load drivers
			Class.forName("com.mysql.jdbc.Driver");

			// Establish connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospital?user=Java&password=Java");

			// Create new SQL statement
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// Generate query
			String query = "SELECT * FROM callouts WHERE NHSNo = '" + NHSno + "'";

			// Execute query
			ResultSet results = statement.executeQuery(query);

			if (results.next()) {
				results.first();
				// Update data
				results.updateString("NHSno", NHSno);
				results.updateString("PatientName", name);
				results.updateString("PatientCondition", condition);
				results.updateString("Date", date);
				results.updateString("Time", time);
				results.updateString("LocationOfAccident", location);
				results.updateString("ActionTaken", action);
				results.updateString("TimeSpent", timeOnCall);
				results.updateRow();
				System.out.println("Record updated successfully");
			} else {
				System.out.println("Record does not exist");
			}

			// Free resources
			statement.close();
			conn.close();
		} catch (ClassNotFoundException cnf) {
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
			// System.exit(-1);
		} catch (SQLException sqe) {
			System.err.println("Error in SQL Update");
			System.err.println(sqe.getMessage());
			// System.exit(-1);
		}
	}
}
