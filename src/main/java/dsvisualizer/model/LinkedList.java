package dsvisualizer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Singly Linked List data structure.
 */
public class LinkedList {
    public static class Node {
        public int data;
        public Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Insert at head.
     */
    public void insertHead(int value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
        size++;
    }

    /**
     * Insert at tail.
     */
    public void insertTail(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Insert at specific index.
     */
    public void insertAt(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == 0) {
            insertHead(value);
            return;
        }
        Node newNode = new Node(value);
        Node current = getNodeAt(index - 1);
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    /**
     * Delete from head.
     */
    public int deleteHead() {
        if (head == null) {
            throw new RuntimeException("List is empty");
        }
        int value = head.data;
        head = head.next;
        size--;
        return value;
    }

    /**
     * Delete at specific index.
     */
    public int deleteAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == 0) {
            return deleteHead();
        }
        Node prev = getNodeAt(index - 1);
        int value = prev.next.data;
        prev.next = prev.next.next;
        size--;
        return value;
    }

    /**
     * Get element at specific index.
     */
    public int get(int index) {
        return getNodeAt(index).data;
    }

    /**
     * Search for element (returns index or -1).
     */
    public int search(int value) {
        Node current = head;
        int index = 0;
        while (current != null) {
            if (current.data == value) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Get node at specific index.
     */
    private Node getNodeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * Get head node.
     */
    public Node getHead() {
        return head;
    }

    /**
     * Get current size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Check if list is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clear the list.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Get all elements in order.
     */
    public List<Integer> getElements() {
        List<Integer> result = new ArrayList<>();
        Node current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }
}
