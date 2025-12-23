package dsvisualizer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stack data structure - LIFO (Last In First Out).
 */
public class Stack {
    private List<Integer> elements;
    private int capacity;

    public Stack(int capacity) {
        this.elements = new ArrayList<>();
        this.capacity = capacity;
    }

    /**
     * Push element onto stack.
     */
    public boolean push(int value) {
        if (isFull()) {
            throw new RuntimeException("Stack Overflow");
        }
        elements.add(value);
        return true;
    }

    /**
     * Pop element from stack.
     */
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack Underflow");
        }
        return elements.remove(elements.size() - 1);
    }

    /**
     * Peek at top element without removing.
     */
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return elements.get(elements.size() - 1);
    }

    /**
     * Check if stack is empty.
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Check if stack is full.
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
     * Clear the stack.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Get all elements in order (bottom to top).
     */
    public List<Integer> getElements() {
        return new ArrayList<>(elements);
    }

    /**
     * Get element at specific index (0 = bottom, size-1 = top).
     */
    public int getElementAt(int index) {
        if (index < 0 || index >= elements.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return elements.get(index);
    }
}
