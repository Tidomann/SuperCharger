package superCharger;

/**
 * The Battery object represents the battery of a vehicle
 * The object stores information that represents the Battery and
 * methods representing actions on and for the battery.
 * @author Tidomann
 *
 */
public class Battery {
	//data fields
	private double charge;
	private double totalCharge;
	private double chargeRate;
	
	/**
	 * Argumented Constructor for the Battery object
	 * Initializes the object with the passed parameters.
	 * 
	 * @param totalCharge the charge capacity of the batter (KWh)
	 * @param chargeRate the charge rate of the battery (KW)
	 */
	public Battery(double totalCharge, double chargeRate) {
		this.totalCharge = totalCharge;
		this.chargeRate = chargeRate;
	}

	/**
	 * Accessor for the Battery charge
	 * @return the current charge of the battery (KWh)
	 */
	public double getCharge() {
		return charge;
	}

	/**
	 * Accessor for the Battery Capacity
	 * @return the total charge capacity of the battery (KWh)
	 */
	public double getTotal() {
		return totalCharge;
	}

	/**
	 * Method to calculate the difference between the battery capacity and
	 * the current charge of the battery
	 * @return the calculated difference (KWh)
	 */
	public double getRemaining() {
		return totalCharge - charge;
	}

	/**
	 * Accessor for the Battery Charge Rate
	 * @return the total charge rate of the battery (KW)
	 */
	public double getChargeRate() {
		return chargeRate;
	}

	/**
	 * Method to modify the charge of the battery
	 * @param currentCharge the value to be set to (KWh)
	 */
	public void setCharge(double currentCharge) {
		charge = currentCharge;
	}

	/**
	 * Overridden toString Method to print the Battery Object as a
	 * string that can be read by the SuperCharger parser.
	 */
	@Override
	public String toString() {
		return ("Charge=" + charge + ",totalCharge=" + totalCharge + ",chargeRate=" + chargeRate);
	}
}
