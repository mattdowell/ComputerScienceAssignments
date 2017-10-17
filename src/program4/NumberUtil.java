package program4;

import java.util.Random;

/**
 * This class is responsible for returning valid random distributed numbers
 * where the application asks for them.
 * 
 * @author matt
 *
 */
public class NumberUtil {

	private static final RandomGaussian gaussian = new RandomGaussian();

	public static final int QUEUE_VALUE_MEAN = 1000;
	public static final int QUEUE_VALUE_VARIANCE = 100;

	public static final int ENQUEUE_MEAN = 1000;
	public static final int ENQUEUE_VARIANCE = 100;

	/**
	 * The value of an integer should have a random Gaussian distribution with a
	 * mean of 1000 ms.
	 * 
	 * @return
	 */
	public static int getRandomInteger() {
		synchronized (gaussian) {
			return (int) gaussian.getGaussian(QUEUE_VALUE_MEAN, QUEUE_VALUE_VARIANCE);
		}
	}

	/**
	 * Each enqueuing thread should try to enqueue randomly with a mean of 1000
	 * ms between attempts. (You can do this by sleeping a thread a random
	 * amount of time with a mean of 1 second.)
	 * 
	 * @return
	 */
	public static int getEnqueueSleepTime() {
		synchronized (gaussian) {
			return (int) gaussian.getGaussian(ENQUEUE_MEAN, ENQUEUE_VARIANCE);
		}
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
}
