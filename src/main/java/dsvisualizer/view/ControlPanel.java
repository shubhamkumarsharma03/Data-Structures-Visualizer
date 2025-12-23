package dsvisualizer.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import dsvisualizer.util.Constants;

/**
 * Global control panel for all data structures.
 */
public class ControlPanel extends HBox {
    private TextField inputField;
    private Button insertBtn;
    private Button deleteBtn;
    private Button searchBtn;
    private Button resetBtn;
    private Slider speedSlider;
    private CheckBox stepByStepCheckBox;
    private ComboBox<String> dataStructureSelector;
    private Label statusLabel;

    public ControlPanel() {
        this.setPrefHeight(Constants.CONTROL_PANEL_HEIGHT);
        this.setStyle("-fx-background-color: #F5F5F5; -fx-border-color: #CCCCCC; -fx-border-width: 0 0 1 0;");
        this.setPadding(new Insets(10));
        this.setSpacing(10);

        // Input Field
        inputField = new TextField();
        inputField.setPromptText("Enter value...");
        inputField.setPrefWidth(80);
        inputField.setStyle("-fx-font-size: 12; -fx-padding: 5;");

        // Buttons
        insertBtn = createButton("Insert");
        deleteBtn = createButton("Delete");
        searchBtn = createButton("Search");
        resetBtn = createButton("Reset");

        // Speed Slider
        Label speedLabel = new Label("Speed:");
        speedLabel.setStyle("-fx-font-size: 11;");
        speedSlider = new Slider(Constants.SPEED_MIN, Constants.SPEED_MAX, Constants.SPEED_DEFAULT);
        speedSlider.setPrefWidth(100);
        speedSlider.setShowTickLabels(false);
        speedSlider.setStyle("-fx-control-inner-background: #E0E0E0;");

        // Step-by-Step Toggle
        stepByStepCheckBox = new CheckBox("Step-by-Step");
        stepByStepCheckBox.setStyle("-fx-font-size: 11;");

        // Data Structure Selector
        Label dsLabel = new Label("Data Structure:");
        dsLabel.setStyle("-fx-font-size: 11;");
        dataStructureSelector = new ComboBox<>();
        dataStructureSelector.getItems().addAll(
            "Array", "Stack", "Queue", "Linked List", "BST", "Graph"
        );
        dataStructureSelector.setValue("Array");
        dataStructureSelector.setPrefWidth(120);

        // Status Label
        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #2196F3;");
        statusLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(statusLabel, Priority.ALWAYS);

        // Add all components
        this.getChildren().addAll(
            dsLabel, dataStructureSelector,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            inputField,
            insertBtn, deleteBtn, searchBtn, resetBtn,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            speedLabel, speedSlider,
            stepByStepCheckBox,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            statusLabel
        );
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(80);
        button.setStyle(
            "-fx-font-size: 11; " +
            "-fx-padding: 5; " +
            "-fx-background-color: #2196F3; " +
            "-fx-text-fill: white; " +
            "-fx-cursor: hand; " +
            "-fx-border-radius: 3; " +
            "-fx-background-radius: 3;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-font-size: 11; " +
            "-fx-padding: 5; " +
            "-fx-background-color: #1976D2; " +
            "-fx-text-fill: white; " +
            "-fx-cursor: hand; " +
            "-fx-border-radius: 3; " +
            "-fx-background-radius: 3;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-font-size: 11; " +
            "-fx-padding: 5; " +
            "-fx-background-color: #2196F3; " +
            "-fx-text-fill: white; " +
            "-fx-cursor: hand; " +
            "-fx-border-radius: 3; " +
            "-fx-background-radius: 3;"
        ));
        return button;
    }

    // Getters
    public String getInputValue() {
        return inputField.getText();
    }

    public void setInputValue(String value) {
        inputField.setText(value);
    }

    public void clearInput() {
        inputField.clear();
    }

    public Button getInsertButton() {
        return insertBtn;
    }

    public Button getDeleteButton() {
        return deleteBtn;
    }

    public Button getSearchButton() {
        return searchBtn;
    }

    public Button getResetButton() {
        return resetBtn;
    }

    public double getSpeed() {
        return speedSlider.getValue();
    }

    public void setSpeed(double speed) {
        speedSlider.setValue(speed);
    }

    public boolean isStepByStepEnabled() {
        return stepByStepCheckBox.isSelected();
    }

    public void setStepByStepEnabled(boolean enabled) {
        stepByStepCheckBox.setSelected(enabled);
    }

    public String getSelectedDataStructure() {
        return dataStructureSelector.getValue();
    }

    public ComboBox<String> getDataStructureSelector() {
        return dataStructureSelector;
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    public void setStatusMessageError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #F44336;");
    }

    public void setStatusMessageSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #4CAF50;");
    }

    public void setStatusMessageWarning(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-font-size: 11; -fx-text-fill: #FFC107;");
    }

    public TextField getInputField() {
        return inputField;
    }
}
