package hw4.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Board implements WorldState {

    private int size;
    private int[][] board;
    private int[][] next = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private int zeroX, zeroY;

    public Board(int[][] tiles) {
        size = tiles.length;
        board = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (tiles[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
                board[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > size - 1 || j < 0 || j > size - 1) {
            throw new java.lang.IndexOutOfBoundsException("i or j is out of range");
        }
        return board[i][j];
    }

    public int size() {
        return size;
    }

    public int hamming() {
        int cnt = 0, idx = 1, sum = size() * size();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (idx == sum) {
                    break;
                }
                if (tileAt(i, j) != idx) {
                    cnt++;
                }
                idx++;
            }
        }
        return cnt;
    }

    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (tileAt(i, j) == 0) {
                    continue;
                }
                int disx = (board[i][j] - 1) / size();
                int disy = (board[i][j] - 1) % size();
                sum += Math.abs(i - disx) + Math.abs(j - disy);
            }
        }
        return sum;
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board p = (Board) y;
        if (p.size != this.size) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (tileAt(i, j) != p.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public Iterable<WorldState> neighbors() {
        List<WorldState> l;
//        for (int i = 0; i < size; ++i) {
//            for (int j = 0; j < size; ++j) {
//                if (tileAt(i, j) == 0) {
//                    l = zeroNeighbors(i, j, board);
//                    break;
//                }
//            }
//        }
        l = zeroNeighbors(zeroX, zeroY, board);
        return l;
    }

    private List<WorldState> zeroNeighbors(int x, int y, int[][] board) {
        List<WorldState> l = new ArrayList<>();
        for (int k = 0; k < 4; ++k) {
            int dx = x + next[k][0];
            int dy = y + next[k][1];
            if (dx < size && dy < size && dx >= 0 && dy >= 0) {
                int[][] tmp = new int[size][size];
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        tmp[i][j] = board[i][j];
                    }
                }
                tmp[x][y] = tmp[dx][dy];
                tmp[dx][dy] = 0;
                l.add(new Board(tmp));
            }
        }
        return l;
    }

//    private boolean isNextToZero(int x, int y, int[][] board) {
//        for (int i = 0; i < 4; ++i) {
//            int dx = x + next[i][0];
//            int dy = y + next[i][1];
//            if (dx < size && dy < size && dx >=0 && dy >= 0 && tileAt(dx, dy) == 0) {
//                return true;
//            }
//        }
//        return false;
//    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
