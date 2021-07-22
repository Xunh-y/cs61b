package hw4.puzzle;

public class MyNode implements Comparable<MyNode>{
    private WorldState w;
    private int times;
    private MyNode preNode;
    private int priority;

    public MyNode(WorldState _w, int _times, MyNode _preNode) {
        w = _w;
        times = _times;
        preNode = _preNode;
        priority = times + w.estimatedDistanceToGoal();
    }

    public int getTimes() {
        return times;
    }

    public WorldState getW() {
        return w;
    }

    public MyNode getPreNode() {
        return preNode;
    }

    @Override
    public int compareTo(MyNode o) {
        return this.priority - o.priority;
    }

}
