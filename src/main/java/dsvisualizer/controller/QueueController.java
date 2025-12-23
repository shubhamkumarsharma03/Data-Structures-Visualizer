package dsvisualizer.controller;

import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import dsvisualizer.animation.AnimationFactory;
import dsvisualizer.model.Queue;
import dsvisualizer.util.Constants;
import dsvisualizer.util.ValidationUtil;
import dsvisualizer.view.ControlPanel;
import dsvisualizer.view.QueueVisualizer;
import dsvisualizer.view.StatusPanel;

/**
 * Controller for Queue operations.
 */
public class QueueController {
    private Queue queue;
    private QueueVisualizer visualizer;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    private double animationSpeed;

    public QueueController(Pane canvas, ControlPanel controlPanel, StatusPanel statusPanel) {
        this.queue = new Queue(Constants.DEFAULT_QUEUE_CAPACITY);
        this.visualizer = new QueueVisualizer(canvas, queue);
        this.controlPanel = controlPanel;
        this.statusPanel = statusPanel;
        this.animationSpeed = Constants.SPEED_DEFAULT;
        visualizer.render();
    }

    /**
     * Enqueue element to rear.
     */
    public void enqueue(String input) {
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
            
            statusPanel.setOperationLabel("Enqueueing " + value);
            statusPanel.setPseudocode("queue.enqueue(" + value + "); rear++");
            statusPanel.setExplanation("Adding element to rear of queue and incrementing rear pointer");

            queue.enqueue(value);
            visualizer.render();
            
            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeIn(visualizer.getElementGroup(), getAnimationDuration()),
                AnimationFactory.slideNode(visualizer.getElementGroup(), 0, 0, getAnimationDuration())
            );
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Enqueued " + value);
            controlPanel.setStatusMessageSuccess("Element enqueued successfully!");
            controlPanel.clearInput();
        } catch (RuntimeException e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Dequeue element from front.
     */
    public void dequeue() {
        try {
            if (queue.isEmpty()) {
                statusPanel.setError("Queue Underflow!");
                controlPanel.setStatusMessageError("Queue is empty!");
                return;
            }

            int value = queue.dequeue();
            
            statusPanel.setOperationLabel("Dequeueing " + value);
            statusPanel.setPseudocode("element = queue.dequeue(); front++");
            statusPanel.setExplanation("Removing front element (value: " + value + ") and incrementing front pointer");

            SequentialTransition animation = AnimationFactory.sequence(
                AnimationFactory.fadeOut(visualizer.getElementGroup(), getAnimationDuration())
            );
            animation.setOnFinished(e -> visualizer.render());
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Dequeued " + value);
            controlPanel.setStatusMessageSuccess("Element dequeued: " + value);
            controlPanel.clearInput();
        } catch (RuntimeException e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Peek at front element.
     */
    public void peek() {
        try {
            int value = queue.peek();
            
            statusPanel.setOperationLabel("Peeking");
            statusPanel.setPseudocode("element = queue.peek()");
            statusPanel.setExplanation("Viewing front element without removing: " + value);

            visualizer.highlightFront(Constants.COLOR_SUCCESS);
            
            javafx.animation.Transition animation = AnimationFactory.pulseNode(visualizer.getElementGroup(), getAnimationDuration());
            animation.play();
            
            statusPanel.setOperationLabelSuccess("Front element: " + value);
            controlPanel.setStatusMessageSuccess("Front element: " + value);
        } catch (RuntimeException e) {
            statusPanel.setError(e.getMessage());
            controlPanel.setStatusMessageError(e.getMessage());
        }
    }

    /**
     * Reset the queue.
     */
    public void reset() {
        queue.clear();
        visualizer.render();
        statusPanel.clearStatus();
        controlPanel.setStatusMessage("Queue cleared");
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
     * Get the current queue.
     */
    public Queue getQueue() {
        return queue;
    }
}
