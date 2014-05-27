package asgn2CarParks;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Vector;

import asgn2Exceptions.VehicleException;
import asgn2Exceptions.SimulationException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;

/**
 * Car Park Class
 * 
 * @author Joseph Salmond 8823928
 * 
 */
public class CarPark {

    private enum spaceType {
	NONE, MOTORCYCLE, SMALLCAR, CAR
    };

    // need to do transitions
    private spaceType space;

    private int maxCarSpaces;
    private int maxSmallCarSpaces;
    private int maxMotorCycleSpaces;
    private int maxQueueSize;

    private int count;
    private int numCars;
    private int numMotorCycles;
    private int numSmallCars;
    private int numCarSpaces;
    private int numSmallCarSpaces;
    private int numMotorCycleSpaces;
    private int numDissatisfied;

    private String status = "";
    private String transitions = "";
    private Vector<Vehicle> spaces = new Vector<Vehicle>();
    private Vector<Vehicle> carSpaces = new Vector<Vehicle>();
    private Vector<Vehicle> smallCarSpaces = new Vector<Vehicle>();
    private Vector<Vehicle> motorCycleSpaces = new Vector<Vehicle>();
    private ArrayDeque<Vehicle> queue = new ArrayDeque<Vehicle>();
    private Vector<Vehicle> past = new Vector<Vehicle>();

    /**
     * CarPark constructor sets the basic size parameters.
     * 
     * @author Joseph Salmond 8823928
     */
    public CarPark() {
	this.maxCarSpaces = Constants.DEFAULT_MAX_CAR_SPACES;
	this.maxSmallCarSpaces = Constants.DEFAULT_MAX_SMALL_CAR_SPACES;
	this.maxMotorCycleSpaces = Constants.DEFAULT_MAX_MOTORCYCLE_SPACES;
	this.maxQueueSize = Constants.DEFAULT_MAX_QUEUE_SIZE;

    }

    /**
     * CarPark constructor sets the basic size parameters.
     * 
     * @author Joseph Salmond 8823928
     * @param maxCarSpaces
     * @param maxSmallCarSpaces
     * @param maxMotorCycleSpaces
     * @param maxQueueSize
     */
    public CarPark(int maxCarSpaces, int maxSmallCarSpaces,
	    int maxMotorCycleSpaces, int maxQueueSize) {
	this.maxCarSpaces = maxCarSpaces;
	this.maxSmallCarSpaces = maxSmallCarSpaces;
	this.maxMotorCycleSpaces = maxMotorCycleSpaces;
	this.maxQueueSize = maxQueueSize;

    }

    /**
     * Archives vehicles exiting the car park after a successful Stay
     * 
     * @author Joseph Salmond 8823928
     * @param time
     * @param force
     * @throws VehicleException
     * @throws SimulationException
     */
    public void archiveDepartingVehicles(int time, boolean force)
	    throws VehicleException, SimulationException {
	Iterator<Vehicle> iter;
	if (force) {
	    if (!spaces.isEmpty()) {
		iter = spaces.iterator();
		while (iter.hasNext()) {
		    Vehicle v = iter.next();
		    this.unparkVehicle(v, time);
		    past.add(v);
		    transitionVehicle(v);
		    iter.remove();
		}
	    }
	    if (!smallCarSpaces.isEmpty()) {
		iter = smallCarSpaces.iterator();
		while (iter.hasNext()) {
		    iter.next();
		    iter.remove();
		}
	    }
	    if (!carSpaces.isEmpty()) {
		iter = carSpaces.iterator();
		while (iter.hasNext()) {
		    iter.next();
		    iter.remove();
		}
	    }
	    if (!motorCycleSpaces.isEmpty()) {
		iter = motorCycleSpaces.iterator();
		while (iter.hasNext()) {
		    iter.next();
		    iter.remove();
		}
	    }
	    if (!queue.isEmpty()) {
		iter = queue.iterator();
		while (iter.hasNext()) {
		    Vehicle v = iter.next();
		    v.exitQueuedState(time);
		    past.add(v);
		    iter.remove();
		    transitions += "|" + vehicleString(v) + ":Q>A|";
		    numDissatisfied++;
		}
	    }

	} else {

	    if (!spaces.isEmpty()) {
		iter = spaces.iterator();
		while (iter.hasNext()) {
		    Vehicle v = iter.next();
		    if (v.getDepartureTime() == time) {
			if (time == 362) {
			    System.out.println(" ");
			}
			this.unparkVehicle(v, time);
			past.add(v);
			transitionVehicle(v);
			iter.remove();
		    }

		}

	    }
	    if (!smallCarSpaces.isEmpty()) {
		iter = smallCarSpaces.iterator();
		while (iter.hasNext()) {
		    Vehicle v = iter.next();
		    if (v.getDepartureTime() == time) {
			iter.remove();
		    }
		}
	    }
	    if (!carSpaces.isEmpty()) {
		iter = carSpaces.iterator();
		while (iter.hasNext()) {
		    Vehicle v = iter.next();
		    if (v.getDepartureTime() == time) {
			iter.remove();
		    }
		}
	    }

	    if (!motorCycleSpaces.isEmpty()) {
		iter = motorCycleSpaces.iterator();
		while (iter.hasNext()) {
		    Vehicle v = iter.next();
		    if (v.getDepartureTime() == time) {
			iter.remove();
		    }
		}
	    }

	}
    }

