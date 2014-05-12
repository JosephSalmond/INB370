package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;
import asgn2Vehicles.Vehicle;
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
	
	
	@Before
	public void setUp() throws Exception {
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
	}
	
	
	@Test(timeout=1000, expected = VehicleException.class)
	public void testConstructorArrivalTimeZero() throws VehicleException{
		new Car(testConstructorVehID, testValueZero, testCarNotSmall);
	}
	
	@Test(timeout=1000, expected = VehicleException.class)
	public void testArrivalTimeNegative() throws VehicleException{
		new Car(testConstructorVehID, testValueNegative, testCarNotSmall);
	}
	
	@Test(timeout = 1000)
	public void testArrivalTimeConstructor() throws VehicleException{
		new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
	}
	
	@Test(timeout = 1000)
	public void testCarNotSmall() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
		assertEquals(false, testCar.isSmall());
	}
	
	@Test(timeout = 1000)
	public void testCarIsSmall() throws VehicleException{
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarSmall);
		assertEquals(true, testCar.isSmall());
	}
	
	//@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

		
	
}
