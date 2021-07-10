public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    boolean isEmpty();
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);
    int size();
}
