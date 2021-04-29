package superCharger;

/**
 * The account object is an object that can be stored in a data structure.
 * It represents an account at our charging station.
 * @author Tidomann
 *
 */
public class Account {

	//Account Data Fields
	private int id;
	private Owner accntOwner;
	private double balance;
	private Vehicle vehicle;

	/**
	 * The getID method returns the account ID represented by an integer
	 * @return the int account id
	 */
	public int getId() {
		return id;
	}

	/**
	 * The getOwner method returns the Owner object representing the account owner.
	 * @return the account owner Owner object
	 */
	public Owner getOwner() {
		return accntOwner;
	}

	/**
	 * The getBalance method returns the account balance represented by a double
	 * @return the account balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * The chargeAccount method deducts the balance of the account
	 * by an amount dictated by the passed parameter.
	 * @param amount a double dictating the amount to be deducted
	 */
	protected void chargeAccount(double amount) {
		balance -= amount;
	}

	/**
	 * The getVehicles method returns the Vehicle object representing the vehicle
	 * tied to the account.
	 * @return the vehicle object tied to the account.
	 */
	public Vehicle getVehicles() {
		return vehicle;
	}

	/**
	 * Argumented account constructor
	 * @param id the integer id to set the account to
	 * @param owner the owner object tied to the account
	 * @param balance the balance to set the account to
	 * @param vehicle the vehicle object to be tied to the account 
	 */
	public Account(int id, Owner owner, double balance, Vehicle vehicle) {
		this.id = id;
		this.accntOwner = owner;
		this.balance = balance;
		this.vehicle = vehicle;
	}	
	
	/**
	 * Overridden toString method to print relevants account information into a form that can
	 * be parsed by the SuperCharge object to maintain account information.
	 */
	@Override
	public String toString() {
		return ("{ID=" + id + "," + accntOwner.toString() + ",Balance=" + balance + "," + vehicle.toString() + "}");
	}
}
