package dsvisualizer.view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import dsvisualizer.model.Queue;
import dsvisualizer.util.Constants;
import dsvisualizer.util.LayoutUtil;

/**
 * Visual representation of a queue (FIFO).
 */
public class QueueVisualizer {
    private Pane canvas;
    private Queue queue;
    private Group elementGroup;
    private Text frontIndicator;
    private Text rearIndicator;

    public QueueVisualizer(Pane canvas, Queue queue) {
        this.canvas = canvas;
        this.queue = queue;
        this.elementGroup = new Group();
        this.frontIndicator = new Text("FRONT");
        this.rearIndicator = new Text("REAR");
        
        frontIndicator.setFont(Font.font("System", FontWeight.BOLD, Constants.FONT_SIZE_TITLE));
        frontIndicator.setFill(Constants.COLOR_ERROR);
        rearIndicator.setFont(Font.font("System", FontWeight.BOLD, Constants.FONT_SIZE_TITLE));
        rearIndicator.setFill(Constants.COLOR_WARNING);
        
        canvas.getChildren().addAll(elementGroup, frontIndicator, rearIndicator);
    }

    /**
     * Render the queue visualization.
     */
    public void render() {
        LayoutUtil.clearGroup(elementGroup);

        int startX = 100;
        int startY = 200;

        java.util.List<Integer> elements = queue.getElements();
        for (int i = 0; i < elements.size(); i++) {
            drawQueueElement(startX + i * (Constants.QUEUE_ELEMENT_WIDTH + 20), startY, 
                           i, elements.get(i), i == 0, i == elements.size() - 1);
        }

        // Position indicators
        if (!elements.isEmpty()) {
            frontIndicator.setX(startX - 30);
            frontIndicator.setY(startY + Constants.QUEUE_ELEMENT_HEIGHT + 30);
            
            int lastIndex = elements.size() - 1;
            rearIndicator.setX(startX + lastIndex * (Constants.QUEUE_ELEMENT_WIDTH + 20) - 20);
            rearIndicator.setY(startY + Constants.QUEUE_ELEMENT_HEIGHT + 30);
        }
    }

    /**
     * Draw a single queue element.
     */
    private void drawQueueElement(double x, double y, int index, int value, boolean isFront, boolean isRear) {
        Color fill = isFront ? Constants.COLOR_ERROR : (isRear ? Constants.COLOR_WARNING : Constants.COLOR_PRIMARY);
        
        Rectangle element = new Rectangle(x, y, Constants.QUEUE_ELEMENT_WIDTH, Constants.QUEUE_ELEMENT_HEIGHT);
        element.setFill(fill);
        element.setStroke(Constants.COLOR_EDGE);
        element.setStrokeWidth(2.0);
        elementGroup.getChildren().add(element);

        Text valueText = new Text(String.valueOf(value));
        valueText.setFont(Font.font("System", FontWeight.BOLD, Constants.FONT_SIZE_VALUE));
        valueText.setFill(Constants.COLOR_TEXT_LIGHT);
        valueText.setTextAlignment(TextAlignment.CENTER);
        double textX = x + (Constants.QUEUE_ELEMENT_WIDTH - valueText.getBoundsInLocal().getWidth()) / 2;
        double textY = y + (Constants.QUEUE_ELEMENT_HEIGHT - valueText.getBoundsInLocal().getHeight()) / 2 + 10;
        valueText.setX(textX);
        valueText.setY(textY);
        elementGroup.getChildren().add(valueText);
    }

    /**
     * Highlight front element.
     */
    public void highlightFront(Color color) {
        if (queue.isEmpty()) return;
        
        int rectCount = 0;
        for (javafx.scene.Node node : elementGroup.getChildren()) {
            if (node instanceof Rectangle) {
                if (rectCount == 0) {
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
     * Get front indicator.
     */
    public Text getFrontIndicator() {
        return frontIndicator;
    }

    /**
     * Get rear indicator.
     */
    public Text getRearIndicator() {
        return rearIndicator;
    }
}
