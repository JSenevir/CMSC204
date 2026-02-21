/**
 * NOTE TO STUDENTS:
* -These tests represent the minimum requirements to validate the basic functionality of your project.
* -If the project due date has passed, you must meet these requirements in order to pass the course.
* -If you have not yet done so, move on to testing your code using the public tests provided to you.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

public class WareHouseGFATests {

	// Simple comparator for integers (ascending)
	private static final Comparator<Integer> INT_ASC = new Comparator<Integer>() {
		@Override
		public int compare(Integer a, Integer b) {
			return a.compareTo(b);
		}
	};

	@Test
	void testPriorityQueue() {
		MyPriorityQueue<Integer> pq = new MyPriorityQueue<Integer>(INT_ASC);
		pq.enqueue(10);
		pq.enqueue(7);
		assertEquals(7, (int) pq.peek());
		assertEquals(7, (int) pq.dequeue());
	}

	@Test
	public void testStack() {
		MyStack<String> stack = new MyStack<>();
		assertTrue(stack.isEmpty());

		stack.push("A");
		assertEquals(1, stack.size());
		assertEquals("A", stack.peek());
		assertEquals("A", stack.pop());
		assertTrue(stack.isEmpty());
	}

}
