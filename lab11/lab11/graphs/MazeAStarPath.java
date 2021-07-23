package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<MyNode> minPQ = new MinPQ<>();

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    private class MyNode implements Comparable<MyNode>{
        private int val;
        private int times;
        private MyNode pre;
        private int priority;

        public MyNode(int v, int t, MyNode p) {
            val = v;
            times = t;
            pre = p;
            priority = h(v) + times;
        }

        @Override
        public int compareTo(MyNode o) {
            return priority - o.priority;
        }
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int dx = Math.abs(maze.toX(v) - maze.toX(t));
        int dy = Math.abs(maze.toY(v) - maze.toY(t));
        return dy + dx;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        marked[s] = true;
        announce();
        minPQ.insert(new MyNode(s, 0, null));

        while (!minPQ.isEmpty() &&  minPQ.min().val != t) {
            MyNode m = minPQ.delMin();
            for (int w : maze.adj(m.val)) {
                if (!marked[w]) {
                    minPQ.insert(new MyNode(w, m.times + 1, m));
                    marked[w] = true;
                    distTo[w] = m.times + 1;
                    edgeTo[w] = m.val;
                    announce();
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

