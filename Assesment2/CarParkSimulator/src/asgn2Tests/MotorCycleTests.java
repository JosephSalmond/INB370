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
	
	private int testIntendedDuration = 60;
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
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testParkedStateAlreadyParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);	
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testParkedStateAlreadyQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		testMotorCycle.enterQueuedState();
	}
	
	@Test(timeout = 1000)
	public void testParkedStateNotParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isParked());	
	}
	
	@Test(timeout = 1000)
	public void testParkedStateNotQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isQueued());	
	}
	
	//write tests for working enterParkedState method
	
	@Test(timeout = 1000)
	public void testQueuedStateEnterPark() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		assertTrue(testMotorCycle.isParked());	
	}
	
	@Test(timeout = 1000)
	public void testQueuedStateEnterQueue() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		assertTrue(testMotorCycle.isQueued());	
	}
	
	@Test(timeout = 1000)
	public void testQueuedStateNotParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isParked());	
	}
	
	@Test(timeout = 1000)
	public void testQueuedStateNotQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isQueued());	
	}
	
	//write tests for working enterQueuedState
	
	
	@Test(timeout = 1000)
	public void testExitParkedStateNotParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isParked());
	}
	
	@Test(timeout = 1000)
	public void testExitParkedStateIsQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isQueued());
	}
	
	@Test(timeout = 1000)
	public void testExitParkedStateDepartureLessThanParkingTime() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		testMotorCycle.exitParkedState(testParkingTime + testIntendedDuration);
		assertEquals(false, (testMotorCycle.getDepartureTime() < testMotorCycle.getParkingTime()));
	}
	
	//write tests for working exitParkedState
	
	@Test(timeout = 1000)
	public void testExitQueuedStateIsParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isParked());
	}
	
	@Test(timeout = 1000)
	public void testExitQueuedStateNotQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isQueued());
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitQueuedStateExitNotLaterThanArrivalTime() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		testMotorCycle.exitQueuedState(testConstructorArrivalTime - 1);
	}
	
	//write tests for working exitQueuedState
	

}
