public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> ans = new ArrayDeque<>();
        for (int i = 0; i < word.length(); ++i) {
            ans.addLast(word.charAt(i));
        }
        return ans;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> words = wordToDeque(word);
        return isPalindromeByRecurse(words);
    }

    private boolean isPalindromeByRecurse(Deque<Character> words) {
        if (words.size() == 1 || words.size() == 0) return true;
        return words.removeFirst().equals(words.removeLast()) && isPalindromeByRecurse(words);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> words = wordToDeque(word);
        while (words.size() > 1) {
            if (!cc.equalChars(words.removeFirst(),words.removeLast())) return false;
        }
        return true;
    }
}
