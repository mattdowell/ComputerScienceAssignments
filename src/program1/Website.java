package program1;

/**
 * Requirements:
 * 
 * Jack’s customers order pumpkins only through his website. 
 * Implementation
 * 
 * The website is a simple thread that generates order objects every couple time
 * units. The time is exponentially distrubuted, and it gets the time from the 
 * TimeUtil.
 * 
 * @author matt
 *
 */
public class Website implements Runnable {

	private final OrderList orders;
	private boolean alive = true;

	/**
	 * 
	 * @param inOrders
	 *            the collection of Orders
	 */
	public Website(OrderList inOrders) {
		orders = inOrders;
	}

	/**
	 * Main thread method.
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(TimeUtil.INITIAL_ORDER_WAIT_TIME);
			
			while (alive) {
				generateOrder();
				Thread.sleep(TimeUtil.getOrderTime());
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			kill();
		}
	}

	/**
	 * Adds an order to the OrderList
	 */
	private void generateOrder() {
		orders.add(new Order());
		Log.getInstance().event("customer places order");
	}

	public void kill() {
		alive = false;
	}
}
