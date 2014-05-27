package asgn2Simulators;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;

/**
 * GUISimulator
 * 
 * @author Joseph Salmond 8823928
 * 
 */
@SuppressWarnings("serial")
public class GUISimulator extends ApplicationFrame implements ActionListener {
    private static CarPark carPark;
    private Simulator sim;
    private Log log;

    private static final String TITLE = "Car Park Statistics";
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
    int archive;
    int dissatisfied;

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel subPanel;
    private JTextArea textArea;
    private JButton btnGo;
    private JButton goToGraph;
    private JScrollPane scrollPane;

    private JTextField fieldMaxCarSpaces;
    private JTextField fieldMaxSmallCarSpaces;
    private JTextField fieldMaxMotorCycleSpaces;
    private JTextField fieldMaxQueueSize;
    private JTextField fieldRanSeed;
    private JTextField fieldStayMean;

    private JSlider staySdSlider;
    private JSlider motorCycleProbSlider;
    private JSlider smallCarProbSlider;
    private JSlider carProbSlider;
    private JSlider timeSlider;

    private JLabel labelCarProbNum;
    private JLabel labelSmallProbNum;
    private JLabel labelMotorCycleProbNum;
    private JLabel labelStaySdNum;
    private JLabel labelTimeNum;

    @SuppressWarnings("unused")
    // is used
    private Date timePoint;

    /**
     * GUISimulator constructor
     * 
     * @author Joseph Salmond 8823928
     * @param title
     * @param args
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    public GUISimulator(final String title, String[] args)
	    throws NoSuchFieldException, SecurityException {
	super(title);

	cal.set(2014, 0, 1, 6, time);
	timePoint = cal.getTime();

	// creates the console output window
	mainFrame = new JFrame("Log of Carpark");
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
	subPanel.setPreferredSize(new Dimension(400, 0));

	// add JLabel and JTextArea s

	createConsoleOutput(args);

    }

    /**
     * @author Joseph Salmond 8823928
     * @param args
     *            Command Line Args
     */
    private void createConsoleOutput(String[] args) {

	// Create Labels and there text fields
	JLabel labelMaxCarSpaces = new JLabel("Max Car Spaces");
	fieldMaxCarSpaces = new JTextField();
	JLabel labelMaxSmallCarSpaces = new JLabel("Max Small Car Spaces");
	fieldMaxSmallCarSpaces = new JTextField();
	JLabel labelMaxMotorCycleSpaces = new JLabel("Max MotorCyccle Spaces");
	fieldMaxMotorCycleSpaces = new JTextField();
	JLabel labelMaxQueueSize = new JLabel("Max Queue Size");
	fieldMaxQueueSize = new JTextField();
	JLabel labelRanSeed = new JLabel("Randomiser Seed");
	fieldRanSeed = new JTextField();
	JLabel labelStayMean = new JLabel("Mean Stay Time");
	fieldStayMean = new JTextField();
	JLabel spacerOne = new JLabel(" ");
	JLabel spacerTwo = new JLabel(" ");
	spacerOne.setPreferredSize(new Dimension(200, 0));
	spacerTwo.setPreferredSize(new Dimension(200, 0));

	// Create sliders
	JLabel labelTime = new JLabel("Lines Per Second");
	timeSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
	JLabel labelStaySd = new JLabel("Average Percent of Mean");
	staySdSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
	JLabel labelMotorCycleProb = new JLabel("MotorCycle Probability");
	motorCycleProbSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
	JLabel labelSmallProb = new JLabel("Small Car Probability");
	smallCarProbSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
	JLabel labelCarProb = new JLabel("All Car Probablitity");
	carProbSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);

