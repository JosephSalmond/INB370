package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;
import asgn2Simulators.Constants;


/** @author Pearl Gariano 8318522 */


public class CarTests {
	
	Car testCar;
	
	private String testConstructorVehID = "123ABC";
	private int testConstructorArrivalTime = 1;
	
	private int testValueZero = 0;
	private int testValueNegative = -1;
	
	private boolean testCarNotSmall = false;
	private boolean testCarSmall = true;
	
	private int testIntendedDuration = 60;
	private int testParkingTime = 30;
	
	private int testQueueExitTime = 25;
	
	@Before
	public void setUp() throws Exception {
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
	}
	
	/* TESTS FOR CAR CONSTRUCTOR VALUES */
	
	@Test(timeout=1000, expected = VehicleException.class)
	public void testConstructorArrivalTimeZero() throws VehicleException{
		new Car(testConstructorVehID, testValueZero, testCarNotSmall);
	}
	
	@Test(timeout=1000, expected = VehicleException.class)
	public void testConstructorArrivalTimeNegative() throws VehicleException{
		new Car(testConstructorVehID, testValueNegative, testCarNotSmall);
	}
	
	@Test(timeout = 1000)
	public void testConstructorArrivalTime() throws VehicleException{
		new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
	}
	
	/* TESTS FOR IS SMALL METHOD */
	
	@Test(timeout = 1000)
	public void testCarIsSmallFalse() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		assertFalse(testCar.isSmall());
	}
	
	@Test(timeout = 1000)
	public void testCarIsSmallTrue() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarSmall);
		assertTrue(testCar.isSmall());
	}
	
	/* TESTS FOR TO STRING METHOD */
	
	//write tests for to string method here? 
	
	/* TESTS FOR ENTER PARKED STATE METHOD */
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterParkedStateParkingTimeNeg() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testValueNegative, testIntendedDuration);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterParkedStateIntendedDurationLessThanMin() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, (Constants.MINIMUM_STAY-1));		
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterParkedStateAlreadyParked() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);	
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterParkedStateAlreadyQueued() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
	}
	
	@Test(timeout = 1000)
	public void testEnterParkedStateEnterPark() throws VehicleException {
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		assertTrue(testCar.isParked());
	}
	
	
	/* TESTS FOR ENTER QUEUED STATE METHOD */
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterQueuedStateAlreadyParked() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		testCar.enterQueuedState();
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testEnterQueuedAlreadyQueued() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		testCar.enterQueuedState();
	}
	
	@Test(timeout = 1000)
	public void testEnterQueuedStateEnterQueue() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		assertTrue(testCar.isQueued());	
	}
	
	
	/* TESTS FOR EXIT PARKED STATE METHOD */
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitParkedStateNotParked() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.exitParkedState(testParkingTime + testIntendedDuration);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitParkedStateIsQueued() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		testCar.exitParkedState(testParkingTime + testIntendedDuration);
	}
	
	
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitParkedStateDepartureLessThanParkingTime() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		testCar.exitParkedState(testParkingTime-1);
		assertTrue(testCar.getDepartureTime() < testCar.getParkingTime());
	}
	
	public void testExitParkedStateDurationGreaterThanParkingTime() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		testCar.exitParkedState(testParkingTime + testIntendedDuration);
		assertFalse(testCar.getDepartureTime() < testCar.getParkingTime());
	}
	
	/* TESTS FOR EXIT QUEUED STATE METHOD */
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitQueuedStateIsParked() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		testCar.exitQueuedState(testQueueExitTime);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitQueuedStateNotQueued() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.exitQueuedState(testQueueExitTime);
	}
	
	@Test(timeout = 1000, expected = VehicleException.class)
	public void testExitQueuedStateExitNotLaterThanArrivalTime() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		testCar.exitQueuedState(testConstructorArrivalTime - 1);
	}
	
	@Test(timeout = 1000)
	public void testExitQueuedStateExitQueue() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		testCar.exitQueuedState(testQueueExitTime);		
	}
	
	/* TEST FOR GET ARRIVAL TIME METHOD */
	
	@Test(timeout = 1000)
	public void testGetArrivalTime() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		assertEquals(testConstructorArrivalTime, testCar.getArrivalTime());
	}
	
	/* TEST FOR GET DEPARTURE TIME METHOD */
	
	@Test(timeout = 1000)
	public void testGetDepartureTime() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		assertEquals((testParkingTime + testIntendedDuration), testCar.getDepartureTime());
	}
	
	/* TEST FOR GET PARKING TIME METHOD */
	
	@Test(timeout = 1000)
	public void testGetParkingTime() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		assertEquals(testParkingTime, testCar.getParkingTime());
	}
	
	/* TEST FOR GET VEHICLE ID METHOD */
	
	@Test(timeout = 1000)
	public void testGetVehID() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		assertEquals(testConstructorVehID, testCar.getVehID());
	}
	
	/* TESTS FOR IS PARKED METHOD */
	
	@Test(timeout = 1000)
	public void testIsParkedTrue() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		assertTrue(testCar.isParked());
	}
	
	@Test(timeout = 1000)
	public void testIsParkedFalse() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		assertFalse(testCar.isParked());
	}
	
	/* TESTS FOR IS QUEUED METHOD */
	
	@Test(timeout = 1000)
	public void testIsQueuedTrue() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		assertTrue(testCar.isQueued());
	}
	
	@Test(timeout = 1000)
	public void testIsQueuedFalse() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		assertFalse(testCar.isQueued());
	}
	
	/* TESTS FOR IS SATISFIED METHOD */
	@Test(timeout = 1000)
	public void testIsSatisfiedFalseNeverParked() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		assertFalse(testCar.isSatisfied());
	}
	
	@Test(timeout = 1000)
	public void testIsSatisfiedFalseQueueTooLong() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		testCar.exitQueuedState(Constants.MAXIMUM_QUEUE_TIME+1);
		assertFalse(testCar.isSatisfied());
	}
	
	//write test for satisfied is true
	
	/* TESTS FOR WAS PARKED METHOD */
	
	@Test(timeout = 1000)
	public void testWasParkedTrueWasParked() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		testCar.exitParkedState(testParkingTime + testIntendedDuration);
		assertTrue(testCar.wasParked());
	}
	
	@Test(timeout = 1000)
	public void testWasParkedTrueIsParked() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterParkedState(testParkingTime, testIntendedDuration);
		assertFalse(testCar.wasParked());
	}
	
	@Test(timeout = 1000)
	public void testWasParkedFalse() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		assertFalse(testCar.wasParked());
	}
	
	/* TESTS FOR WAS QUEUED METHOD */
	
	@Test(timeout = 1000)
	public void testWasQueuedTrueWasQueued() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		testCar.exitQueuedState(testQueueExitTime);
		assertTrue(testCar.wasQueued());
	}
	
	@Test(timeout = 1000)
	public void testWasQueuedTrueIsQueued() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		testCar.enterQueuedState();
		assertFalse(testCar.wasQueued());
	}
	
	@Test(timeout = 1000)
	public void testWasQueuedFalse() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		assertFalse(testCar.wasQueued());
	}
	
}
	
