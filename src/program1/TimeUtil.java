package program1;

import java.util.Random;

/**
 * 
 * Implementation:
 * 
 * This class is a common utility for all threads to be able to tell their
 * work-time. It contains the math to do the proper distribution and mean.
 * 
 * @author matt
 *
 */
public class TimeUtil {

	private static final RandomGaussian gaussian = new RandomGaussian();

	private static final int PLANT_GROWTH_MEAN = 50000;
	private static final int PLANT_GROWTH_VARIANCE = 10000;

	private static final int TIME_UNITS_TO_GATHER = 2;

	private static final int TIME_UNITS_TO_GENERATE_ORDER = 120;

	// 60 units of time, with a standard deviation of 20 units,
	private static final int TIME_UNITS_TO_DELIVERY = 60;
	private static final int DELIVERY_VARIANCE = 20;

	private static final int CUSTOMER_TIME = 1000000;

	public static final int INITIAL_ORDER_WAIT_TIME = 100000;

	// How many plants in stash before we start planing more seeds.
	private static final int MINIMUM_STASH_THREASHOLD = 500;

	public static int getCustomerTime() {
		return CUSTOMER_TIME;
	}

	public static int getPlantGrowthTime() {
		synchronized (gaussian) {
			return (int) gaussian.getGaussian(PLANT_GROWTH_MEAN, PLANT_GROWTH_VARIANCE);
		}
	}

	public static int getGatherTime() {
		return TIME_UNITS_TO_GATHER;
	}

	public static int getOrderTime() {
		synchronized (gaussian) {
			return (int) gaussian.getExponentialDistribution(TIME_UNITS_TO_GENERATE_ORDER);
		}
	}

	public static int getDeliveryTime() {
		synchronized (gaussian) {
			return (int) gaussian.getGaussian(TIME_UNITS_TO_DELIVERY, DELIVERY_VARIANCE);
		}
	}

	public static int getMinimumStashThreashold() {
		return MINIMUM_STASH_THREASHOLD;
	}
}

/**
 * Class to help with the Gaussian distrubution plus the variance.
 * 
 * Shamelessly taken from here (and modified)
 * 
 * http://www.javapractices.com/topic/TopicAction.do?Id=62
 * http://www.javamex.com/tutorials/random_numbers/gaussian_distribution_2.shtml
 *
 */
class RandomGaussian {

	private Random fRandom = new Random();

	double getGaussian(double aMean, double aVariance) {

		double val = fRandom.nextGaussian() * aVariance + aMean;
		int time = (int) Math.round(val);
		if (time < 0) {
			time = time * -1;
		}
		return time;
	}

	/**
	 * To generate a variable x from an exponential distribution with mean a,
	 * generate a variable u from the U(0,1) distribution and compute the
	 * exponential variable as: x = (-a)*ln(u)
	 * 
	 * @return
	 */
	double getExponentialDistribution(double inRate) {
		double x= (-inRate)*(Math.log(fRandom.nextGaussian()));
		if (x < 0) {
			x = x*-1;
		}
		return x;
	}
}
