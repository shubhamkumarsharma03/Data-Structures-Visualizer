package dsvisualizer.util;

import javafx.scene.paint.Color;

/**
 * Global constants for the Data Structures Visualizer.
 */
public class Constants {
    // Window dimensions
    public static final int WINDOW_WIDTH = 1400;
    public static final int WINDOW_HEIGHT = 900;
    public static final String APP_TITLE = "Data Structures Visualizer";

    // Colors
    public static final Color COLOR_PRIMARY = Color.web("#2196F3");
    public static final Color COLOR_SECONDARY = Color.web("#FF9800");
    public static final Color COLOR_SUCCESS = Color.web("#4CAF50");
    public static final Color COLOR_ERROR = Color.web("#F44336");
    public static final Color COLOR_WARNING = Color.web("#FFC107");
    public static final Color COLOR_VISITED = Color.web("#9C27B0");
    public static final Color COLOR_NEUTRAL = Color.web("#CCCCCC");
    public static final Color COLOR_TEXT_DARK = Color.web("#212121");
    public static final Color COLOR_TEXT_LIGHT = Color.web("#FFFFFF");
    public static final Color COLOR_BACKGROUND = Color.web("#FAFAFA");
    public static final Color COLOR_EDGE = Color.web("#555555");

    // Animation durations (milliseconds)
    public static final int ANIMATION_DURATION_FAST = 300;
    public static final int ANIMATION_DURATION_NORMAL = 600;
    public static final int ANIMATION_DURATION_SLOW = 1000;

    // UI dimensions
    public static final int CONTROL_PANEL_HEIGHT = 100;
    public static final int STATUS_PANEL_HEIGHT = 150;
    public static final int VISUALIZATION_AREA_MIN_WIDTH = 1000;
    public static final int VISUALIZATION_AREA_MIN_HEIGHT = 600;

    // Array visualization
    public static final int ARRAY_CELL_WIDTH = 60;
    public static final int ARRAY_CELL_HEIGHT = 60;
    public static final int ARRAY_CELL_SPACING = 10;
    public static final double ARRAY_STROKE_WIDTH = 2.0;

    // Stack/Queue visualization
    public static final int STACK_ELEMENT_WIDTH = 80;
    public static final int STACK_ELEMENT_HEIGHT = 50;
    public static final int QUEUE_ELEMENT_WIDTH = 50;
    public static final int QUEUE_ELEMENT_HEIGHT = 80;

    // Linked List visualization
    public static final int NODE_RADIUS = 30;
    public static final int NODE_SPACING = 150;
    public static final double LINK_STROKE_WIDTH = 2.5;
    public static final double ARROW_SIZE = 10.0;

    // Tree visualization
    public static final int TREE_NODE_RADIUS = 25;
    public static final int TREE_LEVEL_HEIGHT = 100;
    public static final int TREE_NODE_HORIZONTAL_SPACING = 60;

    // Graph visualization
    public static final int GRAPH_VERTEX_RADIUS = 20;
    public static final int GRAPH_VERTEX_SPACING = 100;

    // Speed slider ranges
    public static final double SPEED_MIN = 0.5;
    public static final double SPEED_MAX = 2.0;
    public static final double SPEED_DEFAULT = 1.0;

    // Default data structure sizes
    public static final int DEFAULT_ARRAY_SIZE = 10;
    public static final int DEFAULT_STACK_CAPACITY = 15;
    public static final int DEFAULT_QUEUE_CAPACITY = 10;

    // Font sizes
    public static final int FONT_SIZE_LABEL = 12;
    public static final int FONT_SIZE_VALUE = 14;
    public static final int FONT_SIZE_TITLE = 16;
}
