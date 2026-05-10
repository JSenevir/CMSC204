import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A Generic Linked List class that implements the List ADT Interface and provides a custom Iterator 
 * @param <T> Generic data type parameter
 */
public class GenericLinkedList<T> implements Iterable<T>, ListInterface<T> {
	private Node firstNode;
	private Node lastNode;
	private int numberOfElements;
	
	@Override
	public ListIterator<T> iterator() {
		return new GenericIterator();
	}
	
	/**
	 * Custom iterator for the GenericLinkedList outer class
	 */
	private class GenericIterator implements ListIterator<T>{
		private Node nodePointer;
		private Node returnNode;
		private boolean wasNextOrPreviousCalled;
		
		/**
		 * Default constructor initializes the nodePointer to the position next to firstNode
		 */
		private GenericIterator() {
			nodePointer = firstNode;
		}
		
		@Override
		public boolean hasNext() {
			return nodePointer != null;
		}
		
		@Override
		public T next() {
			if(hasNext()) {
				returnNode = nodePointer; //return current node
				nodePointer = nodePointer.next; //advance pointer to next node
				wasNextOrPreviousCalled = true;
				
				return returnNode.data;
			}
			throw new NoSuchElementException();
		}
		
		@Override
		public boolean hasPrevious() {
			//nodePointer will be null if iterator has moved after last node
			if (nodePointer == null) {
				//if list is not empty (lastNode is not null), will return true
				return lastNode != null;
			}
			
			return nodePointer.previous != null;
		}
		
		@Override
		public T previous() {
			if (hasPrevious()) {
				//if iterator is after last node, move back to last node
				if (nodePointer == null) {
					nodePointer = lastNode;
				} else {
					nodePointer = nodePointer.previous;
				}
				
				returnNode = nodePointer;
				wasNextOrPreviousCalled = true;
				return returnNode.data;
			}
			throw new NoSuchElementException();
		}
		
		@Override
		public void remove() throws IllegalStateException {		
			if (!wasNextOrPreviousCalled) {
				throw new IllegalStateException();
			}
			
			//assuming no duplicate data in the List, use list's remove method, providing data of returnNode as search item
			GenericLinkedList.this.remove(returnNode.data);
			
			//reset flag
			wasNextOrPreviousCalled = false;
		}
		
