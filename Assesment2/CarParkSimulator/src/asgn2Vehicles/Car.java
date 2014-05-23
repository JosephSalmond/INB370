package asgn2Vehicles;

import asgn2Exceptions.VehicleException;

/** @author Joseph Salmond 8823928 */
public class Car extends Vehicle {
    boolean small;

    /**
     * Constructs Car
     * 
     * @author Joseph Salmond 8823928
     * @param vehID
     * @param arrivalTime
     * @param small
     * @throws VehicleException
     */
    public Car(String vehID, int arrivalTime, boolean small)
	    throws VehicleException {
	super(vehID, arrivalTime);
	if (arrivalTime <= 0) {
	    throw new VehicleException("Car: Does Not Have A Engine");
	}
	this.small = small;

    }

    /**
     * Boolean status indicating whether car is small car
     * 
     * @author Joseph Salmond 8823928
     * @return Small
     */
    public boolean isSmall() {
	return small;
    }

    /**
     * Overrides toString in class Vehicle Overrides toString in class
     * java.lang.Object
     * 
     * @author Joseph Salmond 8823928
     */
    @Override
    public String toString() {

	// For Compatibility purposes & readability
	String endl = System.getProperty("line.separator");

	String stringQueue;
	String stringPark;
	String stringSatisfied;
	String stringSmall;

	// Create Queue String
	if (wasQueued) {
	    stringQueue = "Exit ffrom Queue: " + queueTime + endl
		    + " Queuing Time " + (queueTime - arrivalTime) + endl;
	} else {
	    stringQueue = "Vehicle was not queued";
	}

	// Create Parking String
	if (wasParked) {
	    stringPark = "Entry to Car Park: " + parkingTime + endl
		    + "Exit from Car Park: " + departureTime + endl
		    + "Parking Time: " + (departureTime - arrivalTime) + endl;
	} else {
	    stringPark = "Vehicle was not parked";
	}

	// Create Satisfaction String
	if (this.isSatisfied()) {
	    stringSatisfied = "Customer was satisfied" + endl;
	} else {
	    stringSatisfied = "Customer was not satisfied" + endl;
	}

	// Create Small String
	if (this instanceof Car) {
	    if (this.isSmall()) {
		stringSmall = "Car can use small parking space" + endl;
	    } else {
		stringSmall = "Car cannot use small parking space" + endl;
	    }
	} else {
	    stringSmall = "";
	}

	// Tie them all together
	String str = "Vehicle vehID: " + vehID + endl + "Arrival Time: "
		+ arrivalTime + endl + stringQueue + stringPark
		+ stringSatisfied + stringSmall;

	return str;
    }
}
