import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } /*Uncomment this class once you've created your Palindrome class. */

    @Test
    public void testIsPalindrome() {
        String s = "cat";
        assertFalse(palindrome.isPalindrome(s));
        String s1 = "";
        assertTrue(palindrome.isPalindrome(s1));
        String s2 = "caC";
        assertFalse(palindrome.isPalindrome(s2));
        String s3 = "c";
        assertTrue(palindrome.isPalindrome(s3));
    }

    @Test
    public void testOffByOne() {
        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome("cs", cc));
        assertTrue(palindrome.isPalindrome("AB", cc));
        assertTrue(palindrome.isPalindrome("A", cc));
        assertTrue(palindrome.isPalindrome("CAB", cc));
    }

    @Test
    public void testOffByN() {
        CharacterComparator cc = new OffByN(5);
        assertFalse(palindrome.isPalindrome("cs", cc));
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("af", cc));
        assertTrue(palindrome.isPalindrome("bbg", cc));
    }
}
