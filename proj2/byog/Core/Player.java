package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


public class Player {
    private static Position p;

    public static void setPosition(Position player) {
        Player.p = player;
    }

    public static void walkUp(TETile[][] world) {
        if (world[p.x][p.y + 1].equals(Tileset.WALL)) {
            return;
        }
        world[p.x][p.y + 1] = Tileset.PLAYER;
        world[p.x][p.y] = Tileset.FLOOR;
        p.y = p.y + 1;
    }

    public static void walkLeft(TETile[][] world) {
        if (world[p.x - 1][p.y].equals(Tileset.WALL)) {
            return;
        }
        world[p.x - 1][p.y] = Tileset.PLAYER;
        world[p.x][p.y] = Tileset.FLOOR;
        p.x = p.x - 1;
    }

    public static void walkDown(TETile[][] world) {
        if (world[p.x][p.y - 1].equals(Tileset.WALL)) {
            return;
        }
        world[p.x][p.y - 1] = Tileset.PLAYER;
        world[p.x][p.y] = Tileset.FLOOR;
        p.y = p.y - 1;
    }

    public static void walkRight(TETile[][] world) {
        if (world[p.x + 1][p.y].equals(Tileset.WALL)) {
            return;
        }
        world[p.x + 1][p.y] = Tileset.PLAYER;
        world[p.x][p.y] = Tileset.FLOOR;
        p.x = p.x + 1;
    }

    public static Object getpos() {
        return p;
    }
}
