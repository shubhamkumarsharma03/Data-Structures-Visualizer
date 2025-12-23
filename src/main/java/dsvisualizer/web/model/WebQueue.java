package dsvisualizer.web.model;

import java.util.ArrayList;
import java.util.List;

public class WebQueue {
    private List<Integer> elements = new ArrayList<>();
    private static final int MAX_SIZE = 100;

    public void enqueue(int value) {
        if (elements.size() >= MAX_SIZE) {
            throw new RuntimeException("Queue Full");
        }
        elements.add(value);
    }

    public Integer dequeue() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.remove(0);
    }

    public Integer peek() {
        return elements.isEmpty() ? null : elements.get(0);
    }

    public List<Integer> getElements() {
        return new ArrayList<>(elements);
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public void reset() {
        elements.clear();
    }
}
