import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyStackTestStudent {
	
	MyStack<Order> stack;
	Order[] orders = {new Order("o1", 5), new Order("o2", 1), new Order("o3", 2), new Order("o4", 1), new Order("o5", 8)};

	@BeforeEach
	void setUp() throws Exception {
		stack = new MyStack<>();
	}

	@AfterEach
	void tearDown() throws Exception {
		stack = null;
		orders = null;
	}

	@Test
	void testPopPeekPush() {
		assertTrue(stack.isEmpty());
		
		assertThrows(NoSuchElementException.class, () -> stack.pop());
		assertThrows(NoSuchElementException.class, () -> stack.peek());
		
		for (int i = 0; i < orders.length; i++) {
			stack.push(orders[i]);
		}
		
		assertEquals(stack.size(), orders.length);

		//references to same object
		assertEquals(stack.peek(), orders[orders.length-1]);
		//two objects with same id and deadline that are equal by Order equals method
		assertEquals(stack.peek(), new Order("o5", 8));
		
		int i = 1;
		while(!stack.isEmpty()) {
			assertEquals(stack.pop(), orders[orders.length-i]);
			assertTrue(stack.size() == orders.length-i);
			i++;
		}
		
		assertTrue(stack.isEmpty());
		assertEquals(stack.size(), 0);
		
		assertThrows(IllegalArgumentException.class, () -> stack.push(null));
	}
	
	@Test
	void testCapacityOverflow() {
		for (int i = 0; i < MyStack.DEFAULT_CAPACITY; i++) {
			stack.push(new Order("o", 1));
		}
		
		assertThrows(IllegalStateException.class, () -> stack.push(new Order("o", 1)));
	}

}
