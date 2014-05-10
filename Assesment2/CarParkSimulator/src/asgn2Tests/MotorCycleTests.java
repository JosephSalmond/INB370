package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;


/** @author Pearl Gariano 8318522 */

public class MotorCycleTests {
	
	MotorCycle testMotorCycle;
	
	private String testConstructorVehID = "123ABC";
	private int testConstructorArrivalTime = 1;
	
	private int testValueZero = 0;
	private int testValueNegative = -1;
	
	private int testIntendedDuration = 30;
	private int testParkingTime = 30;

	@Before
	public void setUp() throws Exception {
		new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
	}
	
	@Test(timeout=1000, expected = VehicleException.class)
	public void testConstructorArrivalTimeIsZero() throws VehicleException{
		new MotorCycle(testConstructorVehID, testValueZero);
	}
	
	@Test(timeout=1000, expected = VehicleException.class)
	public void testArrivalTimeIsNegative() throws VehicleException{
		new MotorCycle(testConstructorVehID, testValueNegative);
	}
	
	@Test(timeout = 1000)
	public void testArrivalTimeConstructor() throws VehicleException{
		new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testParkedStateParkingTimeNeg() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testValueNegative, testIntendedDuration);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testParkedStateIntendedDurationLessThanMin() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, (Constants.MINIMUM_STAY-1));		
	}

}
