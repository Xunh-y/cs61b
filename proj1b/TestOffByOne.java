import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    /*Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    @Test
    public void testEqualChars() {
        Character c1 = 'C', c2 = 'c', c3 = 'C', c4 = 'B', c5 = 'D';
        assertFalse(offByOne.equalChars(c1, c2));
        assertFalse(offByOne.equalChars(c1, c3));
        assertTrue(offByOne.equalChars(c1, c4));
        assertTrue(offByOne.equalChars(c1, c5));
    }
}
