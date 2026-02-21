import static org.junit.Assert.*;
import java.util.Comparator;
import java.util.NoSuchElementException;
import org.junit.Test;

public class MyPriorityQueuePublicTest {

    // --- A very simple class used only in this test file ---
    private static class Task {
        private final String name;
        private final int priority; // smaller number = higher priority

        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }
        String getName() { return name; }
        int getPriority() { return priority; }
        @Override public String toString() { return name + "(p=" + priority + ")"; }
    }

    // Orders by priority ascending; if tie, by name alphabetically
    private static final Comparator<Task> TASK_COMPARATOR =
        new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                int c = Integer.compare(t1.getPriority(), t2.getPriority());
                if (c != 0) return c;
                return t1.getName().compareTo(t2.getName());
            }
        };

    // Simple comparator for integers (ascending)
    private static final Comparator<Integer> INT_ASC =
        new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a.compareTo(b);
            }
        };

    @Test
    public void testIntegersBasic() {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(INT_ASC);
        pq.enqueue(3);
        pq.enqueue(1);
        pq.enqueue(2);

        assertEquals(1, (int) pq.dequeue());
        assertEquals(2, (int) pq.dequeue());
        assertEquals(3, (int) pq.dequeue());
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
    }

    @Test
    public void testPeekDoesNotRemove() {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(INT_ASC);
        pq.enqueue(10);
        pq.enqueue(5);

        assertEquals(5, (int) pq.peek());
        assertEquals(5, (int) pq.peek());
        assertEquals(2, pq.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDequeueOnEmptyThrows() {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(INT_ASC);
        pq.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void testPeekOnEmptyThrows() {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(INT_ASC);
        pq.peek();
    }

    @Test
    public void testWithCustomTaskComparator() {
        MyPriorityQueue<Task> pq = new MyPriorityQueue<>(TASK_COMPARATOR);
        pq.enqueue(new Task("Write report", 2));
        pq.enqueue(new Task("Fix bug", 1));
        pq.enqueue(new Task("Answer emails", 3));
        pq.enqueue(new Task("Attend meeting", 2));

        // Expect priority=1 first
        assertEquals("Fix bug", pq.dequeue().getName());
        // Next two are priority=2; alphabetical by name
        assertEquals("Attend meeting", pq.dequeue().getName());
        assertEquals("Write report", pq.dequeue().getName());
        // Last is priority=3
        assertEquals("Answer emails", pq.dequeue().getName());
        assertTrue(pq.isEmpty());
    }
    @Test
    public void testDefaultCapacityLimit() {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(INT_ASC);

        // Fill to default capacity (10)
        for (int i = 0; i < 10; i++) {
            pq.enqueue(i);
        }
        assertEquals(10, pq.size());

        // Enqueue one more should throw
        try {
            pq.enqueue(11);
            fail("Expected IllegalStateException when exceeding default capacity");
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("PriorityQueue is full"));
        }
    }
}
