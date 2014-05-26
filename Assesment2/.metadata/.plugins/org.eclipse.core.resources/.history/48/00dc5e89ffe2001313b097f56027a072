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

/** @author Pearl Gariano 8318522 */

public class CarParkTests {

    CarPark testCarPark;

    static private int testTime = 10;
    static private boolean testForce = true;

    private String testConstructorVehID = "123ABC";
    private int testConstructorArrivalTime = 1;
    private boolean testCarNotSmall = false;
    private int testIntendedDuration = 60;
    private int testParkingTime = 30;
    Car testCar;

    @Before
    public void setUp() throws Exception {
	testCarPark = new CarPark();
	testCar = new Car(testConstructorVehID, testConstructorArrivalTime,
		testCarNotSmall);
    }


    // TESTS FOR ARCHIVE DEPARTING VEHICLES
    /*
     * @Test public void testArchiveDepartingVehicles() throws VehicleException
     * {
     * 
     * }
     */

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
 
    // @Test(timeout = 1000, expected = VehicleException.class)
    public void testArchiveQueuesFailureViolatedTimeConstraint()
	    throws SimulationException, VehicleException {
	// throws ve if timing constraints are violated
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
    	Simulator sim = new Simulator();
    	testCarPark.tryProcessNewVehicles(testTime, sim);
    	assertFalse(testCarPark.carParkEmpty());
    }

    //TESTS FOR CAR PARK FULL
    
    //@Test(timeout = 1000)
    public void testCarParkFullTrue()throws VehicleException, SimulationException{
   
    }
    
    //...
    @Test(timeout = 1000)
    public void testCarParkFullFalse()throws VehicleException, SimulationException{
    	testCarPark = new CarPark();
    	assertFalse(testCarPark.carParkFull());
    }
    
    //TESTS FOR ENTER QUEUE
    
    //@Test(timeout = 1000, expected = SimulationException.class)
    public void testEnterQueueFullQueue() throws SimulationException {
	// enterQueue(Vehicle v)
    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testEnterQueueVehicleIncorrectState() throws VehicleException {

    }
    
    //TESTS FOR EXIT QUEUE
    
    //@Test(timeout = 100, expected = SimulationException.class)
    public void testExitQueueVehicleNotQueued() throws SimulationException {
	// exitQueue(Vehicle v, int exitTime)
    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testExitQueueVehicleIncorrectState() throws VehicleException {

    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testExitQueueViolatedTimeConstraints() throws VehicleException {

    }

    //TESTS FOR FINAL STATE

    //TESTS FOR GET NUMBER OF CARS
    
    //TESTS FOR GET NUMBER OF MOTORCYCLES
    
    //TESTS FOR GET NUMBER OF SMALL CARS

    //TESTS FOR GET STATUS
    
    //TESTS FOR INITIAL STATE

    //TESTS FOR NUMBER OF VEHICLES IN QUEUE
    
    //TESTS FOR PARK VEHICLE 
    
    //@Test(timeout = 1000, expected = SimulationException.class)
    public void testParkVehicleNoSuitableSpaces() throws SimulationException {
	// parkVehicle(Vehicle v, int time, int intendedDuration)
    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testParkVehicleIncorrectState() throws VehicleException {

    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testParkVehicleViolatedTimeConstraints()
	    throws VehicleException {

    }
    
    //TESTS FOR PROCESS QUEUE
    //@Test(timeout = 1000, expected = SimulationException.class)
    public void testProcessQueueNoSpacesAvailable() throws SimulationException {
	// processQueue(int time, Simulator sim)
    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testProcessQueueIncorrectState() throws VehicleException {

    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testProcessQueueViolatedTimingConstraints()
	    throws VehicleException {

    }

    //TESTS FOR QUEUE EMPTY
    
    //TESTS FOR QUEUE FULL
    
    //TESTS FOR SPACES AVAILABLE
    
    //TESTS FOR TO STRING
    
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
    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleNotParked() throws VehicleException {
	// unparkVehicle(Vehicle v, int departureTime)
    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleInQueue() throws VehicleException, SimulationException {

    }

    //@Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleViolatesTimingConstraints()
	    throws VehicleException, SimulationException {

    }

    //@Test(timeout = 1000, expected = SimulationException.class)
    public void testUnparkVehicleNotInCarPark() throws VehicleException, SimulationException {

    }

}
