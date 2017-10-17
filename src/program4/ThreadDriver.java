package program4;

import java.util.HashSet;
import java.util.Set;

/**
 * This class takes a Queue and does the requested actions for the requested
 * number of threads.
 * 
 * The user should be able to specify how many enqueuing threads to instantiate.
 * Each enqueuing thread should try to enqueue randomly with a mean of 1000 ms
 * between attempts. (You can do this by sleeping a thread a random amount of
 * time with a mean of 1 second.)
 * 
 * The items enqueued should be Integers.
 * 
 * The value of an integer should have a random Gaussian distribution with a
 * mean of 1000 ms.
 * 
 * @author matt
 *
 */
public class ThreadDriver extends Thread {

	private int enqThreads;
	private int deqThreads;
	private int pushThreads;
	private int timeToLive;

	private BoundedQueue<Integer> queue;

	private Set<Killable> allThreads = new HashSet<>();
	private Log log = Log.getInstance();

	public ThreadDriver(int enqThreads, int deqThreads, int pushThreads, BoundedQueue<Integer> queue,
			int inTimeToLive) {
		super();
		this.enqThreads = enqThreads;
		this.deqThreads = deqThreads;
		this.pushThreads = pushThreads;
		this.timeToLive = inTimeToLive * 1000; // Seconds
		this.queue = queue;
	}

	public void start() {
		try {
			process();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method that spins up all the threads, sleeps for the requested
	 * amount of time, then kills the thread
	 * 
	 * @throws InterruptedException
	 */
	public void process() throws InterruptedException {

		for (int i = 0; i < enqThreads; i++) {
			EnqueueThread et = new EnqueueThread(queue);
			allThreads.add(et);
			et.start();
		}
		for (int i = 0; i < deqThreads; i++) {
			DequeueThread et = new DequeueThread(queue);
			allThreads.add(et);
			et.start();
		}
		for (int i = 0; i < pushThreads; i++) {
			PushThread et = new PushThread((BoundedDequeue<Integer>) queue);
			allThreads.add(et);
			et.start();
		}

		Thread.sleep(timeToLive);

		System.out.println("Items in queue: " + queue.size);
		for (Killable k : allThreads) {
			k.kill();
			System.out.println("Killed thread: " + k.getId());
		}
		System.out.println("Items in queue: " + queue.size);
	}

	/**
	 * The output should both appear on the console and be captured in a file.
	 * Each line should report one event. Each event will be of one of the
	 * following three forms:
	 * 
	 * @param tn
	 * @param event
	 * @param value
	 * @param time
	 */
	public void event(long tn, String event, Integer value, long time) {
		String full = "Thread " + tn + " " + event + " item " + value + " at time " + time;
		System.out.println(full);
		log.write(full);
	}

	/**
	 * This is a simple subclass of Thread that enqueues.
	 * 
	 * @author matt
	 *
	 */
	class EnqueueThread extends Thread implements Killable {

		private BoundedQueue<Integer> queue;
		private boolean alive = true;

		public EnqueueThread(BoundedQueue<Integer> queue) {
			super();
			this.queue = queue;
		}

		public void run() {
			while (alive) {
				Integer val = NumberUtil.getRandomInteger();
				this.queue.enq(val);
				event(super.getId(), "enqueued", val, System.currentTimeMillis());
				try {
					Thread.sleep(NumberUtil.getEnqueueSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void kill() {
			alive = false;
		}
	}

	/**
	 * This is a simple subclass of Thread that dequeue's per the
	 * specifications.
	 * 
	 * @author matt
	 *
	 */
	class DequeueThread extends Thread implements Killable {

		private BoundedQueue<Integer> queue;
		private boolean alive = true;

		public DequeueThread(BoundedQueue<Integer> queue) {
			super();
			this.queue = queue;
		}

		public void run() {
			while (alive) {
				Integer val = this.queue.deq();
				event(super.getId(), "dequeued", val, System.currentTimeMillis());
				try {
					Thread.sleep(val);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void kill() {
			alive = false;
		}
	}

	/**
	 * This is a simple subclass of Thread that pushes to the front of the
	 * queue.
	 * 
	 * @author matt
	 *
	 */
	class PushThread extends Thread implements Killable {

		private BoundedDequeue<Integer> queue;
		private boolean alive = true;

		public PushThread(BoundedDequeue<Integer> queue) {
			super();
			this.queue = queue;
		}

		public void run() {
			while (alive) {
				Integer val = NumberUtil.getRandomInteger();
				this.queue.pushToFront(val);
				event(super.getId(), "pushed", val, System.currentTimeMillis());
				try {
					Thread.sleep(val);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void kill() {
			alive = false;
		}
	}
}
