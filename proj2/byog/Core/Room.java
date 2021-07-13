package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Room extends Helper{
    private static List<Room> rooms = new LinkedList<>();
    private Position p;
    private int width;
    private int height;
    public Room(Position _p, int _w, int _h) {
        p = _p;
        width = _w;
        height = _h;
    }


    public static List<Room> roomCreate(TETile[][] world, Random random) {
        int numOfRoom = 1000 + random.nextInt(30);
        generateRooms(numOfRoom, world, random);
        removeOverlapRoom(rooms);
        for (Room r : rooms) {
            printRoom(r, world);
        }
        return rooms;
    }

    private static void printRoom(Room r, TETile[][] world) {
        if (r.p.x + r.width - 1 > world.length - 1) {
            r.width = world.length - r.p.x;
        }
        if (r.p.y + r.height - 1 > world[0].length - 1) {
            r.height = world[0].length - r.p.y;
        }
        int right = r.p.x + r.width - 1;
        int left = r.p.x;
        int top = r.p.y + r.height - 1;
        int bottom = r.p.y;
        for (int i = bottom; i <= top; ++i) {
            for (int j = left; j <= right; ++j) {
                if (i == bottom || i == top || j == left || j == right) {
                    world[j][i] = Tileset.WALL;
                } else {
                    world[j][i] = Tileset.FLOOR;
                }
            }
        }
    }

    private static void removeOverlapRoom(List<Room> rooms) {
        for (int i = 0; i < rooms.size(); ++i) {
            for (int j = i + 1; j < rooms.size(); ++j) {
                if (rooms.get(i).isOverlap(rooms.get(j))) {
                    rooms.remove(j);
                    j--;
                }
            }
        }
    }

    public static void connectRoomAndHallway(TETile[][] world, List<Room> rooms, Random random) {
        for (Room r : rooms) {
            List<Position> conpoints = getpoints(r, world, random);
            int connect = random.nextInt(conpoints.size());
            world[conpoints.get(connect).x][conpoints.get(connect).y] = Tileset.FLOOR;
            int another = random.nextInt(conpoints.size() / 2);
            if (another * 3 < conpoints.size()) {
                world[conpoints.get(another).x][conpoints.get(another).y] = Tileset.FLOOR;
            }
        }
    }

    private static List<Position> getpoints(Room r, TETile[][] world, Random random) {
        List<Position> conpoints = new LinkedList<>();
        for (int i = r.p.x + 1; i < r.p.x + r.width - 1; ++i) {
            Position p = new Position(i, r.p.y);
            if (noOverRange(p, world) && betweenHoz(p, world)) {
                conpoints.add(p);
            }
            p = new Position(i, r.p.y + r.height - 1);
            if (noOverRange(p, world) && betweenHoz(p, world)) {
                conpoints.add(p);
            }
        }
        for (int j = r.p.y + 1; j < r.p.y + r.height - 1; ++j) {
            Position p = new Position(r.p.x, j);
            if (noOverRange(p, world) && betweenVer(p, world)) {
                conpoints.add(p);
            }
            p = new Position(r.p.x + r.width - 1, j);
            if (noOverRange(p, world) && betweenVer(p, world)) {
                conpoints.add(p);
            }
        }
        return conpoints;
    }

    private boolean isOverlap(Room room) {
        Position[] corners = getCorners(room.p, room.width, room.height);
        for (int i = 0; i < 4; ++i) {
            if (contain(corners[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean contain(Position corner) {
        return corner.x >= p.x && corner.x <= p.x + width - 1 && corner.y >= p.y && corner.y <= p.y + height - 1;
    }


    private static void generateRooms(int n, TETile[][] world, Random random) {
        for (int i = 0; i < n; ++i) {
            Room room = randomRoom(world, random);
            rooms.add(room);
        }
    }

    private static Room randomRoom(TETile[][] world, Random random) {
        int rx = random.nextInt((int) (0.5 * (world.length - 2))) * 2;
        int ry = random.nextInt((int) (0.5 * (world[0].length - 2))) * 2;
        Position rp = new Position(rx, ry);
        int rw = (3 + random.nextInt(2)) * 2 - 1;
        int rh = (3 + random.nextInt(2)) * 2 - 1;
        return new Room(rp, rw, rh);
    }

}
