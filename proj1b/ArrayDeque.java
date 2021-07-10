import org.junit.Test;

public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            T[] a = (T[]) new Object[items.length * 2];
            System.arraycopy(items, 0, a, 1, size);
            items = a;
        } else {
            T[] a = (T[]) new Object[items.length];
            System.arraycopy(items, 0, a, 1, size);
            items = a;
        }
        items[0] = item;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = item;
        size += 1;
    }

    private void resize(int i) {
        T[] a = (T[]) new Object[i];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; ++i) {
            System.out.println(items[i]);
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T res = get(0);
        T[] a;
        if (items.length > 8 && (double) (size - 1) / items.length < 0.25) {
            a = (T[]) new Object[items.length / 2];
        } else {
            a = (T[]) new Object[items.length];
        }
        System.arraycopy(items, 1, a, 0, size - 1);
        items = a;
        size -= 1;
        return res;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T res = get(size - 1);
        if (items.length > 8 && (double) (size - 1) / items.length < 0.25) {
            T[] a = (T[]) new Object[items.length / 2];
            System.arraycopy(items, 0, a, 0, size - 1);
            items = a;
        } else {
            items[size - 1] = null;
        }
        size -= 1;
        return res;
    }

    @Override
    public T get(int index) {
        return items[index];
    }

    @Override
    public int size() {
        return size;
    }
}
