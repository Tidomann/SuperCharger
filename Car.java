package superCharger;

/**
 * An subclass that extends Vehicle that represents an
 * standard electric cars
 * @author Tidomann
 *
 */
public class Car extends Vehicle {

	/**
	 * The subclass Constructor method calls upon the constructor method of vehicle
	 * to initialize the object with the passed parameters.
	 * 
	 * @param make The make of the vehicle.
	 * @param model The model of the vehicle
	 * @param totalCharge The total charge capacity of the vehicle (KWh)
	 * @param chargeRate The chargerate of the vehicle's battery (KW)
	 */
	public Car(String make, String model, double totalCharge, double chargeRate) {
		super(make, model, totalCharge, chargeRate);
	}

	
	/**
	 * Overridden abstract charge method. Takes the parameter and calculates the amount of
	 * charge to apply to the vehicle battery based on chargeRate of the vehicle's battery.
	 * @param time the time in nanoseconds
	 */
	@Override
	public void charge(long time) {
		//Charge rate (KW) * time (h) = KWh
		double chargeDif = this.getBattery().getChargeRate() * time / 3600000000000.0; //converts nanoseconds to hours
		if (chargeDif > this.getBattery().getRemaining()) {
			this.getBattery().setCharge(this.getBattery().getTotal());
		} else {
			this.getBattery().setCharge(this.getBattery().getCharge() + chargeDif);
		}

	}
	
	
	/**
	 * Overridden toString Method to print the Car Object as a
	 * string that can be read by the SuperCharger parser.
	 */
	@Override
	public String toString() {
		return ("VehicleType=0,Make=" + this.getMake() + ",Model=" + this.getModel() + "," + this.getBattery().toString());
	}

}
