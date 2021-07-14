package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 81;
    public static final int HEIGHT = 41;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawInit();
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (c == 'n') {
                newGame();
            } else if (c == 'l') {
                loadGame();
            } else if (c == 'q') {
                System.exit(0);
            }
        }
    }

    private void loadGame() {
        TETile[][] world = null;
        world = getSavedGame();
        ter.renderFrame(world);
        playGame(world);
    }

    private void newGame() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        long seed = getSeed();
        Random RANDOM = new Random(seed);
        world = worldGenerator.worldCreate(world, RANDOM);
        playGame(world);
    }

    private void playGame(TETile[][] world) {
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = Character.toLowerCase(StdDraw.nextKeyTyped());
            switch (c){
                case 'w':
                    Player.walkUp(world);
                    ter.renderFrame(world);
                    break;
                case 's':
                    Player.walkDown(world);
                    ter.renderFrame(world);
                    break;
                case 'a':
                    Player.walkLeft(world);
                    ter.renderFrame(world);
                    break;
                case 'd':
                    Player.walkRight(world);
                    ter.renderFrame(world);
                    break;
                case ':':
                    while (true) {
                        if (!StdDraw.hasNextKeyTyped()) {
                            continue;
                        }
                        char next = Character.toLowerCase(StdDraw.nextKeyTyped());
                        if (next == 'q') {
                            saveGame(world);
                            System.exit(0);
                        } else {
                            break;
                        }
                    }
                    break;
                default:
            }
        }
    }

    private long getSeed() {
        String seed = "";
        drawSeed("");
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = StdDraw.nextKeyTyped();
            if (c == 's' && !seed.equals("")) {
                break;
            } else if (c == 'd' && !seed.equals("")) {
                seed = seed.substring(0, seed.length() - 1);
                drawSeed(seed);
            } else if (Character.isLetter(c)) {
                continue;
            } else if (Character.isDigit(c)){
                seed += c;
                drawSeed(seed);
            }
        }
        return Long.parseLong(seed);
    }


    private void drawSeed(String s) {
        StdDraw.clear(Color.BLACK);
        Font Bigfont = new Font("Monaco", Font.BOLD, 42);
        StdDraw.setFont(Bigfont);
        StdDraw.text(WIDTH / 2, HEIGHT - 10, "Type your seed");
        StdDraw.text(WIDTH / 2, 10, "Type 's' to continue");
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, s);
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        input = toLower(input);
        drawInit();
        char first = input.charAt(0);
        TETile[][] finalWorldFrame = null;
        if (first == 'n') {
            finalWorldFrame = newGame(input);
        } else if (first == 'l') {
            finalWorldFrame = loadGame(input);
        } else {
            System.exit(0);
        }
        return finalWorldFrame;
    }

    private TETile[][] loadGame(String input) {
        TETile[][] world = null;
        world = getSavedGame();
        playGame(world, input.substring(1));
        return world;
    }

    private TETile[][] getSavedGame() {
        TETile[][] world = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("game.txt"));
            world = (TETile[][]) objectInputStream.readObject();
            Player.setPosition((Position) objectInputStream.readObject());
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return world;
    }

    private TETile[][] newGame(String input) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        int idxS = input.indexOf('s');
        long seed = Long.parseLong(input.substring(1, idxS));
        Random RANDOM = new Random(seed);
        world = worldGenerator.worldCreate(world, RANDOM);
        playGame(world, input.substring(idxS + 1));

        return world;
    }

    private void playGame(TETile[][] world, String substring) {
        for (int i = 0; i < substring.length(); ++i) {
            char c = substring.charAt(i);
            switch (c){
                case 'w':
                    Player.walkUp(world);
                    break;
                case 'a':
                    Player.walkLeft(world);
                    break;
                case 's':
                    Player.walkDown(world);
                    break;
                case 'd':
                    Player.walkRight(world);
                    break;
                case ':':
                    if (i + 1 < substring.length() && substring.charAt(i + 1) == 'q') {
                        saveGame(world);
                    }
                    break;
                default:
            }
        }
        ter.renderFrame(world);
    }

    private void saveGame(TETile[][] world) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("game.txt"));
            objectOutputStream.writeObject(world);
            objectOutputStream.writeObject(Player.getpos());
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toLower(String input) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                ans.append(Character.toLowerCase(c));
            } else {
                ans.append(c);
            }
        }
        return ans.toString();
    }

    private void drawInit() {
        initCanvas();

        int mx = WIDTH / 2;
        int my = HEIGHT / 2;
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(mx, HEIGHT - 10, "Xunh Game");
        Font menufont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(menufont);
        StdDraw.text(mx, my + 5, "(N)New game");
        StdDraw.text(mx, my, "(L)Load game");
        StdDraw.text(mx, my - 5, "(Q)Quit");
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    private void initCanvas() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();
    }
}
