/**
 * An interface for the List ADT
 * @param <T> Generic data type parameter
 */
public interface ListInterface<T> {
	
	/**
	 * Checks if the list contains a specified element
	 * @param element the specified element
	 * @return true if the list contains the element, false otherwise
	 */
	boolean contains(T element);
	
	/**
	 * Returns the element at the specified position in this list
	 * @param index the index of the element to be retrieved 
	 * @throws IndexOutOfBoundsException if the index is out of range
	 * @return the element at the specified index
	 */
	T get(int index);
	
	/**
	 * Gets the first element in the list without removing it
	 * @return the first element in the list
	 * @throws NoSuchElementException if list is empty
	 */
	T getFirst();
	
	/**
	 * Gets the last element in the list without removing it
	 * @return the last element in the list
	 * @throws NoSuchElementException if list is empty
	 */
	T getLast();
	
	/**
	 * Checks if the list is empty
	 * @return true if the list is empty, false otherwise
	 */
	boolean isEmpty();
	
	/**
	 * Removes the element at the specified index from the list
	 * @param index the index of the element to be removed
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	T remove(int index);
	
	/**
	 * Removes the first occurrence of the specified element from the list 
	 * @param element the element to be removed
	 * @return true if removal was successful, false if specified element could not be found
	 */
	boolean remove(T element);
	
	/**
	 * Removes and returns the first element from the list
	 * @return the first element of the list
	 * @throws NoSuchElementException if the list is empty
	 */
	T removeFirst();
	
	/**
	 * Removes and returns the last element from the list. 
	 * @return the last element of the list
	 * @throws NoSuchElementException if the list is empty
	 */
	T removeLast();
	
	/**
	 * Adds an item to the end of the list
	 * @param element the element to be added
	 * @return true if successful
	 */
	boolean addLast(T element);
	
	/**
	 * Adds an item to the end of the list
	 * @param element the element to be added
	 * @return true if successful
	 */
	boolean addFirst(T element);
	
	/**
	 * Removes all elements from list 
	 */
	void clear();
	
	/**
	 * Gets the number of elements in the list
	 * @return the number of elements in the list
	 */
	int size();
	
	/**
	 * Converts the list to an array containing all elements in the same order as in the current list
	 * @return an array of all elements in the list in the same order
	 */
	Object[] toArray();
}