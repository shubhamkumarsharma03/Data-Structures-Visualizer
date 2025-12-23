package dsvisualizer.util;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * Layout utilities for positioning nodes in visualization areas.
 */
public class LayoutUtil {

    /**
     * Centers a node horizontally within a pane.
     */
    public static void centerHorizontally(Pane parent, Group child) {
        Bounds bounds = child.getBoundsInLocal();
        double centerX = (parent.getWidth() - bounds.getWidth()) / 2;
        child.setLayoutX(centerX);
    }

    /**
     * Centers a node both horizontally and vertically within a pane.
     */
    public static void centerNode(Pane parent, Group child) {
        Bounds bounds = child.getBoundsInLocal();
        double centerX = (parent.getWidth() - bounds.getWidth()) / 2;
        double centerY = (parent.getHeight() - bounds.getHeight()) / 2;
        child.setLayoutX(centerX);
        child.setLayoutY(centerY);
    }

    /**
     * Draws an arrow from one circle to another.
     */
    public static Line drawArrow(Circle from, Circle to, Group parent) {
        double fromX = from.getCenterX() + from.getTranslateX();
        double fromY = from.getCenterY() + from.getTranslateY();
        double toX = to.getCenterX() + to.getTranslateX();
        double toY = to.getCenterY() + to.getTranslateY();

        // Calculate direction and adjust endpoints to circle boundaries
        double dx = toX - fromX;
        double dy = toY - fromY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        double ratio = (distance - from.getRadius() - to.getRadius()) / distance;
        double startX = fromX + dx * (from.getRadius() / distance);
        double startY = fromY + dy * (from.getRadius() / distance);
        double endX = toX - dx * (to.getRadius() / distance);
        double endY = toY - dy * (to.getRadius() / distance);

        Line arrow = new Line(startX, startY, endX, endY);
        arrow.setStrokeWidth(Constants.LINK_STROKE_WIDTH);
        arrow.setStroke(Constants.COLOR_EDGE);
        parent.getChildren().add(arrow);
        return arrow;
    }

    /**
     * Calculates position for tree nodes using level-order layout.
     */
    public static double[] calculateTreeNodePosition(int level, int position, int siblingCount) {
        double y = 50 + level * Constants.TREE_LEVEL_HEIGHT;
        double totalWidth = siblingCount * Constants.TREE_NODE_HORIZONTAL_SPACING;
        double x = 500 + position * Constants.TREE_NODE_HORIZONTAL_SPACING - totalWidth / 2;
        return new double[]{x, y};
    }

    /**
     * Anchors a node to fill a pane.
     */
    public static void anchorFill(AnchorPane pane, javafx.scene.Node node) {
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }

    /**
     * Anchors a node with specific margins.
     */
    public static void anchorWithMargins(AnchorPane pane, javafx.scene.Node node, 
                                         double top, double bottom, double left, double right) {
        AnchorPane.setTopAnchor(node, top);
        AnchorPane.setBottomAnchor(node, bottom);
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setRightAnchor(node, right);
    }

    /**
     * Clears all children from a group.
     */
    public static void clearGroup(Group group) {
        group.getChildren().clear();
    }
}
