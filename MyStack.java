import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * The MyStack class implements the StackADT interface. 
 * @param <T> the type of elements stored in the stack
 */
public class MyStack<T> implements StackADT<T>{
	/**
	 * The default capacity of the stack
	 */
	public static final int DEFAULT_CAPACITY = 10;

	private T[] stack;
	private int topIndex;	
	
	/**
	 * Default constructor. Calls parameterized constructor passing the default capacity as an argument.
	 */
	public MyStack() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Parameterized constructor
	 * @param capacity length for initialization of the stack's internal array
	 * @throws IllegalStateException when provided capacity exceed maximum possible capacity
	 */
	public MyStack(int capacity) {
		if (capacity > MAX_CAPACITY) {
			throw new IllegalStateException("The maximum capacity is " + MAX_CAPACITY);
		}
				
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[capacity];
		stack = temp;
		
		topIndex = -1; //no items in stack yet
	}
	

    /**
     * Pushes an item onto the top of the stack.
     * @param item the element to push 
     * @throws IllegalArgumentException if item is null 
     * @throws IllegalStateException if stack has reached max capacity
     */
	@Override
	public void push(T item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		if(capacityOverflow()) {
			throw new IllegalStateException();
		}
		
		//first increment topIndex, then add item to new top index of stack
		stack[++topIndex] = item;
	}
	
	/**
	 * Checks if stack is full and returns true if so and false otherwise
	 * @return true if the top index is the maximum index of the stack array, false otherwise
	 */
	private boolean capacityOverflow() {
		int maxIndex = stack.length-1;
		return topIndex == maxIndex;
	}
	
	/**
     * Removes and returns the top item from the stack.
     * @return the popped element
     * @throws NoSuchElementException if the stack is empty
     */
	@Override
	public T pop() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		T item = stack[topIndex];
		stack[topIndex] = null;
		topIndex--;
		
		return item;
	}

	/**
     * Returns the top item of the stack without removing.
     * @return the top element
     * @throws NoSuchElementException if the stack is empty
     */
	@Override
	public T peek() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return stack[topIndex];
	}
	
	/**
     * Checks whether the stack is empty.
     * @return true if the stack has no elements, false otherwise
     */
	@Override
	public boolean isEmpty() {
		return topIndex == -1;
	}
	
	/**
     * Returns the current number of elements in the stack.
     * @return number of elements
     */
	@Override
	public int size() {
		return topIndex+1;
	}
	
    /**
     * Returns an array containing all elements in this stack.
     * The element at index 0 is the bottom of the stack, and the
     * element at index size()-1 is the top.
     */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(stack, size());
	}

}
