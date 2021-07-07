import static org.junit.Assert.*;

import org.junit.Test;

import javax.naming.InsufficientResourcesException;

public class FlikTest {
    @Test
    public void testFlik() {
        Integer A = 128;
        Integer exp = 128;
        assertTrue(Flik.isSameNumber(A,exp));
    }
}
