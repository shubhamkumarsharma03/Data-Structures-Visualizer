package dsvisualizer.view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import dsvisualizer.model.Stack;
import dsvisualizer.util.Constants;
import dsvisualizer.util.LayoutUtil;

/**
 * Visual representation of a stack (LIFO).
 */
public class StackVisualizer {
    private Pane canvas;
    private Stack stack;
    private Group elementGroup;
    private Text topIndicator;

    public StackVisualizer(Pane canvas, Stack stack) {
        this.canvas = canvas;
        this.stack = stack;
        this.elementGroup = new Group();
        this.topIndicator = new Text("TOP");
        this.topIndicator.setFont(Font.font("System", FontWeight.BOLD, Constants.FONT_SIZE_TITLE));
        this.topIndicator.setFill(Constants.COLOR_ERROR);
        canvas.getChildren().addAll(elementGroup, topIndicator);
    }

    /**
     * Render the stack visualization.
     */
    public void render() {
        LayoutUtil.clearGroup(elementGroup);

        int startX = 200;
        int startY = 300;

        java.util.List<Integer> elements = stack.getElements();
        for (int i = 0; i < elements.size(); i++) {
            drawStackElement(startX, startY - i * (Constants.STACK_ELEMENT_HEIGHT + 10), 
                           i, elements.get(i), i == elements.size() - 1);
        }

        // Position TOP indicator
        if (elements.size() > 0) {
            topIndicator.setX(startX - 50);
            topIndicator.setY(startY - (elements.size() - 1) * (Constants.STACK_ELEMENT_HEIGHT + 10) + 20);
        }
    }

    /**
     * Draw a single stack element.
     */
    private void drawStackElement(double x, double y, int index, int value, boolean isTop) {
        Color fill = isTop ? Constants.COLOR_SUCCESS : Constants.COLOR_PRIMARY;
        
        Rectangle element = new Rectangle(x, y, Constants.STACK_ELEMENT_WIDTH, Constants.STACK_ELEMENT_HEIGHT);
        element.setFill(fill);
        element.setStroke(Constants.COLOR_EDGE);
        element.setStrokeWidth(2.0);
        elementGroup.getChildren().add(element);

        Text valueText = new Text(String.valueOf(value));
        valueText.setFont(Font.font("System", FontWeight.BOLD, Constants.FONT_SIZE_VALUE));
        valueText.setFill(Constants.COLOR_TEXT_LIGHT);
        valueText.setTextAlignment(TextAlignment.CENTER);
        double textX = x + (Constants.STACK_ELEMENT_WIDTH - valueText.getBoundsInLocal().getWidth()) / 2;
        double textY = y + (Constants.STACK_ELEMENT_HEIGHT - valueText.getBoundsInLocal().getHeight()) / 2 + 10;
        valueText.setX(textX);
        valueText.setY(textY);
        elementGroup.getChildren().add(valueText);
    }

    /**
     * Highlight the top element.
     */
    public void highlightTop(Color color) {
        java.util.List<Integer> elements = stack.getElements();
        if (elements.isEmpty()) return;
        
        int rectCount = 0;
        for (javafx.scene.Node node : elementGroup.getChildren()) {
            if (node instanceof Rectangle) {
                if (rectCount == elements.size() - 1) {
                    ((Rectangle) node).setFill(color);
                    return;
                }
                rectCount++;
            }
        }
    }

    /**
     * Get element group for animation.
     */
    public Group getElementGroup() {
        return elementGroup;
    }

    /**
     * Get TOP indicator.
     */
    public Text getTopIndicator() {
        return topIndicator;
    }
}
