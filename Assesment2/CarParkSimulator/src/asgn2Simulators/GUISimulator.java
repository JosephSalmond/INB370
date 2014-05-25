package asgn2Simulators;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;

@SuppressWarnings("serial")
public class GUISimulator extends ApplicationFrame implements ActionListener {
    private CarPark carPark;
    private Simulator sim;
    private Log log;

    /*
     * Add CLI args if no args then default if 10 args process else throw error
     * fill boxes with defaults || fill with 10 cli arfs
     * 
     * maxCarSpaces, maxMotorCycleSpaces, maxQueueSize >= 00 <=
     * maxSmallCarSpaces <= maxCarSpacescp check if validyou may deal with
     * invalid data by an error message via the text area or modal dialog
     * 
     * Text Box non negitive 0 <-> 10000 public static final int
     * DEFAULT_MAX_CAR_SPACES = 100; public static final int
     * DEFAULT_MAX_SMALL_CAR_SPACES = 30; public static final int
     * DEFAULT_MAX_MOTORCYCLE_SPACES = 20; public static final int
     * DEFAULT_MAX_QUEUE_SIZE = 10; public static final int DEFAULT_SEED = 100;
     * // 0 <-> Max_int - 1
     * 
     * Sliders public static final double DEFAULT_CAR_PROB = 1.0; public static
     * final double DEFAULT_SMALL_CAR_PROB = 0.20; public static final double
     * DEFAULT_MOTORCYCLE_PROB = 0.05; public static final double
     * DEFAULT_INTENDED_STAY_MEAN = 120.0; //1 <-> CLOSING_TIME
     */
    private static final String TITLE = "Z Car Park";
    private static GUISimulator demo;
    private TimeSeriesCollection tsc = new TimeSeriesCollection();
    private TimeSeries numToDate = new TimeSeries("Number To Date"); // black
    private TimeSeries numVehicles = new TimeSeries("Total Vehicles"); // blue
    private TimeSeries numCars = new TimeSeries("Current Cars"); // Cyan
    private TimeSeries numSmallCars = new TimeSeries("Current Small Cars"); // Grey
    private TimeSeries numMotorCycles = new TimeSeries("Current MotorCycles"); // DarkGrey
    private TimeSeries numInQueue = new TimeSeries("Current Queue"); // Yellow
    private TimeSeries numArchived = new TimeSeries("Number Archived"); // green
    private TimeSeries numDissatisfied = new TimeSeries("Number Disatisfied"); // red

    private Calendar cal = GregorianCalendar.getInstance();
    private int time = 0;
    
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel subPanel;
    private JTextArea textArea;
    private JButton btnGo;
    private JButton goToGraph;
    private JScrollPane scrollPane;
    
    @SuppressWarnings("unused")
    private Date timePoint;
    
