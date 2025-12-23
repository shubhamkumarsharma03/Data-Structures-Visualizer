package dsvisualizer.web.model;

import java.util.ArrayList;
import java.util.List;

public class WebArray {
    private List<Integer> elements = new ArrayList<>();
    private static final int MAX_SIZE = 100;

    public void insert(int value) {
        if (elements.size() >= MAX_SIZE) {
            throw new RuntimeException("Array is full");
        }
        elements.add(value);
    }

    public Integer delete(int index) {
        if (index < 0 || index >= elements.size()) {
            return null;
        }
        return elements.remove(index);
    }

    public int search(int value) {
        return elements.indexOf(value);
    }

    public List<Integer> getElements() {
        return new ArrayList<>(elements);
    }

    public int size() {
        return elements.size();
    }

    public int getCapacity() {
        return MAX_SIZE;
    }

    public void reset() {
        elements.clear();
    }
}
