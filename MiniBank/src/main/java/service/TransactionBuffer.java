package service;
import java.util.ArrayDeque;
import java.util.Queue;
/** Bounded producer-consumer queue coordinated with wait/notify. */
public class TransactionBuffer<T> {
    private final Queue<T> queue=new ArrayDeque<>(); private final int capacity;
    public TransactionBuffer(int capacity) { if(capacity<1) throw new IllegalArgumentException(); this.capacity=capacity; }
    public synchronized void put(T item) throws InterruptedException { while(queue.size()==capacity) wait(); queue.add(item); notifyAll(); }
    public synchronized T take() throws InterruptedException { while(queue.isEmpty()) wait(); T item=queue.remove(); notifyAll(); return item; }
}
