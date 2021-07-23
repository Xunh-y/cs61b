package lab11.graphs;



import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int s) {
        Queue<Integer> queue = new LinkedList<>();
        marked[s] = true;
        announce();

        queue.add(s);

        while (!queue.isEmpty() && queue.peek() != t) {
            int i = queue.poll();
            for (int w : maze.adj(i)) {
                if (!marked[w]) {
                    edgeTo[w] = i;
                    distTo[w] = distTo[i] + 1;
                    queue.offer(w);
                    marked[w] = true;
                    announce();
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(s);
    }
}

