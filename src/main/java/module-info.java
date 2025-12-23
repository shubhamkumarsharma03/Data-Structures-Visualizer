module dsvisualizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;

    exports dsvisualizer;
    exports dsvisualizer.model;
    exports dsvisualizer.view;
    exports dsvisualizer.controller;
    exports dsvisualizer.animation;
    exports dsvisualizer.util;
}
