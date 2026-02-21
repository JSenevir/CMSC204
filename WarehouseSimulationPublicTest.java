 import static org.junit.Assert.*;
import org.junit.Test;

public class WarehouseSimulationPublicTest {

    @Test
    public void basicFlowNoLate() {
        // One order released per minute in the given array order
        Order[] orders = new Order[] {
            new Order("A", 2), // t=0, ships at t=0, on time
            new Order("B", 1), // t=1, ships at t=1, on time
            new Order("C", 5)  // t=2, ships at t=2, on time
        };

        WarehouseSimulation sim = new WarehouseSimulation(orders);

        while (!sim.isFinished()) {
            sim.tick();
        }

        assertEquals(3, sim.getTotalArrived());
        assertEquals(3, sim.getTotalShipped());
        assertEquals(0, sim.getTotalLate());
        assertEquals(3, sim.getCurrentMinute()); // 0→1→2→3
    }

    @Test
    public void backlogCausesLate() {
        // Third order has a tight deadline → will be late when shipped at t=2
        Order[] orders = new Order[] {
            new Order("X1", 10), // harmless long deadline
            new Order("X2", 10),
            new Order("T",  1)   // released at t=2, ships at t=2 (>1) → late
        };

        WarehouseSimulation sim = new WarehouseSimulation(orders);

        while (!sim.isFinished()) {
            sim.tick();
        }

        assertEquals(3, sim.getTotalArrived());
        assertEquals(3, sim.getTotalShipped());
        assertEquals(1, sim.getTotalLate()); // "T" is late
        assertEquals(3, sim.getCurrentMinute());
    }

    
}
