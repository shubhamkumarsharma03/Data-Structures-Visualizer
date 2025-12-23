package dsvisualizer.controller;

import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import dsvisualizer.animation.AnimationFactory;
import dsvisualizer.model.LinkedList;
import dsvisualizer.util.Constants;
import dsvisualizer.util.ValidationUtil;
import dsvisualizer.view.ControlPanel;
import dsvisualizer.view.LinkedListVisualizer;
import dsvisualizer.view.StatusPanel;

/**
 * Controller for Linked List operations.
 */
public class LinkedListController {
    private LinkedList linkedList;
    private LinkedListVisualizer visualizer;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    private double animationSpeed;

    public LinkedListController(Pane canvas, ControlPanel controlPanel, StatusPanel statusPanel) {
        this.linkedList = new LinkedList();
        this.visualizer = new LinkedListVisualizer(canvas, linkedList);
        this.controlPanel = controlPanel;
        this.statusPanel = statusPanel;
        this.animationSpeed = Constants.SPEED_DEFAULT;
        visualizer.render();
    }

    /**
     * Insert at head.
     */
    public void insertHead(String input) {
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
            
            statusPanel.setOperationLabel("Inserting " + value + " at head");
            statusPanel.setPseudocode("node = new Node(" + value + "); node.next = head; head = node");
            statusPanel.setExplanation("Creating new node and inserting at front of list");

            linkedList.insertHead(value);
            visualizer.render();
            
            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeIn(visualizer.getNodeGroup(), getAnimationDuration()),
                AnimationFactory.slideNode(visualizer.getNodeGroup(), 0, 0, getAnimationDuration())
            );
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Inserted " + value + " at head");
            controlPanel.setStatusMessageSuccess("Element inserted at head!");
            controlPanel.clearInput();
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Insert at tail.
     */
    public void insertTail(String input) {
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
            
            statusPanel.setOperationLabel("Inserting " + value + " at tail");
            statusPanel.setPseudocode("current = head; while(current.next != null) current = current.next; current.next = new Node(" + value + ")");
            statusPanel.setExplanation("Traversing to end of list and appending new node");

            linkedList.insertTail(value);
            visualizer.render();
            
            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeIn(visualizer.getNodeGroup(), getAnimationDuration()),
                AnimationFactory.slideNode(visualizer.getNodeGroup(), 0, 0, getAnimationDuration())
            );
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Inserted " + value + " at tail");
            controlPanel.setStatusMessageSuccess("Element inserted at tail!");
            controlPanel.clearInput();
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Delete from head.
     */
    public void deleteHead() {
        try {
            if (linkedList.isEmpty()) {
                statusPanel.setError("List is empty!");
                controlPanel.setStatusMessageError("List is empty!");
                return;
            }

            int value = linkedList.deleteHead();
            
            statusPanel.setOperationLabel("Deleting head");
            statusPanel.setPseudocode("temp = head; head = head.next; free(temp)");
            statusPanel.setExplanation("Removing head node (value: " + value + ") and updating head pointer");

            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeOut(visualizer.getNodeGroup(), getAnimationDuration())
            );
            animation.setOnFinished(e -> visualizer.render());
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Deleted " + value);
            controlPanel.setStatusMessageSuccess("Head deleted: " + value);
        } catch (RuntimeException e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Search for element.
     */
    public void search(String input) {
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
            int foundIndex = linkedList.search(value);
            
            statusPanel.setOperationLabel("Searching for " + value);
            statusPanel.setPseudocode("current = head; while(current != null && current.data != " + value + ") current = current.next");
            
            if (foundIndex != -1) {
                visualizer.highlightNode(foundIndex, Constants.COLOR_SUCCESS);
                statusPanel.setExplanation("Found " + value + " at position " + foundIndex);
                statusPanel.setOperationLabelSuccess("Found at position " + foundIndex);
                controlPanel.setStatusMessageSuccess("Element found at position " + foundIndex);
            } else {
                statusPanel.setExplanation("Element not found in list");
                controlPanel.setStatusMessageWarning("Element not found");
            }
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Reset the list.
     */
    public void reset() {
        linkedList.clear();
        visualizer.render();
        statusPanel.clearStatus();
        controlPanel.setStatusMessage("List cleared");
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
     * Get the current linked list.
     */
    public LinkedList getLinkedList() {
        return linkedList;
    }
}
