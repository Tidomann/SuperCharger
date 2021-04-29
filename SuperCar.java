package superCharger;

/**
 * An subclass that extends Vehicle that represents an electric car
 * that has the ability to use a higher charge rate until it reaches
 * 80 percent of it's batteries capacity.
 * @author Tidomann
 *
 */
public class SuperCar extends Vehicle {
	
	//data fields
	private double superchargeRate;
	
	/**
	 * Argumented Constructor for the SuperCar object
	 * Initializes the object with the passed parameters.
	 * 
	 * @param make The make of the vehicle.
	 * @param model The model of the vehicle
	 * @param totalCharge The total charge capacity of the vehicle (KWh)
	 * @param chargeRate The charge rate of the vehicle's battery (KW)
	 * @param superchargeRate the charge rate of the vehicle's battery when superchargine (KW)
	 */
	public SuperCar(String make, String model, double totalCharge, double chargeRate , double superchargeRate) {
		super(make, model, totalCharge, chargeRate);
		this.superchargeRate = superchargeRate;
	}
	
	/**
	 * Copy Constructor for the SuperCar object
	 * Performs a deep copy of the SuperCar object by copying data fields
	 * and values in the vehicles battery.
	 * 
	 * @param inVehicle the vehicle to be copied
	 */
	public SuperCar(SuperCar inVehicle) {
		super(inVehicle.getMake(), inVehicle.getModel(), inVehicle.getBattery().getTotal(), inVehicle.getBattery().getChargeRate());
		this.superchargeRate = inVehicle.superchargeRate;
	}

	
	/**
	 * Overridden abstract charge method for the SuperCar Object
	 * Utilizes the getChargeRate method from SuperCar to determine which chargeRate to use 
	 * Takes the parameter and calculates the amount of charge to apply to the vehicles 
	 * battery based on the appropriate chargeRate of the vehicle's battery.
	 * @param time the time elapsed in nanoseconds
	 */
	@Override
	public void charge(long time) {
		//Charge rate (KW) * time (h) = KWh
		double chargeDif = this.getChargeRate() * time / 3600000000000.0; //convert nanoseconds to hours
		this.getBattery().setCharge(this.getBattery().getCharge() + chargeDif);
	}
	
	
	/**
	 * Overridden getChargeRate method for the SuperCar Object
	 * This method determines which chargerate to utilize
	 * Supercar's use their superchargeRate until 80 percent battery.
	 * @return the charge rate depending on current charge of the battery (KW)
	 */
	@Override
	public double getChargeRate() {
		if (this.getChargePct() < 80.0) {
			return superchargeRate;
		}else {
			return this.getBattery().getChargeRate();
		}
	}
	
	/**
	 * Overridden getETA method for the SuperCar Object
	 * Calculates the remaining time required to charge the vehicle
	 * based on the charge remaining and the charge rate. The SuperCar
	 * must account for it's unique charge rate until it reaches 80 percent
	 * charge.
	 * @return the estimated time remaining until 100% charge in minutes
	 */
	@Override
	public double getETA() {
		if(this.getChargePct() < 80) {
			//calculate time required to reach 80 percent charge
			double eta = ((this.getBattery().getTotal()*0.8) - this.getBattery().getCharge()) / (this.superchargeRate);
			//add calculated time required for remaining 20 percent charge
			eta += ((this.getBattery().getTotal()*0.2) / (this.getBattery().getChargeRate()));
			return eta * 60; //return converted time in minutes (hours to minutes)
		}else {
			//if charge is greater than 80 percent superchargeRate is not used
			return this.getBattery().getRemaining() / (this.getBattery().getChargeRate()) * 60; //return converted time in minutes
		}
	}
	
	
	/**
	 * Overridden toString Method to print the SuperCar Object as a
	 * string that can be read by the SuperCharger parser.
	 */
	@Override
	public String toString() {
		return ("VehicleType=1,Make=" + this.getMake() + ",Model=" + this.getModel() + "," + this.getBattery().toString() + ",superchargeRate=" + this.superchargeRate);
	}

}
