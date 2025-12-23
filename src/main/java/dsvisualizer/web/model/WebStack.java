package dsvisualizer.web.model;

import java.util.ArrayList;
import java.util.List;

public class WebStack {
    private List<Integer> elements = new ArrayList<>();
    private static final int MAX_SIZE = 100;

    public void push(int value) {
        if (elements.size() >= MAX_SIZE) {
            throw new RuntimeException("Stack Overflow");
        }
        elements.add(value);
    }

    public Integer pop() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.remove(elements.size() - 1);
    }

    public Integer peek() {
        return elements.isEmpty() ? null : elements.get(elements.size() - 1);
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
