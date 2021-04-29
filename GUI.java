package superCharger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.border.TitledBorder;
import java.text.DecimalFormat;

/**
 * The GUI class implements the SuperCharger object and constructs a GUI
 * that handles the visual representation of charging an electric vehicle
 * at a charging station.
 * 
 * @author Tidomann
 *
 */
@SuppressWarnings("serial")
public class GUI extends JFrame {

	//data fields
	private JPanel userInfo;
	
	private JPanel account;
	private JLabel accntNamelbl;
	private JLabel accntName;
	private JLabel accntIDlbl;
	private JLabel accntID;
	private JLabel accntBalancelbl;
	private JLabel accntBalance;

	private JPanel vehicle;
	private JLabel vehicleMakelbl;
	private JLabel vehicleMake;
	private JLabel vehicleModellbl;
	private JLabel vehicleModel;;
	private JLabel vehicleChargelbl;
	private JLabel vehicleCharge;

	private JPanel loginPanel;
	private JLabel loginlbl;
	private JTextField loginField;
	private JButton logoutbtn;

	private JPanel chargePanel;
	private JLabel statuslbl;
	private JLabel etalbl;
	private JLabel costlbl;
	private JToggleButton chargeToggle;
	private JProgressBar progressBar;

	private SuperCharger main = new SuperCharger();
	private chargeControl charger; //Separate object to allow multiple threading

	private DecimalFormat df2 = new DecimalFormat("#.00"); //DecimalFormat object to control displays double values

	private Account activeAccount;

	private long totalTime;
	private long startTime;
	private long currentTime;

	private double chargePct;
	private double cost;

	private final double ENERGY_RATE = 0.130; //variable that controls the cost of energy (Ontario Average is $0.130 per KWH)

