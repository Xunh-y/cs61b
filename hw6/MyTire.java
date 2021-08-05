
import java.util.Map;
import java.util.TreeMap;

public class MyTire {
    public class MyNode {
        boolean exist;
        Map<Character, MyNode> links;
        public MyNode() {
            links = new TreeMap<>();
            exist = false;
        }
    }

    public MyNode root = new MyNode();

    public void put(String key) {
        put(root, key, 0);
    }

    public MyNode put(MyNode n, String k, int d) {
        if (n == null) {
            n = new MyNode();
        }
        if (d == k.length()) {
            n.exist = true;
            return n;
        }
        char c = k.charAt(d);
        n.links.put(c, put(n.links.get(c), k, d + 1));
        return n;
    }
}
