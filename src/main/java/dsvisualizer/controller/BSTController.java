package dsvisualizer.controller;

import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import dsvisualizer.animation.AnimationFactory;
import dsvisualizer.model.BinarySearchTree;
import dsvisualizer.util.Constants;
import dsvisualizer.util.ValidationUtil;
import dsvisualizer.view.ControlPanel;
import dsvisualizer.view.BSTVisualizer;
import dsvisualizer.view.StatusPanel;

/**
 * Controller for Binary Search Tree operations.
 */
public class BSTController {
    private BinarySearchTree bst;
    private BSTVisualizer visualizer;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    private double animationSpeed;

    public BSTController(Pane canvas, ControlPanel controlPanel, StatusPanel statusPanel) {
        this.bst = new BinarySearchTree();
        this.visualizer = new BSTVisualizer(canvas, bst);
        this.controlPanel = controlPanel;
        this.statusPanel = statusPanel;
        this.animationSpeed = Constants.SPEED_DEFAULT;
        visualizer.render();
    }

    /**
     * Insert value into BST.
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
            
            statusPanel.setOperationLabel("Inserting " + value);
            statusPanel.setPseudocode("if root == null: root = new TreeNode(" + value + "); else: recursively find position and insert");
            statusPanel.setExplanation("Traversing tree to find correct position for new value based on BST property");

            bst.insert(value);
            visualizer.render();
            
            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeIn(visualizer.getNodeGroup(), getAnimationDuration()),
                AnimationFactory.pulseNode(visualizer.getNodeGroup(), getAnimationDuration())
            );
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Inserted " + value);
            controlPanel.setStatusMessageSuccess("Value inserted successfully!");
            controlPanel.clearInput();
        } catch (IllegalArgumentException e) {
            statusPanel.setError("Duplicate value!");
            controlPanel.setStatusMessageError("Duplicate values not allowed in BST");
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Delete value from BST.
     */
    public void delete(String input) {
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
            
            statusPanel.setOperationLabel("Deleting " + value);
            statusPanel.setPseudocode("find node; if leaf: remove; if one child: replace with child; if two children: find successor");
            statusPanel.setExplanation("Removing node from tree while maintaining BST property");

            boolean deleted = bst.delete(value);
            
            if (deleted) {
                visualizer.render();
                
                SequentialTransition animation = AnimationFactory.sequence(
                    AnimationFactory.fadeOut(visualizer.getNodeGroup(), getAnimationDuration())
                );
                animation.setOnFinished(e -> visualizer.render());
                animation.play();
                
                statusPanel.setOperationLabelSuccess("Deleted " + value);
                controlPanel.setStatusMessageSuccess("Node deleted successfully!");
            } else {
                statusPanel.setError("Value not found!");
                controlPanel.setStatusMessageError("Value not found in tree");
            }
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Search for value in BST.
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
            
            statusPanel.setOperationLabel("Searching for " + value);
            statusPanel.setPseudocode("if value == current: found; else if value < current: go left; else: go right");
            
            BinarySearchTree.TreeNode found = bst.search(value);
            
            if (found != null) {
                visualizer.highlightNodeByValue(value, Constants.COLOR_SUCCESS);
                statusPanel.setExplanation("Found " + value + " in tree");
                statusPanel.setOperationLabelSuccess("Found!");
                controlPanel.setStatusMessageSuccess("Value found!");
            } else {
                statusPanel.setExplanation("Value not found in tree");
                controlPanel.setStatusMessageWarning("Value not found");
            }
        } catch (Exception e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Perform inorder traversal.
     */
    public void inorder() {
        statusPanel.setOperationLabel("Inorder Traversal");
        statusPanel.setPseudocode("Left -> Root -> Right");
        statusPanel.setExplanation("Visits nodes in ascending order: " + bst.inorderTraversal());
        controlPanel.setStatusMessageSuccess("Inorder: " + bst.inorderTraversal());
    }

    /**
     * Reset the tree.
     */
    public void reset() {
        bst.clear();
        visualizer.render();
        statusPanel.clearStatus();
        controlPanel.setStatusMessage("Tree cleared");
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
     * Get the current BST.
     */
    public BinarySearchTree getBST() {
        return bst;
    }
}
