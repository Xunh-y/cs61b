public class LinkedListDeque<T> implements Deque<T> {

    private class Node {
        private T item;
        private Node sentFront;
        private Node sentBack;

        public Node(T i, Node n, Node p) {
            item = i;
            sentFront = n;
            sentBack = p;
        }
    }

    private Node sentinal;
    private Node value;
    private Node viceSent;
    private int size;

    public LinkedListDeque() {
        sentinal = new Node(null, null, null);
        sentinal.sentBack = sentinal;
        sentinal.sentFront = sentinal;
        viceSent = sentinal;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node node = new Node(item, sentinal, sentinal.sentBack);
        sentinal.sentBack.sentFront = node;
        sentinal.sentBack = node;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node node = new Node(item, sentinal.sentFront, sentinal);
        sentinal.sentFront.sentBack = node;
        sentinal.sentFront = node;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        if (sentinal.sentFront != sentinal) {
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node tmp = sentinal;
        while (tmp.sentBack != sentinal) {
            tmp = tmp.sentBack;
            System.out.println(tmp.item);
        }
    }

    @Override
    public T removeFirst() {
        if (sentinal.sentBack == sentinal) {
            return null;
        }
        Node res = sentinal.sentBack;
        sentinal.sentBack.sentBack.sentFront = sentinal;
        sentinal.sentBack = sentinal.sentBack.sentBack;
        size -= 1;
        return res.item;
    }

    @Override
    public T removeLast() {
        if (sentinal.sentFront == sentinal) {
            return null;
        }
        Node res = sentinal.sentFront;
        sentinal.sentFront.sentFront.sentBack = sentinal;
        sentinal.sentFront = sentinal.sentFront.sentFront;
        size -= 1;
        return res.item;
    }

    @Override
    public T get(int index) {
        if (sentinal.sentBack == sentinal) {
            return null;
        }
        Node tmp = sentinal.sentBack;
        while (index > 0) {
            tmp = tmp.sentBack;
            if (tmp == sentinal) {
                return null;
            }
            index--;
        }
        return tmp.item;
    }

    public T getRecursive(int index) {
        if (viceSent.sentBack == sentinal) {
            viceSent = sentinal;
            return null;
        }
        viceSent = viceSent.sentBack;
        if (index == 0) {
            T res = viceSent.item;
            viceSent = sentinal;
            return res;
        }
        return getRecursive(index - 1);
    }
}
