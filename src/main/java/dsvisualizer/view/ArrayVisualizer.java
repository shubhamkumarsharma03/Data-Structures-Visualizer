package dsvisualizer.view;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Bounds;
import dsvisualizer.model.DynamicArray;
import dsvisualizer.util.Constants;
import dsvisualizer.util.LayoutUtil;

/**
 * Visual representation of an array.
 */
public class ArrayVisualizer {
    private Pane canvas;
    private DynamicArray array;
    private Group elementsGroup;
    private Group indexLabelsGroup;

    public ArrayVisualizer(Pane canvas, DynamicArray array) {
        this.canvas = canvas;
        this.array = array;
        this.elementsGroup = new Group();
        this.indexLabelsGroup = new Group();
        canvas.getChildren().addAll(elementsGroup, indexLabelsGroup);
    }

    /**
     * Render the array visualization.
     */
    public void render() {
        LayoutUtil.clearGroup(elementsGroup);
        LayoutUtil.clearGroup(indexLabelsGroup);

        int[] elements = array.getElements();
        int startX = 50;
        int startY = 100;

        for (int i = 0; i < elements.length; i++) {
            drawCell(startX + i * (Constants.ARRAY_CELL_WIDTH + Constants.ARRAY_CELL_SPACING), 
                     startY, i, elements[i]);
        }
    }

    /**
     * Draw a single cell.
     */
    private void drawCell(double x, double y, int index, int value) {
        // Cell rectangle
        Rectangle cell = new Rectangle(x, y, Constants.ARRAY_CELL_WIDTH, Constants.ARRAY_CELL_HEIGHT);
        cell.setFill(Constants.COLOR_PRIMARY);
        cell.setStroke(Constants.COLOR_EDGE);
        cell.setStrokeWidth(Constants.ARRAY_STROKE_WIDTH);
        elementsGroup.getChildren().add(cell);

        // Cell value text
        Text valueText = new Text(String.valueOf(value));
        valueText.setFont(Font.font("System", FontWeight.BOLD, Constants.FONT_SIZE_VALUE));
        valueText.setFill(Constants.COLOR_TEXT_LIGHT);
        valueText.setTextAlignment(TextAlignment.CENTER);
        double textX = x + (Constants.ARRAY_CELL_WIDTH - valueText.getBoundsInLocal().getWidth()) / 2;
        double textY = y + (Constants.ARRAY_CELL_HEIGHT - valueText.getBoundsInLocal().getHeight()) / 2 + 10;
        valueText.setX(textX);
        valueText.setY(textY);
        elementsGroup.getChildren().add(valueText);

        // Index label
        Text indexText = new Text(String.valueOf(index));
        indexText.setFont(Font.font("System", FontWeight.NORMAL, Constants.FONT_SIZE_LABEL));
        indexText.setFill(Constants.COLOR_TEXT_DARK);
        double indexX = x + (Constants.ARRAY_CELL_WIDTH - indexText.getBoundsInLocal().getWidth()) / 2;
        double indexY = y + Constants.ARRAY_CELL_HEIGHT + 20;
        indexText.setX(indexX);
        indexText.setY(indexY);
        indexLabelsGroup.getChildren().add(indexText);
    }

    /**
     * Highlight a cell.
     */
    public void highlightCell(int index, Color color) {
        if (index < 0 || index >= elementsGroup.getChildren().size()) {
            return;
        }
        // Find the rectangle at this index
        int rectCount = 0;
        for (javafx.scene.Node node : elementsGroup.getChildren()) {
            if (node instanceof Rectangle) {
                if (rectCount == index) {
                    ((Rectangle) node).setFill(color);
                    return;
                }
                rectCount++;
            }
        }
    }

    /**
     * Reset cell color.
     */
    public void resetCellColor(int index, Color color) {
        if (index < 0 || index >= elementsGroup.getChildren().size()) {
            return;
        }
        int rectCount = 0;
        for (javafx.scene.Node node : elementsGroup.getChildren()) {
            if (node instanceof Rectangle) {
                if (rectCount == index) {
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
    public Group getElementsGroup() {
        return elementsGroup;
    }

    /**
     * Get index labels group.
     */
    public Group getIndexLabelsGroup() {
        return indexLabelsGroup;
    }
}
