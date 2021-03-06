public class OffByN implements CharacterComparator{

    private int n = 0;

    public OffByN(int N) {
        n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return diff == n || diff == -n;
    }
}
