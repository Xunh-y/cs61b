package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Hallway extends Helper{

    private static Deque<Position> positions = new LinkedList<>();

    public static void hallwayCreate(TETile[][] world, Random random) {
        Position p = startRandom(world);
        world[p.x][p.y] = Tileset.FLOOR;
        positions.addLast(p);
        while (!positions.isEmpty()) {
            Position curp = positions.getLast();
            List<Position> avails = getAvail(curp, world);
            if (!avails.isEmpty()) {
                int connect = random.nextInt(avails.size());
                connectHallway(avails.get(connect), curp, world, positions);
            } else {
                positions.removeLast();
            }
        }
    }

    private static void connectHallway(Position desp, Position p, TETile[][] world, Deque<Position> positions) {
        int mx = (p.x + desp.x) / 2;
        int my = (p.y + desp.y) / 2;
        positions.addLast(desp);
        world[mx][my] = Tileset.FLOOR;
        world[desp.x][desp.y] = Tileset.FLOOR;
    }

    private static List<Position> getAvail(Position curp, TETile[][] world) {
        List<Position> avails = new LinkedList<>();
        Position[] around = getAround(curp);
        Position[] connect = getConnect(curp, 2);
        for (int i = 0; i < 4; ++i) {
            if (noOverRange(around[i], world)
                    && !world[around[i].x][around[i].y].equals(Tileset.WALL)
                    && world[connect[i].x][connect[i].y].equals(Tileset.NOTHING)) {
                avails.add(connect[i]);
            }
        }
        return avails;
    }

    private static Position startRandom(TETile[][] world) {
        for (int i = 1; i < world.length; i += 2) {
            for (int j = 1; j < world[0].length; j += 2) {
                if (aroundNothing(i, j, world)) return new Position(i, j);
            }
        }
        return null;
    }

}
