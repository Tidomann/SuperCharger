package superCharger;
/**
 * The Owner object  represents the owner of an account
 * The object stores information that represents the owner.
 * @author Tidomann
 *
 */
public class Owner {
	//data fields
	private String name;
	private String address;
	private String phoneNumber;

	/**
	 * Accessor for the Owner name
	 * @return the name of the Owner
	 */
	public String getName() {
		return name;
	}

	/**
	 * Accessor for the Owner Address
	 * @return the address of the Owner
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Accessor for the Owner Phone Number
	 * @return the phone number of the Owner
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Method to modify the name of an owner
	 * @param newName The new name of the account Owner
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Method to modify the Address of an owner
	 * @param newAddress The new address of the account Owner
	 */
	public void setAddress(String newAddress) {
		address = newAddress;
	}

	/**
	 * Argumented Constructor for the Owner Object to
	 * initialize all data fields
	 * @param name the name of the Account Owner
	 * @param address the address of the Account Owner
	 * @param phoneNumber the phoneNumber of the Account Owner
	 */
	public Owner(String name, String address, String phoneNumber) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Overridden toString Method to print the Owner Object as a
	 * string that can be read by the SuperCharger parser.
	 */
	@Override
	public String toString() {
		return ("Owner=" + name + ",Address=" + address + ",Phone Number=" + phoneNumber);
	}
}
