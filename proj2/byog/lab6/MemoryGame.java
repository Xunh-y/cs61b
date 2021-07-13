package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round = 1;
    private Random rand;
    private boolean gameOver = false;
    private boolean playerTurn = false;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n, Random rand) {
        //TODO: Generate random string of letters of length n
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            s.append(CHARACTERS[rand.nextInt(26)]);
        }
        return s.toString();
    }

    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        int mx = width / 2;
        int my = height / 2;
        if (!gameOver) {
            Font microfont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(microfont);
            StdDraw.textLeft(1, height - 1, "Round:" + round);
            StdDraw.text(mx, height - 1, playerTurn ? "Type here" : "Watch it");
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round / 5]);
            StdDraw.line(1, height - 2, width - 2, height - 2);
        }
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(mx, my, s);
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); ++i) {
            drawFrame(String.valueOf(letters.charAt(i)));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String ans = "";
        drawFrame("");
        while (ans.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            ans += StdDraw.nextKeyTyped();
            drawFrame(ans);
        }
        return ans;
    }

    public void startGame() {
        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round: " + round);
            StdDraw.pause(1500);

            String s = generateRandomString(round, rand);
            flashSequence(s);

            playerTurn = true;
            String m = solicitNCharsInput(round);
            if (s.equals(m)) {
                drawFrame("success!");
                round++;
                StdDraw.pause(1500);
            } else {
                gameOver = true;
                drawFrame("Game over!");
            }
        }
    }

}
