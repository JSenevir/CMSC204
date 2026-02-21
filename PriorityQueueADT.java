import java.util.NoSuchElementException;

/**
 * Generic priority queue interface.
 * Implementations must insert elements in priority order according to
 * a provided Comparator.
 * The Maximum Capacity is 1000 and the default capacity is 10
 * 
 * @param <T> the type of elements stored in the priority queue
 */
public interface PriorityQueueADT<T> {

	final static int MAX_CAPACITY =1000;  
    /**
     * Inserts an item into the queue according to the priority order.
     * @param item the element to insert (cannot be null)
     * @throws IllegalArgumentException if item is null
     */
    void enqueue(T item);

    /**
     * Removes and returns the highest-priority item.
     * @return the dequeued element
     * @throws NoSuchElementException if the queue is empty
     */
    T dequeue();

    /**
     * Returns (without removing) the highest-priority item.
     * @return the front element
     * @throws NoSuchElementException if the queue is empty
     */
    T peek();

    /**
     * Checks whether the queue has no elements.
     * @return true if empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the current number of elements in the queue.
     * @return number of elements
     */
    int size();
    
    /**
     * Returns an array containing all elements in this priority queue,
     * in the current internal order (not necessarily sorted by priority).
     */
    Object[] toArray();
    
}