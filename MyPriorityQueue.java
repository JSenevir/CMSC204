import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A generic priority queue class that implements the PriorityQueueADT interface.
 * @param <T> the type of elements stored in the priority queue
 */
public class MyPriorityQueue<T> implements PriorityQueueADT<T> {
	/**
	 * The default capacity of the queue
	 */
	public static final int DEFAULT_CAPACITY = 10;
	
	private Comparator<T> comparator;
	private T[] queue;
	private final int front;
	private int back;
	
	/**
	 * One parameter constructor for MyPriorityQueue class that calls two-parameter constructor,
	 passing in its comparator argument and the default capacity as arguments.
	 * @param comparator argument is an instance of a class that implements Comparator
	 */
	public MyPriorityQueue(Comparator<T> comparator) {
		this(comparator, DEFAULT_CAPACITY);
	}
	
	/**
	 * Two parameter constructor for MyPriorityQueue class
	 * @param comparator argument is an instance of a class that implements Comparator
	 * @param capacity length for initialization of the queue's internal array
	 * @throws IllegalStateException when provided capacity exceeds maximum possible capacity
	 */
	public MyPriorityQueue(Comparator<T> comparator, int capacity) {
		if (capacity > MAX_CAPACITY) {
			throw new IllegalStateException("The maximum capacity is " + MAX_CAPACITY);
		}
		
		this.comparator = comparator;
		
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[capacity];
		queue = temp;
		
		front = 0;
		back = -1;
	}
	
	/**
     * Inserts an item into the queue according to the priority order.
     * @param item the element to insert 
     * @throws IllegalArgumentException if item is null
     * @throws IllegalStateException if size of queue has reached its capacity
     */
	@Override
	public void enqueue(T item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		if (size() == queue.length) {
			throw new IllegalStateException("PriorityQueue is full");
		}
		
		//if the queue is empty (back index = -1), first item automatically has highest priority 
		if (isEmpty()) { 
			//add item to queue (front index is always 0)
			queue[front] = item;
			back = front; //back index and front index are now both 0
		} 
		//otherwise, there must at least one item already in queue so must compare before adding item to queue
		else {
			//item at front currently has the highest priority
			int highestIndex = front;
			boolean found = false;

			//iterate until finds lower priority item or back of queue is reached
			while(highestIndex <= back) {
				//if new item has a greater priority than current item, compare method returns a negative integer
				if (comparator.compare(item, queue[highestIndex]) < 0){
					//shift all elements backwards from the first lower priority index to the back index
					for (int i = back; i >= highestIndex; i--) {
						queue[i+1] = queue[i];
					}
					//insert new item into its designated slot
					queue[highestIndex] = item;
					back++;
					found = true;
					break;
				} 
				highestIndex++;
			}
			
			//if new item has lowest priority, increment back index and add to back of queue
			if (!found) {
				queue[++back] = item;
			}
		}
	}

	/**
     * Removes and returns the highest-priority item.
     * @return the dequeued element
     * @throws NoSuchElementException if the queue is empty
     */
	@Override
	public T dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		T removedItem = queue[front];
		queue[front] = null;
		
		//shift all items forward
		for (int i = front+1; i <= back; i++) {
			queue[i-1] = queue[i];
		}
		queue[back] = null;
		back--;
	
		return removedItem;
	}
	
	/**
     * Returns (without removing) the highest-priority item.
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
	@Override
	public T peek() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return queue[front];
	}
	
	/**
     * Checks whether the queue has no elements.
     * @return true if empty, false otherwise
     */
	@Override
	public boolean isEmpty() {
		return back == -1;
	}
	
	/**
     * Returns the current number of elements in the queue.
     * @return number of elements
     */
	@Override
	public int size() {
		return back+1;
	}
	
	/**
     * Returns an array containing all elements in this priority queue
     in the current internal order.
     */
	@Override
	public Object[] toArray() {
		return (Arrays.copyOf(queue, size()));
	}
}