    /**
     * helper
     * 
     * @author Joseph Salmond 8823928
     * @param v
     */
    private void transitionVehicle(Vehicle v) {
	transitions += "|" + vehicleString(v) + ":P>A|";
    }

    /**
     * Method to archive new vehicles that don't get parked or queued and are
     * turned away
     * 
     * @author Joseph Salmond 8823928
     * @param v
     * @throws SimulationException
     */
    public void archiveNewVehicle(Vehicle v) throws SimulationException {
	if (v.isParked() || v.isQueued()) {
	    throw new SimulationException("archiveNewVehicle: ");
	}

	past.add(v);
	numDissatisfied++;
    }

    /**
     * Archive vehicles which have stayed in the queue too long
     * 
     * @author Joseph Salmond 8823928
     * @param time
     * @throws VehicleException
     */
    public void archiveQueueFailures(int time) throws VehicleException {
	// VehicleException - if one or more vehicles not in the correct state

	Iterator<Vehicle> iter = queue.iterator();
	while (iter.hasNext()) {
	    Vehicle v = iter.next();
	    if (time - v.getArrivalTime() > Constants.MAXIMUM_QUEUE_TIME) {
		v.exitQueuedState(time);
		past.add(v);
		iter.remove();
		transitions += "|" + vehicleString(v) + ":Q>A|";
		numDissatisfied++;
	    }
	}

    }

    /**
     * Simple status showing whether carPark is empty
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public boolean carParkEmpty() {
	return ((numCarSpaces + numMotorCycleSpaces) == 0);
    }

    /**
     * Simple status showing whether carPark is full
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public boolean carParkFull() {
	return (numCarSpaces + numSmallCarSpaces + numMotorCycleSpaces) == (maxCarSpaces + maxMotorCycleSpaces);
    }

    /**
     * Method to add vehicle successfully to the queue
     * 
     * @author Joseph Salmond 8823928
     * @param v
     * @throws SimulationException
     * @throws VehicleException
     */
    public void enterQueue(Vehicle v) throws SimulationException,
	    VehicleException {
	if (queue.size() == maxQueueSize) {
	    throw new SimulationException("enterQueue: Queue Full");
	}
	v.enterQueuedState();
	queue.add(v);

    }

    /**
     * Method to remove vehicle from the queue after which it will be parked or
     * removed altogether.
     * 
     * @author Joseph Salmond 8823928
     * @param v
     * @param exitTime
     * @throws SimulationException
     * @throws VehicleException
     */
    public void exitQueue(Vehicle v, int exitTime) throws SimulationException,
	    VehicleException {
	if (!queue.contains(v)) {
	    throw new SimulationException("exitQueue: ");
	} else if (v.isParked() || !v.isQueued()
		|| (v.getArrivalTime() > exitTime)) {
	    throw new VehicleException("exitQueue: ");
	}

	v.exitQueuedState(exitTime);
	queue.remove(v);
    }

