package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Position implements Serializable {
    public int x;
    public int y;
    public Position(int _x, int _y) {
        x = _x;
        y = _y;
    }


    public boolean isvaild(TETile[][] world) {
        return world[this.x][this.y].equals(Tileset.FLOOR);
    }
}
