package dsvisualizer.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import dsvisualizer.util.Constants;

/**
 * Status and explanation panel for showing operation details.
 */
public class StatusPanel extends VBox {
    private Label operationLabel;
    private Label pseudocodeLabel;
    private Label explanationLabel;
    private ScrollPane scrollPane;

    public StatusPanel() {
        this.setPrefHeight(Constants.STATUS_PANEL_HEIGHT);
        this.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #CCCCCC; -fx-border-width: 1 0 0 0;");
        this.setPadding(new Insets(10));
        this.setSpacing(5);

        // Operation Label
        operationLabel = new Label("No operation");
        operationLabel.setFont(Font.font("System", FontWeight.BOLD, Constants.FONT_SIZE_TITLE));
        operationLabel.setStyle("-fx-text-fill: #2196F3;");

        // Pseudocode Label
        pseudocodeLabel = new Label("Pseudocode: ");
        pseudocodeLabel.setFont(Font.font("Consolas", Constants.FONT_SIZE_VALUE));
        pseudocodeLabel.setStyle("-fx-text-fill: #555555; -fx-font-family: 'Courier New';");
        pseudocodeLabel.setWrapText(true);

        // Explanation Label
        explanationLabel = new Label("Ready for operations");
        explanationLabel.setFont(Font.font("System", Constants.FONT_SIZE_VALUE));
        explanationLabel.setStyle("-fx-text-fill: #212121;");
        explanationLabel.setWrapText(true);

        // ScrollPane for content
        scrollPane = new ScrollPane();
        scrollPane.setContent(new VBox(5, pseudocodeLabel, explanationLabel));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-control-inner-background: #FAFAFA; -fx-border-color: transparent;");

        this.getChildren().addAll(operationLabel, scrollPane);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);
    }

    public void setOperationLabel(String text) {
        operationLabel.setText(text);
        operationLabel.setStyle("-fx-text-fill: #2196F3;");
    }

    public void setOperationLabelSuccess(String text) {
        operationLabel.setText(text);
        operationLabel.setStyle("-fx-text-fill: #4CAF50;");
    }

    public void setOperationLabelError(String text) {
        operationLabel.setText(text);
        operationLabel.setStyle("-fx-text-fill: #F44336;");
    }

    public void setPseudocode(String pseudocode) {
        pseudocodeLabel.setText("Pseudocode: " + pseudocode);
    }

    public void setExplanation(String explanation) {
        explanationLabel.setText(explanation);
    }

    public void clearStatus() {
        operationLabel.setText("Ready");
        pseudocodeLabel.setText("Pseudocode: ");
        explanationLabel.setText("No operation");
    }

    public void setError(String errorMessage) {
        setOperationLabelError("Error: " + errorMessage);
        explanationLabel.setText("");
        pseudocodeLabel.setText("");
    }
}
