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
import dsvisualizer.model.Graph;
import dsvisualizer.util.Constants;
import dsvisualizer.util.LayoutUtil;

import java.util.*;

/**
 * Visual representation of a graph.
 */
public class GraphVisualizer {
    private Pane canvas;
    private Graph graph;
    private Group nodeGroup;
    private Group edgeGroup;
    private Map<Integer, double[]> vertexPositions; // Stores x, y for each vertex

    public GraphVisualizer(Pane canvas, Graph graph) {
        this.canvas = canvas;
        this.graph = graph;
        this.edgeGroup = new Group();
        this.nodeGroup = new Group();
        this.vertexPositions = new HashMap<>();
        canvas.getChildren().addAll(edgeGroup, nodeGroup);
    }

    /**
     * Render the graph visualization.
     */
    public void render() {
        LayoutUtil.clearGroup(edgeGroup);
        LayoutUtil.clearGroup(nodeGroup);
        vertexPositions.clear();

        Set<Integer> vertices = graph.getVertices();
        if (vertices.isEmpty()) {
            return;
        }

        // Position vertices in a circle
        int vertexCount = vertices.size();
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        double radius = 150;

        int index = 0;
        for (Integer vertex : vertices) {
            double angle = 2 * Math.PI * index / vertexCount;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            vertexPositions.put(vertex, new double[]{x, y});
            index++;
        }

        // Draw edges first
        for (Integer from : vertices) {
            for (Integer to : graph.getNeighbors(from)) {
                if (vertexPositions.containsKey(to)) {
                    double[] fromPos = vertexPositions.get(from);
                    double[] toPos = vertexPositions.get(to);
                    drawEdge(fromPos[0], fromPos[1], toPos[0], toPos[1], !graph.isDirected());
                }
            }
        }

        // Draw nodes
        for (Integer vertex : vertices) {
            double[] pos = vertexPositions.get(vertex);
            drawVertex(pos[0], pos[1], vertex);
        }
    }

    /**
     * Draw a vertex (node).
     */
    private void drawVertex(double x, double y, int value) {
        Circle circle = new Circle(x, y, Constants.GRAPH_VERTEX_RADIUS);
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
    }

    /**
     * Draw an edge between two vertices.
     */
    private void drawEdge(double fromX, double fromY, double toX, double toY, boolean skipReverse) {
        // Calculate angle for offset from circle center
        double dx = toX - fromX;
        double dy = toY - fromY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double ratio = Constants.GRAPH_VERTEX_RADIUS / distance;

        double startX = fromX + dx * ratio;
        double startY = fromY + dy * ratio;
        double endX = toX - dx * ratio;
        double endY = toY - dy * ratio;

        Line edge = new Line(startX, startY, endX, endY);
        edge.setStrokeWidth(2.0);
        edge.setStroke(Constants.COLOR_EDGE);
        edgeGroup.getChildren().add(edge);

        // Draw arrowhead if directed
        if (graph.isDirected()) {
            double angle = Math.atan2(dy, dx);
            double arrowX = endX;
            double arrowY = endY;
            double arrowSize = 10;

            Line arrowHead1 = new Line(arrowX, arrowY,
                                      arrowX - arrowSize * Math.cos(angle - Math.PI / 6),
                                      arrowY - arrowSize * Math.sin(angle - Math.PI / 6));
            arrowHead1.setStrokeWidth(2.0);
            arrowHead1.setStroke(Constants.COLOR_EDGE);

            Line arrowHead2 = new Line(arrowX, arrowY,
                                      arrowX - arrowSize * Math.cos(angle + Math.PI / 6),
                                      arrowY - arrowSize * Math.sin(angle + Math.PI / 6));
            arrowHead2.setStrokeWidth(2.0);
            arrowHead2.setStroke(Constants.COLOR_EDGE);

            edgeGroup.getChildren().addAll(arrowHead1, arrowHead2);
        }
    }

    /**
     * Highlight a vertex.
     */
    public void highlightVertex(int value, Color color) {
        for (javafx.scene.Node node : nodeGroup.getChildren()) {
            if (node instanceof Circle) {
                ((Circle) node).setFill(color);
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

    /**
     * Get vertex position.
     */
    public double[] getVertexPosition(int vertex) {
        return vertexPositions.get(vertex);
    }
}
