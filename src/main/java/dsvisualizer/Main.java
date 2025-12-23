package dsvisualizer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import dsvisualizer.controller.*;
import dsvisualizer.util.Constants;
import dsvisualizer.util.LayoutUtil;
import dsvisualizer.view.ControlPanel;
import dsvisualizer.view.StatusPanel;

public class Main extends Application {
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    private ScrollPane scrollPane;
    private Pane visualizationArea;
    private ArrayController arrayController;
    private StackController stackController;
    private QueueController queueController;
    private LinkedListController linkedListController;
    private BSTController bstController;
    private GraphController graphController;

    @Override
    public void start(Stage primaryStage) {
        try {
            controlPanel = new ControlPanel();
            statusPanel = new StatusPanel();
            visualizationArea = new Pane();
            visualizationArea.setStyle("-fx-background-color: white;");
            visualizationArea.setMinWidth(400);
            visualizationArea.setMinHeight(400);
            scrollPane = new ScrollPane(visualizationArea);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(false);
            scrollPane.setPannable(true);
            scrollPane.setStyle("-fx-control-inner-background: white;");
            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            arrayController = new ArrayController(visualizationArea, controlPanel, statusPanel);
            stackController = new StackController(visualizationArea, controlPanel, statusPanel);
            queueController = new QueueController(visualizationArea, controlPanel, statusPanel);
            linkedListController = new LinkedListController(visualizationArea, controlPanel, statusPanel);
            bstController = new BSTController(visualizationArea, controlPanel, statusPanel);
            graphController = new GraphController(visualizationArea, controlPanel, statusPanel);
            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: #FAFAFA;");
            root.setTop(controlPanel);
            root.setCenter(scrollPane);
            root.setBottom(statusPanel);
            javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();
            double windowWidth = Math.max(800, Math.min(screenWidth * 0.85, 1600));
            double windowHeight = Math.max(600, Math.min(screenHeight * 0.85, 1100));
            Scene scene = new Scene(root, windowWidth, windowHeight);
            primaryStage.setScene(scene);
            primaryStage.setTitle(Constants.APP_TITLE);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.show();
            setupEventHandlers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupEventHandlers() {
        controlPanel.getDataStructureSelector().setOnAction(e -> switchDataStructure());
        controlPanel.getInsertButton().setOnAction(e -> handleInsertAction());
        controlPanel.getDeleteButton().setOnAction(e -> handleDeleteAction());
        controlPanel.getSearchButton().setOnAction(e -> handleSearchAction());
        controlPanel.getResetButton().setOnAction(e -> handleResetAction());
    }

    private void switchDataStructure() {
        String selectedDS = controlPanel.getSelectedDataStructure();
        visualizationArea.getChildren().clear();
        switch (selectedDS) {
            case "Array":
                arrayController = new ArrayController(visualizationArea, controlPanel, statusPanel);
                statusPanel.setOperationLabel("Array");
                statusPanel.setExplanation("Insert, Delete, Search");
                break;
            case "Stack":
                stackController = new StackController(visualizationArea, controlPanel, statusPanel);
                statusPanel.setOperationLabel("Stack");
                statusPanel.setExplanation("Push, Pop, Peek");
                break;
            case "Queue":
                queueController = new QueueController(visualizationArea, controlPanel, statusPanel);
                statusPanel.setOperationLabel("Queue");
                statusPanel.setExplanation("Enqueue, Dequeue, Peek");
                break;
            case "Linked List":
                linkedListController = new LinkedListController(visualizationArea, controlPanel, statusPanel);
                statusPanel.setOperationLabel("Linked List");
                statusPanel.setExplanation("Insert, Delete, Search");
                break;
            case "BST":
                bstController = new BSTController(visualizationArea, controlPanel, statusPanel);
                statusPanel.setOperationLabel("Binary Search Tree");
                statusPanel.setExplanation("Insert, Delete, Search");
                break;
            case "Graph":
                graphController = new GraphController(visualizationArea, controlPanel, statusPanel);
                statusPanel.setOperationLabel("Graph");
                statusPanel.setExplanation("Add Vertex, Add Edge, BFS, DFS");
                break;
        }
        controlPanel.setStatusMessage("Switched to " + selectedDS);
        controlPanel.clearInput();
    }

    private void handleInsertAction() {
        String ds = controlPanel.getSelectedDataStructure();
        String input = controlPanel.getInputValue();
        double speed = controlPanel.getSpeed();
        switch (ds) {
            case "Array": arrayController.setAnimationSpeed(speed); arrayController.insert(input); break;
            case "Stack": stackController.setAnimationSpeed(speed); stackController.push(input); break;
            case "Queue": queueController.setAnimationSpeed(speed); queueController.enqueue(input); break;
            case "Linked List": linkedListController.setAnimationSpeed(speed); linkedListController.insertHead(input); break;
            case "BST": bstController.setAnimationSpeed(speed); bstController.insert(input); break;
            case "Graph": graphController.setAnimationSpeed(speed); graphController.addVertex(input); break;
        }
    }

    private void handleDeleteAction() {
        String ds = controlPanel.getSelectedDataStructure();
        String input = controlPanel.getInputValue();
        double speed = controlPanel.getSpeed();
        switch (ds) {
            case "Array": arrayController.setAnimationSpeed(speed); arrayController.delete(input); break;
            case "Stack": stackController.setAnimationSpeed(speed); stackController.pop(); break;
            case "Queue": queueController.setAnimationSpeed(speed); queueController.dequeue(); break;
            case "Linked List": linkedListController.setAnimationSpeed(speed); linkedListController.deleteHead(); break;
            case "BST": bstController.setAnimationSpeed(speed); bstController.delete(input); break;
            case "Graph": graphController.setAnimationSpeed(speed); graphController.addEdge(input); break;
        }
    }

    private void handleSearchAction() {
        String ds = controlPanel.getSelectedDataStructure();
        String input = controlPanel.getInputValue();
        double speed = controlPanel.getSpeed();
        switch (ds) {
            case "Array": arrayController.setAnimationSpeed(speed); arrayController.search(input); break;
            case "Stack": stackController.setAnimationSpeed(speed); stackController.peek(); break;
            case "Queue": queueController.setAnimationSpeed(speed); queueController.peek(); break;
            case "Linked List": linkedListController.setAnimationSpeed(speed); linkedListController.search(input); break;
            case "BST": bstController.setAnimationSpeed(speed); bstController.search(input); break;
            case "Graph": graphController.setAnimationSpeed(speed); graphController.bfs(input); break;
        }
    }

    private void handleResetAction() {
        String ds = controlPanel.getSelectedDataStructure();
        switch (ds) {
            case "Array": arrayController.reset(); break;
            case "Stack": stackController.reset(); break;
            case "Queue": queueController.reset(); break;
            case "Linked List": linkedListController.reset(); break;
            case "BST": bstController.reset(); break;
            case "Graph": graphController.reset(); break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
