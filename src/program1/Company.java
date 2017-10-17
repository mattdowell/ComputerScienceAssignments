package program1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the main class. It instantiates all the objects in order and starts
 * the initial threads.
 * 
 * @author matt
 *
 */
public class Company {

	@SuppressWarnings("static-access")
	public static void main(String args[]) {
		Log log = null;
		System.out.println("Program started...");
		try {
			log = Log.getInstance();
			log.init();
			
			// Create the shared data
			final RipePumpkins ripes = new RipePumpkins(50000); // Ripened
																// pumpkins
			final Stash stash = new Stash(10000);
			final OrderList orders = new OrderList();
			
			// init the log
			log.stash = stash;
			log.orders = orders;
			log.ripes = ripes;

			// Plant the seeds
			PlantField field = new PlantField(ripes);

			// Start GATHERING
			Gatherer g = new Gatherer(field, ripes, stash);
			Thread jThread = new Thread(g);
			jThread.start();

			// Start the WEBSITE
			Website w = new Website(orders);
			Thread webT = new Thread(w);
			webT.start();

			// Start DELIVERING
			UPS u = new UPS(orders, field, stash);
			Thread upsT = new Thread(u);
			upsT.start();

			// Let customers order for this time...
			Thread.sleep(TimeUtil.getCustomerTime());

			// Kill the processes
			g.kill();
			w.kill();
			u.kill();

			Thread.sleep(300); // let the threads die
			Set<Integer> deliverTimes = u.getDeliverTimings();
			double mean = mean(deliverTimes);
			double stanDev = standardDev((int) mean, deliverTimes);
			log.write("\nThe mean of the delivery times is: " + mean(deliverTimes));
			log.write("The standard deviation is: " + stanDev);
			log.write("There are: " + stash.size() + " pumpkins in the stash.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			log.close();
			System.out.println("...program finished.");
			System.exit(0);
		}
	}

	/**
	 * To calculate the standard deviation of those numbers: 1. Work out the
	 * Mean (the simple average of the numbers) 2. Then for each number:
	 * subtract the Mean and square the result. 3. Then work out the mean of
	 * those squared differences. 4. Take the square root of that and we are
	 * done!
	 * 
	 * @param inMean
	 */
	public static double standardDev(int inMean, Collection<Integer> times) {
		Set<Integer> sds = new HashSet<>(times.size());
		for (Integer i : times) {
			int sd = i - inMean;
			sd = sd * sd;
			sds.add(sd);
		}
		double meanOfDiffs = mean(sds);
		return Math.sqrt(meanOfDiffs);
	}

	/**
	 * Calculate the mean. Taken from here:
	 * http://stackoverflow.com/questions/4191687/how-to-calculate-mean-median-
	 * mode-and-range-from-a-set-of-numbers
	 * 
	 * @param m
	 * @return
	 */
	public static double mean(Collection<Integer> m) {
		double sum = 0;
		for (Integer i : m) {
			sum += i;
		}
		return sum / m.size();
	}

}
