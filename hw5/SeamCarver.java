import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture p;
    private int width;
    private int height;
    private boolean horizon = false;

    public SeamCarver(Picture picture) {
        p = new Picture(picture);
        width = p.width();
        height = p.height();
    }

    public Picture picture() {
        return new Picture(p);
    }
    public int width() {
        return width;
    }
    public int height() {
        return height;
    }
    public double energy(int x, int y) {
        Color rgb_x1, rgb_x2, rgb_y1, rgb_y2;
        if (!horizon) {
            rgb_x1 = x == 0 ? p.get(width - 1, y) : p.get(x - 1, y);
            rgb_x2 = x == width - 1 ? p.get(0, y) : p.get(x + 1, y);
            rgb_y1 = y == 0 ? p.get(x, height - 1) : p.get(x, y - 1);
            rgb_y2 = y == height - 1 ? p.get(x, 0) : p.get(x, y + 1);
        } else {
            rgb_x1 = x == 0 ? p.get(y, width - 1) : p.get(y, x - 1);
            rgb_x2 = x == width - 1 ? p.get(y, 0) : p.get(y,  x + 1);
            rgb_y1 = y == 0 ? p.get(height - 1, x) : p.get(y - 1, x);
            rgb_y2 = y == height - 1 ? p.get(0, x) : p.get(y + 1, x);
        }
        int rx = rgb_x1.getRed() - rgb_x2.getRed();
        int gx = rgb_x1.getBlue() - rgb_x2.getBlue();
        int bx = rgb_x1.getGreen() - rgb_x2.getGreen();
        int ry = rgb_y1.getRed() - rgb_y2.getRed();
        int gy = rgb_y1.getBlue() - rgb_y2.getBlue();
        int by = rgb_y1.getGreen() - rgb_y2.getGreen();
        double trix = rx * rx + gx * gx + bx * bx;
        double triy = ry * ry + gy * gy + by * by;
        return trix + triy;
    }
    public int[] findHorizontalSeam() {
        horizon = true;
        turn();
        int[] ans = findVerticalSeam();
        turn();
        horizon = false;
        return ans;
    }

    private void turn() {
        int tmp = height;
        height = width;
        width = tmp;
    }

    public int[] findVerticalSeam() {
        double[][] energys = new double[width][height];
        int[][] path = new int[width][height];
        for (int i = 0; i < width; ++i) {
            path[i][height - 1] = i;
            for (int j = 0; j < height; ++j) {
                energys[i][j] = energy(i, j);
            }
        }
        for (int j = 1; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                energys[i][j] = getMincost(i, j, path, energys);
            }
        }
        int[] ans = new int[height];
        int minpath = 0;
        double mincost = Integer.MAX_VALUE;
        for (int i = 0; i < width; ++i) {
            if (energys[i][height - 1] < mincost) {
                mincost = energys[i][height - 1];
                minpath = i;
            }
        }
        for (int i = height - 1; i >= 0; --i) {
            ans[i] = path[minpath][i];
            minpath = ans[i];
        }
        return ans;
    }

    private double getMincost(int i, int j, int[][] path, double[][] energys) {
        double d1 = i == 0 ? Double.MAX_VALUE : energys[i - 1][j - 1];
        double d2 = energys[i][j - 1];
        double d3 = i == width - 1 ? Double.MAX_VALUE : energys[i + 1][j - 1];
        double min = Math.min(d1, Math.min(d2, d3));
        if (min == d1) {
            path[i][j - 1] = i - 1;
            return d1 + energys[i][j];
        } else if (min == d2) {
            path[i][j - 1] = i;
            return d2 + energys[i][j];
        } else {
            path[i][j - 1] = i + 1;
            return d3 + energys[i][j];
        }
    }

    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(p, seam);
        
    }
    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(p, seam);
    }
}

