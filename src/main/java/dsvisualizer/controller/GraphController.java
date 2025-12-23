package dsvisualizer.controller;

import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import dsvisualizer.animation.AnimationFactory;
import dsvisualizer.model.Graph;
import dsvisualizer.util.Constants;
import dsvisualizer.util.ValidationUtil;
import dsvisualizer.view.ControlPanel;
import dsvisualizer.view.GraphVisualizer;
import dsvisualizer.view.StatusPanel;

import java.util.List;

/**
 * Controller for Graph operations.
 */
public class GraphController {
    private Graph graph;
    private GraphVisualizer visualizer;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    private double animationSpeed;

    public GraphController(Pane canvas, ControlPanel controlPanel, StatusPanel statusPanel) {
        this.graph = new Graph(false); // Undirected by default
        this.visualizer = new GraphVisualizer(canvas, graph);
        this.controlPanel = controlPanel;
        this.statusPanel = statusPanel;
        this.animationSpeed = Constants.SPEED_DEFAULT;
        visualizer.render();
    }

    /**
     * Add vertex to graph.
     */
    public void addVertex(String input) {
        try {
            if (!ValidationUtil.isNotEmpty(input)) {
                statusPanel.setError("Please enter a vertex value");
                return;
            }
            if (!ValidationUtil.isValidInteger(input)) {
                statusPanel.setError("Invalid integer input");
                return;
            }

            int vertex = Integer.parseInt(input);
            
            statusPanel.setOperationLabel("Adding vertex " + vertex);
            statusPanel.setPseudocode("graph.addVertex(" + vertex + ")");
            statusPanel.setExplanation("Creating new vertex in graph");

            graph.addVertex(vertex);
            visualizer.render();
            
            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeIn(visualizer.getNodeGroup(), getAnimationDuration()),
                AnimationFactory.pulseNode(visualizer.getNodeGroup(), getAnimationDuration())
            );
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Vertex " + vertex + " added");
            controlPanel.setStatusMessageSuccess("Vertex added!");
            controlPanel.clearInput();
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Add edge between two vertices (expects format: "from to").
     */
    public void addEdge(String input) {
        try {
            if (!ValidationUtil.isNotEmpty(input)) {
                statusPanel.setError("Please enter edge (format: 'from to')");
                return;
            }

            String[] parts = input.split("\\s+");
            if (parts.length != 2) {
                statusPanel.setError("Invalid format. Use: from to");
                return;
            }

            if (!ValidationUtil.isValidInteger(parts[0]) || !ValidationUtil.isValidInteger(parts[1])) {
                statusPanel.setError("Both values must be integers");
                return;
            }

            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);
            
            statusPanel.setOperationLabel("Adding edge " + from + " -> " + to);
            statusPanel.setPseudocode("graph.addEdge(" + from + ", " + to + ")");
            statusPanel.setExplanation("Creating connection between vertices " + from + " and " + to);

            graph.addEdge(from, to);
            visualizer.render();
            
            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeIn(visualizer.getEdgeGroup(), getAnimationDuration())
            );
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Edge added");
            controlPanel.setStatusMessageSuccess("Edge added!");
            controlPanel.clearInput();
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Perform BFS from starting vertex.
     */
    public void bfs(String input) {
        try {
            if (!ValidationUtil.isNotEmpty(input)) {
                statusPanel.setError("Please enter starting vertex");
                return;
            }
            if (!ValidationUtil.isValidInteger(input)) {
                statusPanel.setError("Invalid integer input");
                return;
            }

            int start = Integer.parseInt(input);
            
            statusPanel.setOperationLabel("BFS from " + start);
            statusPanel.setPseudocode("queue = [start]; while queue not empty: process vertex, add neighbors");
            
            List<Integer> result = graph.bfs(start);
            
            if (result.isEmpty()) {
                statusPanel.setError("Vertex not found!");
            } else {
                statusPanel.setExplanation("BFS order: " + result);
                statusPanel.setOperationLabelSuccess("BFS complete");
                controlPanel.setStatusMessageSuccess("BFS: " + result);
            }
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Perform DFS from starting vertex.
     */
    public void dfs(String input) {
        try {
            if (!ValidationUtil.isNotEmpty(input)) {
                statusPanel.setError("Please enter starting vertex");
                return;
            }
            if (!ValidationUtil.isValidInteger(input)) {
                statusPanel.setError("Invalid integer input");
                return;
            }

            int start = Integer.parseInt(input);
            
            statusPanel.setOperationLabel("DFS from " + start);
            statusPanel.setPseudocode("stack = [start]; while stack not empty: process vertex, add neighbors");
            
            List<Integer> result = graph.dfs(start);
            
            if (result.isEmpty()) {
                statusPanel.setError("Vertex not found!");
            } else {
                statusPanel.setExplanation("DFS order: " + result);
                statusPanel.setOperationLabelSuccess("DFS complete");
                controlPanel.setStatusMessageSuccess("DFS: " + result);
            }
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Reset the graph.
     */
    public void reset() {
        graph.clear();
        visualizer.render();
        statusPanel.clearStatus();
        controlPanel.setStatusMessage("Graph cleared");
        controlPanel.clearInput();
    }

    /**
     * Set animation speed.
     */
    public void setAnimationSpeed(double speed) {
        this.animationSpeed = speed;
    }

    /**
     * Get animation duration.
     */
    private int getAnimationDuration() {
        return (int) (Constants.ANIMATION_DURATION_NORMAL / animationSpeed);
    }

    /**
     * Get the current graph.
     */
    public Graph getGraph() {
        return graph;
    }
}
