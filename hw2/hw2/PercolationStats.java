package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
public class PercolationStats {
    private int T;
    private double[] cases;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0 || T < 0) {
            throw new java.lang.IllegalArgumentException("N and T must > 0");
        }
        this.T = T;
        cases = new double[T];
        for (int i = 0; i < T; ++i) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int x = StdRandom.uniform(N);
                int y = StdRandom.uniform(N);
                p.open(x, y);
            }
            cases[i] = (double) p.numberOfOpenSites() / N * N;
        }
    }
    public double mean() {
        return StdStats.mean(cases);
    }
    public double stddev() {
        return StdStats.stddev(cases);
    }                                  // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
