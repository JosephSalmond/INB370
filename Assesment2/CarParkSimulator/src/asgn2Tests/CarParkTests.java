package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2CarParks.CarPark;

/** @author Pearl Gariano 8318522 */

public class CarParkTests {
	
	CarPark testCarPark;
	
	static private int testTime = 30;
	static private boolean testForce = true;
	
	private String testConstructorVehID = "123ABC";
    private int testConstructorArrivalTime = 1;
    private boolean testCarNotSmall = false;
    private int testIntendedDuration = 60;
    private int testParkingTime = 30;
    
    Car testCar = new Car(testConstructorVehID, testConstructorArrivalTime, testCarNotSmall);
	 

    @Before
    public void setUp() throws Exception {
    	testCarPark = new CarPark();
    }
    
    @Before
    public void setUpWithParams() throws Exception {
    	testCarPark = new CarPark(Constants.DEFAULT_MAX_CAR_SPACES, Constants.DEFAULT_MAX_SMALL_CAR_SPACES, 
    			Constants.DEFAULT_MAX_MOTORCYCLE_SPACES, Constants.DEFAULT_MAX_QUEUE_SIZE);

    }
    
    //WRITE TESTS FOR ARCHIVE DEPARTING VEHICLES
    /*@Test
    public void testArchiveDepartingVehicles() throws VehicleException {
   
    }*/
    
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testArchiveNewVehicleCurrentlyQueued() throws SimulationException{
    	testCar.enterQueuedState();
    	testCarPark.archiveNewVehicle(testCar);
    }
    
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testArchiveNewVehicleCurrentlyParked() throws SimulationException{
    	testCar.enterParkedState(testParkingTime, testIntendedDuration);
    	testCarPark.archiveNewVehicle(testCar);
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testArchiveQueuesFailureNotCorrectState() throws VehicleException{
    	//throws ve if one or more vehicles not in correct state
    	//archiveQueueFailures(int time)
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testArchiveQueuesFailureViolatedTimeConstraint() throws VehicleException{
    	//throws ve if timing constraints are violated
    }
    
    //test car park empty
    //test car park full
    
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testEnterQueueFullQueue() throws SimulationException{
    	//enterQueue(Vehicle v)
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testEnterQueueVehicleIncorrectState() throws VehicleException{
    	
    }
    
    @Test(timeout = 100, expected = SimulationException.class)
    public void testExitQueueVehicleNotQueued() throws SimulationException{
    	//exitQueue(Vehicle v, int exitTime)
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testExitQueueVehicleIncorrectState() throws VehicleException{
    	
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testExitQueueViolatedTimeConstraints() throws VehicleException{
    	
    }
    
    //test final state
    
    //test get num cars
    //test get num motorcycles
    //test get num small calls
    
    //test get status
    //test intial state
    
    //num vehicles in queue
   
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testParkVehicleNoSuitableSpaces() throws SimulationException{
    	//parkVehicle(Vehicle v, int time, int intendedDuration)
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testParkVehicleIncorrectState() throws VehicleException{
    	
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testParkVehicleViolatedTimeConstraints() throws VehicleException{
    	
    }
    
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testProcessQueueNoSpacesAvailable() throws SimulationException{
    	//processQueue(int time, Simulator sim)
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testProcessQueueIncorrectState() throws VehicleException{
    	
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testProcessQueueViolatedTimingConstraints() throws VehicleException{
    	
    }
    
    //test queue empty
    //test queue full
    //test spaces available
    //test to string
    
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testTryProcessNewVehiclesNoSpacesAvailable() throws SimulationException{
    	//tryProcessNewVehicles(int time, Simulator sim)
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testTryProcessNewVehiclesViolatedCreationConstraints() throws VehicleException{
    	
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleNotParked() throws VehicleException{
    	//unparkVehicle(Vehicle v, int departureTime)
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleInQueue() throws VehicleException{
    	
    }
    
    @Test(timeout = 1000, expected = VehicleException.class)
    public void testUnparkVehicleViolatesTimingConstraints() throws VehicleException{
    	
    }
    
    @Test(timeout = 1000, expected = SimulationException.class)
    public void testUnparkVehicleNotInCarPark() throws SimulationException{
    	
    }
    
}