		//unsupported methods
		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}
		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}
		@Override
		public void set(T e) {
			throw new UnsupportedOperationException();
		}
		@Override
		public void add(T e) {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Inner node class
	 */
	private class Node {
		private T data;
		private Node previous;
		private Node next;
		
		private Node(T data) {
			this.data = data;
		}
		
		private Node (T data, Node previous, Node next) {
			this.data = data;
			this.previous = previous;
			this.next = next;
		}
	}
	
	/**
	 * Gets reference to the node at a specified index
	 * @param index of Node to be retrieved
	 * @return the Node at the specified index
	 */
	private Node getNodeAt(int index) {
		if (index < 0 || index > numberOfElements-1) {
			throw new IndexOutOfBoundsException();
		}
		
		Node currentNode;
		int lastIndex = numberOfElements-1;
		
		//if index is closer to the end of list, start search from last node
		if ((lastIndex - index) < index) {
			currentNode = lastNode;
			int i = lastIndex;
			while (i > index) {
				currentNode = currentNode.previous;
				i--;
			}
		}
		//else index is closer to front of list, so start search from first node
		else {
			currentNode = firstNode;
			int i = 0;
			while (i < index) {
				currentNode = currentNode.next;
				i++;
			}
		}
		
		return currentNode;
	}
	
	@Override
	public boolean contains(T element) {
		boolean found = false;
		
		Node currentNode = firstNode;
		while (currentNode != null) {
			if (currentNode.data.equals(element)) {
				found = true;
				break;
			}
			currentNode = currentNode.next;
		}
		
		return found;
	}

	@Override
	public T get(int index) throws IndexOutOfBoundsException {
		return getNodeAt(index).data;
	}

	@Override
	public T getFirst() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return firstNode.data;
	}
	
	@Override
	public T getLast() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return lastNode.data;
	}

	@Override
	public boolean isEmpty() {
		return numberOfElements==0;
	}

	@Override
	public boolean addLast(T element) {
		Node newNode = new Node(element);

		if (isEmpty()) {
			firstNode = lastNode = newNode;
		} else {
			//update next and previous pointers
			lastNode.next = newNode;
			newNode.previous = lastNode;
			
			//lastNode is now newNode
			lastNode = newNode;
		}
		
		numberOfElements++;
		return true;
	}
	
	@Override
	public boolean addFirst(T element) {
		Node newNode = new Node(element);
		
		if (isEmpty()) {
			firstNode = lastNode = newNode;
		} else {
			//update next and previous pointers
			firstNode.previous = newNode;
			newNode.next = firstNode;
			
			//firstNode is now newNode
			firstNode = newNode;
		}
		
		numberOfElements++;
		return true;
	}
	
	@Override
	public void clear() {		
		firstNode = null;
		lastNode = null;
		numberOfElements = 0;
	}
		
	@Override
	public T remove(int index) throws IndexOutOfBoundsException {
		Node deletedNode = getNodeAt(index);
		
		//if the node to be removed is the first node
		if (deletedNode == firstNode) {
			return removeFirst();
		}	
		//if the node to be removed is the last node
		else if (deletedNode == lastNode) {
			return removeLast();
		}
		//the node to be removed is in the middle of the list
		else {
			//set previous node's next pointer to the node after deleted node 
			(deletedNode.previous).next = deletedNode.next;
			//set next node's previous pointer to the node before deleted node
			(deletedNode.next).previous = deletedNode.previous;
			numberOfElements--;
			return deletedNode.data;
		}
	}

	@Override
	public T removeFirst() throws NoSuchElementException{
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T data = firstNode.data;
	
		//set the old first node's next node as the new first node
		firstNode = firstNode.next;
		
		if (firstNode == null) {
			//if that node is null, the list is now empty
			lastNode = firstNode; //so set lastNode to null
		} else {
			//otherwise, remove the old previous reference by setting it to null
			firstNode.previous = null;
		}
		
		numberOfElements--;
		return data;
	}
	
	@Override
	public T removeLast() throws NoSuchElementException{
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T data = lastNode.data;
		
		//set the old last node's previous node as the new last node
		lastNode = lastNode.previous;
		
		if (lastNode == null) {
			//if that node is null, the list is now empty
			firstNode = lastNode; //set firstNode to null
		} else {
			//otherwise, remove the old next reference by setting it to null
			lastNode.next = null;
		}
		
		numberOfElements--;
		return data;
	}
	
	@Override
	public boolean remove(T element) {
		boolean found = false;
		
		Node currentNode = firstNode;
		while (currentNode != null) {
			if (currentNode.data.equals(element)) {
				found = true;
				break;
			}
			currentNode = currentNode.next;
		}
		
		if (!found) {
			return false;
		}
		
		//if the node to be removed is the first node
		if (currentNode == firstNode) {
			removeFirst();
		}	
		//if the node to be removed is the last node
		else if (currentNode == lastNode) {
			removeLast();
		}
		//the node to be removed is in the middle of the list
		else {
			//set previous node's next pointer to the node after deleted node 
			(currentNode.previous).next = currentNode.next;
			//set next node's previous pointer to the node before deleted node
			(currentNode.next).previous = currentNode.previous;
			numberOfElements--;
		}
		
		return true;
	}

	@Override
	public int size() {
		return numberOfElements;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[numberOfElements];
		
		Node currentNode = firstNode;
		for (int i = 0; currentNode != null; i++) {
			array[i] = currentNode.data;
			currentNode = currentNode.next;
		}
		
		return array;
	}

}
