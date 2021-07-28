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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AmbulanceClient {

	public static String isRequest;
	public static String details[];

	public static void main(String[] args) throws InterruptedException {

		try { // Connect with RMI to KwikMedical
			Registry registry = LocateRegistry.getRegistry(null);
			IKwikMedical stub = (IKwikMedical) registry.lookup("Hospital");

			// Initialise GUI for user input
			JFrame frame = new JFrame("Ambulance Client");
			frame.setBounds(0, 0, 300, 180);
			frame.setResizable(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.getContentPane().setLayout(null);
			panel.setBounds(0, 0, 300, 180);
			panel.setLayout(null);
			frame.getContentPane().add(panel);

			JLabel lbl = new JLabel("Please enter your hospital number");
			lbl.setBounds(50, 10, 300, 30);
			JTextField tf = new JTextField();
			tf.setBounds(50, 50, 200, 20);
			JButton submit = new JButton("Submit");
			submit.setBounds(100, 80, 75, 20);
			panel.add(lbl);
			panel.add(tf);
			panel.add(submit);
			frame.setVisible(true);

			submit.addActionListener(new ActionListener() { // Checks for active requests in patient details dB which
															// matching region of PDA operator
				public void actionPerformed(ActionEvent evt) {
					String regionNo = tf.getText();
					try { // Split the return from KwikMedical to get access to patients details
							// and use them in the call out form
						isRequest = stub.generateRequest(regionNo);
						details = isRequest.split(",");
						System.out.println("NHS No: " + details[0] + "\n" + "Patient Name: " + details[1] + "\n"
								+ "Recommended Treatment: " + details[2]);
						callOutDetails(details);
					} catch (RemoteException e) {
						System.out.println("There are no active requests for your region");
						System.exit(1);
					}
					frame.setVisible(false);
				}
			});
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	// Call Out Details form
	public static void callOutDetails(String pDetails[]) {

		try { // Connect with RMI to KwikMedical
			Registry registry = LocateRegistry.getRegistry(null);
			IKwikMedical stub = (IKwikMedical) registry.lookup("Hospital");

			// Initialise GUI for user input
			JFrame frame = new JFrame("Kwik Medical");
			frame.setBounds(0, 0, 250, 600);
			frame.setResizable(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);

			JPanel panel = new JPanel();
			frame.getContentPane().setLayout(null);
			panel.setBounds(0, 0, 250, 600);
			panel.setLayout(null);
			frame.getContentPane().add(panel);

			JLabel inst = new JLabel("Call Out Form");
			inst.setBounds(10, 10, 200, 40);
			panel.add(inst);
			JLabel lblCondition = new JLabel("Condition(Stable/Moderate/Critical):");
			lblCondition.setBounds(10, 60, 200, 20);
			panel.add(lblCondition);
			JTextField tfCondition = new JTextField();
			tfCondition.setBounds(10, 90, 200, 20);
			panel.add(tfCondition);
			JLabel lblTime = new JLabel("Time:");
			lblTime.setBounds(10, 210, 200, 20);
			panel.add(lblTime);
			JTextField tfTime = new JTextField();
			tfTime.setBounds(10, 240, 40, 20);
			panel.add(tfTime);
			JButton currentTime = new JButton("Get Time");
			currentTime.setBounds(10, 270, 200, 22);
			panel.add(currentTime);
			JLabel lblDate = new JLabel("Date (DD/MM/YYYY):");
			lblDate.setBounds(10, 120, 200, 20);
			panel.add(lblDate);
			JTextField tfDate = new JTextField();
			tfDate.setBounds(10, 150, 70, 20);
			panel.add(tfDate);
			JButton currentDate = new JButton("Get Date");
			currentDate.setBounds(10, 180, 200, 22);
			panel.add(currentDate);
			JLabel lblLocation = new JLabel("Location:");
			lblLocation.setBounds(10, 310, 200, 20);
			panel.add(lblLocation);
			JTextField tfLocation = new JTextField();
			tfLocation.setBounds(10, 340, 200, 20);
			panel.add(tfLocation);
			JLabel lblActionTaken = new JLabel("Action Taken:");
			lblActionTaken.setBounds(10, 370, 200, 20);
			panel.add(lblActionTaken);
			JTextField tfActionTaken = new JTextField();
			tfActionTaken.setBounds(10, 400, 200, 25);
			panel.add(tfActionTaken);
			JLabel lblTimeSpent = new JLabel("Time spent on the call (minutes):");
			lblTimeSpent.setBounds(10, 440, 200, 20);
			panel.add(lblTimeSpent);
			JTextField tfTimeSpent = new JTextField();
			tfTimeSpent.setBounds(10, 480, 40, 20);
			panel.add(tfTimeSpent);
			JButton submit = new JButton("Submit");
			submit.setBounds(10, 510, 200, 25);
			panel.add(submit);

			currentDate.addActionListener(new ActionListener() { // Display current date in textbox
				public void actionPerformed(ActionEvent evt) {
					String dFormat = "dd/MM/yyyy";
					DateFormat df = new SimpleDateFormat(dFormat);
					Date date = new Date();
					tfDate.setText(df.format(date));
				}
			});

			currentTime.addActionListener(new ActionListener() { // Displays current time in textbox
				public void actionPerformed(ActionEvent evt) {
					String tFormat = "HH:mm";
					DateFormat df = new SimpleDateFormat(tFormat);
					Date time = new Date();
					tfTime.setText(df.format(time));
				}
			});

			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					// Generate data from existing record and form input
					String NHSNo = pDetails[0];
					String name = pDetails[1];
					String condition = tfCondition.getText();
					String date = tfDate.getText();
					String time = tfTime.getText();
					String location = tfLocation.getText();
					String actionTaken = tfActionTaken.getText();
					String timeSpent = tfTimeSpent.getText();

					try { // Update callout records when entered from the PDA mobile device
						stub.hospitalUpdate(NHSNo, name, condition, date, time, location, actionTaken, timeSpent);
					} catch (RemoteException e) {
						System.err.println("Ambulance Client update exception: " + e.toString());
						e.printStackTrace();
					}
					frame.setVisible(false);
				}
			});
		} catch (Exception e) {
			System.err.println("Ambulance Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}