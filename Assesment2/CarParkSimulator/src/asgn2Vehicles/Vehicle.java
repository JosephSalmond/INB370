package asgn2Vehicles;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;

/** @author Joseph Salmond 8823928 */
public abstract class Vehicle {

	boolean parked;
	boolean queued;
	boolean satisfied;
	boolean wasParked;
	boolean wasQueued;

	int arrivalTime;
	int departureTime;
	int parkingTime;
	int intendedDuration;

	String vehID;

	// Vehicle Constructor
	public Vehicle(String vehID, int arrivalTime) throws VehicleException {
		if (arrivalTime <= 0) {
			throw new VehicleException(
					"Vehicle: Cannot arrive before you arrive");
		}
		this.vehID = vehID;
		this.arrivalTime = arrivalTime;
		this.departureTime = 0;
		this.satisfied = false;

		// VehicleException - if arrivalTime is <= 0
	}

	// Transition vehicle to parked state
	public void enterParkedState(int parkingTime, int intendedDuration)
			throws VehicleException {
		if (parked || queued) {
			throw new VehicleException("enterParkedState: cannot be in two places at once");
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

		// VehicleException - if the vehicle is already in a parked or queued
		// state, if parkingTime < 0, or if intendedDuration is less than the
		// minimum prescribed in asgnSimulators.Constraints

	}

	// Transition vehicle to queued state
	public void enterQueuedState() throws VehicleException {
		
		if (parked || queued) {
			throw new VehicleException("enterParkedState: cannot be in two places at once");
		}
		
		queued = true;
		
		// VehicleException - if the vehicle is already in a queued or parked
		// state

	}

	// Transition vehicle from parked state
	public void exitParkedState(int departureTime) throws VehicleException {
		if (!parked || queued || (departureTime < parkingTime)){
			throw new VehicleException("exitParkedState: ");
		}
		
		
		parked = false;
		wasParked = true;
		satisfied = true;
		// VehicleException - if the vehicle is not in a parked state, is in a
		// queued state or if the revised departureTime < parkingTime

	}

	// Transition vehicle from queued state
	public void exitQueuedState(int exitTime) throws VehicleException {
		
		if (parked || !queued){
			throw new VehicleException("exitQueuedState: ");
		} else if (arrivalTime > exitTime){
			throw new VehicleException("exitQueuedState: ");
		}
		
		queued = false;
		wasQueued = true;
		// VehicleException - if the vehicle is in a parked state or not in a
		// queued state, or if exitTime is not later than arrivalTime for this
		// vehicle

	}

	// Simple getter for the arrival time
	public int getArrivalTime() {
		return arrivalTime;
	}

	// Simple getter for the departure time from the car park
	public int getDepartureTime() {
		return departureTime;
	}

	// Simple getter for the parking time
	public int getParkingTime() {
		return parkingTime;
	}

	// Simple getter for the vehicle ID
	public String getVehID() {
		return vehID;
	}

	// Boolean status indicating whether vehicle is currently parked
	public boolean isParked() {
		return parked;
	}

	// Boolean status indicating whether vehicle is currently queued
	public boolean isQueued() {
		return queued;
	}

	// Boolean status indicating whether customer is satisfied or not Satisfied
	// if they park
	public boolean isSatisfied() {
		return satisfied;
	}

	// Overrides toString in class java.lang.Object
	@Override
	public String toString() {

		return " Veehicular string";
	}

	// Boolean status indicating whether vehicle was ever parked
	public boolean wasParked() {
		return wasParked;
	}

	// Boolean status indicating whether vehicle was ever queued
	public boolean wasQueued() {
		return wasQueued;
	}
}
