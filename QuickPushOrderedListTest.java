import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Comparator;

import static org.junit.Assert.*;
public class QuickPushOrderedListTest<T> {
    private QuickPushOrderedList<T> list;
    private T[] objectsList;
    private Comparator<T> comparator;
    private static final int MAX_PUSH_DURATION = 1191800;



    @Before
    public void setUp() {
        objectsList = (T[]) new Object[]{1,"grapefruit", 5, 3, 11,"apple", "kiwi", 2, 14, 8, 4, "lemon", "orange", "pineapple", "raspberry","mango", "strawberry"};
        comparator = new Comparator<T>() {
            public int compare(T a, T b) {
                if (a.getClass() == b.getClass()) {
                    if (a instanceof Integer) {
                        return Integer.compare((Integer) a, (Integer) b);
                    } else if (a instanceof String) {
                        return ((String) a).compareTo((String) b);
                    }
                } else if (a instanceof Integer && b instanceof String) {
                    return -1; // Consider integers to be smaller than strings
                } else if (a instanceof String && b instanceof Integer) {
                    return 1; // Consider strings to be greater than integers
                }
                throw new IllegalArgumentException("Unsupported types: " + a.getClass().getSimpleName() + " and " + b.getClass().getSimpleName());
            }
        };
        // Create a new QuickPushOrderedList with the custom comparator
        list = new QuickPushOrderedList<>(comparator);
    }

    @Test
    public void testPush_O1Runtime() {
        for (T obj : objectsList) {
            long startTime = System.nanoTime();
            list.push(obj);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("Push operation executed in: " + duration + " nanoseconds");
            // Assert that the execution time is within the expected range for O(1) runtime
            assertTrue("PUSH operation should have O(1) runtime", duration <= MAX_PUSH_DURATION); // Adjust the threshold as needed
        }
    }



    @Test
    public void testPop_ONRuntime() {
        for (T obj : objectsList) {
            list.push(obj);
        }
        // Pop the values and measure the execution time
        while (!list.isEmpty()) {
            long startTime = System.nanoTime();
            T value = list.pop();
            System.out.println("Popped value: " + value);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("Pop operation executed in: " + duration + " nanoseconds");
            // Assert that the execution time is within the expected range for O(N) runtime
            assertTrue("POP operation should have O(N) runtime", duration <= MAX_PUSH_DURATION * this.objectsList.length); 
        }
    }



    @Test
    public void testPushConcurrent() throws InterruptedException {
        final int numThreads = 10;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (T obj : objectsList) {
                    list.push(obj);
                }
            });
        }
    
        // Start all threads
        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }
    
        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
    
        // Assert that the list is empty after all operations
        assertFalse(list.isEmpty());
    }
    
    
    // test for pop concurrent from an empty list.
    @Test
    public void testPopFromEmptyListConcurrent() throws InterruptedException {
        final int numThreads = 10;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                Assertions.assertThrows(IllegalStateException.class, () -> {
                    list.pop();
                });
            });
        }
    
        // Start all threads
        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }
    
        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
    
        // Assert that the list is empty after all operations
        assertTrue(list.isEmpty());
    }

    
    
    @Test
    public void testPopConcurrent() throws InterruptedException {
        final int numThreads = 2;
        Thread[] threads = new Thread[numThreads];
  
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (T obj : objectsList) {
                    list.push(obj);
                }
                for (int j = 0; j < objectsList.length; j++) {
                    T value = list.pop();
                    assertNotNull(value);   
                }
            });
        }
    
        // Start all threads
        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }
    
        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
    
        // Assert that the list is empty after all operations
        assertTrue(list.isEmpty());
    }

 }
