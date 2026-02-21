import java.util.NoSuchElementException;

/**
 * Generic LIFO (last-in-first-out) stack interface.
 * Maximum number of elements allowed is 1000 and a default size of 10 if the size is 
 * not declared.
 * 
 * @param <T> the type of elements stored in the stack
 */
public interface StackADT<T> {
	
	static final int MAX_CAPACITY = 1000; // Hard limit for this stack

    /**
     * Pushes an item onto the top of the stack.
     * @param item the element to push 
     * @throws IllegalArgumentException if item is null 
     * @throws IllegalStateException if stack has reached max capacity
     */
    void push(T item);

    /**
     * Removes and returns the top item from the stack.
     * @return the popped element
     * @throws NoSuchElementException if the stack is empty
     */
    T pop();

    /**
     * Returns the top item of the stack without removing.
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
    T peek();

    /**
     * Checks whether the stack is empty.
     * @return true if the stack has no elements, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the current number of elements in the stack.
     * @return number of elements
     */
    int size();
    
    /**
     * Returns an array containing all elements in this stack.
     * The element at index 0 is the bottom of the stack, and the
     * element at index size()-1 is the top.
     */
    Object[] toArray();
}
