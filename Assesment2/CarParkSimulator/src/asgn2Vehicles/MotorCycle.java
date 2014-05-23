package asgn2Vehicles;

import asgn2Exceptions.VehicleException;

/** @author Joseph Salmond 8823928 */
public class MotorCycle extends Vehicle {
    /**
     * @author Joseph Salmond 8823928 Constructs MotorCycle
     * @param vehID
     * @param arrivalTime
     * @throws VehicleException
     */
    public MotorCycle(String vehID, int arrivalTime) throws VehicleException {
	super(vehID, arrivalTime);
	if (arrivalTime <= 0) {
	    throw new VehicleException("MotorCycle: No Bikes For You");
	}

	// Nothing Special To Be Done Here
    }

}
