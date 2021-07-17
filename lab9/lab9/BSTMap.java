package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;
        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }
    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    private boolean add;
    private V removeValue;
    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
        add = true;
    }

    private V getHelper(K key, Node root) {
        if (root == null) {
            return null;
        }
        if (root.key.equals(key)) {
            return root.value;
        } else if (key.compareTo(root.key) < 0) {
            return getHelper(key, root.left);
        } else return getHelper(key, root.right);
    }

    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    private Node putHelper(K key, V value, Node root) {
        if (root == null) {
            add = true;
            return new Node(key, value);
        }
        if (key.compareTo(root.key) < 0) {
            root.left = putHelper(key, value, root.left);
        } else if (key.compareTo(root.key) > 0) {
            root.right = putHelper(key, value, root.right);
        } else {
            root = new Node(key, value);
            add = false;
        }
        return root;
    }

    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        if (add) {
            size++;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        keySetHelper(root, set);
        return set;
    }

    private void keySetHelper(Node root, Set<K> set) {
        if (root == null) {
            return;
        }
        set.add(root.key);
        keySetHelper(root.left, set);
        keySetHelper(root.right, set);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        root = removeHelper(key, root);
        return removeValue;
    }

    private Node removeHelper(K key, Node root) {
        if (root == null) {
            return null;
        }
        if (key.compareTo(root.key) < 0) {
            root.left = removeHelper(key, root.left);
        } else if (key.compareTo(root.key) > 0) {
            root.right = removeHelper(key, root.right);
        } else {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            removeValue = root.value;
            Node newNode = min(root.right);
            newNode.left = root.left;
            newNode.right = rotate(root.right);
            return newNode;
        }
        return root;
    }

    private Node rotate(Node root) {
        if (root.left == null) {
            return root.right;
        }
        root.left = rotate(root.left);
        size--;
        return root;
    }

    private Node min(Node root) {
        if (root.left == null) {
            return root;
        }
        return min(root.left);
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) != value) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<K>{
        private Iterator<K> cur;

        public BSTIterator() {
            cur = keySet().iterator();
        }
        @Override
        public boolean hasNext() {
            return cur.hasNext();
        }

        @Override
        public K next() {
            return cur.next();
        }
    }
}
