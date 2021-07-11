package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(4);
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        assertEquals(arb.peek(),1);
        arb.enqueue(2);
        assertEquals(arb.peek(),1);
        arb.enqueue(3);
        assertEquals(arb.fillCount(),3);
        arb.enqueue(4);
        assertTrue(arb.isFull());
        assertEquals(arb.dequeue(),1);
        assertEquals(arb.dequeue(),2);
        assertEquals(arb.dequeue(),3);
        assertEquals(arb.dequeue(),4);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
