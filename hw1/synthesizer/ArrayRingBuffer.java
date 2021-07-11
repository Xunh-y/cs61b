// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> implements Iterable<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first = 0;
        last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()) throw new RuntimeException("Ring buffer overflow");
        rb[last++] = x;
        if (last == capacity) {
            last = 0;
        }
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()) throw new RuntimeException("Ring buffer underflow");
        T ans = rb[first++];
        if (first == capacity) {
            first = 0;
        }
        fillCount--;
        return ans;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) throw new RuntimeException("Ring buffer underflow");
        return rb[first];
        // TODO: Return the first item. None of your instance variables should change.
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T>{
        private int pos;
        private T[] copy;
        private int size;

        public MyIterator() {
            pos = first;
            copy = rb;
            size = fillCount;
        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public T next() {
            return code();
        }

        public T code() {
            if (size == 0) return null;
            T ans = copy[pos++];
            if (pos == capacity) {
                pos = 0;
            }
            size--;
            return ans;
        }
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
