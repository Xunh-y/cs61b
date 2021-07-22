package hw4.puzzle;


import  edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Solver {
    private MinPQ<MyNode> pq = new MinPQ<>();
    private List<WorldState> paths = new ArrayList<>();
    public Solver(WorldState initial) {
        MyNode myNode = new MyNode(initial, 0, null);
        pq.insert(myNode);
        while (!pq.isEmpty()) {
            MyNode w = pq.min();
            if (w.getW().isGoal()) {
                break;
            }
            pq.delMin();
            for (WorldState n : w.getW().neighbors()) {
                if (w.getPreNode() == null || !n.equals(w.getPreNode().getW())) {
                    pq.insert(new MyNode(n, w.getTimes() + 1, w));
                }
            }
        }
    }

    public int moves() {
        return pq.min().getTimes();
    }

    public Iterable<WorldState> solution() {
        MyNode tmp = pq.min();
        Stack<WorldState> s = new Stack<>();
        while (tmp != null) {
            s.add(tmp.getW());
            tmp = tmp.getPreNode();
        }
        List<WorldState> solution = new ArrayList<>();
        while (!s.isEmpty()) {
            solution.add(s.peek());
            s.pop();
        }
        return solution;
    }

}