    /**
     * State dump intended for use in logging the final state of the carpark
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public String finalState() {
	String str = "Vehicles Processed: count: " + count + ", logged: "
		+ past.size() + "\nVehicle Record: \n";
	str += this.toString();
	return str;
    }

    /**
     * Simple getter for number of cars in the car park
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public int getNumCars() {
	return numCars;
    }

    /**
     * Simple getter for number of motorcycles in the car park
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public int getNumMotorCycles() {
	return numMotorCycles;
    }

    /**
     * Simple getter for number of small cars in carpark
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public int getNumSmallCars() {
	return numSmallCars;
    }

    /**
     * creates string representation of the carpark
     * 
     * @author Joseph Salmond 8823928
     * @param time
     *            tick time
     * @return the string representation of the state of the carpark at a
     *         certain time
     */
    public String getStatus(int time) {
	String str = time
		+ "::"
		+ this.count
		+ "::"
		+ "P:"
		+ (this.carSpaces.size() + this.motorCycleSpaces.size() + this.smallCarSpaces
			.size()) + "::" + "C:"
		+ (this.numCars + this.numSmallCars) + "::S:"
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
	str += this.status + this.transitions;
	this.status = "";
	this.transitions = "";
	return str + "\n";
    }

    /**
     * State dump intended for use in logging the initial state of the carpark.
     * 
     * @Author Joseph Salmond 8823928
     * @return String containing dump of initial carpark state
     */
    public String initialState() {
	return "CarPark [maxCarSpaces: " + this.maxCarSpaces
		+ " maxSmallCarSpaces: " + this.maxSmallCarSpaces
		+ " maxMotorCycleSpaces: " + this.maxMotorCycleSpaces
		+ " maxQueueSize: " + this.maxQueueSize + "]";
    }

    // Simple status showing number of vehicles in the queue
    /**
     * @author Joseph Salmond 8823928
     * @return
     */
    public int numVehiclesInQueue() {
	return this.queue.size();
    }

    /**
     * Method to add vehicle successfully to the car park store
     * 
     * @author Joseph Salmond 8823928
     * @param v
     * @param time
     * @param intendedDuration
     * @throws SimulationException
     * @throws VehicleException
     */
    public void parkVehicle(Vehicle v, int time, int intendedDuration)
	    throws SimulationException, VehicleException {

	if (!this.spacesAvailable(v)) {
	    throw new SimulationException("parkVehicle: ");
	} else if (v.isParked()) { // or timing constraints are violated
	    throw new VehicleException("parkVehicle: ");
	}

	v.enterParkedState(time, intendedDuration);
	this.spacesAvailable(v);

	if (space == spaceType.MOTORCYCLE) {
	    numMotorCycleSpaces++;
	    motorCycleSpaces.add(v);
	} else if (space == spaceType.SMALLCAR) {
	    numSmallCarSpaces++;
	    smallCarSpaces.add(v);
	} else if (space == spaceType.CAR) {
	    numCarSpaces++;
	    carSpaces.add(v);
	}
	spaces.add(v);

	if (v instanceof MotorCycle) {
	    numMotorCycles++;
	} else if (v instanceof Car) {
	    if (((Car) v).isSmall()) {
		numSmallCars++;

	    } else {
		numCars++;
	    }
	}
    }

    /**
     * Silently process elements in the queue
     * 
     * @author Joseph Salmond 8823928
     */
    public void processQueue(int time, Simulator sim) throws VehicleException,
	    SimulationException {
	if (!this.queueEmpty()) {
	    Vehicle v = queue.peekFirst();

	    if (this.spacesAvailable(v)) {
		v.exitQueuedState(time);
		this.parkVehicle(v, time, sim.setDuration());
		transitions += "|" + vehicleString(v) + ":Q>P|";
		queue.pop();
		processQueue(time, sim);
	    }

	}

    }

    /**
     * Simple status showing whether queue is empty
     * 
     * @author Joseph Salmond 8823928
     * @return
     */
    public boolean queueEmpty() {
	return this.queue.isEmpty();
    }

    /**
     * Simple status showing whether queue is full
     * 
     * @Author Joseph Salmond 8823928
     * @return
     */
    public boolean queueFull() {
	return (this.queue.size() == maxQueueSize);
    }

