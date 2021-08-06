import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class MyTire<V> {

    public class MyNode {
        Object val;
        Map<Character, MyNode> links;
        public MyNode() {
            links = new TreeMap<>();
        }
    }

    public MyNode root = new MyNode();

    public void put(String key, V v) {
        put(v, root, key, 0);
    }

    public MyNode put(V v, MyNode n, String k, int d) {
        if (n == null) {
            n = new MyNode();
        }
        if (d == k.length()) {
            n.val = v;
            return n;
        }
        char c = k.charAt(d);
        n.links.put(c, put(v, n.links.get(c), k, d + 1));
        return n;
    }

    public MyNode get(MyNode n, String s, int idx) {
        if (n == null) {
            return null;
        }
        if (s.length() == idx) {
            return n;
        }
        return get(n.links.get(s.charAt(idx)), s,idx + 1);
    }

    public Iterable<String> getByPrefix(String prefix) {
        Queue<String> queue = new LinkedList<>();
        getqueue(get(root, prefix, 0), prefix, queue);
        return queue;
    }

    private void getqueue(MyNode n, String prefix, Queue<String> queue) {
        if (n == null) {
            return;
        }
        if (n.val != null) {
            queue.offer(prefix);
        }
        for (char m : n.links.keySet()) {
            getqueue(n.links.get(m), prefix + m, queue);
        }
    }
}
