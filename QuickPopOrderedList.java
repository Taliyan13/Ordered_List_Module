import java.util.Comparator;


public class QuickPopOrderedList<T> {
    private Node<T> head;
    private boolean lock;
    private Comparator<T> comparator;

    public QuickPopOrderedList(Comparator<T> comparator) {
        this.head = null;
        this.lock = false;
        this.comparator = comparator;
    }

    // O(N)
 public synchronized void push(T value) {
        checkConcurrent();
        lock();

        Node<T> newNode = new Node<>(value);
            if (head == null || comparator.compare(value, head.data) > 0) {
                newNode.next = head;
                head = newNode;
            } else {
                Node<T> current = head;
                while (current.next != null && comparator.compare(value, current.next.data) <= 0) {
                    current = current.next;
                }
                newNode.next = current.next;
                current.next = newNode;
            }

            unlock();
            notifyAll();
    }


    // O(1)
    public synchronized T pop() {
        checkConcurrent();  
        lock();
        if (head == null) {
            throw new IllegalStateException("The linked list is empty");
        }
        T value = head.data;
        head = head.next;
        unlock();
        notifyAll();
        return value;
    }


    private synchronized boolean isLocked() {
        return lock;
    }

    private synchronized void lock() {
        lock = true;
    }

    private synchronized void unlock() {
        lock = false;
    }

    public synchronized boolean isEmpty() {
        return head == null;
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