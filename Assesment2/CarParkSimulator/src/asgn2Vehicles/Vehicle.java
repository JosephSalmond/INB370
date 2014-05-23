package asgn2Vehicles;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;

/** @author Joseph Salmond 8823928 */
public abstract class Vehicle {

    boolean parked;
    boolean queued;
    boolean wasParked;
    boolean wasQueued;

    int arrivalTime;
    int departureTime;
    int parkingTime;
    int queueTime;
    int intendedDuration;

    String vehID;

    /**
     * Constructor for Vehicle
     * 
     * @author Joseph Salmond 8823928
     * @param vehID
     * @param arrivalTime
     * @throws VehicleException
     */
    public Vehicle(String vehID, int arrivalTime) throws VehicleException {
	if (arrivalTime <= 0) {
	    throw new VehicleException(
		    "Vehicle: Cannot arrive before you arrive");
	}
	// Set Starting Defaults
	this.vehID = vehID;
	this.arrivalTime = arrivalTime;
	this.departureTime = 0;
	this.queueTime = 0;
	this.parkingTime = 0;
	this.intendedDuration = 0;
	this.parked = false;
	this.queued = false;
	this.wasParked = false;
	this.wasQueued = false;
    }

    /**
     * Transition vehicle to parked state
     * 
     * @author Joseph Salmond 8823928
     * @param parkingTime
     * @param intendedDuration
     * @throws VehicleException
     */
    public void enterParkedState(int parkingTime, int intendedDuration)
	    throws VehicleException {

	if (parked || queued) {
	    throw new VehicleException(
		    "enterParkedState: cannot be in two places at once");
	} else if (parkingTime <= 0) {
	    throw new VehicleException("enterParkedState: Negitive Park");
	} else if (intendedDuration < Constants.DEFAULT_INTENDED_STAY_SD) {
	    throw new VehicleException(
		    "enterParkedState: you need to stay longer");
	}

	this.parkingTime = parkingTime;
	this.intendedDuration = intendedDuration;
	this.departureTime = parkingTime + intendedDuration;

	parked = true;
	wasParked = true;
    }

    /**
     * Transition vehicle to queued state
     * 
     * @author Joseph Salmond 8823928
     * @throws VehicleException
     */
    public void enterQueuedState() throws VehicleException {

	if (parked || queued) {
	    throw new VehicleException(
		    "enterQueuedState: cannot be in two places at once");
	}

	queued = true;
	wasQueued = true;
    }

    /**
     * Transition vehicle from parked state
     * 
     * @author Joseph Salmond 8823928
     * @param departureTime
     * @throws VehicleException
     */
    public void exitParkedState(int departureTime) throws VehicleException {
	if (!parked || queued || (departureTime < parkingTime)) {
	    throw new VehicleException("exitParkedState: ");
	}

	parked = false;
    }

    /**
     * Transition vehicle from queued state
     * 
     * @author Joseph Salmond 8823928
     * @param exitTime
     * @throws VehicleException
     */
    public void exitQueuedState(int exitTime) throws VehicleException {

	if (parked || !queued || (arrivalTime > exitTime)) {
	    throw new VehicleException("exitQueuedState: ");
	}

	queued = false;
	queueTime = exitTime;
    }

    /**
     * Simple getter for the arrival time
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public int getArrivalTime() {
	return arrivalTime;
    }

    /**
     * Simple getter for the departure time from the car park
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public int getDepartureTime() {
	return departureTime;
    }

    /**
     * Simple getter for the parking time
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public int getParkingTime() {
	return parkingTime;
    }

    /**
     * Simple getter for the vehicle ID
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public String getVehID() {
	return vehID;
    }

    /**
     * Boolean status indicating whether vehicle is currently parked
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public boolean isParked() {
	return parked;
    }

    /**
     * Boolean status indicating whether vehicle is currently queued
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public boolean isQueued() {
	return queued;
    }

    /**
     * Boolean status indicating whether customer is satisfied
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public boolean isSatisfied() {
	boolean satisfied = true;
	if (!wasParked || queueTime >= Constants.MAXIMUM_QUEUE_TIME) {
	    satisfied = false;
	}
	return satisfied;
    }

    // Overrides toString in class java.lang.Object
    /**
     * @author Joseph Salmond 8823928
     */
    @Override
    public String toString() {

	// For Compatibility purposes & readability
	String endl = System.getProperty("line.separator");

	String stringQueue;
	String stringPark;
	String stringSatisfied;

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

	// Tie them all together
	String str = "Vehicle vehID: " + vehID + endl + "Arrival Time: "
		+ arrivalTime + endl + stringQueue + stringPark
		+ stringSatisfied;

	return str;
    }

    /**
     * Boolean status indicating whether vehicle was ever parked
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public boolean wasParked() {
	return wasParked;
    }

    /**
     * Boolean status indicating whether vehicle was ever queued
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public boolean wasQueued() {
	return wasQueued;
    }
}
