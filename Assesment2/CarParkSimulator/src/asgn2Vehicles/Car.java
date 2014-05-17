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
    @Override
    public String toString() {
	return " ";
    }

}
