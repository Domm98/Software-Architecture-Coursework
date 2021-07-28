import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlQueries {

	public static Boolean exists;
	public static String findData;

	public static Boolean findpatient(String NHSno) {
		try {
			// Load drivers
			Class.forName("com.mysql.jdbc.Driver");

			// Establish Connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospital?user=Java&password=Java");

			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// Generate query
			String query = "SELECT * FROM patient_records WHERE PatientNHSNo = '" + NHSno + "'";

			// Use query to find results
			ResultSet results = statement.executeQuery(query);

			// Record has been found
			if (results.next()) {
				results.first();
				exists = true;
			} else {
				// Record has not been found
				exists = false;
			}
			// Close
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
		return exists;
	}

	public static Boolean findCallout(String NHSno) {
		try {
			// Load drivers
			Class.forName("com.mysql.jdbc.Driver");

			// Establish Connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospital?user=Java&password=Java");

			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// Generate query
			String query = "SELECT * FROM callouts WHERE NHSNo = '" + NHSno + "'";

			// Use query to find results
			ResultSet results = statement.executeQuery(query);

			// Record has been found
			if (results.next()) {
				results.first();
				exists = true;
			} else {
				// Record has not been found
				exists = false;
			}
			// Close
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
		return exists;
	}

	public static String findRequest(String region) {
		try {
			// Load drivers
			Class.forName("com.mysql.jdbc.Driver");

			// Establish Connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospital?user=Java&password=Java");

			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// Generate query
			String query = "SELECT * FROM patient_records WHERE Region = '" + region + "' AND AmbulanceAssigned = 'No'";

			// Use query to find results
			ResultSet results = statement.executeQuery(query);

			// Record has been found
			if (results.next()) {
				results.first();
				String pno = results.getString("PatientNHSNo");
				String pname = results.getString("PatientName");
				String recTreatment = results.getString("RecommendedTreatment");
				findData = pno + "," + pname + "," + recTreatment + ",";
				return findData;
			} else { // Record has not been found
				System.out.println("There are no active requests for your region, disconnecting from server..");
			}
			// Close
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
		return findData;
	}
}