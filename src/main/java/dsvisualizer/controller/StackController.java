package dsvisualizer.controller;

import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import dsvisualizer.animation.AnimationFactory;
import dsvisualizer.model.Stack;
import dsvisualizer.util.Constants;
import dsvisualizer.util.ValidationUtil;
import dsvisualizer.view.ControlPanel;
import dsvisualizer.view.StackVisualizer;
import dsvisualizer.view.StatusPanel;

/**
 * Controller for Stack operations.
 */
public class StackController {
    private Stack stack;
    private StackVisualizer visualizer;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    private double animationSpeed;

    public StackController(Pane canvas, ControlPanel controlPanel, StatusPanel statusPanel) {
        this.stack = new Stack(Constants.DEFAULT_STACK_CAPACITY);
        this.visualizer = new StackVisualizer(canvas, stack);
        this.controlPanel = controlPanel;
        this.statusPanel = statusPanel;
        this.animationSpeed = Constants.SPEED_DEFAULT;
        visualizer.render();
    }

    /**
     * Push element onto stack.
     */
    public void push(String input) {
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
            
            statusPanel.setOperationLabel("Pushing " + value);
            statusPanel.setPseudocode("stack.push(" + value + "); top++");
            statusPanel.setExplanation("Adding element to top of stack and incrementing top pointer");

            stack.push(value);
            visualizer.render();
            
            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeIn(visualizer.getElementGroup(), getAnimationDuration()),
                AnimationFactory.dropNode(visualizer.getElementGroup(), -50, 0, getAnimationDuration())
            );
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Pushed " + value);
            controlPanel.setStatusMessageSuccess("Element pushed successfully!");
            controlPanel.clearInput();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Overflow")) {
                statusPanel.setError("Stack Overflow!");
                javafx.animation.Timeline shake = AnimationFactory.shakeNode(visualizer.getElementGroup(), getAnimationDuration());
                shake.play();
            } else {
                statusPanel.setError(e.getMessage());
            }
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Pop element from stack.
     */
    public void pop() {
        try {
            if (stack.isEmpty()) {
                statusPanel.setError("Stack Underflow!");
                controlPanel.setStatusMessageError("Stack is empty!");
                return;
            }

            int value = stack.pop();
            
            statusPanel.setOperationLabel("Popping " + value);
            statusPanel.setPseudocode("element = stack.pop(); top--");
            statusPanel.setExplanation("Removing top element (value: " + value + ") and decrementing top pointer");

            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeOut(visualizer.getElementGroup(), getAnimationDuration())
            );
            animation.setOnFinished(e -> visualizer.render());
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Popped " + value);
            controlPanel.setStatusMessageSuccess("Element popped: " + value);
            controlPanel.clearInput();
        } catch (RuntimeException e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Peek at top element.
     */
    public void peek() {
        try {
            int value = stack.peek();
            
            statusPanel.setOperationLabel("Peeking");
            statusPanel.setPseudocode("element = stack.peek()");
            statusPanel.setExplanation("Viewing top element without removing: " + value);

            visualizer.highlightTop(Constants.COLOR_SUCCESS);
            
            javafx.animation.Transition animation = AnimationFactory.pulseNode(visualizer.getElementGroup(), getAnimationDuration());
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Top element: " + value);
            controlPanel.setStatusMessageSuccess("Top element: " + value);
        } catch (RuntimeException e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Reset the stack.
     */
    public void reset() {
        stack.clear();
        visualizer.render();
        statusPanel.clearStatus();
        controlPanel.setStatusMessage("Stack cleared");
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
     * Get the current stack.
     */
    public Stack getStack() {
        return stack;
    }
}
