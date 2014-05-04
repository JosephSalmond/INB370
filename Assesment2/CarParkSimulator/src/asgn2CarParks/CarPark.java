package asgn2CarParks;

import java.util.ArrayDeque;
import java.util.Vector;

import asgn2Exceptions.VehicleException;
import asgn2Exceptions.SimulationException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.Vehicle;

/**
 * **USE, BUT YOU MAY NEED TO CHANGE THE VAR NAMES *** Method used to provide
 * the current status of the car park. Uses private status String set whenever a
 * transition occurs. Example follows (using high probability for car creation).
 * At time 262, we have 276 vehicles existing, 91 in car park (P), 84 cars in
 * car park (C), of which 14 are small (S), 7 MotorCycles in car park (M), 48
 * dissatisfied (D), 176 archived (A), queue of size 9 (CCCCCCCCC), and on this
 * iteration we have seen: car C go from Parked (P) to Archived (A), C go from
 * queued (Q) to Parked (P), and small car S arrive (new N) and go straight into
 * the car park<br>
 * 262::276::P:91::C:84::S:14::M:7::D:48::A:176::Q:9CCCCCCCCC|C:P>A||C:Q>P||S:N>
 * P|
 * 
 * @return String containing current state
 */

/** @author Joseph Salmond 8823928 */
public class CarPark {
	
	private int maxCarSpaces;
	private int maxSmallCarSpaces;
	private int maxMotorCycleSpaces;
	private int maxQueueSize;
	
	private int count;
	private int numCars;
	private int numMotorCycles;
	private int numSmallCars;
	private int numDissatisfied;
	private String status;
	private Vector<Vehicle> spaces;
	private ArrayDeque<Vehicle> queue;
	private Vector<Vehicle> past;

	
	// CarPark constructor sets the basic size parameters.
	public CarPark() {
		this.maxCarSpaces = Constants.DEFAULT_MAX_CAR_SPACES;
		this.maxSmallCarSpaces = Constants.DEFAULT_MAX_SMALL_CAR_SPACES;
		this.maxMotorCycleSpaces = Constants.DEFAULT_MAX_MOTORCYCLE_SPACES;
		this.maxQueueSize = Constants.DEFAULT_MAX_QUEUE_SIZE;

	}

	// CarPark constructor sets the basic size parameters.
	public CarPark(int maxCarSpaces, int maxSmallCarSpaces,
			int maxMotorCycleSpaces, int maxQueueSize) {
		this.maxCarSpaces = maxCarSpaces;
		this.maxSmallCarSpaces = maxSmallCarSpaces;
		this.maxMotorCycleSpaces = maxMotorCycleSpaces;
		this.maxQueueSize = maxQueueSize;

	}

	// Archives vehicles exiting the car park after a successful st
	public void archiveDepartingVehicles(int time, boolean force)
			throws VehicleException, SimulationException {
		// VehicleException - if vehicle to be archived is not in the correct
		// state
		// SimulationException - if one or more departing vehicles are not in
		// the car park when operation applied
	}

	// Method to archive new vehicles that don't get parked or queued and are
	// turned away
	public void archiveNewVehicle(Vehicle v) throws SimulationException {
		// SimulationException - if vehicle is currently queued or parked

	}

	// Archive vehicles which have stayed in the queue too long
	public void archiveQueueFailures(int time) throws VehicleException {
		// VehicleException - if one or more vehicles not in the correct state

	}

	// Simple status showing whether carPark is empty
	public boolean carParkEmpty() {
		boolean allGone = false; // temp value
		return allGone;
	}

	// Simple status showing whether carPark is full
	public boolean carParkFull() {
		boolean noMore = false;
		return noMore;
	}

	// Method to add vehicle successfully to the queue
	public void enterQueue(Vehicle v) throws SimulationException,
			VehicleException {
		// SimulationException - if queue is full
		// VehicleException - if vehicle not in the correct state
	}