    /**
     * Method determines, given a vehicle of a particular type, whether there
     * are spaces available for that type in the car park under the parking
     * policy in the class header.
     * 
     * @Author Joseph Salmond 8823928
     * @param v
     * @return
     */
    public boolean spacesAvailable(Vehicle v) {
	space = spaceType.NONE;
	boolean spaces = false;

	if (v instanceof MotorCycle) {
	    if (numMotorCycleSpaces < maxMotorCycleSpaces) {
		spaces = true;
		space = spaceType.MOTORCYCLE;
	    } else if (numSmallCarSpaces < maxSmallCarSpaces) {
		spaces = true;
		space = spaceType.SMALLCAR;
	    }
	} else if (v instanceof Car) {
	    if (((Car) v).isSmall()) {
		if (numSmallCarSpaces < maxSmallCarSpaces) {
		    spaces = true;
		    space = spaceType.SMALLCAR;
		} else if (numCarSpaces < maxCarSpaces - maxSmallCarSpaces) {
		    spaces = true;
		    space = spaceType.CAR;
		}
	    } else if (numCarSpaces < maxCarSpaces - maxSmallCarSpaces) {
		spaces = true;
		space = spaceType.CAR;
	    }
	}

	return spaces;
    }

    /**
     * Overrides toString in class java.lang.Object
     * 
     * @author Joseph Salmond 8823928
     */
    @Override
    public String toString() {
	String str = "\n";
	for (Vehicle v : past) {
	    str += v.toString() + "\n";
	}

	return str;
    }

    /**
     * Method to try to create new vehicles
     * 
     * @author Joseph Salmond 8823928
     * @param time
     * @param sim
     * @throws VehicleException
     * @throws SimulationException
     */
    public void tryProcessNewVehicles(int time, Simulator sim)
	    throws VehicleException, SimulationException {
	Vehicle newVehicle = null;
	if (sim.newCarTrial()) {
	    if (sim.smallCarTrial()) {
		String id = "C" + (count + 1);
		newVehicle = new Car(id, time, true);// create small car
		createVehicle(time, sim, newVehicle);
	    } else {
		String id = "C" + (count + 1);
		newVehicle = new Car(id, time, false);
		createVehicle(time, sim, newVehicle);
	    }
	}

	if (sim.motorCycleTrial()) {
	    String id = "MC" + (count + 1);
	    newVehicle = new MotorCycle(id, time);
	    createVehicle(time, sim, newVehicle);
	}

    }

    /**
     * helper method for tryProcessNewVehicles
     * 
     * @author Joseph Salmond 8823928
     * @param time
     * @param sim
     * @param newVehicle
     * @throws SimulationException
     * @throws VehicleException
     */
    private void createVehicle(int time, Simulator sim, Vehicle newVehicle)
	    throws SimulationException, VehicleException {

	if (newVehicle != null && this.spacesAvailable(newVehicle)) {
	    this.parkVehicle(newVehicle, time, sim.setDuration());
	    count++;
	    transitions += "|" + vehicleString(newVehicle) + ":N>P|";
	} else if (newVehicle != null && !this.queueFull()) {
	    this.enterQueue(newVehicle);
	    count++;
	    transitions += "|" + vehicleString(newVehicle) + ":N>Q|";
	} else if (newVehicle != null) {
	    this.archiveNewVehicle(newVehicle);
	    count++;
	    transitions += "|" + vehicleString(newVehicle) + ":N>A|";
	}

    }

    /**
     * Method to remove vehicle from the carpark.
     * 
     * @author Joseph Salmond 8823928
     * @param v
     * @param departureTime
     * @throws VehicleException
     * @throws SimulationException
     */
    public void unparkVehicle(Vehicle v, int departureTime)
	    throws VehicleException, SimulationException {
	if (!v.isParked()) {
	    throw new VehicleException(
		    "unparkVehicle: cant leave the carpark when not parked");
	} else if (v.isQueued()) {
	    throw new VehicleException("unparkVehicle: Error Queued");
	}

	v.exitParkedState(departureTime);

	if (motorCycleSpaces.contains(v)) {
	    numMotorCycleSpaces--;
	} else if (smallCarSpaces.contains(v)) {
	    numSmallCarSpaces--;
	} else if (carSpaces.contains(v)) {
	    numCarSpaces--;
	}

	if (v instanceof MotorCycle) {
	    numMotorCycles--;
	} else if (v instanceof Car) {
	    if (((Car) v).isSmall()) {
		numSmallCars--;
	    } else {
		numCars--;
	    }
	}

    }

    /**
     * Helper method to get the string representation of Vehicle type
     * 
     * @author Joseph Salmond 8823928
     * @param v
     * @return
     */
    private String vehicleString(Vehicle v) {
	String str = "";
	if (v instanceof MotorCycle) {
	    str = "M";
	} else if (v instanceof Car) {
	    if (((Car) v).isSmall()) {
		str = "S";
	    } else {
		str = "C";
	    }
	}
	return str;
    }

}
