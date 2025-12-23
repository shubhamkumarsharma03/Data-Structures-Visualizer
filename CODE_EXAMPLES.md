# Code Examples & Usage Guide

## Table of Contents
1. [Using the Visualizer (User Perspective)](#using-the-visualizer)
2. [Creating Custom Operations](#creating-custom-operations)
3. [Animation Examples](#animation-examples)
4. [Model Usage Examples](#model-usage-examples)

---

## Using the Visualizer

### Starting the Application

```java
// Main.java automatically:
// 1. Creates all controllers
// 2. Initializes all visualizers
// 3. Wires all event handlers
// 4. Displays JavaFX window

public static void main(String[] args) {
    Application.launch(args);  // Starts JavaFX runtime
}
```

### User Workflow: Array Example

```
1. Application starts with Array selected by default
2. User enters value: 10
3. User clicks Insert
4. Behind the scenes:
   - ArrayController.insert("10") called
   - Input validated
   - array.insert(0, 10) executes
   - arrayVisualizer.render() updates display
   - AnimationFactory.fadeIn() creates animation
   - Status panel updates with pseudocode
5. Animation plays showing insertion
```

---

## Creating Custom Operations

### Example 1: Add a Custom Button to ControlPanel

```java
// In ControlPanel.java, add new button:

// Create button
Button customButton = createButton("Custom Op");

// Add to layout
this.getChildren().add(customButton);

// In Main.java, add event handler:
controlPanel.getCustomButton().setOnAction(e -> {
    // Handle custom operation
});
```

### Example 2: Add a New Operation to Array

**Step 1: Add method to Model**
```java
// In DynamicArray.java
public int getMax() {
    if (size == 0) throw new RuntimeException("Array is empty");
    int max = data[0];
    for (int i = 1; i < size; i++) {
        if (data[i] > max) max = data[i];
    }
    return max;
}
```

**Step 2: Add method to Controller**
```java
// In ArrayController.java
public void findMax() {
    try {
        int maxValue = array.getMax();
        statusPanel.setOperationLabel("Maximum: " + maxValue);
        statusPanel.setPseudocode("max = array[0]; for(i=1 to size) if array[i] > max: max = array[i]");
        statusPanel.setExplanation("Found maximum value: " + maxValue);
        
        // Highlight cell with max value
        int maxIndex = array.search(maxValue);
        visualizer.highlightCell(maxIndex, Constants.COLOR_SUCCESS);
        
        // Pulse animation
        SequentialTransition anim = AnimationFactory.pulseNode(
            visualizer.getElementsGroup(), 
            getAnimationDuration()
        );
        anim.play();
        
        controlPanel.setStatusMessageSuccess("Max: " + maxValue);
    } catch (Exception e) {
        statusPanel.setError(e.getMessage());
    }
}
```

**Step 3: Wire in Main.java**
```java
// Add button to control panel or create toolbar
Button maxButton = new Button("Find Max");
maxButton.setOnAction(e -> arrayController.findMax());

// Add to UI
controlPanel.getChildren().add(maxButton);
```

---

## Animation Examples

### Example 1: Multi-Step Insert Animation

```java
// Create a complex animation for array insertion

SequentialTransition insertAnimation = AnimationFactory.sequence(
    // Step 1: Highlight destination cell
    AnimationFactory.highlightNode(
        cell,
        Constants.COLOR_NEUTRAL,
        Constants.COLOR_WARNING,
        300
    ),
    
    // Step 2: Shift existing elements right
    AnimationFactory.slideNode(
        nextElement,
        Constants.ARRAY_CELL_WIDTH + Constants.ARRAY_CELL_SPACING,
        0,
        400
    ),
    
    // Step 3: Fade in new element
    AnimationFactory.fadeIn(
        newElementGroup,
        300
    ),
    
    // Step 4: Success pulse
    AnimationFactory.pulseNode(
        cell,
        400
    )
);

insertAnimation.play();
```

### Example 2: Error Animation

```java
// Display shake when operation fails

if (stack.isFull()) {
    // Shake animation for visual feedback
    SequentialTransition errorAnim = AnimationFactory.shakeNode(
        visualizer.getElementGroup(),
        Constants.ANIMATION_DURATION_NORMAL
    );
    errorAnim.play();
    
    // Also flash the error color
    SequentialTransition flashAnim = AnimationFactory.flashNode(
        targetShape,
        Constants.COLOR_ERROR,
        Constants.ANIMATION_DURATION_NORMAL
    );
    flashAnim.play();
    
    // Update status
    statusPanel.setError("Stack Overflow!");
}
```

### Example 3: Traversal Animation

```java
// Animate traversal for BST in-order

List<Integer> traversalResult = bst.inorderTraversal();

// Create sequential animation for each node
SequentialTransition traversalAnimation = new SequentialTransition();

for (Integer value : traversalResult) {
    // Highlight node, wait, unhighlight
    SequentialTransition nodeAnimation = AnimationFactory.sequence(
        AnimationFactory.highlightNode(
            getNodeShape(value),
            Constants.COLOR_PRIMARY,
            Constants.COLOR_SUCCESS,
            300
        ),
        AnimationFactory.pulseNode(
            getNodeShape(value),
            200
        )
    );
    traversalAnimation.getChildren().add(nodeAnimation);
}

traversalAnimation.play();
```

---

## Model Usage Examples

### Direct Model Usage (For Testing)

```java
// Create and use data structures directly (no visualizer)

// Array Example
DynamicArray arr = new DynamicArray(10);
arr.insert(0, 42);
arr.insert(1, 17);
arr.insert(2, 93);

int found = arr.search(17);  // Returns 1
System.out.println("Found at index: " + found);

arr.delete(1);  // Removes 17
System.out.println("Size: " + arr.getSize());  // Prints 2
```

```java
// Stack Example
Stack stack = new Stack(5);
stack.push(10);
stack.push(20);
stack.push(30);

System.out.println("Top: " + stack.peek());  // Prints 30
stack.pop();
System.out.println("Size: " + stack.getSize());  // Prints 2
```

```java
// LinkedList Example
LinkedList list = new LinkedList();
list.insertHead(5);
list.insertTail(10);
list.insertTail(15);

System.out.println("Elements: " + list.getElements());  // [5, 10, 15]
int found = list.search(10);  // Returns 1
System.out.println("Found at: " + found);
```

```java
// BST Example
BinarySearchTree bst = new BinarySearchTree();
bst.insert(50);
bst.insert(30);
bst.insert(70);
bst.insert(20);

BinarySearchTree.TreeNode node = bst.search(30);  // Finds node
System.out.println("Inorder: " + bst.inorderTraversal());  // [20, 30, 50, 70]
```

```java
// Graph Example
Graph graph = new Graph(false);  // Undirected
graph.addVertex(1);
graph.addVertex(2);
graph.addVertex(3);

graph.addEdge(1, 2);
graph.addEdge(2, 3);
graph.addEdge(3, 1);

List<Integer> bfsResult = graph.bfs(1);  // [1, 2, 3]
System.out.println("BFS: " + bfsResult);
```

---

## Controller Usage Examples

### Example 1: Using Stack Controller

```java
// In Main.java or any controller interaction

StackController controller = new StackController(canvas, controlPanel, statusPanel);

// Set animation speed
controller.setAnimationSpeed(1.5);  // 1.5x speed

// Perform operations
controller.push("42");      // Animated push
controller.peek();          // Show top
controller.pop();           // Animated pop
controller.reset();         // Clear

// Access underlying model
Stack stack = controller.getStack();
System.out.println("Size: " + stack.getSize());
```

### Example 2: Using BST Controller

```java
BSTController controller = new BSTController(canvas, controlPanel, statusPanel);

// Insert multiple values
String[] values = {"50", "30", "70", "20", "40"};
for (String val : values) {
    controller.insert(val);
    // Wait for animation (in real app, use JavaFX AnimationTimer)
}

// Search for value
controller.search("30");

// Show traversal
controller.inorder();  // Shows in-order traversal

// Delete and reset
controller.delete("20");
controller.reset();
```

---

## Validation Examples

### Input Validation

```java
// Using ValidationUtil

// Check if integer
if (ValidationUtil.isValidInteger("42")) {
    System.out.println("Valid integer");
}

// Check range
if (ValidationUtil.isValidIntegerInRange("5", 0, 10)) {
    System.out.println("Valid integer in range");
}

// Check not empty
if (ValidationUtil.isNotEmpty(userInput)) {
    System.out.println("Input provided");
}

// Get error message
String error = ValidationUtil.getErrorMessage("abc", 0, 100);
System.out.println(error);  // "Input must be a valid integer"
```

---

## Color Usage Examples

### Using Color Constants

```java
// In visualizer or animation code

// Standard colors
shape.setFill(Constants.COLOR_PRIMARY);      // Blue
shape.setFill(Constants.COLOR_SUCCESS);      // Green
shape.setFill(Constants.COLOR_ERROR);        // Red
shape.setFill(Constants.COLOR_WARNING);      // Yellow
shape.setFill(Constants.COLOR_VISITED);      // Purple

// Color transitions
AnimationFactory.changeColor(
    shape,
    Constants.COLOR_PRIMARY,
    Constants.COLOR_SUCCESS,
    500
);
```

---

## Layout Utility Examples

### Using LayoutUtil

```java
// Center a group in pane
LayoutUtil.centerNode(canvas, nodeGroup);

// Anchor to fill pane
LayoutUtil.anchorFill(pane, node);

// Anchor with margins
LayoutUtil.anchorWithMargins(pane, node, 10, 10, 10, 10);

// Clear group
LayoutUtil.clearGroup(visualizationGroup);

// Draw arrow between circles
Line arrow = LayoutUtil.drawArrow(fromCircle, toCircle, parentGroup);
```

---

## Event Handling Examples

### Adding Custom Event Handler

```java
// In Main.java setupEventHandlers()

// Text input on Enter
controlPanel.getInputField().setOnKeyPressed(e -> {
    if (e.getCode() == KeyCode.ENTER) {
        handleInsertAction();
    }
});

// Speed slider change
controlPanel.getSpeedSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
    double speed = newVal.doubleValue();
    updateAllControllerSpeeds(speed);
});

// Data structure selector change
controlPanel.getDataStructureSelector().setOnAction(e -> {
    switchDataStructure();
});
```

---

## Testing Examples

### Unit Testing Data Structure

```java
// In src/test/java/dsvisualizer/model/ArrayTest.java

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayTest {
    @Test
    public void testInsert() {
        DynamicArray arr = new DynamicArray(10);
        arr.insert(0, 42);
        assertEquals(42, arr.get(0));
        assertEquals(1, arr.getSize());
    }
    
    @Test
    public void testDelete() {
        DynamicArray arr = new DynamicArray(10);
        arr.insert(0, 10);
        arr.insert(1, 20);
        arr.delete(0);
        assertEquals(20, arr.get(0));
    }
    
    @Test
    public void testSearch() {
        DynamicArray arr = new DynamicArray(10);
        arr.insert(0, 42);
        assertEquals(0, arr.search(42));
        assertEquals(-1, arr.search(99));
    }
}
```

---

## Performance Tips

### Optimizing for Large Data Structures

```java
// Good: Batch clear
LayoutUtil.clearGroup(largeGroup);  // Efficiently clears all children

// Avoid: Individual removal
// for (Node child : largeGroup.getChildren()) {
//     largeGroup.getChildren().remove(child);  // SLOW!
// }

// Good: Set animation duration based on size
int duration = (int) (Constants.ANIMATION_DURATION_NORMAL * (size / 10.0));
Transition anim = AnimationFactory.fadeIn(group, duration);

// Good: Disable animations for large operations
if (size > 50) {
    visualizer.render();  // Just render, skip animation
} else {
    visualizer.render();
    AnimationFactory.fadeIn(group, 300).play();
}
```

---

## Complete Example: Custom Reverse Operation for Array

```java
// Step 1: Add to DynamicArray model
public void reverse() {
    for (int i = 0; i < size / 2; i++) {
        int temp = data[i];
        data[i] = data[size - 1 - i];
        data[size - 1 - i] = temp;
    }
}

// Step 2: Add to ArrayController
public void reverse() {
    try {
        statusPanel.setOperationLabel("Reversing Array");
        statusPanel.setPseudocode("for i=0 to size/2: swap(array[i], array[size-1-i])");
        statusPanel.setExplanation("Reversing all elements in array");
        
        // Animate reversal with sliding elements
        array.reverse();
        visualizer.render();
        
        SequentialTransition anim = AnimationFactory.sequence(
            AnimationFactory.slideNode(visualizer.getElementsGroup(), 100, 0, getAnimationDuration()),
            AnimationFactory.slideNode(visualizer.getElementsGroup(), 0, 0, getAnimationDuration())
        );
        anim.play();
        
        statusPanel.setOperationLabelSuccess("Array reversed!");
        controlPanel.setStatusMessageSuccess("Array reversed!");
    } catch (Exception e) {
        statusPanel.setError(e.getMessage());
    }
}

// Step 3: Add button in ControlPanel and wire in Main.java
Button reverseBtn = createButton("Reverse");
reverseBtn.setOnAction(e -> {
    if (controlPanel.getSelectedDataStructure().equals("Array")) {
        arrayController.reverse();
    }
});
```

---

## Extending the Visualizer

### Add New Data Structure: Deque (Double-Ended Queue)

```java
// 1. Model (deque.model.Deque)
public class Deque {
    private List<Integer> elements;
    public void addFront(int value) { elements.add(0, value); }
    public void addRear(int value) { elements.add(value); }
    public int removeFront() { return elements.remove(0); }
    public int removeRear() { return elements.remove(size - 1); }
}

// 2. Visualizer (deque.view.DequeVisualizer)
public class DequeVisualizer {
    public void render() {
        // Draw horizontal queue with both ends highlighted
    }
}

// 3. Controller (deque.controller.DequeController)
public class DequeController {
    public void addFront(String input) { /* ... */ }
    public void addRear(String input) { /* ... */ }
    public void removeFront() { /* ... */ }
    public void removeRear() { /* ... */ }
}

// 4. Update Main.java to include Deque option
```

---

This comprehensive guide shows how to use, extend, and customize the Data Structures Visualizer.
