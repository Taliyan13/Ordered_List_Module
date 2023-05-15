import java.util.Comparator;

public class QuickPushOrderedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> prev;

    private boolean lock;
    private Comparator<T> comparator;

    public QuickPushOrderedList(Comparator<T> comparator) {
        this.head = null;
        this.tail = null;
        this.prev = null;
        this.lock = false;
        this.comparator = comparator;
    }

    // O(1)
    public synchronized void push(T obj) {
        checkConcurrent();
        lock();

        Node<T> newNode = new Node<>(obj);
        if (head == null) {
            head = newNode;
            if (tail == null) {
                tail = head.next;
            }
        } else {
            prev = head;
            newNode.next = prev;
            head = newNode;
        }
        unlock();
        notifyAll();
    }

    public synchronized T pop() {
        checkConcurrent();
        lock();

        if (head == null) {
            unlock();
            notifyAll();
            throw new IllegalStateException("The linked list is empty");
        }

        Node<T> current = head;
        Node<T> maxPrev = null;
        Node<T> max = head;

        while (current.next != null) {
            if (comparator.compare(current.next.data, max.data) > 0) {
                maxPrev = current;
                max = current.next;
            }
            current = current.next;
        }

        if (maxPrev == null) {
            head = head.next;
        } else {
            maxPrev.next = max.next;
        }

        T value = max.data;

        unlock();
        notifyAll();

        return value;
    }

    private synchronized boolean isLocked() {
        return lock;
    }

    public synchronized void lock() {
        lock = true;
    }

    public synchronized void unlock() {
        lock = false;
    }

    public synchronized boolean isEmpty() {
        return head == null;
    }

    public synchronized int length() {
        int count = 0;
        while(head.next != null){
            count++ ;
            head = head.next;
        }
        return count+1;
    }

    public synchronized void checkConcurrent(){
        while (isLocked()) {
            System.out.println(Thread.currentThread() + " is waiting to push.");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

}

