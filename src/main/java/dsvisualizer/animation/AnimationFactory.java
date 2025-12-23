package dsvisualizer.animation;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import dsvisualizer.util.Constants;

/**
 * Custom animations for data structure visualizations.
 */
public class AnimationFactory {

    /**
     * Create a transition that highlights a node.
     */
    public static Transition highlightNode(Shape node, Color fromColor, Color toColor, int durationMs) {
        FillTransition fillTransition = new FillTransition(
            Duration.millis(durationMs / 2),
            node,
            fromColor,
            toColor
        );
        
        FillTransition reverseTransition = new FillTransition(
            Duration.millis(durationMs / 2),
            node,
            toColor,
            fromColor
        );
        
        SequentialTransition sequence = new SequentialTransition(fillTransition, reverseTransition);
        return sequence;
    }

    /**
     * Create a pulse effect (scale up and down).
     */
    public static Transition pulseNode(Node node, int durationMs) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(durationMs / 2), node);
        scaleUp.setFromX(1.0);
        scaleUp.setFromY(1.0);
        scaleUp.setToX(1.3);
        scaleUp.setToY(1.3);
        
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(durationMs / 2), node);
        scaleDown.setFromX(1.3);
        scaleDown.setFromY(1.3);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);
        
        SequentialTransition sequence = new SequentialTransition(scaleUp, scaleDown);
        return sequence;
    }

    /**
     * Create a shake effect for overflow errors.
     */
    public static Timeline shakeNode(Node node, int durationMs) {
        Timeline shakeTimeline = new Timeline();
        
        for (int i = 0; i < 5; i++) {
            KeyFrame kf1 = new KeyFrame(
                Duration.millis(durationMs * i / 5),
                event -> node.setTranslateX(-10)
            );
            KeyFrame kf2 = new KeyFrame(
                Duration.millis(durationMs * (i + 0.5) / 5),
                event -> node.setTranslateX(10)
            );
            shakeTimeline.getKeyFrames().addAll(kf1, kf2);
        }
        
        KeyFrame kfEnd = new KeyFrame(
            Duration.millis(durationMs),
            event -> node.setTranslateX(0)
        );
        shakeTimeline.getKeyFrames().add(kfEnd);
        
        return shakeTimeline;
    }

    /**
     * Create a flash effect for errors.
     */
    public static Transition flashNode(Shape node, Color errorColor, int durationMs) {
        FillTransition flash1 = new FillTransition(
            Duration.millis(durationMs / 4),
            node,
            (Color) node.getFill(),
            errorColor
        );
        
        FillTransition flash2 = new FillTransition(
            Duration.millis(durationMs / 4),
            node,
            errorColor,
            (Color) node.getFill()
        );
        
        FillTransition flash3 = new FillTransition(
            Duration.millis(durationMs / 4),
            node,
            (Color) node.getFill(),
            errorColor
        );
        
        FillTransition flash4 = new FillTransition(
            Duration.millis(durationMs / 4),
            node,
            errorColor,
            (Color) node.getFill()
        );
        
        SequentialTransition sequence = new SequentialTransition(flash1, flash2, flash3, flash4);
        return sequence;
    }

    /**
     * Create a slide transition (translate node to target position).
     */
    public static Transition slideNode(Node node, double toX, double toY, int durationMs) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(durationMs), node);
        transition.setToX(toX);
        transition.setToY(toY);
        return transition;
    }

    /**
     * Create a fade-in effect.
     */
    public static Transition fadeIn(Node node, int durationMs) {
        FadeTransition transition = new FadeTransition(Duration.millis(durationMs), node);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);
        return transition;
    }

    /**
     * Create a fade-out effect.
     */
    public static Transition fadeOut(Node node, int durationMs) {
        FadeTransition transition = new FadeTransition(Duration.millis(durationMs), node);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        return transition;
    }

    /**
     * Create a drop animation (from top to target position).
     */
    public static Transition dropNode(Node node, double startY, double endY, int durationMs) {
        node.setTranslateY(startY - endY);
        TranslateTransition transition = new TranslateTransition(Duration.millis(durationMs), node);
        transition.setToY(0);
        return transition;
    }

    /**
     * Create a color transition.
     */
    public static Transition changeColor(Shape node, Color fromColor, Color toColor, int durationMs) {
        FillTransition transition = new FillTransition(Duration.millis(durationMs), node, fromColor, toColor);
        return transition;
    }

    /**
     * Create a sequential animation from multiple transitions.
     */
    public static SequentialTransition sequence(Transition... transitions) {
        SequentialTransition sequential = new SequentialTransition(transitions);
        return sequential;
    }

    /**
     * Create a parallel animation from multiple transitions.
     */
    public static ParallelTransition parallel(Transition... transitions) {
        ParallelTransition parallel = new ParallelTransition(transitions);
        return parallel;
    }
}
