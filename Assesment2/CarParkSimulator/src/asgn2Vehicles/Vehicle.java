package asgn2Vehicles;

import asgn2Exceptions.VehicleException;

/** @author Joseph Salmond 8823928 */
public abstract class Vehicle {

	// Vehicle Constructor
	public Vehicle(String vehID, int arrivalTime) throws VehicleException {
		// VehicleException - if arrivalTime is <= 0

	}

	// Transition vehicle to parked state
	public void enterParkedState(int parkingTime, int intendedDuration)
			throws VehicleException {
		// VehicleException - if the vehicle is already in a parked or queued
		// state, if parkingTime < 0, or if intendedDuration is less than the
		// minimum prescribed in asgnSimulators.Constraints

	}

	// Transition vehicle to queued state
	public void enterQueuedState() throws VehicleException {
		// VehicleException - if the vehicle is already in a queued or parked
		// state

	}

	// Transition vehicle from parked state
	public void exitParkedState(int departureTime) throws VehicleException {
		// VehicleException - if the vehicle is not in a parked state, is in a
		// queued state or if the revised departureTime < parkingTime

	}

	// Transition vehicle from queued state
	public void exitQueuedState(int exitTime) throws VehicleException {
		// VehicleException - if the vehicle is in a parked state or not in a
		// queued state, or if exitTime is not later than arrivalTime for this
		// vehicle

	}

	// Simple getter for the arrival time
	public int getArrivalTime() {
		int arrivalTime = 0; // Temp value
		return arrivalTime;
	}

	// Simple getter for the departure time from the car park
	public int getDepartureTime() {
		int departureTime = 0; // Temp value
		return departureTime;
	}

	// Simple getter for the parking time
	public int getParkingTime() {
		int parkingTime = 0; // Temp value
		return parkingTime;
	}

	// Simple getter for the vehicle ID
	public String getVehID() {
		String vehID = " "; // Temp value
		return vehID;
	}

	// Boolean status indicating whether vehicle is currently parked
	public boolean isParked() {
		boolean parked = false; // Temp value
		return parked;
	}

	// Boolean status indicating whether vehicle is currently queued
	public boolean isQueued() {
		boolean queued = false; // Temp value
		return queued;
	}

	// Boolean status indicating whether customer is satisfied or not Satisfied
	// if they park
	public boolean isSatisfied() {
		boolean satisfied = true; // Temp value, Oh Yeahhhh
		return satisfied;
	}

	// Overrides toString in class java.lang.Object
	@Override
	public String toString() {

		return "";
	}

	// Boolean status indicating whether vehicle was ever parked
	public boolean wasParked() {
		boolean beenThereDoneThat = true; // Temp value
		return beenThereDoneThat;
	}

	// Boolean status indicating whether vehicle was ever queued
	public boolean wasQueued() {
		boolean beenInHoldingPattern = false; // Temp value
		return beenInHoldingPattern;
	}
}