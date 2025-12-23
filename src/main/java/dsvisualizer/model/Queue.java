package dsvisualizer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Queue data structure - FIFO (First In First Out).
 */
public class Queue {
    private List<Integer> elements;
    private int capacity;

    public Queue(int capacity) {
        this.elements = new ArrayList<>();
        this.capacity = capacity;
    }

    /**
     * Enqueue element to rear.
     */
    public boolean enqueue(int value) {
        if (isFull()) {
            throw new RuntimeException("Queue Overflow");
        }
        elements.add(value);
        return true;
    }

    /**
     * Dequeue element from front.
     */
    public int dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue Underflow");
        }
        return elements.remove(0);
    }

    /**
     * Peek at front element without removing.
     */
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return elements.get(0);
    }

    /**
     * Check if queue is empty.
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Check if queue is full.
     */
    public boolean isFull() {
        return elements.size() >= capacity;
    }

    /**
     * Get current size.
     */
    public int getSize() {
        return elements.size();
    }

    /**
     * Get capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Clear the queue.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Get all elements in order (front to rear).
     */
    public List<Integer> getElements() {
        return new ArrayList<>(elements);
    }

    /**
     * Get element at specific index (0 = front, size-1 = rear).
     */
    public int getElementAt(int index) {
        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return elements.get(index);
    }
}
