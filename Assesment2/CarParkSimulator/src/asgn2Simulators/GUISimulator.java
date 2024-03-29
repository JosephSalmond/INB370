package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


@SuppressWarnings("serial")
public class GUISimulator extends ApplicationFrame {
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
    int cars;
    int mc;
    TimeSeriesCollection tsc = new TimeSeriesCollection();
    TimeSeries numToDate = new TimeSeries("Number To Date"); // black
    TimeSeries numVehicles = new TimeSeries("Total Vehicles"); // blue
    TimeSeries numCars = new TimeSeries("Current Cars"); // cyan
    TimeSeries numSmallCars = new TimeSeries("Current Small Cars"); // Grey
    TimeSeries numMotorCycles = new TimeSeries("Current MotorCycles"); // DarkGrey
    TimeSeries numInQueue = new TimeSeries("Current Queue"); // Yellow
    TimeSeries numArchived = new TimeSeries("Number Archived"); // green
    TimeSeries numDissatisfied = new TimeSeries("Number Disatisfied");
    Calendar cal = GregorianCalendar.getInstance();
    int time = 0;
    Date timePoint;

    public GUISimulator(final String title) {
	super(title);
	cars = 0;
	mc = 0;
	cal.set(2014, 0, 1, 6, time);
	timePoint = cal.getTime();
	// run program
	chartAddValues();

	final TimeSeriesCollection dataset = chartForge();
	JFreeChart chart = createChart(dataset);
	this.add(new ChartPanel(chart), BorderLayout.CENTER);
	JPanel btnPanel = new JPanel(new FlowLayout());
	this.add(btnPanel, BorderLayout.SOUTH);

    }

    private void chartAddValues() {

	// pull from getStatus each tick
	// This is important - steal it shamelessly
	numToDate.add(new Minute(timePoint), mc);
	numVehicles.add(new Minute(timePoint), mc);
	numCars.add(new Minute(timePoint), mc);
	numSmallCars.add(new Minute(timePoint), mc);
	numMotorCycles.add(new Minute(timePoint), mc);
	numInQueue.add(new Minute(timePoint), mc);
	numArchived.add(new Minute(timePoint), mc);
	numDissatisfied.add(new Minute(timePoint), mc);
    }

    private TimeSeriesCollection chartForge() {
	// Collection
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
	return result;
    }

    /**
     * Simple main GUI runner
     * 
     * @param args
     *            ignored
     */
    public static void main(final String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		GUISimulator demo = new GUISimulator(TITLE);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	    }
	});
    }
}
