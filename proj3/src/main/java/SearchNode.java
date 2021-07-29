public class SearchNode implements Comparable<SearchNode>{
    public Long id;
    public double dis;
    public SearchNode preNode;
    public double priority;

    public SearchNode(Long _id, double _d, SearchNode _p) {
        id = _id;
        dis = _d;
        preNode = _p;
        priority = dis + Router.disToDes(id);
    }

    @Override
    public int compareTo(SearchNode o) {
        if (this.priority < o.priority) {
            return -1;
        } else if (this.priority > o.priority) {
            return 1;
        } else return 0;
    }
}
