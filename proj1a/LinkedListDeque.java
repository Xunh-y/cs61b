public class LinkedListDeque<T> {

    private class Node {
        public T item;
        public Node sentFront;
        public Node sentBack;

        public Node(T i, Node n, Node p) {
            item = i;
            sentFront = n;
            sentBack = p;
        }
    }

    private Node sentinal;
    private Node value;
    private Node vice_sent;
    private int size;

    public LinkedListDeque(){
        sentinal = new Node(null, null, null);
        sentinal.sentBack = sentinal;
        sentinal.sentFront = sentinal;
        vice_sent = sentinal;
        size = 0;
    }

    public LinkedListDeque(T t){
        sentinal = new Node(t,null,null);
        value = new Node(t, sentinal, sentinal);
        sentinal.sentBack = value;
        sentinal.sentFront = value;
        vice_sent = sentinal;
        size = 1;
    }

    public void addFirst(T item){
        Node node = new Node(item, sentinal,sentinal.sentBack);
        sentinal.sentBack.sentFront = node;
        sentinal.sentBack = node;
        size += 1;
    }

    public void addLast(T item){
        Node node = new Node(item, sentinal.sentFront,sentinal);
        sentinal.sentFront.sentBack = node;
        sentinal.sentFront = node;
        size += 1;
    }

    public boolean isEmpty(){
        if (sentinal.sentFront != sentinal)return false;
        return true;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        Node tmp = sentinal;
        while(tmp.sentBack != sentinal){
            tmp = tmp.sentBack;
            System.out.println(tmp.item);
        }
    }

    public T removeFirst(){
        if (sentinal.sentBack == sentinal)return null;
        Node res = sentinal.sentBack;
        sentinal.sentBack.sentBack.sentFront = sentinal;
        sentinal.sentBack = sentinal.sentBack.sentBack;
        size -= 1;
        return res.item;
    }

    public T removeLast(){
        if(sentinal.sentFront == sentinal)return null;
        Node res = sentinal.sentFront;
        sentinal.sentFront.sentFront.sentBack = sentinal;
        sentinal.sentFront = sentinal.sentFront.sentFront;
        size -= 1;
        return res.item;
    }

    public T get(int index){
        if (sentinal.sentBack == sentinal){
            return null;
        }
        Node tmp = sentinal.sentBack;
        while(index > 0){
            tmp = tmp.sentBack;
            if(tmp == sentinal)return null;
        }
        return tmp.item;
    }

    public T getRecursive(int index){
        if (vice_sent.sentBack == sentinal) {
            vice_sent = sentinal;
            return null;
        }
        vice_sent = vice_sent.sentBack;
        if(index == 0){
            T res = vice_sent.item;
            vice_sent = sentinal;
            return res;
        }
        return getRecursive(index-1);
    }
}
