import java.rmi.RemoteException;

public class KwikMedical implements IKwikMedical {
	private boolean exists;
	private String region = "";

	public void patientInsert(String NHSno, String name, String address, String condition, String recommendedTreatment,
			String region, String assigned) throws RemoteException {

		exists = SqlQueries.findpatient(NHSno);

		if (!exists) { // New call out
			System.out.println("Inserting patient details...");
			SqlInsert.insertPatient(NHSno, name, address, condition, recommendedTreatment, region, assigned);
		} else if (exists) { // Call out exists, so update the record and close the request
			System.out.println("Updating patient details...");
			SqlUpdate.updatePatient(NHSno, name, address, condition, recommendedTreatment, region, assigned);
		}
	}

	// Update hospital and close active request
	public void hospitalUpdate(String NHSno, String name, String condition, String date, String time, String location,
			String action, String timeSpent) throws RemoteException {

		exists = SqlQueries.findCallout(NHSno);

		if (!exists) { // New call out, insert details and close request by updating patient details
			System.out.println("Inserting callout details...");
			SqlInsert.insertCallout(NHSno, name, condition, date, time, location, action, timeSpent);
			SqlUpdate.updateAmbulance(NHSno, "Yes");
		} else if (exists) { // Update call out details if one already exists (should never be the case)
			System.out.println("Updating callout details...");
			SqlUpdate.updateCallout(NHSno, name, condition, date, time, location, action, timeSpent);
			SqlUpdate.updateAmbulance(NHSno, "Yes");
		}
	}

	public String generateRequest(String regionInput) throws RemoteException {

		String activeRequest = SqlQueries.findRequest(regionInput);

		if (activeRequest == null) {
			System.out.println("Exiting...");
			System.exit(1);
			;
		}
		return activeRequest;
	}

	public String region() throws RemoteException {
		return region;
	}

	public void storeRegion(String input) {
		region = input;
	}
}