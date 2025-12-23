package dsvisualizer.controller;

import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import dsvisualizer.animation.AnimationFactory;
import dsvisualizer.model.DynamicArray;
import dsvisualizer.util.Constants;
import dsvisualizer.util.ValidationUtil;
import dsvisualizer.view.ArrayVisualizer;
import dsvisualizer.view.ControlPanel;
import dsvisualizer.view.StatusPanel;

/**
 * Controller for Array operations.
 */
public class ArrayController {
    private DynamicArray array;
    private ArrayVisualizer visualizer;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    private double animationSpeed;

    public ArrayController(Pane canvas, ControlPanel controlPanel, StatusPanel statusPanel) {
        this.array = new DynamicArray(Constants.DEFAULT_ARRAY_SIZE);
        this.visualizer = new ArrayVisualizer(canvas, array);
        this.controlPanel = controlPanel;
        this.statusPanel = statusPanel;
        this.animationSpeed = Constants.SPEED_DEFAULT;
        visualizer.render();
    }

    /**
     * Insert element at end of array.
     */
    public void insert(String input) {
        try {
            if (!ValidationUtil.isNotEmpty(input)) {
                statusPanel.setError("Please enter a value");
                return;
            }
            if (!ValidationUtil.isValidInteger(input)) {
                statusPanel.setError("Invalid integer input");
                return;
            }

            int value = Integer.parseInt(input);
            int insertIndex = array.getSize();
            
            statusPanel.setOperationLabel("Inserting " + value);
            statusPanel.setPseudocode("array[" + insertIndex + "] = " + value);
            statusPanel.setExplanation("Adding element at end of array (index " + insertIndex + ")");

            array.insert(insertIndex, value);
            
            visualizer.render();
            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeIn(visualizer.getElementsGroup(), getAnimationDuration())
            );
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Inserted " + value + " at index " + insertIndex);
            controlPanel.setStatusMessageSuccess("Element inserted successfully!");
            controlPanel.clearInput();
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Delete element at specific index.
     */
    public void delete(String input) {
        try {
            if (!ValidationUtil.isNotEmpty(input)) {
                statusPanel.setError("Please enter an index");
                return;
            }
            if (!ValidationUtil.isValidIntegerInRange(input, 0, array.getSize() - 1)) {
                statusPanel.setError("Invalid index");
                return;
            }

            int index = Integer.parseInt(input);
            int value = array.delete(index);
            
            statusPanel.setOperationLabel("Deleting from index " + index);
            statusPanel.setPseudocode("remove(array[" + index + "])");
            statusPanel.setExplanation("Removing element at index " + index + " (value: " + value + ") and shifting remaining elements left");

            visualizer.highlightCell(index, Constants.COLOR_ERROR);
            
            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.flashNode(
                    ((javafx.scene.shape.Rectangle) visualizer.getElementsGroup().getChildren().get(index * 2)),
                    Constants.COLOR_ERROR,
                    getAnimationDuration()
                )
            );
            animation.setOnFinished(e -> {
                visualizer.render();
            });
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Deleted " + value);
            controlPanel.setStatusMessageSuccess("Element deleted successfully!");
            controlPanel.clearInput();
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Search for element in array.
     */
    public void search(String input) {
        try {
            if (!ValidationUtil.isNotEmpty(input)) {
                statusPanel.setError("Please enter a value to search");
                return;
            }
            if (!ValidationUtil.isValidInteger(input)) {
                statusPanel.setError("Invalid integer input");
                return;
            }

            int value = Integer.parseInt(input);
            int foundIndex = array.search(value);
            
            statusPanel.setOperationLabel("Searching for " + value);
            statusPanel.setPseudocode("for i = 0 to size-1: if array[i] == " + value);
            
            if (foundIndex != -1) {
                visualizer.highlightCell(foundIndex, Constants.COLOR_SUCCESS);
                statusPanel.setExplanation("Found " + value + " at index " + foundIndex);
                statusPanel.setOperationLabelSuccess("Found at index " + foundIndex);
                controlPanel.setStatusMessageSuccess("Element found at index " + foundIndex);
            } else {
                statusPanel.setExplanation("Element not found in array");
                statusPanel.setOperationLabel("Element not found");
                controlPanel.setStatusMessageWarning("Element not found in array");
            }
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Reset the array.
     */
    public void reset() {
        array.clear();
        visualizer.render();
        statusPanel.clearStatus();
        controlPanel.setStatusMessage("Array cleared");
        controlPanel.clearInput();
    }

    /**
     * Set animation speed.
     */
    public void setAnimationSpeed(double speed) {
        this.animationSpeed = speed;
    }

    /**
     * Get animation duration based on speed multiplier.
     */
    private int getAnimationDuration() {
        return (int) (Constants.ANIMATION_DURATION_NORMAL / animationSpeed);
    }

    /**
     * Get the current array.
     */
    public DynamicArray getArray() {
        return array;
    }

    /**
     * Get the visualizer.
     */
    public ArrayVisualizer getVisualizer() {
        return visualizer;
    }
}
