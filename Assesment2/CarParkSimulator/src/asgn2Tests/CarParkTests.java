package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2CarParks.CarPark;

/** @author Pearl Gariano 8318522 */

public class CarParkTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		CarPark parkCar = new CarPark();
		assertTrue(parkCar.carParkEmpty());
	}

}
