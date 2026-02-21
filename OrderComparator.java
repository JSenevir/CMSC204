import java.util.Comparator;

/**
 * The OrderComparator class implements the Comparator interface with a type argument of the Order class.
 	Overrides Comparator's compare method to compare two instances of Order based on deadline or arrival time.
 */
public class OrderComparator implements Comparator<Order> {
	/**
	 * 	This method compares two Orders and returns an integer. 
		Comparison is based on the deadline minute unless the orders share a deadline. 
		If orders have the same deadline, comparison is based on arrival minute.
	 */
	@Override
	public int compare(Order o1, Order o2) {
		int compareDeadlines = Integer.compare(o1.getDeadlineMinute(), o2.getDeadlineMinute());
		
		if (compareDeadlines == 0){
			return Integer.compare(o1.getArrivalMinute(), o2.getArrivalMinute());
		}
		
		return compareDeadlines;
	}
}
