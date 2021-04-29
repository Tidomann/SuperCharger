package superCharger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The SuperCharger object handles the charging and management of account object
 * data for the charging station. By handling the account object, it also functions in the
 * handling of the account owner object, and vehicle object tied to the account.
 * @author Tidomann
 *
 */
public class SuperCharger {

	//Data Fields
	TreeMap<Integer, Account> accounts = new TreeMap<Integer, Account>();
	Account activeAccount;

	/**
	 * SuperCharger Constructor
	 * Generates a SuperCharger object by populating the TreeMap with accounts
	 * by parsing the accountData text file for account information. Will remove
	 * invalid accounts and print them to a separate invalidAccounts text file.
	 */
	public SuperCharger() {
		try {
			File accountData = new File("accountData.txt");
			Scanner dataIn = new Scanner(accountData);
			while (dataIn.hasNext()) {
				String line = dataIn.nextLine();
				String debug = line;
				try {
					int id, vehicleType;
					double balance, charge, totalCharge, chargeRate, superchargeRate;
					line = line.substring(line.indexOf('=') + 1);
					try {
						id = Integer.parseInt(line.substring(0, line.indexOf(',')));
					} catch (NumberFormatException n) {
							System.out.println("Invalid Account ID written to logs");
						// n.printStackTrace();
						invalidAccount(debug);
						continue;
					}
					
					line = line.substring(line.indexOf('=') + 1);
					String owner = line.substring(0, line.indexOf(','));
					
					line = line.substring(line.indexOf('=') + 1);
					String address = line.substring(0, line.indexOf(','));
					
					line = line.substring(line.indexOf('=') + 1);
					String phoneNumber = line.substring(0, line.indexOf(','));
					
					line = line.substring(line.indexOf('=') + 1);
					try {
						balance = Double.parseDouble(line.substring(0, line.indexOf(',')));
					}catch (NumberFormatException n) {
						System.out.println("Invalid Account Balance written to logs");
						// n.printStackTrace();
						invalidAccount(debug);
						continue;
					}
					
					line = line.substring(line.indexOf('=') + 1);
					try {
						vehicleType = Integer.parseInt(line.substring(0, line.indexOf(',')));
					}catch (NumberFormatException n) {
						System.out.println("Invalid Vehicle Type written to logs");
						// n.printStackTrace();
						invalidAccount(debug);
						continue;
					}
					
					line = line.substring(line.indexOf('=') + 1);
					String make = line.substring(0, line.indexOf(','));
					
					line = line.substring(line.indexOf('=') + 1);
					String model = line.substring(0, line.indexOf(','));
					
					line = line.substring(line.indexOf('=') + 1);
					try {
						charge = Double.parseDouble(line.substring(0, line.indexOf(',')));
					}catch (NumberFormatException n) {
						System.out.println("Invalid Charge written to logs");
						// n.printStackTrace();
						invalidAccount(debug);
						continue;
					}
					
					
					line = line.substring(line.indexOf('=') + 1);
					try {
						totalCharge = Double.parseDouble(line.substring(0, line.indexOf(',')));
						if (totalCharge < charge)
							throw new NumberFormatException("Total charge less than current charge");
					}catch (NumberFormatException n) {
						System.out.println("Invalid Total Charge written to logs");
						// n.printStackTrace();
						invalidAccount(debug);
						continue;
					}

					if (vehicleType == 1) {
						line = line.substring(line.indexOf('=') + 1);
						try {
							chargeRate = Double.parseDouble(line.substring(0, line.indexOf(',')));
						}catch (NumberFormatException n) {
							System.out.println("Invalid Charge Rate written to logs");
							// n.printStackTrace();
							invalidAccount(debug);
							continue;
						}

						line = line.substring(line.lastIndexOf('=') + 1, line.length() - 1);
						try {
							superchargeRate = Double.parseDouble(line);
						}catch (NumberFormatException n) {
							System.out.println("Invalid Super Charge Rate written to logs");
							// n.printStackTrace();
							invalidAccount(debug);
							continue;
						}
						
						Account temp = new Account(id, new Owner(owner, address, phoneNumber), balance,
								new SuperCar(make, model, totalCharge, chargeRate, superchargeRate));
						temp.getVehicles().getBattery().setCharge(charge);
						
						if(!accounts.containsKey(id))
							accounts.put(id, temp);
						else {
							System.out.println("Duplicate Account ID written to logs");
							invalidAccount(debug);
							continue;
						}
					} else { //can change to if else when new vehicle objects introduced
						
						line = line.substring(line.lastIndexOf('=') + 1, line.length() - 1);
						try {
							chargeRate = Double.parseDouble(line);
						}catch (NumberFormatException n) {
							System.out.println("Invalid Charge Rate written to logs");
							// n.printStackTrace();
							invalidAccount(debug);
							continue;
						}
						
						Account temp = new Account(id, new Owner(owner, address, phoneNumber), balance,
								new Car(make, model, totalCharge, chargeRate));
						temp.getVehicles().getBattery().setCharge(charge);
						
						if(!accounts.containsKey(id))
							accounts.put(id, temp);
						else{
							System.out.println("Duplicate Account ID written to logs");
							invalidAccount(debug);
							continue;
						}
					}
					
				} catch (StringIndexOutOfBoundsException r) {
					System.out.println("Account Missing Data written to logs.");
					//r.printStackTrace();
					invalidAccount(debug);
					continue;
				}
			}//end while loop
			
			dataIn.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Invalid Account Data File");
			//e.printStackTrace();
		}
	}//end constructor