	public GUI() {
		// Create Widgets
		userInfo = new JPanel();

		account = new JPanel();
		accntNamelbl = new JLabel("Name: ");
		accntName = new JLabel("");
		accntIDlbl = new JLabel("Account ID: ");
		accntID = new JLabel("");
		accntBalancelbl = new JLabel("Balance: ");
		accntBalance = new JLabel("");

		vehicle = new JPanel();
		vehicleMakelbl = new JLabel("Make: ");
		vehicleMake = new JLabel("");
		vehicleModellbl = new JLabel("Model: ");
		vehicleModel = new JLabel("");
		vehicleChargelbl = new JLabel("Charge: ");
		vehicleCharge = new JLabel("");

		loginPanel = new JPanel();
		loginlbl = new JLabel("Account Number: ");
		loginField = new JTextField();
		logoutbtn = new JButton("Logout");

		chargePanel = new JPanel();
		statuslbl = new JLabel("Status: Idle");
		etalbl = new JLabel("ETA: N/A");
		costlbl = new JLabel("Cost:");
		chargeToggle = new JToggleButton("Charge");
		progressBar = new JProgressBar();

		// widget settings
		userInfo.setVisible(false);

		account.setBorder(new TitledBorder(null, "Your Account", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		vehicle.setBorder(new TitledBorder(null, "Your Vehicle", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		loginlbl.setBounds(351, 135, 99, 14);

		loginField.setToolTipText("Enter Account Number");
		loginField.setBounds(351, 160, 96, 20);

		logoutbtn.setBounds(351, 191, 96, 23);
		logoutbtn.setVisible(false);

		chargePanel.setVisible(false);
		chargePanel.setBorder(new TitledBorder(null, "Battery", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// label alignment and font settings
		Font boldDefault = new Font("Tahoma", Font.BOLD, 11);
		accntNamelbl.setFont(boldDefault);
		accntNamelbl.setHorizontalAlignment(SwingConstants.RIGHT);

		accntIDlbl.setFont(boldDefault);
		accntIDlbl.setHorizontalAlignment(SwingConstants.RIGHT);

		accntBalancelbl.setFont(boldDefault);
		accntBalancelbl.setHorizontalAlignment(SwingConstants.RIGHT);

		vehicleMakelbl.setFont(boldDefault);
		vehicleMakelbl.setHorizontalAlignment(SwingConstants.RIGHT);

		vehicleModellbl.setFont(boldDefault);
		vehicleModellbl.setHorizontalAlignment(SwingConstants.RIGHT);

		vehicleChargelbl.setFont(boldDefault);
		vehicleChargelbl.setHorizontalAlignment(SwingConstants.RIGHT);

		loginlbl.setFont(boldDefault);

		// set panel layouts
		getContentPane().setLayout(new BorderLayout(0, 0));

		userInfo.setLayout(new GridLayout(0, 2, 0, 0));
		account.setLayout(new GridLayout(0, 2, 0, 0));
		vehicle.setLayout(new GridLayout(0, 2, 0, 0));

		loginPanel.setLayout(null); // absolute layout

		chargePanel.setLayout(new GridLayout(0, 3, 0, 0));

		// place panels and widgets
		add(userInfo, BorderLayout.NORTH);

		userInfo.add(account);

		account.add(accntNamelbl);
		account.add(accntName);
		account.add(accntIDlbl);
		account.add(accntID);
		account.add(accntBalancelbl);
		account.add(accntBalance);

		userInfo.add(vehicle);

		vehicle.add(vehicleMakelbl);
		vehicle.add(vehicleMake);
		vehicle.add(vehicleModellbl);
		vehicle.add(vehicleModel);
		vehicle.add(vehicleChargelbl);
		vehicle.add(vehicleCharge);

		add(loginPanel, BorderLayout.CENTER);

		loginPanel.add(loginlbl);
		loginPanel.add(loginField);
		
		loginPanel.add(logoutbtn);

		add(chargePanel, BorderLayout.SOUTH);

		chargePanel.add(statuslbl);
		chargePanel.add(etalbl);
		chargePanel.add(costlbl);
		chargePanel.add(chargeToggle);
		chargePanel.add(progressBar);

		// Action Events
		/*
		 * This action event simulates the arrival of a vehicle. Currently a valid account
		 * id must be entered. With a real charging station, account may be automatically
		 * detected upon vehicle arrival.
		 */
		loginField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					int key = Integer.parseInt(loginField.getText()); //parse the int from the textField

					if (key < 0 || key > 999999999) { //if the ID is negative or greater than our bounds, throw error
						throw new NumberFormatException("ID out of bounds");
					}

					try {
						activeAccount = main.login(key); //set account to gathered account from TreeMap data structure
						//Update the GUI elements to reflect the active Account
						loginlbl.setVisible(false);
						loginField.setVisible(false);
						loginField.setText("");
						accntName.setText(activeAccount.getOwner().getName());
						accntID.setText(Integer.toString(activeAccount.getId()));
						accntBalance.setText("$" + df2.format(activeAccount.getBalance()));

						vehicleMake.setText(activeAccount.getVehicles().getMake());
						vehicleModel.setText(activeAccount.getVehicles().getModel());
						chargePct = main.getChargePct();
						vehicleCharge.setText(df2.format(chargePct) + "%");
						progressBar.setValue((int) chargePct);

						userInfo.setVisible(true);
						chargePanel.setVisible(true);
						logoutbtn.setVisible(true);
						
					} catch (NoSuchElementException g) {
						// g.printStackTrace();
						JOptionPane.showMessageDialog(null, "That Account does not exist.", "Invalid Account ID",
								JOptionPane.WARNING_MESSAGE);
					}

				} catch (NumberFormatException f) {
					// n.printStackTrace();
					JOptionPane.showMessageDialog(null, "The Account ID entered is not a valid number.",
							"Invalid Account ID", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		/*
		 * This action event simulates the departure of a vehicle. It will discontinue
		 * the charging process which will handle any account deductions by toggling the jToggleButton.
		 * With a real charging station, logout may be automatically handled by vehicle departing.
		 */
		logoutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent l) {

				chargeToggle.setSelected(false);
				activeAccount = null;
				
				//Clear and Update GUI elements
				logoutbtn.setVisible(false);
				accntName.setText("");
				accntID.setText("");
				accntBalance.setText("");

				vehicleMake.setText("");
				vehicleModel.setText("");
				chargePct = 0;
				vehicleCharge.setText("");
				
				etalbl.setText("ETA: N/A");
				costlbl.setText("Cost:");
				

				userInfo.setVisible(false);
				chargePanel.setVisible(false);
				logoutbtn.setVisible(false);
				loginlbl.setVisible(true);
				loginField.setVisible(true);

			}
		});
		
		/*
		 * The toggling of the chargeToggle jToggleButton handles the start of the charging process,
		 * and the end of the charging process. This is handled in another thread to allow the Java GUI
		 * to remain open to interaction from the user.
		 * 
		 * Toggling to deselected discontinues the charging process, and deducts funds from the active
		 * account for the energy used, and updates the relevant GUI objects.
		 */
		chargeToggle.addItemListener(new ItemListener() {
			Thread t;
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					//System.out.println("Charging Started");
					charger = new chargeControl(); //create new chargecontrol object, setting charge logic to true
					t = new Thread(charger);
					t.start();

				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					//System.out.println("Charging Stopped.");
					charger.stopCharge();
					charger = null;
					statuslbl.setText("Status: Idle");
					main.activeAccount.chargeAccount(cost);
					accntBalance.setText("$" + df2.format(activeAccount.getBalance()));
					t = null;

				}

			}
		});

		/*
		 * By overriding the default windows action- we can set the program to update the relevant
		 * accountData file by calling the SuperCharger shutdown method. This allows multiple accounts
		 * to use the GUI and only writes the Account Data once the charging station is shut down.
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				chargeToggle.setSelected(false);
				main.shutdown();
				System.exit(0);
			}
		});

		// GUI behaviors
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // this is what the x button
		setSize(800, 600); // default size of GUI in pixels, width,height
		setTitle("Kinglsey Charging Station"); // title on the Windows
		setResizable(false);
		setVisible(true); // can see the GUI

	}

	/**
	 * Main method launches the application.
	 * Prepares a separate thread so the GUI can be interacted with
	 * when the program is in a loop to charge the vehicle.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
			}
		});
	}

	/**
	 * This innerclass object handles the charging of a vehicle in a seperate thread.
	 * This way the loop can continue to run without locking the GUI, the method
	 * updates the relevant GUI elements for the user.
	 * @author Tidomann
	 *
	 */
	public class chargeControl implements Runnable {
		
		//Inner Object Data Fields
		private AtomicBoolean keepCharging;

		/**
		 * The chargeControl constructor sets the charging state to true upon construction
		 */
		public chargeControl() {
			keepCharging = new AtomicBoolean(true);
		}

		/**
		 * The stopCharge method can be called to stop the charging logic from running.
		 * By making it a method we can call the discontinuation of the charging logic
		 * from other areas of the program.
		 */
		public void stopCharge() {
			keepCharging.set(false);
		}
		
		/**
		 * Override of the run() method implemented from Runnable. Must be defined in
		 * an object using seperate threads.
		 * The method controls the charging of a Vehicle in the account object by determining the
		 * elapsed time, and adding charge to the battery of the vehicle based on the charge rate and
		 * time elapsed. It then determines the cost of that energy based on how much energy has passed
		 * to the vehicle.
		 * This method constantly calculates the time elapsed using the System.nanoTime()
		 * shown in class to determine how much time has passed. By updating the time at the end of the loop
		 * we can account for the time it took to update the GUI until the next iteration.
		 * Charge rate is assumed to be constant according to the battery of the vehicle.
		 */
		@Override
		public void run() {
			//reset GUI data fields
			totalTime = 0;
			cost = 0;
			startTime = System.nanoTime();
			statuslbl.setText("Status: Charging");
			/*
			 * While the cost does not exceed account balance, and while the vehicle can still be charged,
			 * and while the chargeControl boolean is still true...
			 */
			while (cost < main.activeAccount.getBalance() && main.activeAccount.getVehicles().getBattery()
					.getCharge() < main.activeAccount.getVehicles().getBattery().getTotal() && keepCharging.get()) {
				
				currentTime = System.nanoTime();
				
				/*
				 * Calculates the cost based on the set energy rate and how much charge goes to the vehicle during the
				 * elapsed time (converts nanoseconds to hours).
				 */
				cost += ENERGY_RATE * ((currentTime - startTime) / 3600000000000.0)
						* main.activeAccount.getVehicles().getChargeRate();
				
				main.activeAccount.getVehicles().charge(currentTime - startTime); //add charge to the vehicle based on the elapsed time
				

				//Update GUI elements
				costlbl.setText("Cost: $" + df2.format(cost));
				chargePct = main.getChargePct();
				vehicleCharge.setText(df2.format(chargePct) + "%");
				progressBar.setValue((int) chargePct); //setValue requires int parameter, typecast double percentage (will always round down)
				totalTime += currentTime - startTime;
				etalbl.setText("ETA: " + df2.format(main.getETA()) + " minutes");
				startTime = currentTime;
				//Debug
				//System.out.println("Completed loop " + cost);
				//System.out.println(main.activeAccount.toString());
				//System.out.println(main.activeAccount.getVehicles().getChargeRate());
			}
			chargeToggle.setSelected(false); //If loop logic fails, discontinue charging by toggling the GUI element
		}
	}
}
