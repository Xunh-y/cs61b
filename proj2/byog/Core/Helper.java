package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Helper {
    protected Position[] getCorners(Position p, int w, int h) {
        Position[] corners = new Position[4];
        corners[0] = new Position(p.x, p.y);
        corners[1] = new Position(p.x + w - 1, p.y);
        corners[2] = new Position(p.x + w - 1, p.y + h - 1);
        corners[3] = new Position(p.x, p.y + h - 1);
        return corners;
    }

    protected static boolean aroundNothing(int x, int y, TETile[][] world) {
        if (world[x - 1][y].equals(Tileset.NOTHING)
                && world[x + 1][y].equals(Tileset.NOTHING)
                && world[x][y + 1].equals(Tileset.NOTHING)
                && world[x][y - 1].equals(Tileset.NOTHING)) {
            return true;
        }
        return false;
    }

    protected static Position[] getConnect(Position p, int dis) {
        Position[] connects = new Position[4];
        connects[0] = new Position(p.x - dis, p.y);
        connects[1] = new Position(p.x + dis, p.y);
        connects[2] = new Position(p.x, p.y - dis);
        connects[3] = new Position(p.x, p.y + dis);
        return connects;

    }

    protected static Position[] getAround(Position p) {
        Position[] arounds = new Position[4];
        arounds[0] = new Position(p.x - 1, p.y);
        arounds[1] = new Position(p.x + 1, p.y);
        arounds[2] = new Position(p.x, p.y - 1);
        arounds[3] = new Position(p.x, p.y + 1);
        return arounds;
    }

    protected static boolean noOverRange(Position p, TETile[][] world) {
        if (p.x == world.length - 1 || p.x == 0 || p.y == 0 || p.y == world[0].length - 1) {
            return false;
        }
        return true;
    }

    protected static boolean betweenVer(Position p, TETile[][] world) {
        if (world[p.x - 1][p.y].equals(Tileset.FLOOR) && world[p.x + 1][p.y].equals(Tileset.FLOOR)) {
            return true;
        }
        return false;
    }

    protected static boolean betweenHoz(Position p, TETile[][] world) {
        if (world[p.x][p.y - 1].equals(Tileset.FLOOR) && world[p.x][p.y + 1].equals(Tileset.FLOOR)) {
            return true;
        }
        return false;
    }
}
