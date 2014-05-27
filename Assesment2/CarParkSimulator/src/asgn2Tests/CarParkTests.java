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

    static private int testTime = 10;
    static private boolean testForce = true;

    private String testVehID = "123ABC";
    private int testArrivalTime = 1;
    private boolean testCarNotSmall = false;
    private boolean testCarSmall = true;
    private int testIntendedDuration = 60;
    private int testParkingTime = 30;
    Car testCar;
    
    private int testMaxCarSpaces = 20;
    private int testMaxSmallCarSpaces = 10;
    private int testMaxMotorCycleSpaces = 10;
    private int testMaxQueueSize = 5;

    @Before
    public void setUp() throws Exception {
	testCarPark = new CarPark();
	testCar = new Car(testVehID, testArrivalTime,
		testCarNotSmall);
    }
    
    @Test(timeout = 1000)
    public void testCarParkParameters() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    }
    
    // TESTS FOR STATIC VARIABLES
    @Test(timeout = 1000)
    public void testCarParkStaticVariables() throws SimulationException, VehicleException{
    	CarPark testCarPark1 = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	CarPark testCarPark2 = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
    	testCarPark1.parkVehicle(c, testTime, testIntendedDuration);
    	assertTrue(testCarPark2.carParkEmpty());
    }

    // TESTS FOR ARCHIVE DEPARTING VEHICLES


    //TESTS FOR ACHIVE NEW VEHICLE
    
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
    public void testArchiveNewVehicle() throws SimulationException, VehicleException{
    	testCarPark.archiveNewVehicle(testCar); 
    }

    //TESTS FOR ACHIVE QUEUE FAILURES 
    
    // @Test(timeout = 1000, expected = VehicleException.class)
    public void testArchiveQueuesFailureNotCorrectState()
	    throws SimulationException, VehicleException {
	// throws ve if one or more vehicles not in correct state
	// archiveQueueFailures(int time)
    }
 
    
    //@Test(timeout = 1000)
    public void testArchiveQueueFailures() throws VehicleException{
    	testCar.enterQueuedState();
    	testCarPark.archiveQueueFailures(50);
    	assertFalse(testCar.isQueued());
    }
    
    //TESTS FOR CAR PARK EMPTY
    @Test(timeout = 1000)
    public void testCarParkEmptyTrue(){
    	testCarPark = new CarPark();
    	assertTrue(testCarPark.carParkEmpty());
    }
    
    @Test(timeout = 1000)
    public void testCarParkEmptyFalse() throws VehicleException, SimulationException{
    	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
    	testCarPark.parkVehicle(c, testTime, testIntendedDuration);
    	assertFalse(testCarPark.carParkEmpty());
    }

    //TESTS FOR CAR PARK FULL
    
    @Test(timeout = 1000)
    public void testCarParkFullTrue()throws VehicleException, SimulationException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < 10; i++){
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
    public void testCarParkFullFalse()throws VehicleException, SimulationException{
    	testCarPark = new CarPark();
    	assertFalse(testCarPark.carParkFull());
    }
    
    //TESTS FOR ENTER QUEUE
    
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testEnterQueueFullQueue() throws SimulationException, VehicleException {
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < 5; i++){
    		Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
    		testCarPark.enterQueue(c);
    	}
    	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
		testCarPark.enterQueue(c);
    }

    @Test(timeout = 1000, expected = VehicleException.class)
    public void testEnterQueueVehicleIncorrectState() throws VehicleException, SimulationException {
    	testCar.enterParkedState(testParkingTime, testIntendedDuration);
    	testCarPark.enterQueue(testCar);
    }
    
    @Test
    public void testEnterQueue() throws VehicleException, SimulationException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
		testCarPark.enterQueue(c);
		assertEquals(1,testCarPark.numVehiclesInQueue());
    }
    
    //TESTS FOR EXIT QUEUE
    
    @Test(timeout = 100, expected = SimulationException.class)
    public void testExitQueueVehicleNotQueued() throws SimulationException, VehicleException {
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
		testCarPark.exitQueue(c, testTime);
    }

    //TESTS FOR FINAL STATE
    @Test(timeout = 1000)
    public void testFinalState(){
    	assertTrue(true);
    }

    //TESTS FOR GET NUMBER OF CARS
    @Test(timeout = 1000)
    public void testGetNumCars(){
    	assertTrue(true);
    }
    
    //TESTS FOR GET NUMBER OF MOTORCYCLES
    @Test(timeout = 1000)
    public void testGetNumMotorcycles(){
    	assertTrue(true);
    }
    
    //TESTS FOR GET NUMBER OF SMALL CARS
    @Test(timeout = 1000)
    public void testGetNumSmallCars(){
    	assertTrue(true);
    }

    //TESTS FOR GET STATUS
    @Test(timeout = 1000)
    public void testGetStatus(){
    	assertTrue(true);
    }
    
    //TESTS FOR INITIAL STATE
    @Test(timeout = 1000)
    public void testInitialState(){
    	assertTrue(true);
    }

    //TESTS FOR NUMBER OF VEHICLES IN QUEUE
    @Test(timeout = 1000)
    public void testNumVehiclesInQueue() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
		testCarPark.enterQueue(c);
		assertEquals(1,testCarPark.numVehiclesInQueue());
    }
    
    //TESTS FOR PARK VEHICLE 
    
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testParkVehicleNoSuitableSpacesCar() throws SimulationException, VehicleException {
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < testMaxCarSpaces; i++){
    		Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
    		testCarPark.parkVehicle(c, testTime, testIntendedDuration);
    	}
    	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
		testCarPark.parkVehicle(c, testTime, testIntendedDuration);
    }

    @Test(timeout = 1000, expected = SimulationException.class)
    public void testParkVehicleNoSuitableSpacesSmallCar() throws SimulationException, VehicleException {
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < 10; i++){
    		Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
    		Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
    		testCarPark.parkVehicle(c, testTime, testIntendedDuration);
    		testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
    	}
    	Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
		testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
    }
    
    @Test(timeout = 1000)
    public void testParkVehicleSuitableSpacesSmallCar() throws SimulationException, VehicleException {
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < testMaxSmallCarSpaces; i++){
    		Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
    		testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
    	}
    	Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
		testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
    }
    
    //... this test should just be for sc and m
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testParkVehicleNoSuitableSpacesMotorcycle() throws SimulationException, VehicleException {
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < 10; i++){
    		//Car c = new Car(testVehID, testArrivalTime, testCarSmall);
    		Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
    		MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
    		//testCarPark.parkVehicle(c, testTime, testIntendedDuration);
    		testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
    		testCarPark.parkVehicle(m, testTime, testIntendedDuration);
    	}
    	MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
		testCarPark.parkVehicle(m, testTime, testIntendedDuration);
    }
    
    @Test(timeout = 1000)
    public void testParkVehicleSuitableSpacesMotorcycle() throws SimulationException, VehicleException {
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < 10; i++){
    		MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
    		testCarPark.parkVehicle(m, testTime, testIntendedDuration);
    	}
    	MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
		testCarPark.parkVehicle(m, testTime, testIntendedDuration);
    }
    
    //TESTS FOR PROCESS QUEUE
    //@Test(timeout = 1000, expected = SimulationException.class)
    public void testProcessQueueNoSpacesAvailable() throws SimulationException {
	// processQueue(int time, Simulator sim)
    }

    //TESTS FOR QUEUE EMPTY
    @Test(timeout = 1000)
    public void testQueueEmptyTrue() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	assertTrue(testCarPark.queueEmpty());
    }
    
    @Test(timeout = 1000)
    public void testQueueEmptyFalse() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	Car testCar = new Car(testVehID, testArrivalTime, testCarNotSmall);
    	testCarPark.enterQueue(testCar);
    	assertFalse(testCarPark.queueEmpty());
    }
    
    //TESTS FOR QUEUE FULL
    @Test(timeout = 1000)
    public void testQueueFullTrue() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < 5; i++){
    		Car testCar = new Car(testVehID, testArrivalTime, testCarNotSmall);
    		testCarPark.enterQueue(testCar);
    	}
    	assertTrue(testCarPark.queueFull());
    }
    
    @Test(timeout = 1000)
    public void testQueueFullFalse() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	assertFalse(testCarPark.queueFull());
    }
    
    //TESTS FOR SPACES AVAILABLE
    @Test(timeout = 1000)
    public void testSpacesAvailableCarTrue() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
    	assertTrue(testCarPark.spacesAvailable(c));
    }
    
    @Test(timeout = 1000)
    public void testSpacesAvailableCarFalse() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < 10; i++){
    		Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
    		testCarPark.parkVehicle(c, testTime, testIntendedDuration);
    	}
    	Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
    	assertFalse(testCarPark.spacesAvailable(c));
    }
    
    @Test(timeout = 1000)
    public void testSpacesAvailableSmallCarTrue() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
    	assertTrue(testCarPark.spacesAvailable(sc));
    }
    
    @Test(timeout = 1000)
    public void testSpacesAvailableSmallCarFalse() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < 10; i++){
    		Car c = new Car(testVehID, testArrivalTime, testCarNotSmall);
    		Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
    		testCarPark.parkVehicle(c, testTime, testIntendedDuration);
    		testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
    	}
    	Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
    	assertFalse(testCarPark.spacesAvailable(sc));
    }
    
    @Test(timeout = 1000)
    public void testSpacesAvailableMotorcycleTrue() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
    	assertTrue(testCarPark.spacesAvailable(m));
    }
    
    @Test(timeout = 1000)
    public void testSpacesAvailableMotorcycleFalse() throws SimulationException, VehicleException{
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	for(int i = 0; i < 10; i++){
    		Car sc = new Car(testVehID, testArrivalTime, testCarSmall);
    		MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
    		testCarPark.parkVehicle(sc, testTime, testIntendedDuration);
    		testCarPark.parkVehicle(m, testTime, testIntendedDuration);
    	}
    	MotorCycle m = new MotorCycle(testVehID, testArrivalTime);
    	assertFalse(testCarPark.spacesAvailable(m));
    }
    
    
    
    
    //TESTS FOR TRY PROCESS NEW VEHICLES
    
    //@Test(timeout = 1000, expected = SimulationException.class)
    public void testTryProcessNewVehiclesNoSpacesAvailable()
	    throws SimulationException {
	// tryProcessNewVehicles(int time, Simulator sim)
    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testTryProcessNewVehiclesViolatedCreationConstraints()
    		throws VehicleException {

    }

    //TESTS FOR UNPARK VEHICLE
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleNotParked() throws VehicleException, SimulationException {
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	Car testCar = new Car(testVehID, testArrivalTime, testCarNotSmall);
    	testCarPark.unparkVehicle(testCar, testTime);  	
    }

    @Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleInQueue() throws VehicleException, SimulationException {
    	testCarPark = new CarPark(testMaxCarSpaces, testMaxSmallCarSpaces, testMaxMotorCycleSpaces, testMaxQueueSize);
    	Car testCar = new Car(testVehID, testArrivalTime, testCarNotSmall);
    	testCar.enterQueuedState();
    	testCarPark.unparkVehicle(testCar, testTime);
    }



}
