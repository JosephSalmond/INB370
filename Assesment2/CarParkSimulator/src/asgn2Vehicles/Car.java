package asgn2Vehicles;

import asgn2Exceptions.VehicleException;

/** @author Joseph Salmond 8823928 */
public class Car extends Vehicle {
    boolean small;

    public Car(String vehID, int arrivalTime, boolean small)
	    throws VehicleException {
	super(vehID, arrivalTime);
	this.small = small;
	// TODO Auto-generated constructor stub
	// small = indicator whether car is regarded as small or not
	// VehicleException - if arrivalTime is <= 0
    }

    // Boolean status indicating whether car is small enough for small car
    // parking spaces
    public boolean isSmall() {
	return small;
    }

    // Overrides toString in class Vehicle
    // Overrides toString in class java.lang.Object
    @Override
    public String toString() {
	String endl = System.getProperty("line.separator");

	String stringQueue;
	if (wasQueued) {
	    stringQueue = "Exit ffrom Queue: " + queueTime + endl
		    + " Queuing Time " + (queueTime - arrivalTime) + endl;
	} else {
	    stringQueue = "Vehicle was not queued";
	}

	String stringPark;
	if (wasParked) {
	    stringPark = "Entry to Car Park: " + parkingTime + endl
		    + "Exit from Car Park: " + departureTime + endl
		    + "Parking Time: " + (departureTime - arrivalTime) + endl;
	} else {
	    stringPark = "Vehicle was not parked";
	}

	String stringSatisfied;
	if (satisfied) {
	    stringSatisfied = "Customer was satisfied" + endl;
	} else {
	    stringSatisfied = "Customer was not satisfied" + endl;
	}

	String stringSmall;
	if (this instanceof Car) {
	    if (this.isSmall()) {
		stringSmall = "Car can use small parking space" + endl;
	    } else {
		stringSmall = "Car cannot use small parking space" + endl;
	    }
	} else {
	    stringSmall = "";
	}

	String str = "Vehicle vehID: " + vehID + endl + "Arrival Time: "
		+ arrivalTime + endl + stringQueue + stringPark
		+ stringSatisfied + stringSmall;

	return str;
    }
}
