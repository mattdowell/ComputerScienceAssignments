package program2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the main program for assignment 2, this class is responsible for
 * managing the threads and implementing the proper Lock, and reporting the
 * counter.
 * 
 * @author matt
 *
 */
public class Program {

	// Seconds for each group of threads to process the counter.
	private static final int SECONDS_TO_INC = 8;
	
	/**
	 * Main method, run and kill all the thread groups.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Log log = Log.getInstance();
			log.init();
			log.write("Lock Name, Number threads, Counter value, Run Millis");
			Counter bakeryCounter = new Counter();
			Counter ttasCounter = new Counter();
			Counter alockCounter = new Counter();

			/////////////////////// 2 Threads
			int numThreads = 2;
			int time = SECONDS_TO_INC * 1000;
			//// BAKERY
			Set<CounterThread> bakeryThreads = launchThreads(numThreads, new BakeryLock(20), bakeryCounter);
			Thread.sleep(time);
			killThreads(bakeryThreads);
			report("Bakery", numThreads, bakeryCounter.getCount(), time);
			
			///// TTAS
			Set<CounterThread> ttasThreads = launchThreads(numThreads, new TTASLock(), ttasCounter);
			Thread.sleep(time);
			killThreads(ttasThreads);
			report("TTASLock", numThreads, ttasCounter.getCount(), time);
			
			////// A LOCK
			Set<CounterThread> alockThreads = launchThreads(numThreads, new ALock(20), alockCounter);
			Thread.sleep(time);
			killThreads(alockThreads);
			report("ALock", numThreads, alockCounter.getCount(), time);
			
			//////////////////// 4 Threads
		    numThreads = 4;
			
			//// BAKERY
			bakeryThreads = launchThreads(numThreads, new BakeryLock(100), bakeryCounter);
			Thread.sleep(time);
			killThreads(bakeryThreads);
			report("Bakery", numThreads, bakeryCounter.getCount(), time);
			
			///// TTAS
			ttasThreads = launchThreads(numThreads, new TTASLock(), ttasCounter);
			Thread.sleep(time);
			killThreads(ttasThreads);
			report("TTASLock", numThreads, ttasCounter.getCount(), time);
			
			////// A LOCK
			alockThreads = launchThreads(numThreads, new ALock(100), alockCounter);
			Thread.sleep(time);
			killThreads(alockThreads);
			report("ALock", numThreads, alockCounter.getCount(), time);
			
			//////////////////// 6 Threads
		    numThreads = 6;
			
			//// BAKERY
			bakeryThreads = launchThreads(numThreads, new BakeryLock(100), bakeryCounter);
			Thread.sleep(time);
			killThreads(bakeryThreads);
			report("Bakery", numThreads, bakeryCounter.getCount(), time);
			
			///// TTAS
			ttasThreads = launchThreads(numThreads, new TTASLock(), ttasCounter);
			Thread.sleep(time);
			killThreads(ttasThreads);
			report("TTASLock", numThreads, ttasCounter.getCount(), time);
			
			////// A LOCK
			alockThreads = launchThreads(numThreads, new ALock(100), alockCounter);
			Thread.sleep(time);
			killThreads(alockThreads);
			report("ALock", numThreads, alockCounter.getCount(), time);
			
			
			//////////////////// 8 Threads
		    numThreads = 8;
		    //time = SECONDS_TO_INC * 500;
			//// BAKERY
			bakeryThreads = launchThreads(numThreads, new BakeryLock(100), bakeryCounter);
			Thread.sleep(time);
			killThreads(bakeryThreads);
			report("Bakery", numThreads, bakeryCounter.getCount(), time);
			
			///// TTAS
			ttasThreads = launchThreads(numThreads, new TTASLock(), ttasCounter);
			Thread.sleep(time);
			killThreads(ttasThreads);
			report("TTASLock", numThreads, ttasCounter.getCount(), time);
			
			////// A LOCK
			alockThreads = launchThreads(numThreads, new ALock(100), alockCounter);
			Thread.sleep(time);
			killThreads(alockThreads);
			report("ALock", numThreads, alockCounter.getCount(), time);
			
			
			//////////////////// 10 Threads
		    numThreads = 10;
			
			//// BAKERY
			bakeryThreads = launchThreads(numThreads, new BakeryLock(150), bakeryCounter);
			Thread.sleep(time);
			killThreads(bakeryThreads);
			report("Bakery", numThreads, bakeryCounter.getCount(), time);
			
			///// TTAS
			ttasThreads = launchThreads(numThreads, new TTASLock(), ttasCounter);
			Thread.sleep(time);
			killThreads(ttasThreads);
			report("TTASLock", numThreads, ttasCounter.getCount(), time);
			
			////// A LOCK
			alockThreads = launchThreads(numThreads, new ALock(150), alockCounter);
			Thread.sleep(time);
			killThreads(alockThreads);
			report("ALock", numThreads, alockCounter.getCount(), time);
			
			
			//////////////////// 14 Threads
		    numThreads = 14;
			
			//// BAKERY
			bakeryThreads = launchThreads(numThreads, new BakeryLock(150), bakeryCounter);
			Thread.sleep(time);
			killThreads(bakeryThreads);
			report("Bakery", numThreads, bakeryCounter.getCount(), time);
			
			///// TTAS
			ttasThreads = launchThreads(numThreads, new TTASLock(), ttasCounter);
			Thread.sleep(time);
			killThreads(ttasThreads);
			report("TTASLock", numThreads, ttasCounter.getCount(), time);
			
			////// A LOCK
			alockThreads = launchThreads(numThreads, new ALock(150), alockCounter);
			Thread.sleep(time);
			killThreads(alockThreads);
			report("ALock", numThreads, alockCounter.getCount(), time);
			
			
			//////////////////// 18 Threads
		    numThreads = 18;
			
			//// BAKERY
			bakeryThreads = launchThreads(numThreads, new BakeryLock(200), bakeryCounter);
			Thread.sleep(time);
			killThreads(bakeryThreads);
			report("Bakery", numThreads, bakeryCounter.getCount(), time);
			
			///// TTAS
			ttasThreads = launchThreads(numThreads, new TTASLock(), ttasCounter);
			Thread.sleep(time);
			killThreads(ttasThreads);
			report("TTASLock", numThreads, ttasCounter.getCount(), time);
			
			////// A LOCK
			alockThreads = launchThreads(numThreads, new ALock(200), alockCounter);
			Thread.sleep(time);
			killThreads(alockThreads);
			report("ALock", numThreads, alockCounter.getCount(), time);
			
			log.close();
			System.out.println("Done.");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Report an event to the Log
	 * @param lockName
	 * @param numThreads
	 * @param inCount
	 * @param time
	 */
	private static void report(String lockName, int numThreads, long inCount, int time) {
		String event = (lockName + "," + numThreads + "," + inCount + "," + time);
		Log.getInstance().write(event);
	}

	/**
	 * Launches the given CounterThread numThread instances.
	 * 
	 * @param numThreads
	 * @param inLock
	 * @param inCounter
	 * @return
	 */
	private static Set<CounterThread> launchThreads(int numThreads, SpinLock inLock, Counter inCounter) {
		Set<CounterThread> ret = new HashSet<CounterThread>(numThreads);
		for (int i = 0; i < numThreads; i++) {
			CounterThread t = new CounterThread(inLock, inCounter);
			t.start();
			ret.add(t);
		}
		return ret;
	}
	
	/**
	 * Kill the given threads. Iterate over the collection and call kill on all of them.
	 * 
	 * @param threads
	 */
	private static void killThreads(Collection<CounterThread> threads) {
		//System.out.println("Killing threads...");
		for (CounterThread ct : threads) {
			ct.kill();
		}
	}
}