	/**
	 * The Login method fetches the account tied to the int id parameter
	 * and sets it as the activeAccount.
	 * @param key the integer id
	 * @return Returns the account object tied to the parameter id key
	 * @throws NoSuchElementException an account is not tied to that id in the data structure
	 */
	public Account login(int key) throws NoSuchElementException {
		if (accounts.containsKey(key)) {
			activeAccount = accounts.get(key);
			return activeAccount;
		}

		else
			throw new NoSuchElementException("Invalid ID");
	}

	/**
	 * The shutdown method iterates through the valid accounts within the  TreeMap data structure
	 * and writes the account data to the accountData.txt with any modified data.
	 */
	public void shutdown() {
		File accountData = new File("accountData.txt");
		try {
			PrintWriter pw = new PrintWriter(accountData);
			accounts.forEach((key, value) -> {
				pw.println(value.toString());
			});
			pw.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Unable to write to data file on shutdown");
		}
	}

	/**
	 * The getChargePct method evaluates the current charge of the vehicle's battery as a
	 * percent value of the total charge capacity of the vehicle's battery.
	 * @return the charge percentage represented as a double
	 * @throws NoSuchElementException Thrown if there is no active account or no vehicle tied to the account
	 */
	public double getChargePct() throws NoSuchElementException {
		if (activeAccount == null)
			throw new NoSuchElementException("No Account Loaded");
		if (activeAccount.getVehicles() == null)
			throw new NoSuchElementException("No Vehicle Assigned to Account");
		return activeAccount.getVehicles().getChargePct();
	}
	
	/**
	 * The getETA method calls the vehicles getETA() method which evaluates 
	 * the time it would take to reach 100% charge. By calling this method, added checks
	 * for existing activeAccount and assigned Vehicles are performed.
	 * @return The time remaining in minutes until 100% charge.
	 * @throws NoSuchElementException Thrown if there is no active account or no vehicle tied to the account
	 */
	public double getETA() throws NoSuchElementException{
		if (activeAccount == null)
			throw new NoSuchElementException("No Account Loaded");
		if (activeAccount.getVehicles() == null)
			throw new NoSuchElementException("No Vehicle Assigned to Account");
		return activeAccount.getVehicles().getETA();
	}
	
	/**
	 * The invalid Account method is used when an invalid account is read from the accountData
	 * text file. It will print the passed string to the invalidAccount.txt file for review.
	 * @param line The line representing the invalid account.
	 */
	private void invalidAccount(String line) {
		try {
			File invalidAccounts = new File("invalidAccounts.txt");
			PrintWriter pw = new PrintWriter(new FileOutputStream(invalidAccounts), true);
			pw.println(line);
			pw.close();
		}catch (FileNotFoundException e) {
			System.out.println("Unable to generate invalid account data");
			//e.printStackTrace();
		}
		
	}

}