	// Method to remove vehicle from the queue after which it will be parked or
	// removed altogether.
	public void exitQueue(Vehicle v, int exitTime) throws SimulationException,
			VehicleException {
		// SimulationException - if vehicle is not in car park or not in parked
		// state
		// VehicleException - if the vehicle is in a parked state or not in a
		// queued state, or if exitTime is not later than arrivalTime for this
		// vehicle
	}

	// State dump intended for use in logging the final state of the carpark
	public String finalState() {

		return "finalState";
	}

	// Simple getter for number of cars in the car park
	public int getNumCars() {
		int cars = 0; // Temp value
		return cars;
	}

	// Simple getter for number of motorcycles in the car park
	public int getNumMotorCycles() {
		int cycles = 0;// Temp value
		return cycles;
	}

	public int getNumSmallCars() {
		int minis = 0;// Temp value
		return minis;
	}

	// Method used to provide the current status of the car park.
	public String getStatus(int time) {
		String str = time + "::" + this.count + "::" + "P:"
				+ this.spaces.size() + "::" + "C:" + this.numCars + "::S:"
				+ this.numSmallCars + "::M:" + this.numMotorCycles + "::D:"
				+ this.numDissatisfied + "::A:" + this.past.size() + "::Q:"
				+ this.queue.size();
		for (Vehicle v : this.queue) {
			if (v instanceof Car) {
				if (((Car) v).isSmall()) {
					str += "S";
				} else {
					str += "C";
				}
			} else {
				str += "M";
			}
		}
		str += this.status;
		this.status = "";
		return str + "\n";
	}

	/**
	 * SAME COMMENTS State dump intended for use in logging the initial state of
	 * the carpark. Mainly concerned with parameters.
	 * 
	 * @return String containing dump of initial carpark state
	 */
	public String initialState() {
		return "CarPark [maxCarSpaces: " + this.maxCarSpaces
				+ " maxSmallCarSpaces: " + this.maxSmallCarSpaces
				+ " maxMotorCycleSpaces: " + this.maxMotorCycleSpaces
				+ " maxQueueSize: " + this.maxQueueSize + "]";
	}

	// Simple status showing number of vehicles in the queue
	public int numVehiclesInQueue() {
		int queuedVehicle = 0;// Temp value
		return queuedVehicle;
	}

	// Method to add vehicle successfully to the car park store
	public void parkVehicle(Vehicle v, int time, int intendedDuration)
			throws SimulationException, VehicleException {
		// SimulationException - if no suitable spaces are available for parking
		// VehicleException - if vehicle not in the correct state
	}

	// Silently process elements in the queue
	public void processQueue(int time, Simulator sim) throws VehicleException,
			SimulationException {
		// SimulationException - if no suitable spaces available when parking
		// attempted
		// VehicleException - if state is incorrect, or times violate
		// constraints
	}

	// Simple status showing whether queue is empty
	public boolean queueEmpty() {
		boolean empty = false;// Temp value
		return empty;
	}

	// Simple status showing whether queue is full
	public boolean queueFull() {
		boolean full = false;// Temp value
		return full;
	}

	// Method determines, given a vehicle of a particular type, whether there
	// are spaces available for that type in the car park under the parking
	// policy in the class header.
	public boolean spacesAvailable(Vehicle v) {
		boolean spaceAvalible = true;// Temp value
		return spaceAvalible;
	}

	// Overrides toString in class java.lang.Object
	@Override
	public String toString() {
		return "CarPark";
	}

	// Method to try to create new vehicles
	public void tryProcessNewVehicles(int time, Simulator sim)
			throws VehicleException, SimulationException {
		// SimulationException - if no suitable spaces available when operation
		// attempted
		// VehicleException - if vehicle creation violates constraints
	}
	
	//Method to remove vehicle from the carpark.
	public void unparkVehicle(Vehicle v,
            int departureTime)
              throws VehicleException,
                     SimulationException{
//		VehicleException - if Vehicle is not parked, is in a queue, or violates timing constraints
//		SimulationException - if vehicle is not in car park or in incorrect state or violates timing constraints
	}

}