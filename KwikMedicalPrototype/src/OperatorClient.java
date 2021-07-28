import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OperatorClient {

	public static String region = "";
	public static String recommendedTreatment = "";
	private static String assigned = "No";

	public static void main(String[] args) throws InterruptedException {

		try {
			// Get the registry from the server (null = local host)
			Registry registry = LocateRegistry.getRegistry(null);
			// Look up the remote object
			IKwikMedical stub = (IKwikMedical) registry.lookup("Hospital");

			// Send the region of emergency to KwikMedical via stub
			JFrame frame = new JFrame("Operator Client");
			frame.setBounds(0, 0, 300, 180);
			frame.setResizable(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.getContentPane().setLayout(null);
			panel.setBounds(0, 0, 300, 180);
			panel.setLayout(null);
			frame.getContentPane().add(panel);

			JLabel lbl = new JLabel("Enter the region of the emergency (1-20)");
			lbl.setBounds(50, 10, 300, 30);
			panel.add(lbl);
			JTextField tf = new JTextField();
			tf.setBounds(50, 50, 200, 20);
			panel.add(tf);
			JButton submit = new JButton("Submit");
			submit.setBounds(100, 80, 75, 20);
			panel.add(submit);
			frame.setVisible(true);

			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					region = tf.getText();
					try {
						stub.storeRegion(region);
						patientDetails(region);
					} catch (RemoteException e) {
						System.err.println("Client exception: " + e.toString());
						e.printStackTrace();
					}
					frame.setVisible(false);
				}
			});
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public static void patientDetails(String regionNo) { // Do we need to throw exception

		try { // Connect with RMI to KwikMedical
			Registry registry = LocateRegistry.getRegistry(null);
			IKwikMedical stub = (IKwikMedical) registry.lookup("Hospital");

			// Initialise GUI for user input
			JFrame frame = new JFrame("Kwik Medical");
			frame.setBounds(0, 0, 250, 400);
			frame.setResizable(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);

			JPanel panel = new JPanel();
			frame.getContentPane().setLayout(null);
			panel.setBounds(0, 0, 250, 400);
			panel.setLayout(null);
			frame.getContentPane().add(panel);

			JLabel inst = new JLabel("Patient Details Form");
			inst.setBounds(10, 10, 200, 40);
			panel.add(inst);
			JLabel lblNHSno = new JLabel("NHS Number:");
			lblNHSno.setBounds(10, 60, 200, 20);
			panel.add(lblNHSno);
			JTextField tfNHSno = new JTextField();
			tfNHSno.setBounds(10, 90, 200, 20);
			panel.add(tfNHSno);
			JLabel lblName = new JLabel("Name:");
			lblName.setBounds(10, 120, 200, 20);
			panel.add(lblName);
			JTextField tfName = new JTextField();
			tfName.setBounds(10, 150, 200, 20);
			panel.add(tfName);
			JLabel lblAddress = new JLabel("Address:");
			lblAddress.setBounds(10, 180, 200, 20);
			panel.add(lblAddress);
			JTextField tfAddress = new JTextField();
			tfAddress.setBounds(10, 210, 200, 20);
			panel.add(tfAddress);
			JLabel lblMedCon = new JLabel("Medical Condition:");
			lblMedCon.setBounds(10, 240, 200, 20);
			panel.add(lblMedCon);
			JTextField tfMedCon = new JTextField();
			tfMedCon.setBounds(10, 270, 200, 20);
			panel.add(tfMedCon);
			JButton submit = new JButton("Submit");
			submit.setBounds(10, 310, 200, 30);
			panel.add(submit);

			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					// Generate data from existing record and form input
					String NHSNo = tfNHSno.getText();
					String name = tfName.getText();
					String address = tfAddress.getText();
					String medicalCondition = tfMedCon.getText();

					// Recommended treatments
					// List may be updated to match a list of possible treatments
					if (medicalCondition.contains("Burn")) {
						recommendedTreatment = "Apply Coolant";
					}
					if (medicalCondition.contains("Asthma")) {
						recommendedTreatment = "Provide Oxygen";
					}

					try { // Insert into patients
						stub.patientInsert(NHSNo, name, address, medicalCondition, recommendedTreatment, regionNo,
								assigned);
					} catch (RemoteException e) {
						System.err.println("Operator Client update exception: " + e.toString());
						e.printStackTrace();
					}
					frame.setVisible(false);
				}
			});
		} catch (Exception e) {
			System.err.println("Operator Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}