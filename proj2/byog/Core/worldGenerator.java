package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class worldGenerator {
    private static final int WIDTH = 81;
    private static final int HEIGHT = 41;
    private static TERenderer ter;
    public static TETile[][] worldCreate(TETile[][] world, Random random) {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        initWorld(world, random);

        ter.renderFrame(world);

        return world;

    }

    private static void initWorld(TETile[][] world, Random random) {
        List<Room> rooms = Room.roomCreate(world, random);
        Hallway.hallwayCreate(world,random);
        fillofWall(world);
        Room.connectRoomAndHallway(world, rooms, random);
        addPlayer(world, random);
    }

    private static void addPlayer(TETile[][] world, Random random) {
        boolean hasPlayer = false;
        while (!hasPlayer) {
            int px = random.nextInt((int) (0.5 * (world.length - 2))) * 2;
            int py = random.nextInt((int) (0.5 * (world[0].length - 2))) * 2;
            Position player = new Position(px, py);
            if (player.isvaild(world)) {
                hasPlayer = true;
                world[px][py] = Tileset.PLAYER;
                Player.setPosition(player);
            }
        }
    }


    private static void fillofWall(TETile[][] world) {
        for (int i = 0; i < world.length; ++i) {
            for (int j = 0; j < world[0].length; ++j) {
                if (world[i][j].equals(Tileset.NOTHING)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

}
