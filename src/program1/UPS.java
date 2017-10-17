package program1;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author matt
 * 
 * Implementation
 * 
 * This class is a thread that works relatively quickly. It checks the
 * Order collection and if there is an order, it checks to see if there
 * is a pumpkin that we can take off the stash. If both exists, it
 * removes both.
 * 
 * It is also responsible for checking the Stash level and if we can,
 * plant more plants once the level gets below 1000.
 *
 */
public class UPS implements Runnable {

	private boolean alive = true;
	private boolean hasServedCustomer = false;
	private final OrderList orders;
	private final PlantField field;
	private final Stash stash;
	private final Set<Integer> timings = new HashSet<>(10000);

	/**
	 * The constructor for our delivery thread has a handle on the Orders and
	 * can also talk to the field in case we need to plant more pumpkin plants
	 * to increase production.
	 * 
	 * @param orders
	 *            OrderList collection
	 * @param field
	 *            PlantField, to be able to plant more plants
	 * @param stash
	 *            Stash collection we deliver from
	 */
	public UPS(OrderList orders, PlantField field, Stash stash) {
		super();
		this.orders = orders;
		this.field = field;
		this.stash = stash;
	}

	/**
	 * Checks to see if the stash is low, and if so we plant more plants. We
	 * cant plant more plants unless we have a pumpkin to take seeds from.
	 */
	private void checkStash() {
		if (hasServedCustomer) {
			if (stash.size() <= TimeUtil.getMinimumStashThreashold()) {

				// If there is a pumpkin available, take seeds from it.
				if (stash.pop() != null) {
					Log.getInstance().event("Stash is low, adding plants");
					field.plant(4);
				}
			}
		}
	}

	/**
	 * Deliver up to 50 orders at a time.. Pop an order off the stack, and if we
	 * have a pumpkin in the stash, ship it, otherwise place it back on the
	 * stack.
	 */
	private void deliver(long startTime) {
		if (stash.size() > 0 && orders.size() > 0) {
			int i = 0;
			for (; i < 50; i++) {
				Order o = orders.pop();
				if (o != null) {
					if (stash.pop() != null) {
						hasServedCustomer = true;
					} else {

						Log.getInstance().debug("Cannot pop a pumpkin from stash");
						// We dont have a pumpkin to deliver, so put the order
						// back.
						orders.add(o);
					}
				} else {
					Log.getInstance().event("Devliered all orders");
					break;
				}
			}
			long endTime = System.currentTimeMillis();
			long deliverTime = (endTime - startTime);
			Log.getInstance().event(++i + " order(s) delivered in: " + deliverTime + " TU's");
			timings.add((int) deliverTime);
		}
	}

	/**
	 * Thread run method that calls the deliver and checkStash methods.
	 */
	@Override
	public void run() {
		while (alive) {
			long startTime = System.currentTimeMillis();
			try {
				Thread.sleep(TimeUtil.getDeliveryTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
				kill();
			}
			deliver(startTime);
			checkStash();
		}
	}

	public void kill() {
		alive = false;
	}

	public Set<Integer> getDeliverTimings() {
		return this.timings;
	}

}