	// set defaults
	if (args.length == 10) {
	    // maxCarSpaces maxSmallCarSpaces maxMotorCycleSpaces maxQueueSize
	    // seed carProb smallCarProb mcProb meanStay sdStay
	    fieldMaxCarSpaces.setText(args[0]);
	    fieldMaxSmallCarSpaces.setText(args[1]);
	    fieldMaxMotorCycleSpaces.setText(args[2]);
	    fieldMaxQueueSize.setText(args[3]);
	    fieldRanSeed.setText(args[4]);
	    carProbSlider.setValue(Integer.parseInt(args[5]));
	    smallCarProbSlider.setValue(Integer.parseInt(args[6]));
	    motorCycleProbSlider.setValue(Integer.parseInt(args[7]));
	    staySdSlider.setValue(Integer.parseInt(args[8]));
	    fieldStayMean.setText(args[9]);
	} else {
	    fieldMaxCarSpaces.setText("" + Constants.DEFAULT_MAX_CAR_SPACES);
	    fieldMaxSmallCarSpaces.setText(""
		    + Constants.DEFAULT_MAX_SMALL_CAR_SPACES);
	    fieldMaxMotorCycleSpaces.setText(""
		    + Constants.DEFAULT_MAX_MOTORCYCLE_SPACES);
	    fieldMaxQueueSize.setText("" + Constants.DEFAULT_MAX_QUEUE_SIZE);
	    fieldRanSeed.setText("" + Constants.DEFAULT_SEED);
	    carProbSlider.setValue((int) (Constants.DEFAULT_CAR_PROB * 100));
	    smallCarProbSlider
		    .setValue((int) (Constants.DEFAULT_SMALL_CAR_PROB * 100));
	    motorCycleProbSlider
		    .setValue((int) (Constants.DEFAULT_MOTORCYCLE_PROB * 100));
	    staySdSlider.setValue((int) (Constants.DEFAULT_INTENDED_STAY_SD
		    / Constants.DEFAULT_INTENDED_STAY_MEAN * 100));
	    fieldStayMean.setText("" + Constants.DEFAULT_INTENDED_STAY_MEAN);
	}

