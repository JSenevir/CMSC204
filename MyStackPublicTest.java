import static org.junit.Assert.*;
import org.junit.Test;
import java.util.NoSuchElementException;

public class MyStackPublicTest {

    @Test
    public void testPushPopPeek() {
        MyStack<String> stack = new MyStack<>();
        assertTrue(stack.isEmpty());

        stack.push("A");
        assertEquals(1, stack.size());
        assertEquals("A", stack.peek());
        assertEquals("A", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPushNullThrows() {
        MyStack<String> stack = new MyStack<>();
        stack.push(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testPopEmptyThrows() {
        MyStack<Integer> stack = new MyStack<>();
        stack.pop();
    }

    @Test(expected = NoSuchElementException.class)
    public void testPeekEmptyThrows() {
        MyStack<Integer> stack = new MyStack<>();
        stack.peek();
    }
}
