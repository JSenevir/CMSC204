/**
 * The WarehouseSimulation class implements the SimulationInterface interface.
 Represents a warehouse simulation. Receives and ships orders and keeps track of totals.
 */
public class WarehouseSimulation implements SimulationInterface {
	private Order[] orders;
	private MyPriorityQueue<Order> queue;
	private MyStack<Order> returnsStack;
	private int currentMinute;
	private int totalArrived;
	private int totalShipped;
	private int totalLate;
	
	/**
	 * One parameter constructor that instantiates a WarehouseSimulation object 
	 * @param orders An array of Orders
	 */
	public WarehouseSimulation(Order[] orders) {
		this.orders = orders;
		queue = new MyPriorityQueue<>(new OrderComparator(), orders.length);
		returnsStack = new MyStack<>(orders.length);
	}
	
	/**
     * Advances the simulation by exactly one minute.
     * This may involve:
     *  - Adding new orders to the queue
     *  - Shipping one order
     *  - Moving late orders to the returns stack
     */
	public void tick() {
		//As long as there are orders left to add to queue
		if (currentMinute < orders.length) {
			//Add one new order this minute
			queue.enqueue(orders[currentMinute]);
			
			//Stamp order with current minute as its arrival time
			queue.peek().setArrivalMinute(currentMinute);
			
			totalArrived++;
		}
		
		//If the queue is not empty, ship an order
		if (!queue.isEmpty()) {
			//if the highest priority item in the queue (at the front) is not late, ship it
			if (currentMinute <= queue.peek().getDeadlineMinute()) {
				queue.dequeue();
			} 
			//otherwise, the item is past is deadline
			//remove it from the queue and push it to the return stack for late shipping
			else {
				returnsStack.push(queue.dequeue());
				totalLate++;
			}
			totalShipped++;
		}
		
		currentMinute++; //advance minute by 1
	}
    
    /**
     * Returns true if:
     *  - All orders have been released into the queue, AND
     *  - The queue is empty (all orders shipped).
     */
    public boolean isFinished() {
    	return totalArrived == orders.length && queue.isEmpty();
    }

    /** Returns the current simulation time in minutes. */
    public int getCurrentMinute() {
		return currentMinute;
    }

    /** Returns the total number of orders that have arrived. */
    public int getTotalArrived() {
		return totalArrived;
    	
    }

    /** Returns the total number of orders that have been shipped. */
    public int getTotalShipped() {
    	return totalShipped;
    }

    /** Returns the total number of orders that shipped late. */
    public int getTotalLate() {
    	return totalLate;
    }

}
