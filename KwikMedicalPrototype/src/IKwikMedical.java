import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IKwikMedical extends Remote {
	void hospitalUpdate(String nHSno, String name, String condition, String date, String time, String location,
			String action, String timeOnCall) throws RemoteException;

	String generateRequest(String regionInput) throws RemoteException;

	String region() throws RemoteException;

	void storeRegion(String region) throws RemoteException;

	void patientInsert(String nHSNo, String name, String address, String medicalCondition, String recommendedTreatment,
			String regionNo, String assigned) throws RemoteException;
}
