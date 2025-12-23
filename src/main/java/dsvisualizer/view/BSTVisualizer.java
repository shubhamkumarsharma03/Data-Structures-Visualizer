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
import dsvisualizer.model.BinarySearchTree;
import dsvisualizer.util.Constants;
import dsvisualizer.util.LayoutUtil;

/**
 * Visual representation of a Binary Search Tree.
 */
public class BSTVisualizer {
    private Pane canvas;
    private BinarySearchTree bst;
    private Group nodeGroup;
    private Group edgeGroup;
    private int nodeCount;

    public BSTVisualizer(Pane canvas, BinarySearchTree bst) {
        this.canvas = canvas;
        this.bst = bst;
        this.edgeGroup = new Group();
        this.nodeGroup = new Group();
        this.nodeCount = 0;
        canvas.getChildren().addAll(edgeGroup, nodeGroup);
    }

    /**
     * Render the BST visualization.
     */
    public void render() {
        LayoutUtil.clearGroup(edgeGroup);
        LayoutUtil.clearGroup(nodeGroup);
        this.nodeCount = 0;

        if (bst.getRoot() != null) {
            renderNode(bst.getRoot(), 400, 50, 150);
        }
    }

    /**
     * Recursively render tree nodes.
     */
    private void renderNode(BinarySearchTree.TreeNode node, double x, double y, double xOffset) {
        if (node == null) {
            return;
        }

        // Draw edges to children first (so they appear behind nodes)
        if (node.left != null) {
            double leftX = x - xOffset;
            double leftY = y + Constants.TREE_LEVEL_HEIGHT;
            drawEdge(x, y, leftX, leftY);
            renderNode(node.left, leftX, leftY, xOffset / 2);
        }

        if (node.right != null) {
            double rightX = x + xOffset;
            double rightY = y + Constants.TREE_LEVEL_HEIGHT;
            drawEdge(x, y, rightX, rightY);
            renderNode(node.right, rightX, rightY, xOffset / 2);
        }

        // Draw node
        drawNode(x, y, node.data);
    }

    /**
     * Draw a node circle with value.
     */
    private void drawNode(double x, double y, int value) {
        Circle circle = new Circle(x, y, Constants.TREE_NODE_RADIUS);
        circle.setFill(Constants.COLOR_PRIMARY);
        circle.setStroke(Constants.COLOR_EDGE);
        circle.setStrokeWidth(2.0);
        nodeGroup.getChildren().add(circle);

        Text valueText = new Text(String.valueOf(value));
        valueText.setFont(javafx.scene.text.Font.font("System", FontWeight.BOLD, Constants.FONT_SIZE_VALUE));
        valueText.setFill(Constants.COLOR_TEXT_LIGHT);
        valueText.setTextAlignment(TextAlignment.CENTER);
        double textX = x - valueText.getBoundsInLocal().getWidth() / 2;
        double textY = y + 5;
        valueText.setX(textX);
        valueText.setY(textY);
        nodeGroup.getChildren().add(valueText);
    }

    /**
     * Draw an edge between two nodes.
     */
    private void drawEdge(double fromX, double fromY, double toX, double toY) {
        Line edge = new Line(fromX, fromY + Constants.TREE_NODE_RADIUS, 
                            toX, toY - Constants.TREE_NODE_RADIUS);
        edge.setStrokeWidth(Constants.LINK_STROKE_WIDTH);
        edge.setStroke(Constants.COLOR_EDGE);
        edgeGroup.getChildren().add(edge);
    }

    /**
     * Highlight a node by value.
     */
    public void highlightNodeByValue(int value, Color color) {
        for (javafx.scene.Node node : nodeGroup.getChildren()) {
            if (node instanceof Circle) {
                ((Circle) node).setFill(color);
                break;
            }
        }
    }

    /**
     * Get node group for animation.
     */
    public Group getNodeGroup() {
        return nodeGroup;
    }

    /**
     * Get edge group for animation.
     */
    public Group getEdgeGroup() {
        return edgeGroup;
    }
}
