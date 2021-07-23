package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private Stack<Integer> stack = new Stack<>();
    private boolean isCycle = false;
    private int cyclePoint;
    private boolean inCycle;

    /*
        Node.val record current value of node, Node.pre record the parent of node which means as same as edgeTo[].
    When we undergo dfs, we need to know where the cycle begin .So wo use cyclePoint, isCycle and inCycle to store.
    If we find marked[i] is true but Node.pre doesn't equal i, this denote that we find the cyclePoint.
     */
    private class Node {
        int val, pre;
        public Node(int v, int p) {
            val = v;
            pre = p;
        }
        
    }

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        dfs(new Node(0, 0));
        while (!stack.isEmpty()) {
            int t = stack.pop();
            if (stack.isEmpty()) {
                break;
            }
            edgeTo[t] = stack.peek();
        }
        announce();
    }

    private void dfs(Node node) {
        marked[node.val] = true;
        announce();

        for (int w : maze.adj(node.val)) {
            if (marked[w] && w != node.pre) {
                isCycle = true;
                inCycle = true;
                cyclePoint = w;
                stack.push(w);
                return;
            }
            if (!marked[w]) {
                marked[w] = true;
                dfs(new Node(w, node.val));
            }
            if (isCycle) {
                if (inCycle) {
                    stack.push(w);
                }
                if (cyclePoint == w) {
                    inCycle = false;
                }
                return;
            }
        }
    }

    // Helper methods go here
}