    public GUISimulator(final String title, String[] args)
	    throws NoSuchFieldException, SecurityException {
	super(title);
	mainSetup(args);
	cal.set(2014, 0, 1, 6, time);
	timePoint = cal.getTime();

	mainFrame = new JFrame("DUMP");
	mainPanel = new JPanel();
	subPanel = new JPanel();
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mainFrame.add(mainPanel);
	mainPanel.setLayout(new BorderLayout());
	subPanel.setLayout(new GridBagLayout());
	textArea = new JTextArea(50, 100);
	scrollPane = new JScrollPane(textArea);
	mainPanel.add(scrollPane, BorderLayout.CENTER);
	mainPanel.add(subPanel, BorderLayout.WEST);

	btnGo = new JButton("Start");
	goToGraph = new JButton("Graph");
	btnGo.addActionListener(new AbstractAction("add") {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    demo.runSimulation();
		} catch (Exception e1) {
		    e1.printStackTrace();
		    System.exit(-1);
		}
	    }
	});
	goToGraph.addActionListener(new AbstractAction("add") {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		mainFrame.setVisible(false);
		demo.setVisible(true);
	    }
	});

	// add JLabel and JTextArea s
	GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 99;
	subPanel.add(btnGo, c);
	
	c.gridx = 1;
	c.gridy = 99;
	subPanel.add(goToGraph, c);
	
	textArea.setEditable(false);
	mainFrame.pack();
	mainFrame.setVisible(true);
    }

    /**
     * Method to run the simulation from start to finish. Exceptions are
     * propagated upwards from Vehicle, Simulation and Log objects as necessary
     * 
     * @throws VehicleException
     *             if Vehicle creation or operation constraints violated
     * @throws SimulationException
     *             if Simulation constraints are violated
     * @throws IOException
     *             on logging failures
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public void runSimulation() throws VehicleException, SimulationException,
	    IOException {
	this.log.initialEntry(this.carPark, this.sim);
	for (int time = 0; time <= Constants.CLOSING_TIME; time++) {
	    // queue elements exceed max waiting time
	    if (!this.carPark.queueEmpty()) {
		this.carPark.archiveQueueFailures(time);
	    }
	    // vehicles whose time has expired
	    if (!this.carPark.carParkEmpty()) {

		// force exit at closing time, otherwise normal
		boolean force = (time == Constants.CLOSING_TIME);
		this.carPark.archiveDepartingVehicles(time, force);
	    }
	    // attempt to clear the queue
	    if (!this.carPark.carParkFull()) {
		this.carPark.processQueue(time, this.sim);
	    }
	    // new vehicles from minute 1 until the last hour
	    if (newVehiclesAllowed(time)) {
		this.carPark.tryProcessNewVehicles(time, this.sim);
	    }
	    // Log progress

	    this.log.logEntry(time, this.carPark);
	    textOutput(time);
	    // read next line of log
	}
	System.out.println("THE END");
	this.log.finalise(this.carPark);
	doTheChartThings();
    }

    private void doTheChartThings() {
	final TimeSeriesCollection dataset = chartForge();
	JFreeChart chart = createChart(dataset);
	this.add(new ChartPanel(chart), BorderLayout.CENTER);
	JPanel btnPanel = new JPanel(new FlowLayout());
	this.add(btnPanel, BorderLayout.SOUTH);
    }

    private void textOutput(int time) {

	String currString = carPark.getStatus(time);
	textArea.append(currString);
	int toDate = findValue(currString, (time + ":+(\\d+):+"));
	int vehicleNum = findValue(currString, "P:+(\\d+):+");
	int cars = findValue(currString, "C:+(\\d+):+");
	int smallCars = findValue(currString, "S:+(\\d+):+");
	int motorCycles = findValue(currString, "M:+(\\d+):+");
	int numQueue = findValue(currString, "Q:+(\\d+)");
	int archive = findValue(currString, "A:+(\\d+):+");
	int dissatisfied = findValue(currString, "D:+(\\d+):+");
	// outputs to screen text

	// add to graph
	chartAddValues(toDate, vehicleNum, cars, smallCars, motorCycles,
		numQueue, archive, dissatisfied, time);
    }

    private int findValue(String currString, String expression) {
	Pattern r = Pattern.compile(expression);
	Matcher m = r.matcher(currString);
	m.find();
	String s = m.group(1);
	return Integer.parseInt(s);
    }

    private void chartAddValues(int toDate, int vehicleNum, int cars,
	    int smallCars, int motorCycles, int numQueue, int archive,
	    int dissatisfied, int time) {
	cal.set(2014, 0, 1, 6, time);
	Date timePoint = cal.getTime();
	numToDate.add(new Minute(timePoint), toDate);
	numVehicles.add(new Minute(timePoint), vehicleNum);
	numCars.add(new Minute(timePoint), cars);
	numSmallCars.add(new Minute(timePoint), smallCars);
	numMotorCycles.add(new Minute(timePoint), motorCycles);
	numInQueue.add(new Minute(timePoint), numQueue);
	numArchived.add(new Minute(timePoint), archive);
	numDissatisfied.add(new Minute(timePoint), dissatisfied);
    }

    private TimeSeriesCollection chartForge() {
	// With our powers combined
	tsc.addSeries(numToDate);
	tsc.addSeries(numVehicles);
	tsc.addSeries(numCars);
	tsc.addSeries(numSmallCars);
	tsc.addSeries(numMotorCycles);
	tsc.addSeries(numInQueue);
	tsc.addSeries(numArchived);
	tsc.addSeries(numDissatisfied);

	return tsc;
    }

    /**
     * Helper method to deliver the Chart - currently uses default colours and
     * auto range
     * 
     * @param dataset
     *            TimeSeriesCollection for plotting
     * @returns chart to be added to panel
     */
    private JFreeChart createChart(final XYDataset dataset) {
	final JFreeChart result = ChartFactory.createTimeSeriesChart(TITLE,
		"hh:mm:ss", "Vehicles", dataset, true, true, false);
	final XYPlot plot = result.getXYPlot();
	ValueAxis domain = plot.getDomainAxis();
	domain.setAutoRange(true);
	ValueAxis range = plot.getRangeAxis();
	range.setAutoRange(true);
	XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true,
		false);

	renderer.setAutoPopulateSeriesStroke(false);
	renderer.setBaseStroke(new BasicStroke(2));
	renderer.setSeriesPaint(0, Color.BLACK);
	renderer.setSeriesPaint(1, Color.BLUE);
	renderer.setSeriesPaint(2, Color.CYAN);
	renderer.setSeriesPaint(3, Color.LIGHT_GRAY);
	renderer.setSeriesPaint(4, Color.DARK_GRAY);
	renderer.setSeriesPaint(5, Color.YELLOW);
	renderer.setSeriesPaint(6, Color.GREEN);
	renderer.setSeriesPaint(7, Color.RED);
	plot.setRenderer(renderer);

	return result;
    }

    /**
     * Simple main GUI runner
     * 
     * @param args
     *            ignored
     */
    public static void main(final String[] args) {
	if (args.length == 0 || args.length == 10) {

	    EventQueue.invokeLater(new Runnable() {
		@Override
		public void run() {
		    demo = null;
		    try {
			demo = new GUISimulator(TITLE, args);
		    } catch (NoSuchFieldException e1) {
			e1.printStackTrace();
			System.exit(-1);
		    } catch (SecurityException e1) {
			e1.printStackTrace();
			System.exit(-1);
		    }

		    demo.setMinimumSize(new Dimension(1024, 720));
		    demo.pack();
		    RefineryUtilities.centerFrameOnScreen(demo);
		}
	    });
	} else {
	    System.exit(404);
	}
    }

    private void mainSetup(String[] args) throws NoSuchFieldException,
	    SecurityException {
	CarPark cp = null;
	Simulator s = null;
	Log l = null;
	cp = new CarPark();
	try {
	    s = new Simulator();
	    l = new Log();
	} catch (IOException | SimulationException e1) {
	    e1.printStackTrace();
	    System.exit(-1);
	}
	this.carPark = cp;
	this.sim = s;
	this.log = l;
    }

    /**
     * Helper method to determine if new vehicles are permitted
     * 
     * @param time
     *            int holding current simulation time
     * @return true if new vehicles permitted, false if not allowed due to
     *         simulation constraints.
     */
    private boolean newVehiclesAllowed(int time) {
	boolean allowed = (time >= 1);
	return allowed && (time <= (Constants.CLOSING_TIME - 60));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub

    }

}
