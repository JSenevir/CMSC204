import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarehouseSimulationTestStudent {
	
	WarehouseSimulation ws;
	Order[] orders = {new Order("o1", 5), new Order("o2", 1), new Order("o3", 2), new Order("o4", 1), new Order("o5", 0)};


	@BeforeEach
	void setUp() throws Exception {
		ws = new WarehouseSimulation(orders);
	}

	@AfterEach
	void tearDown() throws Exception {
		ws = null;
		orders = null;
	}

	@Test
	void test() {
		assertEquals(ws.getCurrentMinute(), 0);
		ws.tick();
		assertEquals(ws.getCurrentMinute(), 1);
		assertEquals(ws.getTotalArrived(), 1);
		assertEquals(ws.getTotalShipped(), 1);
		assertEquals(ws.getTotalLate(), 0);
		
		ws.tick();
		assertEquals(ws.getCurrentMinute(), 2);
		assertEquals(ws.getTotalArrived(), 2);
		assertEquals(ws.getTotalShipped(), 2);
		assertEquals(ws.getTotalLate(), 0);
		
		ws.tick();
		assertEquals(ws.getCurrentMinute(), 3);
		assertEquals(ws.getTotalArrived(), 3);
		assertEquals(ws.getTotalShipped(), 3);
		assertEquals(ws.getTotalLate(), 0);
		
		ws.tick();
		assertEquals(ws.getCurrentMinute(), 4);
		assertEquals(ws.getTotalArrived(), 4);
		assertEquals(ws.getTotalShipped(), 4);
		assertEquals(ws.getTotalLate(), 1);
		assertFalse(ws.isFinished());
		
		ws.tick();
		assertEquals(ws.getCurrentMinute(), 5);
		assertEquals(ws.getTotalArrived(), 5);
		assertEquals(ws.getTotalShipped(), 5);
		assertEquals(ws.getTotalLate(), 2);
		
		assertEquals(ws.getTotalArrived(), orders.length);
		assertTrue(ws.isFinished());
	}

}
