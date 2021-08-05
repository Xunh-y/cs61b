import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";
    static String dictPath1 = "trivial_words.txt";

    private static Comparator<String> cmp = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            if (s1.length() > s2.length()) {
                return -1;
            } else if (s1.length() < s2.length()) {
                return 1;
            } else {
                return s1.compareTo(s2);
            }
        }
    };
    private static MyTire myTire = new MyTire();
    private static PriorityQueue<String> pq;
    private static MyTire.MyNode r;
    private static boolean[][] vis;
    private static String word;
    private static int[][] EightDis = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        In in = new In(dictPath);
        while (in.hasNextLine()) {
            myTire.put(in.readLine());
        }
        In in1 = new In(boardFilePath);
        String[] s = in1.readAllLines();
        int h = s.length, w = s[0].length();
        char[][] board = new char[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                board[i][j] = s[i].charAt(j);
            }
        }
        vis = new boolean[h][w];
        pq = new PriorityQueue<>(cmp);
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                r = myTire.root;
                if (!r.links.containsKey(board[i][j])) {
                    continue;
                }
                r = r.links.get(board[i][j]);
                vis[i][j] = true;
                word = "";
                word += board[i][j];
                dfs(board, i, j, word, r);
                vis[i][j] = false;
            }
        }
        List<String> ans = new ArrayList<>();
        while (k > 0 && !pq.isEmpty()) {
            ans.add(pq.poll());
            k--;
        }
        return ans;
    }

    private static void dfs(char[][] board, int x, int y, String word, MyTire.MyNode r) {
        if (r.exist && !pq.contains(word)) {
            pq.offer(word);
        }
        for (int i = 0; i < 8; ++i) {
            int dx = x + EightDis[i][0];
            int dy = y + EightDis[i][1];
            if (dx >= 0 && dx < board.length
                    && dy >=0 && dy < board[0].length
                    && !vis[dx][dy] && r.links.containsKey(board[dx][dy])) {
                vis[dx][dy] = true;
                word += board[dx][dy];
                dfs(board, dx, dy, word, r.links.get(board[dx][dy]));
                word = word.substring(0, word.length() - 1);
                vis[dx][dy] = false;
            }
        }
    }

    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        List<String> l = solve(7, "smallBoard.txt");
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return;
    }
}
