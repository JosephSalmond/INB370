package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
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
	
	private int testQueueExitTime = 25;

	@Before
	public void setUp() throws Exception {
		new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
	}
	
	/* TESTS FOR CONSTRUCTOR VALUES */
	
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
	
	
	/* TESTS FOR ENTER PARKED STATE METHOD */
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterParkedStateParkingTimeNeg() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testValueNegative, testIntendedDuration);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterParkedStateIntendedDurationLessThanMin() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, (Constants.MINIMUM_STAY-1));		
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterParkedStateAlreadyParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);	
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterParkedStateAlreadyQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
	}
	
	@Test(timeout = 1000)
	public void testEnterParkedStateEnterPark() throws VehicleException {
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		assertTrue(testMotorCycle.isParked());
	}
	
	
	/* TESTS FOR ENTER QUEUED STATE METHOD */
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterQueuedStateAlreadyParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		testMotorCycle.enterQueuedState();
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterQueuedAlreadyQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		testMotorCycle.enterQueuedState();
	}
	
	@Test(timeout = 1000)
	public void testEnterQueuedStateEnterQueue() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		assertTrue(testMotorCycle.isQueued());	
	}
	
	
	/* TESTS FOR EXIT PARKED STATE METHOD */
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitParkedStateNotParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.exitParkedState(testParkingTime + testIntendedDuration);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitParkedStateIsQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		testMotorCycle.exitParkedState(testParkingTime + testIntendedDuration);
	}
	
	
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitParkedStateDepartureLessThanParkingTime() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		testMotorCycle.exitParkedState(testParkingTime-1);
		assertTrue(testMotorCycle.getDepartureTime() < testMotorCycle.getParkingTime());
	}
	
	public void testExitParkedStateDurationGreaterThanParkingTime() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		testMotorCycle.exitParkedState(testParkingTime + testIntendedDuration);
		assertFalse(testMotorCycle.getDepartureTime() < testMotorCycle.getParkingTime());
	}
	
	/* TESTS FOR EXIT QUEUED STATE METHOD */
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitQueuedStateIsParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		testMotorCycle.exitQueuedState(testQueueExitTime);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitQueuedStateNotQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.exitQueuedState(testQueueExitTime);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitQueuedStateExitNotLaterThanArrivalTime() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		testMotorCycle.exitQueuedState(testConstructorArrivalTime - 1);
	}
	
	@Test(timeout = 1000)
	public void testExitQueuedStateExitQueue() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		testMotorCycle.exitQueuedState(testQueueExitTime);		
	}
	
	/* TEST FOR GET ARRIVAL TIME METHOD */
	
	@Test(timeout = 1000)
	public void testGetArrivalTime() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertEquals(testConstructorArrivalTime, testMotorCycle.getArrivalTime());
	}
	
	/* TEST FOR GET DEPARTURE TIME METHOD */
	
	@Test(timeout = 1000)
	public void testGetDepartureTime() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		assertEquals((testParkingTime + testIntendedDuration), testMotorCycle.getDepartureTime());
	}
	
	/* TEST FOR GET PARKING TIME METHOD */
	
	@Test(timeout = 1000)
	public void testGetParkingTime() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		assertEquals(testParkingTime, testMotorCycle.getParkingTime());
	}
	
	/* TEST FOR GET VEHICLE ID METHOD */
	
	@Test(timeout = 1000)
	public void testGetVehID() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertEquals(testConstructorVehID, testMotorCycle.getVehID());
	}
	
	/* TESTS FOR IS PARKED METHOD */
	
	@Test(timeout = 1000)
	public void testIsParkedTrue() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		assertTrue(testMotorCycle.isParked());
	}
	
	@Test(timeout = 1000)
	public void testIsParkedFalse() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isParked());
	}
	
	/* TESTS FOR IS QUEUED METHOD */
	
	@Test(timeout = 1000)
	public void testIsQueuedTrue() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		assertTrue(testMotorCycle.isQueued());
	}
	
	@Test(timeout = 1000)
	public void testIsQueuedFalse() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isQueued());
	}
	
	/* TESTS FOR IS SATISFIED METHOD */
	@Test(timeout = 1000)
	public void testIsSatisfiedFalseNeverParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.isSatisfied());
	}
	
	@Test(timeout = 1000)
	public void testIsSatisfiedFalseQueueTooLong() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		testMotorCycle.exitQueuedState(Constants.MAXIMUM_QUEUE_TIME+1);
		assertFalse(testMotorCycle.isSatisfied());
	}
	
	//write test for satisfied is true
	
	//do we need tests for the to string method here? 
	
	/* TESTS FOR WAS PARKED METHOD */
	
	@Test(timeout = 1000)
	public void testWasParkedTrueWasParked() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
		testMotorCycle.exitParkedState(testParkingTime + testIntendedDuration);
		assertTrue(testMotorCycle.wasParked());
	}
//  What is this ment to test?	
//	@Test(timeout = 1000)
//	public void testWasParkedTrueIsParked() throws VehicleException{
//		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
//		testMotorCycle.enterParkedState(testParkingTime, testIntendedDuration);
//		assertTrue(testMotorCycle.wasParked());
//	}
	
	@Test(timeout = 1000)
	public void testWasParkedFalse() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.wasParked());
	}
	
	/* TESTS FOR WAS QUEUED METHOD */
	
	@Test(timeout = 1000)
	public void testWasQueuedTrueWasQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		testMotorCycle.exitQueuedState(testQueueExitTime);
		assertTrue(testMotorCycle.wasQueued());
	}
	
	@Test(timeout = 1000)
	public void testWasQueuedTrueIsQueued() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		testMotorCycle.enterQueuedState();
		assertFalse(testMotorCycle.wasQueued());
	}
	
	@Test(timeout = 1000)
	public void testWasQueuedFalse() throws VehicleException{
		testMotorCycle = new MotorCycle(testConstructorVehID, testConstructorArrivalTime);
		assertFalse(testMotorCycle.wasQueued());
	}
	
}
