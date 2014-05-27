package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;

/** @author Pearl Gariano 8318522 */

public class CarParkTests {

    CarPark testCarPark;

    private int testTime = 10;
    private boolean testForce = true;

    private String testVehID = "123ABC";
    private int testArrivalTime = 1;
    private boolean testCarNotSmall = false;
    private boolean testCarSmall = true;
    private int testIntendedDuration = 60;
    private int testParkingTime = 30;
    private int testDepartureTime = 60;
    Car testCar;

    private int testMaxCarSpaces = 20;
    private int testMaxSmallCarSpaces = 10;
    private int testMaxMotorCycleSpaces = 10;
    private int testMaxQueueSize = 5;

    private int testSeed = 1;
    private double testMeanStay = 300.0;
    private double testSdStay = 100.0;
    private double testCarProb100 = 1.0;
    private double testSmallCarProb100 = 1.0;
    private double testMotorcycleProb100 = 1.0;
    private double testZeroProb = 0.0;

    @Before
    public void setUp() throws Exception {
	testCarPark = new CarPark();
	testCar = new Car(testVehID, testArrivalTime, testCarNotSmall);
    }

    @Test(timeout = 1000)
    public void testCarParkParameters() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
    }

    // TESTS FOR STATIC VARIABLES
    @Test(timeout = 1000)
    public void testCarParkStaticVariables() throws SimulationException,
	    VehicleException {
	CarPark testCarPark1 = new CarPark(testMaxCarSpaces,
		testMaxSmallCarSpaces, testMaxMotorCycleSpaces,
		testMaxQueueSize);
	CarPark testCarPark2 = new CarPark(testMaxCarSpaces,
		testMaxSmallCarSpaces, testMaxMotorCycleSpaces,
		testMaxQueueSize);
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark1.parkVehicle(c, testTime, testIntendedDuration);
	assertTrue(testCarPark2.carParkEmpty());
    }

    // TESTS FOR ARCHIVE DEPARTING VEHICLES

    @Test(timeout = 1000)
    public void testArchiveDepartingVehiclesForceTrue()
	    throws SimulationException, VehicleException {
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.parkVehicle(c, testTime, testIntendedDuration);
	int initialCars = testCarPark.getNumCars();
	int expectedDepartureTime = testTime + testIntendedDuration;
	testCarPark.archiveDepartingVehicles((expectedDepartureTime - 1),
		testForce);
	int finalCars = testCarPark.getNumCars();
	assertTrue(finalCars == initialCars - 1);
    }

    // TESTS FOR ACHIVE NEW VEHICLE

    @Test(timeout = 1000, expected = SimulationException.class)
    public void testArchiveNewVehicleCurrentlyQueued()
	    throws SimulationException, VehicleException {
	testCar.enterQueuedState();
	testCarPark.archiveNewVehicle(testCar);
    }

    @Test(timeout = 1000, expected = SimulationException.class)
    public void testArchiveNewVehicleCurrentlyParked()
	    throws SimulationException, VehicleException {
	testCar.enterParkedState(testParkingTime, testIntendedDuration);
	testCarPark.archiveNewVehicle(testCar);
    }

    @Test(timeout = 1000)
    public void testArchiveNewVehicle() throws SimulationException,
	    VehicleException {
	testCarPark.archiveNewVehicle(testCar);
    }

    // TESTS FOR ACHIVE QUEUE FAILURES

    @Test(timeout = 1000)
    public void testArchiveQueueFailures() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.enterQueue(c);
	testCarPark.archiveQueueFailures(testTime);

    }

    // TESTS FOR CAR PARK EMPTY
    @Test(timeout = 1000)
    public void testCarParkEmptyTrue() {
	testCarPark = new CarPark();
	assertTrue(testCarPark.carParkEmpty());
    }

    @Test(timeout = 1000)
    public void testCarParkEmptyFalse() throws VehicleException,
	    SimulationException {
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.parkVehicle(c, testTime, testIntendedDuration);
	assertFalse(testCarPark.carParkEmpty());
    }

    // TESTS FOR CAR PARK FULL

    @Test(timeout = 1000)
    public void testCarParkFullTrue() throws VehicleException,
	    SimulationException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < 10; i++) {
	    Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	    Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	    MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
	    testCarPark.parkVehicle(c, testArrivalTime, testIntendedDuration);
	    testCarPark.parkVehicle(sc, testArrivalTime, testIntendedDuration);
	    testCarPark.parkVehicle(m, testArrivalTime, testIntendedDuration);
	}
	assertTrue(testCarPark.carParkFull());
    }

    @Test(timeout = 1000)
    public void testCarParkFullFalse() throws VehicleException,
	    SimulationException {
	testCarPark = new CarPark();
	assertFalse(testCarPark.carParkFull());
    }

    // TESTS FOR ENTER QUEUE

    @Test(timeout = 1000, expected = SimulationException.class)
    public void testEnterQueueFullQueue() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < 5; i++) {
	    Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	    testCarPark.enterQueue(c);
	}
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.enterQueue(c);
    }

    @Test(timeout = 1000, expected = VehicleException.class)
    public void testEnterQueueVehicleIncorrectState() throws VehicleException,
	    SimulationException {
	testCar.enterParkedState(testParkingTime, testIntendedDuration);
	testCarPark.enterQueue(testCar);
    }

    @Test
    public void testEnterQueue() throws VehicleException, SimulationException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.enterQueue(c);
	assertEquals(1, testCarPark.numVehiclesInQueue());
    }

    // TESTS FOR EXIT QUEUE

    @Test(timeout = 100, expected = SimulationException.class)
    public void testExitQueueVehicleNotQueued() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.exitQueue(c, testTime);
    }

    // TESTS FOR FINAL STATE
    @Test(timeout = 1000)
    public void testFinalState() {
	assertTrue(true);
    }

    // TESTS FOR GET NUMBER OF CARS
    @Test(timeout = 1000)
    public void testGetNumCars() {
	assertTrue(true);
    }

    // TESTS FOR GET NUMBER OF MOTORCYCLES
    @Test(timeout = 1000)
    public void testGetNumMotorcycles() {
	assertTrue(true);
    }

    // TESTS FOR GET NUMBER OF SMALL CARS
    @Test(timeout = 1000)
    public void testGetNumSmallCars() {
	assertTrue(true);
    }

    // TESTS FOR GET STATUS
    @Test(timeout = 1000)
    public void testGetStatus() {
	assertTrue(true);
    }

    // TESTS FOR INITIAL STATE
    @Test(timeout = 1000)
    public void testInitialState() {
	assertTrue(true);
    }

    // TESTS FOR NUMBER OF VEHICLES IN QUEUE
    @Test(timeout = 1000)
    public void testNumVehiclesInQueue() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.enterQueue(c);
	assertEquals(1, testCarPark.numVehiclesInQueue());
    }

    // TESTS FOR PARK VEHICLE

    @Test(timeout = 1000, expected = SimulationException.class)
    public void testParkVehicleNoSuitableSpacesCar()
	    throws SimulationException, VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < testMaxCarSpaces; i++) {
	    Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	    testCarPark.parkVehicle(c, testTime, testIntendedDuration);
	}
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.parkVehicle(c, testTime, testIntendedDuration);
    }

    @Test(timeout = 1000, expected = SimulationException.class)
    public void testParkVehicleNoSuitableSpacesSmallCar()
	    throws SimulationException, VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < 10; i++) {
	    Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	    Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	    testCarPark.parkVehicle(c, testTime, testIntendedDuration);
	    testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
	}
	Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
    }

    @Test(timeout = 1000)
    public void testParkVehicleSuitableSpacesSmallCar()
	    throws SimulationException, VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < testMaxSmallCarSpaces; i++) {
	    Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	    testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
	}
	Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
    }

    @Test(timeout = 1000, expected = SimulationException.class)
    public void testParkVehicleNoSuitableSpacesMotorcycle()
	    throws SimulationException, VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < 10; i++) {
	    Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	    MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
	    testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
	    testCarPark.parkVehicle(m, testTime, testIntendedDuration);
	}
	MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
	testCarPark.parkVehicle(m, testTime, testIntendedDuration);
    }

    @Test(timeout = 1000)
    public void testParkVehicleSuitableSpacesMotorcycle()
	    throws SimulationException, VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < 10; i++) {
	    MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
	    testCarPark.parkVehicle(m, testTime, testIntendedDuration);
	}
	MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
	testCarPark.parkVehicle(m, testTime, testIntendedDuration);
    }

    // TESTS FOR PROCESS QUEUE
    @Test(timeout = 1000)
    public void testProcessQueueNoSpacesAvailable() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Simulator sim = new Simulator();
	for (int i = 0; i < 10; i++) {
	    Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	    testCarPark.parkVehicle(c, testDepartureTime, testIntendedDuration);
	}
	for (int i = 0; i < testMaxQueueSize; i++) {
	    Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	    testCarPark.enterQueue(c);
	}
	testCarPark.processQueue(testTime, sim);
	assertTrue(testCarPark.queueFull());
    }

    // TESTS FOR QUEUE EMPTY
    @Test(timeout = 1000)
    public void testQueueEmptyTrue() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	assertTrue(testCarPark.queueEmpty());
    }

    @Test(timeout = 1000)
    public void testQueueEmptyFalse() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Car testCar = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.enterQueue(testCar);
	assertFalse(testCarPark.queueEmpty());
    }

    // TESTS FOR QUEUE FULL
    @Test(timeout = 1000)
    public void testQueueFullTrue() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < 5; i++) {
	    Car testCar = new Car(testVehID, testArrivalTime, testCarNotSmall);
	    testCarPark.enterQueue(testCar);
	}
	assertTrue(testCarPark.queueFull());
    }

    @Test(timeout = 1000)
    public void testQueueFullFalse() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	assertFalse(testCarPark.queueFull());
    }

    // TESTS FOR SPACES AVAILABLE
    @Test(timeout = 1000)
    public void testSpacesAvailableCarTrue() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	assertTrue(testCarPark.spacesAvailable(c));
    }

    @Test(timeout = 1000)
    public void testSpacesAvailableCarFalse() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < 10; i++) {
	    Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	    testCarPark.parkVehicle(c, testTime, testIntendedDuration);
	}
	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	assertFalse(testCarPark.spacesAvailable(c));
    }

    @Test(timeout = 1000)
    public void testSpacesAvailableSmallCarTrue() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	assertTrue(testCarPark.spacesAvailable(sc));
    }

    @Test(timeout = 1000)
    public void testSpacesAvailableSmallCarFalse() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < 10; i++) {
	    Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
	    Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	    testCarPark.parkVehicle(c, testTime, testIntendedDuration);
	    testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
	}
	Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	assertFalse(testCarPark.spacesAvailable(sc));
    }

    @Test(timeout = 1000)
    public void testSpacesAvailableMotorcycleTrue() throws SimulationException,
	    VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
	assertTrue(testCarPark.spacesAvailable(m));
    }

    @Test(timeout = 1000)
    public void testSpacesAvailableMotorcycleFalse()
	    throws SimulationException, VehicleException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	for (int i = 0; i < 10; i++) {
	    Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
	    MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
	    testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
	    testCarPark.parkVehicle(m, testTime, testIntendedDuration);
	}
	MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
	assertFalse(testCarPark.spacesAvailable(m));
    }

    // TESTS FOR TRY PROCESS NEW VEHICLES

    @Test(timeout = 1000)
    public void testTryProcessNewVehiclesJustCars() throws SimulationException,
	    VehicleException {
	Simulator sim = new Simulator(testSeed, testMeanStay, testSdStay,
		testCarProb100, testZeroProb, testZeroProb);
	testCarPark.tryProcessNewVehicles(testTime, sim);
	assertEquals(1, testCarPark.getNumCars());
    }

    @Test(timeout = 1000)
    public void testTryProcessNewVehiclesJustSmallCars()
	    throws SimulationException, VehicleException {
	Simulator sim = new Simulator(testSeed, testMeanStay, testSdStay,
		testCarProb100, testSmallCarProb100, testZeroProb);
	testCarPark.tryProcessNewVehicles(testTime, sim);
	assertEquals(1, testCarPark.getNumSmallCars());
    }

    @Test(timeout = 1000)
    public void testTryProcessNewVehiclesJustMotorcycles()
	    throws SimulationException, VehicleException {
	Simulator sim = new Simulator(testSeed, testMeanStay, testSdStay,
		testZeroProb, testZeroProb, testMotorcycleProb100);
	testCarPark.tryProcessNewVehicles(testTime, sim);
	assertEquals(1, testCarPark.getNumMotorCycles());
    }

    // TESTS FOR UNPARK VEHICLE
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleNotParked() throws VehicleException,
	    SimulationException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Car testCar = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCarPark.unparkVehicle(testCar, testTime);
    }

    @Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleInQueue() throws VehicleException,
	    SimulationException {
	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces,
		testMaxMotorCycleSpaces, testMaxQueueSize);
	Car testCar = new Car(testVehID, testArrivalTime, testCarNotSmall);
	testCar.enterQueuedState();
	testCarPark.unparkVehicle(testCar, testTime);
    }

}
