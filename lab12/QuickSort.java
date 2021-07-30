import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    public static void main(String[] args) {
        Queue<String> students = new Queue<>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        students.enqueue("Bob");
        students.enqueue("NOC");
        students.enqueue("MF");
        System.out.println(students);
        students = quickSort(students);
        System.out.println(students);
        Queue<Integer> id = new Queue<>();
        id.enqueue(6);
        id.enqueue(3);
        id.enqueue(5);
        id.enqueue(2);
        id.enqueue(9);
        id.enqueue(7);
        System.out.println(id);
        id = quickSort(id);
        System.out.println(id);
    }
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        for (Item item : unsorted) {
            if (item.compareTo(pivot) > 0) {
                greater.enqueue(item);
            } else if (item.compareTo(pivot) < 0) {
                less.enqueue(item);
            } else {
                equal.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        if (items.size() <= 1) {
            return items;
        }
        Item pivot = getRandomItem(items);
        Queue<Item> less = new Queue<>(), equal = new Queue<>(), greater = new Queue<>();
        partition(items, pivot, less, equal, greater);
        less = quickSort(less);
        greater = quickSort(greater);
        items = catenate(less, catenate(equal, greater));
        return items;
    }
}
