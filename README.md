
# Ordered List Module

The Ordered List Module consists of two classes: `QuickPopOrderedList` and `QuickPushOrderedList`. This module provides functionality for managing ordered lists, implemented using linked lists. Each class offers two main functions: `POP` and `PUSH`.

## QuickPushOrderedList

In the `QuickPushOrderedList` class, the `push` function operates with O(1) complexity, ensuring that the insertion is always made at the top of the list. On the other hand, the `pop` function runs with O(N) complexity, which means that removal from the list requires traversing the entire list until the relevant element is reached.

## QuickPopOrderedList

In the `QuickPopOrderedList` class, the `pop` function runs with O(1) complexity, ensuring that the retrieval is always performed from the top of the list. Conversely, the `push` function operates with O(N) complexity, meaning that inserting elements into the list requires traversing the entire list until reaching the correct position for the new element.
In the tests I conducted, the atomic operation executed by the threads happened too quickly, and the expected output for "System.out.println(Thread.currentThread() + " is waiting to push.");" was not printed on the screen. To observe the output and verify the behavior of the threads, I introduced a delay of wait(1000) in the test. With this delay, the expected output was successfully printed, confirming that the test is functioning as expected. The delay allowed me to visualize and validate the preference that threads have in accessing and processing the ordered list.



**Unit Test for operation executed  time**

To ensure the functionality and performance of the Ordered List Module, I have implemented unit tests that focus on the running times of the O(1) and O(N) operations for each function, respectively.

By executing the tests, I have determined the maximum running time for the O(1) operation, and to evaluate the O(N) operation, I multiplied the maximum O(1) execution time by the length of the list input.

**I have saved them as static values with the following names -**

 MAX_PUSH_DURATION
 MAX_POP_DURATION


**Unit Test for Multi-Threads**

Additionally, I have incorporated unit tests for multi-threaded scenarios. These tests employ synchronization mechanisms and controlled delays.

**Running the Program**

To compile the program, navigate to the `src` directory using the following command:
### cd .\src\
### javac Node.java QuickPushOrderedList.java QuickPopOrderedList.java




