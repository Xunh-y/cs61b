package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] g;
    private int top, bottom;
    private int[] sta;
    private int size;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufReal;
    private int[][] mo = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N <= 0");
        }
        size = 0;
        top = N * N;
        bottom = N * N + 1;
        g = new int[N][N];
        int k = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                g[i][j] = k++;
            }
        }
        sta = new int[N * N];
        for (int i = 0; i < N * N; ++i) {
            sta[i] = 0;
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufReal = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; ++i) {
            uf.union(g[0][i], top);
            ufReal.union(g[0][i], top);
            uf.union(g[N - 1][i], bottom);
        }
    }

    public void open(int row, int col) {
        if (row < 0 || row >= g.length || col < 0 || col >= g.length) {
            throw new java.lang.IndexOutOfBoundsException("the argument is outside its range");
        }
        if (sta[g[row][col]] == 0) {
            sta[g[row][col]] = 1;
            gridCon(row, col);
            size++;
        }
    }

    private void gridCon(int row, int col) {
        for (int i = 0; i < 4; ++i) {
            int x = row + mo[i][0];
            int y = col + mo[i][1];
            if (x >= 0 && x < g.length && y >= 0 && y < g.length && isOpen(x, y)) {
                uf.union(g[row][col], g[x][y]);
                ufReal.union(g[row][col], g[x][y]);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= g.length || col < 0 || col >= g.length) {
            throw new java.lang.IndexOutOfBoundsException("the argument is outside its range");
        }
        return sta[g[row][col]] == 1;
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row >= g.length || col < 0 || col >= g.length) {
            throw new java.lang.IndexOutOfBoundsException("the argument is outside its range");
        }
        return ufReal.connected(top, g[row][col]) && sta[g[row][col]] == 1;
    }

    public int numberOfOpenSites()  {
        return size;
    }

    public boolean percolates()  {
        return uf.connected(top, bottom);
    }

    public static void main(String[] args) {

    }
}
