import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyPriorityQueueTestStudent {
	
	MyPriorityQueue<Order> queue;
	Order[] orders = {new Order("o1", 5), new Order("o2", 1), new Order("o3", 2), new Order("o4", 1), new Order("o5", 8)};


	@BeforeEach
	void setUp() throws Exception {
		queue = new MyPriorityQueue<>(new OrderComparator(), orders.length);
		orders[3].setArrivalMinute(1); //id: o4
		orders[1].setArrivalMinute(3); //id: o2
	}

	@AfterEach
	void tearDown() throws Exception {
		queue = null;
		orders = null;
	}

	@Test
	void testEnqueueAndDequeue() {
		assertThrows(IllegalArgumentException.class,
				() -> queue.enqueue(null));
		
		for (int i = 0; i < orders.length; i++) {
			queue.enqueue(orders[i]);
		}
		
		//test overflow
		assertThrows(IllegalStateException.class,
				() -> queue.enqueue(new Order("over",0)));
		
		
		//use Order class's overriden equals() method to check for same orderId and deadline 
		assertEquals(queue.dequeue(), new Order("o4", 1)); //o4 and o2 have same deadline but o4 has earlier arrival time
		assertEquals(queue.dequeue(), new Order("o2", 1));
		assertEquals(queue.dequeue(), new Order("o3", 2));
		assertEquals(queue.dequeue(), new Order("o1", 5));
		assertEquals(queue.dequeue(), new Order("o5", 8));
		
		//test underflow 
		assertThrows(NoSuchElementException.class,
				() -> queue.dequeue());
				
	}
	
	@Test
	void testToArray() {
		for (int i = 0; i < orders.length; i++) {
			queue.enqueue(orders[i]);
		}
		
		Object[] copy = queue.toArray();
		Order[] ordersCopy = new Order[copy.length];
		
		for (int i = 0; i < copy.length; i++) {
			ordersCopy[i] = (Order)copy[i];
		}
		
		for (int i = 0; i < queue.size(); i++) {
			assertEquals(ordersCopy[i], queue.dequeue());
		}
	}
	
	@Test
	void testMaxCapacity() {
		assertDoesNotThrow(() ->  new MyPriorityQueue<>(new OrderComparator(), MyPriorityQueue.MAX_CAPACITY));
		assertThrows(IllegalStateException.class,
				() -> new MyPriorityQueue<>(new OrderComparator(), MyPriorityQueue.MAX_CAPACITY+1));
	}
	
}
