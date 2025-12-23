package dsvisualizer.view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import dsvisualizer.model.LinkedList;
import dsvisualizer.util.Constants;
import dsvisualizer.util.LayoutUtil;

/**
 * Visual representation of a singly linked list.
 */
public class LinkedListVisualizer {
    private Pane canvas;
    private LinkedList linkedList;
    private Group nodeGroup;
    private Group linkGroup;

    public LinkedListVisualizer(Pane canvas, LinkedList linkedList) {
        this.canvas = canvas;
        this.linkedList = linkedList;
        this.linkGroup = new Group();
        this.nodeGroup = new Group();
        canvas.getChildren().addAll(linkGroup, nodeGroup);
    }

    /**
     * Render the linked list visualization.
     */
    public void render() {
        LayoutUtil.clearGroup(linkGroup);
        LayoutUtil.clearGroup(nodeGroup);

        LinkedList.Node current = linkedList.getHead();
        int index = 0;
        int startX = 50;
        int startY = 200;

        while (current != null) {
            double x = startX + index * Constants.NODE_SPACING;
            drawNode(x, startY, current.data, index);

            // Draw link to next node
            if (current.next != null) {
                double nextX = x + Constants.NODE_SPACING;
                drawLink(x, startY, nextX, startY);
            }

            current = current.next;
            index++;
        }
    }

    /**
     * Draw a node circle with value.
     */
    private void drawNode(double x, double y, int value, int index) {
        Circle circle = new Circle(x, y, Constants.NODE_RADIUS);
        circle.setFill(Constants.COLOR_PRIMARY);
        circle.setStroke(Constants.COLOR_EDGE);
        circle.setStrokeWidth(2.0);
        nodeGroup.getChildren().add(circle);

        Text valueText = new Text(String.valueOf(value));
        valueText.setFont(Font.font("System", FontWeight.BOLD, Constants.FONT_SIZE_VALUE));
        valueText.setFill(Constants.COLOR_TEXT_LIGHT);
        valueText.setTextAlignment(TextAlignment.CENTER);
        double textX = x - valueText.getBoundsInLocal().getWidth() / 2;
        double textY = y + 5;
        valueText.setX(textX);
        valueText.setY(textY);
        nodeGroup.getChildren().add(valueText);

        // Index label
        Text indexText = new Text("[" + index + "]");
        indexText.setFont(Font.font("System", Constants.FONT_SIZE_LABEL));
        indexText.setFill(Constants.COLOR_TEXT_DARK);
        double indexX = x - indexText.getBoundsInLocal().getWidth() / 2;
        double indexY = y + Constants.NODE_RADIUS + 20;
        indexText.setX(indexX);
        indexText.setY(indexY);
        nodeGroup.getChildren().add(indexText);
    }

    /**
     * Draw a link (arrow) between two nodes.
     */
    private void drawLink(double fromX, double fromY, double toX, double toY) {
        Line arrow = new Line(fromX + Constants.NODE_RADIUS + 5, fromY, 
                             toX - Constants.NODE_RADIUS - 5, toY);
        arrow.setStrokeWidth(Constants.LINK_STROKE_WIDTH);
        arrow.setStroke(Constants.COLOR_EDGE);
        linkGroup.getChildren().add(arrow);

        // Arrowhead
        double angle = Math.atan2(toY - fromY, toX - fromX);
        double arrowX = toX - Constants.NODE_RADIUS - 5;
        double arrowY = toY;
        
        Line arrowHead1 = new Line(arrowX, arrowY,
                                  arrowX - Constants.ARROW_SIZE * Math.cos(angle - Math.PI / 6),
                                  arrowY - Constants.ARROW_SIZE * Math.sin(angle - Math.PI / 6));
        arrowHead1.setStrokeWidth(Constants.LINK_STROKE_WIDTH);
        arrowHead1.setStroke(Constants.COLOR_EDGE);
        
        Line arrowHead2 = new Line(arrowX, arrowY,
                                  arrowX - Constants.ARROW_SIZE * Math.cos(angle + Math.PI / 6),
                                  arrowY - Constants.ARROW_SIZE * Math.sin(angle + Math.PI / 6));
        arrowHead2.setStrokeWidth(Constants.LINK_STROKE_WIDTH);
        arrowHead2.setStroke(Constants.COLOR_EDGE);
        
        linkGroup.getChildren().addAll(arrowHead1, arrowHead2);
    }

    /**
     * Highlight a node.
     */
    public void highlightNode(int index, Color color) {
        LinkedList.Node current = linkedList.getHead();
        int currentIndex = 0;

        while (current != null) {
            if (currentIndex == index) {
                // Find and update the circle
                for (javafx.scene.Node node : nodeGroup.getChildren()) {
                    if (node instanceof Circle) {
                        int circleCount = 0;
                        for (javafx.scene.Node n : nodeGroup.getChildren()) {
                            if (n instanceof Circle) {
                                if (circleCount == index) {
                                    ((Circle) n).setFill(color);
                                    return;
                                }
                                circleCount++;
                            }
                        }
                    }
                }
            }
            current = current.next;
            currentIndex++;
        }
    }

    /**
     * Get node group for animation.
     */
    public Group getNodeGroup() {
        return nodeGroup;
    }

    /**
     * Get link group for animation.
     */
    public Group getLinkGroup() {
        return linkGroup;
    }
}
