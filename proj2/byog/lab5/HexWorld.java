package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int size = 4;
        int oriX = WIDTH / 2 - 1 + size / 2;
        int oriY = 0;

        addHexagon(world, Tileset.FLOWER, size, oriX, oriY);

        ter.renderFrame(world);
    }

    public static void addHexagon(TETile[][] world, TETile t, int n, int xp, int yp) {
        for (int i = 0; i < n * 2; ++i) {
            int xStart = getXSt(xp, n, i);
            int width = getWid(n, i);
            addByRow(world, t, n, xStart, yp++, width);
        }
    }

    private static int getWid(int n, int layer) {
        if (n > layer) {
            return n + layer * 2;
        }
        return 5 * n - 2 * layer - 2;
    }

    private static int getXSt(int xp, int n, int layer) {
        if (n > layer) {
            return xp - n - layer + 1;
        }
        return xp - 3 * n + layer + 2;
    }

    private static void addByRow(TETile[][] world, TETile t, int n, int xStart, int yp, int width) {
        for (int i = xStart; i < xStart + width; ++i) {
            world[i][yp] = t;
        }
    }

}
