package classfinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Final, ICS 440
 * 
 * @author matt
 *
 */
public class SharedBathroomProblemDowell {

	private static Bathroom bathroom = new Bathroom();
	private static volatile boolean isStopped = false;
	private static Random rnd = new Random();

	public static void main(String[] args) throws InterruptedException {
		System.out.println("[Start]");
		Producer producer = new Producer();
		producer.start();
		TimeUnit.SECONDS.sleep(10);
		isStopped = true;
		producer.join();
		System.out.println("[End]");
	}

	protected static class Producer extends Thread {

		@Override
		public void run() {
			try {
				List<Thread> threadList = new ArrayList<Thread>();
				while (!isStopped) {
					int val = rnd.nextInt(3);
					if (val == 2) {
						Male male = new Male();
						threadList.add(male);
						male.start();
					} else if (val == 1) {
						Female female = new Female();
						threadList.add(female);
						female.start();
					} else {
						Zombie z = new Zombie();
						threadList.add(z);
						z.start();
					}
					TimeUnit.MILLISECONDS.sleep(10);
				}
				for (Thread t : threadList) {
					t.join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected static class Female extends Thread {
		@Override
		public void run() {
			Bathroom.FemaleLock femaleLock = bathroom.getFemaleLock();
			try {
				femaleLock.enterFemale();
				TimeUnit.MILLISECONDS.sleep(rnd.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				femaleLock.leaveFemale();
			}
		}
	}

	protected static class Male extends Thread {
		@Override
		public void run() {
			Bathroom.MaleLock maleLock = bathroom.getMaleLock();
			try {
				maleLock.enterMale();
				TimeUnit.MILLISECONDS.sleep(rnd.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				maleLock.leaveMale();
			}
		}
	}

	/**
	 * Our new member..
	 * 
	 * @author matt
	 *
	 */
	protected static class Zombie extends Thread {
		@Override
		public void run() {
			Bathroom.ZombieLock zLock = bathroom.getZombieLock();
			try {
				zLock.enterZombie();
				TimeUnit.MILLISECONDS.sleep(rnd.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				zLock.leaveZombie();
			}
		}
	}

	protected static class Bathroom {

		private enum UsedBy {
			NO_ONE, MALES, FEMALES, ZOMBIE
		}

		private int males = 0;
		private int females = 0;
		// private int zombies = 0;
		private boolean maleTurn = false;
		private boolean femaleTurn = false;
		private boolean zombieTurn = false;
		private UsedBy usedBy = UsedBy.NO_ONE;

		private Lock lock = new ReentrantLock();
		private Condition maleCondition = lock.newCondition();
		private Condition femaleCondition = lock.newCondition();
		private Condition zombieCondition = lock.newCondition();
		private MaleLock malelock = new MaleLock();
		private FemaleLock femaleLock = new FemaleLock();
		private ZombieLock zombielock = new ZombieLock();

		public MaleLock getMaleLock() {
			return malelock;
		}

		public ZombieLock getZombieLock() {
			return zombielock;
		}

		public FemaleLock getFemaleLock() {
			return femaleLock;
		}

		/**
		 * Make lock.
		 * 
		 * @author matt
		 *
		 */
		protected class MaleLock {

			public void enterMale() throws InterruptedException {
				lock.lock();
				try {
					maleTurn = true;
					while (femaleTurn || zombieTurn) {
						maleCondition.await();
					}
					while (usedBy == UsedBy.FEMALES || usedBy == UsedBy.ZOMBIE) {
						maleCondition.await();
					}
					usedBy = UsedBy.MALES;
					males++;
					System.out.println("[Male # " + males + " enter bathroom]");
				} finally {
					lock.unlock();
				}
			}

			public void leaveMale() {
				lock.lock();
				try {
					System.out.println("[Male # " + males + " leave bathroom]");
					males--;
					if (males == 0) {
						maleTurn = false;
						usedBy = UsedBy.NO_ONE;
						System.out.println("[All males leave bathroom]");

						zombieCondition.signalAll();
						femaleCondition.signalAll();
					}
				} finally {
					lock.unlock();
				}
			}
		}

		/**
		 * Female lock.
		 * 
		 * @author matt
		 *
		 */
		protected class FemaleLock {

			public void enterFemale() throws InterruptedException {
				lock.lock();
				try {
					while (maleTurn || zombieTurn) {
						femaleCondition.await();
					}
					femaleTurn = true;
					while (usedBy == UsedBy.MALES || usedBy == UsedBy.ZOMBIE) {
						femaleCondition.await();
					}
					usedBy = UsedBy.FEMALES;
					females++;
					System.out.println("[Female # " + females + " enter bathroom]");
				} finally {
					lock.unlock();
				}
			}

			public void leaveFemale() {
				lock.lock();
				try {
					System.out.println("[Female # " + females + " leave bathroom]");
					females--;
					if (females == 0) {
						femaleTurn = false;
						usedBy = UsedBy.NO_ONE;
						System.out.println("[All females leave bathroom]");
						zombieCondition.signalAll();
						maleCondition.signalAll();
					}
				} finally {
					lock.unlock();
				}
			}
		}

		/**
		 * Zombie lock, can be used by only one Zombie at a time. We check to
		 * make sure other zombies are not using the bathroom.
		 * 
		 * @author matt
		 *
		 */
		protected class ZombieLock {

			public void enterZombie() throws InterruptedException {
				lock.lock();
				try {
					while (maleTurn || femaleTurn || zombieTurn) {
						zombieCondition.await();
					}
					zombieTurn = true;
					while (usedBy == UsedBy.MALES || usedBy == UsedBy.FEMALES || usedBy == UsedBy.ZOMBIE) {
						zombieCondition.await();
					}
					usedBy = UsedBy.ZOMBIE;
					System.out.println("[Zombie enters bathroom]");
				} finally {
					lock.unlock();
				}
			}

			public void leaveZombie() {
				lock.lock();
				try {
					System.out.println("[Zombie leaves bathroom]");
					zombieTurn = false;
					usedBy = UsedBy.NO_ONE;

					zombieCondition.signalAll();
					maleCondition.signalAll();
					femaleCondition.signalAll();
				} finally {
					lock.unlock();
				}
			}
		}
	}
}