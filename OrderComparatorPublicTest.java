import static org.junit.Assert.*;
import org.junit.Test;
public class OrderComparatorPublicTest {

    @Test
    public void testDifferentDeadlines() {
        OrderComparator cmp = new OrderComparator();
        Order earlyDeadline = new Order("A", 3);
        Order lateDeadline  = new Order("B", 5);
        assertTrue("Earlier deadline should come first",
                   cmp.compare(earlyDeadline, lateDeadline) < 0);
    }

    @Test
    public void testSameDeadlineEarlierArrivalFirst() {
        OrderComparator cmp = new OrderComparator();
        Order earlyArrival = new Order("C",  5);
        earlyArrival.setArrivalMinute(1);
        Order lateArrival  = new Order("D",  5);
        lateArrival.setArrivalMinute(2);
        assertTrue("Earlier arrival should come first when deadlines tie",
                   cmp.compare(earlyArrival, lateArrival) < 0);
    }
}