	// Create slider listeners
	timeSlider.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		int value = (int) source.getValue();
		labelTimeNum.setText(" " + value);

	    }
	});

	carProbSlider.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		int value = (int) source.getValue();
		labelCarProbNum.setText(value + "%");

	    }
	});

	smallCarProbSlider.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		int value = (int) source.getValue();
		labelSmallProbNum.setText(value + "%");

	    }
	});

	motorCycleProbSlider.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		int value = (int) source.getValue();
		labelMotorCycleProbNum.setText(value + "%");

	    }
	});

	staySdSlider.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		int value = (int) source.getValue();
		labelStaySdNum.setText(value + "%");

	    }
	});

	// set initial values to labels
	labelCarProbNum = new JLabel(carProbSlider.getValue() + "%");
	labelSmallProbNum = new JLabel(smallCarProbSlider.getValue() + "%");
	labelMotorCycleProbNum = new JLabel(motorCycleProbSlider.getValue()
		+ "%");
	labelStaySdNum = new JLabel(staySdSlider.getValue() + "%");
	labelTimeNum = new JLabel(" " + 1);
	timeSlider.setValue(1);

	// do setup things to the sliders
	timeSlider.setMajorTickSpacing(10);
	timeSlider.setMinorTickSpacing(1);
	timeSlider.setPaintTicks(true);

	carProbSlider.setMajorTickSpacing(10);
	carProbSlider.setMinorTickSpacing(1);
	carProbSlider.setPaintTicks(true);

	smallCarProbSlider.setMajorTickSpacing(10);
	smallCarProbSlider.setMinorTickSpacing(1);
	smallCarProbSlider.setPaintTicks(true);

	motorCycleProbSlider.setMajorTickSpacing(10);
	motorCycleProbSlider.setMinorTickSpacing(1);
	motorCycleProbSlider.setPaintTicks(true);

	staySdSlider.setMajorTickSpacing(10);
	staySdSlider.setMinorTickSpacing(1);
	staySdSlider.setPaintTicks(true);

	// create the buttons
	btnGo = new JButton("Start");
	goToGraph = new JButton("Graph");
	goToGraph.setEnabled(false);

	// start button
	btnGo.addActionListener(new AbstractAction("add") {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int MaximumTextArea = 1080;
		int MaxSlider = 100;
		int fmcs = Integer.parseInt(fieldMaxCarSpaces.getText());
		int fmscs = Integer.parseInt(fieldMaxSmallCarSpaces.getText());
		int fmmcs = Integer.parseInt(fieldMaxMotorCycleSpaces.getText());
		int fmqs = Integer.parseInt(fieldMaxQueueSize.getText());
		int frs = Integer.parseInt(fieldRanSeed.getText());
		double cp = (double) carProbSlider.getValue() / 100;
		double scp = (double) smallCarProbSlider.getValue() / 100;
		double mcp = (double) motorCycleProbSlider.getValue() / 100;
		double sss = (double) staySdSlider.getValue() / 100;
		double fsm = (double) Double.parseDouble(fieldStayMean
			.getText()) * sss;

		if ((fmcs >= 0 && fmcs <= MaximumTextArea)
			&& (fmscs >= 0 && fmscs <= MaximumTextArea)
			&& (fmmcs >= 0 && fmmcs <= MaximumTextArea)
			&& (fmqs >= 0 && fmqs <= MaximumTextArea)
			&& (frs >= 0 && frs <= MaximumTextArea)
			&& (fsm >= 0 && fsm <= MaximumTextArea)
			&& (cp >= 0 && cp <= MaximumTextArea)
			&& (scp >= 0 && scp <= MaxSlider)
			&& (mcp >= 0 && mcp <= MaxSlider)
			&& (sss >= 0 && sss <= MaximumTextArea)) {

		    if (fmcs >= fmscs) {

			double[] realARGS = { fmcs, fmscs, fmmcs, fmqs, frs,
				cp, scp, mcp, sss, fsm };

			disableThings();
			try {
			    mainSetup(realARGS);
			    demo.runSimulation();
			} catch (Exception e1) {
			    e1.printStackTrace();
			    System.exit(-1);
			}
		    } else {
			textArea.append("error: numbers are out of bounds MAX:1080");
		    }

		} else {
		    textArea.append("error: numbers are out of bounds MIN:0 MAX:1080");
		}

	    }

	    /**
	     * disables components
	     * 
	     * @author Joseph Salmond 8823928
	     */
	    private void disableThings() {
		btnGo.setEnabled(false);
		fieldMaxCarSpaces.setEnabled(false);
		fieldMaxSmallCarSpaces.setEnabled(false);
		fieldMaxMotorCycleSpaces.setEnabled(false);
		fieldMaxQueueSize.setEnabled(false);
		fieldRanSeed.setEnabled(false);
		carProbSlider.setEnabled(false);
		smallCarProbSlider.setEnabled(false);
		motorCycleProbSlider.setEnabled(false);
		staySdSlider.setEnabled(false);
		fieldStayMean.setEnabled(false);
	    }
	});

	// button to go to fraph
	goToGraph.addActionListener(new AbstractAction("add") {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		mainFrame.setVisible(false);
		demo.setVisible(true);
	    }
	});

	// start of gui building
	Insets padding = new Insets(10, 0, 0, 0);
	Insets noPadding = new Insets(0, 0, 0, 0);
	GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.HORIZONTAL;

	// spacers
	c.gridx = 0;
	c.gridy = 0;
	subPanel.add(spacerOne, c);
	c.gridx = 0;
	c.gridy = 1;
	subPanel.add(spacerTwo, c);

	// timer
	c.insets = padding;
	c.gridx = 0;
	c.gridy = 0;
	subPanel.add(labelTime, c);

	c.gridx = 1;
	c.gridy = 0;
	subPanel.add(labelTimeNum, c);

	c.insets = noPadding;
	c.gridwidth = 2;
	c.gridx = 0;
	c.gridy = 1;
	subPanel.add(timeSlider, c);

	// add labels + text fields
	c.insets = new Insets(30, 0, 0, 0);
	c.gridx = 0;
	c.gridy = 2;
	subPanel.add(labelMaxCarSpaces, c);

	c.gridx = 1;
	c.gridy = 2;
	subPanel.add(fieldMaxCarSpaces, c);
	c.insets = noPadding;
	c.gridx = 0;
	c.gridy = 3;
	subPanel.add(labelMaxSmallCarSpaces, c);

	c.gridx = 1;
	c.gridy = 3;
	subPanel.add(fieldMaxSmallCarSpaces, c);

	c.gridx = 0;
	c.gridy = 4;
	subPanel.add(labelMaxMotorCycleSpaces, c);

	c.gridx = 1;
	c.gridy = 4;
	subPanel.add(fieldMaxMotorCycleSpaces, c);

	c.gridx = 0;
	c.gridy = 5;
	subPanel.add(labelMaxQueueSize, c);

	c.gridx = 1;
	c.gridy = 5;
	subPanel.add(fieldMaxQueueSize, c);

	c.insets = padding;
	c.gridx = 0;
	c.gridy = 6;
	subPanel.add(labelRanSeed, c);

	c.gridx = 1;
	c.gridy = 6;
	subPanel.add(fieldRanSeed, c);

	c.insets = noPadding;
	c.gridx = 0;
	c.gridy = 7;
	subPanel.add(labelStayMean, c);

	c.gridx = 1;
	c.gridy = 7;
	subPanel.add(fieldStayMean, c);

	// percent of mean
	c.insets = padding;
	c.gridx = 0;
	c.gridy = 8;
	subPanel.add(labelStaySd, c);

	c.gridx = 1;
	c.gridy = 8;
	subPanel.add(labelStaySdNum, c);

	c.insets = noPadding;
	c.gridwidth = 2;
	c.gridx = 0;
	c.gridy = 9;
	subPanel.add(staySdSlider, c);

	// Car Probability
	c.insets = padding;
	c.gridx = 0;
	c.gridy = 10;
	subPanel.add(labelCarProb, c);

	c.gridx = 1;
	c.gridy = 10;
	subPanel.add(labelCarProbNum, c);

	c.insets = noPadding;
	c.gridwidth = 2;
	c.gridx = 0;
	c.gridy = 11;
	subPanel.add(carProbSlider, c);

	// small car Probability
	c.insets = padding;
	c.gridx = 0;
	c.gridy = 12;
	subPanel.add(labelSmallProb, c);

	c.gridx = 1;
	c.gridy = 12;
	subPanel.add(labelSmallProbNum, c);

	c.insets = noPadding;
	c.gridwidth = 2;
	c.gridx = 0;
	c.gridy = 13;
	subPanel.add(smallCarProbSlider, c);

	// Motorcycle Probability
	c.insets = padding;
	c.gridx = 0;
	c.gridy = 14;
	subPanel.add(labelMotorCycleProb, c);

	c.gridx = 1;
	c.gridy = 14;
	subPanel.add(labelMotorCycleProbNum, c);

	c.insets = noPadding;
	c.gridwidth = 2;
	c.gridx = 0;
	c.gridy = 15;
	subPanel.add(motorCycleProbSlider, c);

	// buttons
	c.gridwidth = 1;
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
     * creates thread to run the simulation on
     * 
     * @author Joseph Salmond 8823928
     * @throws VehicleException
     * @throws SimulationException
     * @throws IOException
     */
    public void runSimulation() throws VehicleException, SimulationException,
	    IOException {
	final Thread SimulatorThread = new Thread(new Runnable() {
	    public void run() {
		try {
		    runTheLoop();
		} catch (VehicleException | SimulationException
			| InterruptedException | IOException e) {

		    e.printStackTrace();
		}
	    }

	    /**
	     * this is here because Run() can't have the throws declaration
	     * 
	     * @Author JosephSalmond 8823928
	     */
	    private void runTheLoop() throws VehicleException,
		    SimulationException, InterruptedException, IOException {
		log.initialEntry(GUISimulator.carPark, sim);
		for (int time = 0; time <= Constants.CLOSING_TIME; time++) {
		    // queue elements exceed max waiting time
		    if (!carPark.queueEmpty()) {
			carPark.archiveQueueFailures(time);
		    }
		    // vehicles whose time has expired
		    if (!carPark.carParkEmpty()) {
			// force exit at closing time, otherwise normal
			boolean force = (time == Constants.CLOSING_TIME);
			carPark.archiveDepartingVehicles(time, force);
		    }
		    // attempt to clear the queue
		    if (!carPark.carParkFull()) {
			carPark.processQueue(time, sim);
		    }
		    // new vehicles from minute 1 until the last hour
		    if (newVehiclesAllowed(time)) {
			carPark.tryProcessNewVehicles(time, sim);
		    }
		    // Log progress
		    log.logEntry(time, carPark);
		    textOutput(time);
		    // read next line of log
		    synchronized (Thread.currentThread()) {
			Thread.currentThread().wait(
				1000 / timeSlider.getValue());
		    }
		}
		System.out.println("THE END");
		log.finalise(GUISimulator.carPark);
		doTheChartThings();
		goToGraph.setEnabled(true);

	    }

	});
	SimulatorThread.start();
    }

    /**
     * creates the graphs
     * 
     * @author Joseph Salmond 8823928
     */
    private void doTheChartThings() {
	final TimeSeriesCollection dataset = chartForge();
	JFreeChart chart = createChart(dataset);
	JFreeChart barChart = createChart(dissatisfied, archive);
	this.add(new ChartPanel(barChart), BorderLayout.CENTER);
	this.add(new ChartPanel(chart), BorderLayout.WEST);

    }

    /**
     * creates the text output
     * 
     * @author Joseph Salmond 8823928
     * @param time
     *            tick time
     */
    private void textOutput(int time) {

	String currString = carPark.getStatus(time);

	// finds values
	int toDate = findValue(currString, (time + ":+(\\d+):+"));
	int vehicleNum = findValue(currString, "P:+(\\d+):+");
	int cars = findValue(currString, "C:+(\\d+):+");
	int smallCars = findValue(currString, "S:+(\\d+):+");
	int motorCycles = findValue(currString, "M:+(\\d+):+");
	int numQueue = findValue(currString, "Q:+(\\d+)");
	archive = findValue(currString, "A:+(\\d+):+");
	dissatisfied = findValue(currString, "D:+(\\d+):+");

	// outputs to screen text
	textArea.append("Minute: " + time + "\nCars to Date: " + toDate
		+ "\nNumber of Vehicles Parked: " + vehicleNum
		+ "\nTotal Cars Parked: " + cars
		+ "\nNumber Small Cars Parked: " + smallCars
		+ "\nNumber MotorCycles Parked: " + motorCycles
		+ "\nNumber in Queue: " + numQueue
		+ "\nNumber Customers Dissatisfied: " + dissatisfied
		+ "\nNumber No Longer in Residance: " + archive + "\n\n");

	/*
	 * other text output format
	 * 
	 * textArea.append("Minute: " + time + " | Cars to Date: " + toDate +
	 * " | Number of Vehicles Parked: " + vehicleNum +
	 * "\nTotal Cars Parked: " + cars + " | Number Small Cars Parked: " +
	 * smallCars + " | Number MotorCycles Parked: " + motorCycles +
	 * "\nNumber in Queue: " + numQueue +
	 * "  | Number Customers Dissatisfied: " + dissatisfied +
	 * "  | Number No Longer in Residance: " + archive + "\n\n");
	 */

	textArea.setCaretPosition(textArea.getDocument().getLength());

	// add to graph
	chartAddValues(toDate, vehicleNum, cars, smallCars, motorCycles,
		numQueue, archive, dissatisfied, time);
    }

    /**
     * uses REGEX to find values
     * 
     * @author Joseph Salmond 8823928
     * @param currString
     * @param expression
     * @return
     */
    private int findValue(String currString, String expression) {
	Pattern r = Pattern.compile(expression);
	Matcher m = r.matcher(currString);
	m.find();
	String s = m.group(1);
	return Integer.parseInt(s);
    }

    /**
     * adds values to the Timeseries
     * 
     * @author Joseph Salmond 8823928
     * @param toDate
     * @param vehicleNum
     * @param cars
     * @param smallCars
     * @param motorCycles
     * @param numQueue
     * @param archive
     * @param dissatisfied
     * @param time
     */
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

    /**
     * adds all the series into the collection
     * 
     * @author Joseph Salmond 8823928
     * @return completed time series collection
     */
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
     * creates the line chart
     * 
     * @author Joseph Salmond 8823928
     * @param dataset
     * @return
     */
    private JFreeChart createChart(final XYDataset dataset) {
	final JFreeChart result = ChartFactory.createTimeSeriesChart(
		"Car Park Over Time", "hh:mm:ss", "Vehicles", dataset, true,
		true, false);
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
     * creates the bar graph
     * 
     * @author Joseph Salmond 8823928
     * @param dissatisfied
     * @param archived
     * @return
     */
    private JFreeChart createChart(int dissatisfiedNum, int archivedNum) {
	// create dataset
	final DefaultCategoryDataset chartDataSet = new DefaultCategoryDataset();
	final String series1 = "Total Customers";
	final String series2 = "Disatisfied Customers";
	final String comp = "";
	chartDataSet.addValue(archivedNum, series1, comp);
	chartDataSet.addValue(dissatisfiedNum, series2, comp);
	final CategoryDataset cDataSet = chartDataSet;

	// create the chart...
	final JFreeChart chart = ChartFactory.createBarChart("Customer Review",
		"Comparison", "Number of Customers", cDataSet,
		PlotOrientation.VERTICAL, true, false, false);

	return chart;
    }

    /**
     * Simple main GUI runner
     * 
     * @author Joseph Salmond 8823928
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
		    } catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		    }

		    demo.setMinimumSize(new Dimension(1080, 720));
		    demo.pack();
		    RefineryUtilities.centerFrameOnScreen(demo);
		}
	    });
	} else {
	    System.exit(404);
	}
    }

    /** main setup of the simulator
     * @author Joseph Salmond 8823928
     * @param realARGS
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    private void mainSetup(double[] realARGS) throws NoSuchFieldException,
	    SecurityException {

	CarPark cp = null;
	Simulator s = null;
	Log l = null;

	try {
	    cp = new CarPark((int) realARGS[0], (int) realARGS[1],
		    (int) realARGS[2], (int) realARGS[3]);
	    s = new Simulator((int) realARGS[4], realARGS[8], realARGS[9],
		    realARGS[5], realARGS[6], realARGS[7]);
	    l = new Log();
	} catch (IOException | SimulationException e1) {
	    e1.printStackTrace();
	    System.exit(-1);
	}
	GUISimulator.carPark = cp;
	this.sim = s;
	this.log = l;
    }

    /**
     * Helper method to determine if new vehicles are permitted
     *  @author James M. Hoagan
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
	// needed for compilation

    }

}
