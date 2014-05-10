package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;

public class CarTests {
	
	Car testCar;
	
	private String testConstructorVehID = "123ABC";
	private int testConstructorArrivalTime = 1;
	private int testArrivalTimeIsZero = 0;
	private int testArrivalTimeIsNegative = -1;
	private int testCarNotSmall = false;
	private int testCarSmall = true;
	
	
	@Before
	public void setUp() throws Exception {
		testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
	}
	
	
	@Test(timeout=1000, expected = VehicleException.class)
	public void testConstructorArrivalTimeIsZero() throws VehicleException{
		new Car(testConstructorVehID, testArrivalTimeIsZero, testCarNotSmall);
	}
	
	@Test(timeout=1000, expected = VehicleException.class)
	public void testArrivalTimeIsNegative() throws VehicleException{
		new Car(testConstructorVehID, testArrivalTimeIsNegative, testCarNotSmall);
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
	
	
}
