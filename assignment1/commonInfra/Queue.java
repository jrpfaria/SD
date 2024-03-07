package assignment1.commonInfra;

public class Queue<T extends Object> {
    private int pos;
    private int start;
    private T[] queue;

    public Queue(int length) {
        this.queue = (T[]) new Object[length];
        this.pos = 0;
        this.start = 0;
    }

    public int length() {
        return queue.length;
    }

    public void put(T value) {
        if (!isFull()) {
            queue[(start+pos++)%queue.length] = value;
        }
    }

    public T peek() {
        return queue[start];
    }

    public T get() {
        T result = queue[start];
        pos--;
        start = (start+1)%queue.length;
        return result;
    }

    public boolean isEmpty() {
        return pos==0;
    }

    public boolean isFull() {
        return pos==queue.length;
    }
}
