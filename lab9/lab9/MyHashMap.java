package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return buckets[hash(key)].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (loadFactor() > MAX_LF) {
            resize(buckets.length * 2);
        }
        if (!containsKey(key)) {
            size++;
        }
        buckets[hash(key)].put(key, value);
    }

    private void resize(int cap) {
        ArrayMap<K, V>[] tmp = buckets;
        buckets = new ArrayMap[cap];
        this.clear();
        for (int i = 0; i < tmp.length; ++i) {
            Set<K> s = tmp[i].keySet();
            for (K k : s) {
                put(k, tmp[i].get(k));
            }
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
        Set<K> sumS = new HashSet<>();
        for (ArrayMap<K, V> m : buckets) {
            Set<K> s = m.keySet();
            for (K k : s) {
                sumS.add(k);
            }
        }
        return sumS;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        for (ArrayMap<K, V> m : buckets) {
            Set<K> s = m.keySet();
            for (K k : s) {
                if (k.equals(key)) {
                    return m.remove(key);
                }
            }
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        for (ArrayMap<K, V> m : buckets) {
            Set<K> s = m.keySet();
            for (K k : s) {
                if (k.equals(key) && m.get(key).equals(value)) {
                    return m.remove(key, value);
                }
            }
        }
        return null;
//        if (get(key) != value) {
//            return null;
//        }
//        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashIterator();
    }

    private class MyHashIterator implements Iterator<K>{
        private Iterator<K> cur;
        public MyHashIterator() {
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
