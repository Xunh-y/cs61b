import java.io.Serializable;
import java.util.*;

public class BinaryTrie implements Serializable {


    private static class Node implements Serializable {
    private final char ch;
    private final int freq;
    private final Node left, right;

    Node(char ch, int freq, Node left, Node right) {
        this.ch    = ch;
        this.freq  = freq;
        this.left  = left;
        this.right = right;
    }

    // is the node a leaf node?
    private boolean isLeaf() {
        return (left == null) && (right == null);
    }

}

    private static Comparator<Node> cmp = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.freq > o2.freq) {
                return 1;
            } else {
                return -1;
            }
        }
    };
    private int R = 26;
    private Node root;
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        PriorityQueue<Node> pq = new PriorityQueue<>(cmp);
        Iterator<Character> i = frequencyTable.keySet().iterator();
        while (i.hasNext()) {
            char c = i.next();
            pq.offer(new Node(c, frequencyTable.get(c), null, null));
        }
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.offer(parent);
        }
        root = pq.poll();
    }
    public Match longestPrefixMatch(BitSequence querySequence) {
        StringBuilder sb = new StringBuilder();
        Node tmp = root;
        for (int i = 0; i < querySequence.length(); ++i) {
            int num = querySequence.bitAt(i);
            if (num == 0) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
            sb.append(num);
            if (tmp.isLeaf()) {
                break;
            }
        }
        BitSequence bs = new BitSequence(sb.toString());
        return new Match(bs, tmp.ch);
    }
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> map = new HashMap<>();
        buildCode(map, root, "");
        return map;
    }

    private void buildCode(Map<Character,BitSequence> map, Node root, String s) {
        if (!root.isLeaf()) {
            buildCode(map, root.left, s + '0');
            buildCode(map, root.right, s + '1');
        } else {
            map.put(root.ch, new BitSequence(s));
        }
    }

}
