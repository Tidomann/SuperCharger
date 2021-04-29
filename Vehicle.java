package superCharger;

/**
 * The abstract Vehicle object represents a vehicle on an account
 * The object stores information and objects that represents the Vehicle.
 * @author Tidomann
 *
 */
abstract class Vehicle {
	//data fields
	private String make;
	private String model;
	private Battery battery;
	
	/**
	 * Argumented Constructor for the vehicle object
	 * Initializes the object with the passed parameters.
	 * 
	 * @param make The make of the vehicle.
	 * @param model The model of the vehicle
	 * @param totalCharge The total charge capacity of the vehicle (KWh)
	 * @param chargeRate The chargerate of the vehicle's battery (KW)
	 */
	public Vehicle(String make, String model, double totalCharge, double chargeRate) {
		this.make = make;
		this.model = model;
		battery = new Battery(totalCharge, chargeRate);
	}
	
	/**
	 * Copy Constructor for the vehicle object
	 * Performs a deep copy of the vehicle object by copying data fields
	 * and values in the vehicles battery.
	 * 
	 * @param inVehicle the vehicle to be copied
	 */
	public Vehicle(Vehicle inVehicle) {
		this.make = inVehicle.getMake();
		this.model = inVehicle.getModel();
		this.battery = new Battery(inVehicle.getBattery().getTotal(), inVehicle.getBattery().getChargeRate());
	}

	/**
	 * Accessor for the Vehicle make
	 * @return the name of the Vehicle
	 */
	public String getMake() {
		return make;
	}

	/**
	 * Accessor for the Vehicle model
	 * @return the model of the Vehicle
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Accessor for the Vehicle Battery
	 * @return the battery object of the Vehicle
	 */
	public Battery getBattery() {
		return battery;
	}
	
	/**
	 * Accessor for the Vehicle chargerate calls upon the getChargeRate()
	 * method of the battery
	 * @return the chargerate of the battery
	 */
	public double getChargeRate() {
		return this.battery.getChargeRate();
	}
	
	/**
	 * Method for the Vehicle Battery Charge Percent
	 * Calculates the charge as a percentage value based on the values of the battery
	 * @return the charge of the battery represented as a percentage
	 */
	public double getChargePct() {
		return this.battery.getCharge() / this.battery.getTotal() * 100.0;
	}
	
	/**
	 * Method for the Vehicle Estimated Charge Time
	 * Calculates the remaining time required to charge the vehicle
	 * based on the charge remaining and the charge rate
	 * @return The time remaining until full charge in minutes
	 */
	public double getETA() {
		return this.getBattery().getRemaining() / (this.getChargeRate()) * 60;
	}

	/**
	 * Overridden toString Method to print the Vehicle Object as a
	 * string that can be read by the SuperCharger parser.
	 */
	@Override
	public String toString() {
		return ("Make=" + make + ",Model=" + model + "," + battery.toString());
	}

	/**
	 * Abstract Method Charge will apply charge to the battery based
	 * on the vehicle's properties and an elapsed time
	 * @param time the time elapsed in nanoseconds
	 */
	public abstract void charge(long time);
}
