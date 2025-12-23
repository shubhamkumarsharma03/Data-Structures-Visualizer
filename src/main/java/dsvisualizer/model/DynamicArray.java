package dsvisualizer.model;

/**
 * Array data structure - Fixed size, index-based operations.
 */
public class DynamicArray {
    private int[] data;
    private int size;
    private int capacity;

    public DynamicArray(int initialCapacity) {
        this.capacity = initialCapacity;
        this.data = new int[capacity];
        this.size = 0;
    }

    /**
     * Insert element at specific index.
     */
    public boolean insert(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (size >= capacity) {
            resize();
        }
        // Shift elements to the right
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
        return true;
    }

    /**
     * Delete element at specific index.
     */
    public int delete(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        int value = data[index];
        // Shift elements to the left
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        return value;
    }

    /**
     * Get element at specific index.
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return data[index];
    }

    /**
     * Search for element (returns index or -1).
     */
    public int search(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get current size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Check if array is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clear the array.
     */
    public void clear() {
        size = 0;
        data = new int[capacity];
    }

    /**
     * Get all current elements.
     */
    public int[] getElements() {
        int[] result = new int[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    /**
     * Resize internal array when capacity is exceeded.
     */
    private void resize() {
        int newCapacity = capacity * 2;
        int[] newData = new int[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
        capacity = newCapacity;
    }
}
