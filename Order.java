/**
 * This class represents an order that may be received and shipped from the Warehouse.
 */
public class Order {
	private String orderId;
	private int deadlineMinute;
	private int arrivalMinute;
	
	/**
	 * Two parameter constructor that instantiates an Order object 
	 * @param orderId the order ID
	 * @param deadlineMinute the deadline time in minutes from start (t=0)
	 */
	public Order(String orderId, int deadlineMinute) {
		this.orderId = orderId;
		this.deadlineMinute = deadlineMinute;
	}
	
	/**
	 * Sets the arrival minute
	 * @param arrivalMinute the arrival minute of the order
	 */
	public void setArrivalMinute(int arrivalMinute) {
		this.arrivalMinute = arrivalMinute;
	}
	
	/**
	 * Returns the arrival minute
	 * @return arrivalMinute the arrive minute of the order
	 */
	public int getArrivalMinute() {
		return arrivalMinute;
	}
	
	/**
	 * Returns the deadline minute
	 * @return deadlineMinute the deadline minute
	 */
	public int getDeadlineMinute() {
		return deadlineMinute;
	}
	
	/**
	 * Returns the order's identification String
	 * @return orderId the order ID
	 */
	public String getId() {
		return orderId;
	}

	@Override
	public boolean equals(Object o) {
		Order other = (Order) o;
		
		if (other == null) {
			return false;
		}
		
		if (this == other) {
			return true;
		} 
		
		if (other.orderId.equals(orderId)) {
			if (other.deadlineMinute == deadlineMinute) {
				return true;
			}
		}
		
		return false;
	}
}
