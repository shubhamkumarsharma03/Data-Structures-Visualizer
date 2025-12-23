# Architecture & Design Patterns

## Overview

This project implements a **strict MVC (Model-View-Controller)** architecture with additional layers for animations and utilities. The design prioritizes **separation of concerns** and **educational clarity**.

## Architectural Layers

### 1. Model Layer (`model/`)

**Purpose**: Pure business logic for data structures, independent of UI framework.

**Key Principle**: Zero JavaFX dependencies

**Classes**:
- `DynamicArray` - Dynamic array with resize capability
- `Stack` - LIFO implementation using ArrayList
- `Queue` - FIFO implementation using ArrayList
- `LinkedList` - Singly-linked list with Node inner class
- `BinarySearchTree` - BST with recursive operations
- `Graph` - Adjacency list graph with BFS/DFS

**Characteristics**:
```java
// NO JavaFX imports in model classes
import java.util.*;

public class Stack {
    private List<Integer> elements;
    
    public boolean push(int value) { ... }
    public int pop() { ... }
    // Pure Java operations only
}
```

**Benefits**:
- Independently testable
- Reusable in other projects
- No UI framework lock-in
- Fast unit testing

---

### 2. View Layer (`view/`)

**Purpose**: JavaFX UI components and visualization rendering.

**Key Principle**: No data structure logic, only visualization

**Components**:
- `ControlPanel` - Input controls, buttons, speed slider
- `StatusPanel` - Operation display, pseudocode, explanations
- `ArrayVisualizer` - Visual representation of Array
- `StackVisualizer` - Visual representation of Stack
- `QueueVisualizer` - Visual representation of Queue
- `LinkedListVisualizer` - Visual representation of LinkedList
- `BSTVisualizer` - Visual representation of BST
- `GraphVisualizer` - Visual representation of Graph

**Design Pattern**: **Observer-based Rendering**

```java
public class ArrayVisualizer {
    private DynamicArray array;      // Reference to model
    private Group elementsGroup;      // JavaFX containers
    
    public void render() {
        // Read from model, render to view
        int[] elements = array.getElements();
        for (int i = 0; i < elements.length; i++) {
            drawCell(x, y, i, elements[i]);
        }
    }
}
```

**Characteristics**:
- Visualizers hold references to models
- No state modification in view
- Pure rendering logic
- Reusable visualization components

---

### 3. Controller Layer (`controller/`)

**Purpose**: Bridge between model and view, orchestrate operations.

**Key Principle**: Event handling, animation triggering, state coordination

**Classes**:
- `ArrayController` - Handles array operations
- `StackController` - Handles stack operations
- `QueueController` - Handles queue operations
- `LinkedListController` - Handles linked list operations
- `BSTController` - Handles BST operations
- `GraphController` - Handles graph operations

**Design Pattern**: **Command Pattern + Strategy Pattern**

```java
public class ArrayController {
    private DynamicArray array;           // Model
    private ArrayVisualizer visualizer;   // View
    private ControlPanel controlPanel;    // Control
    
    public void insert(String input) {
        // Validate input
        // Modify model
        array.insert(index, value);
        
        // Update view
        visualizer.render();
        
        // Trigger animation
        SequentialTransition anim = AnimationFactory.fadeIn(...);
        anim.play();
        
        // Update status
        statusPanel.setOperationLabelSuccess(...);
    }
}
```

**Workflow**:
1. Receive user input from UI
2. Validate with `ValidationUtil`
3. Modify model (safe operation)
4. Update visualization via `render()`
5. Trigger animation via `AnimationFactory`
6. Update status/feedback panel

---

### 4. Animation Layer (`animation/`)

**Purpose**: Centralized animation creation and management.

**Key Principle**: All animations use JavaFX Transition APIs only

**Class**: `AnimationFactory`

**Animation Types** (all return `Transition` objects):

```java
// Highlight animation - change color and back
Transition highlightNode(Shape node, Color from, Color to, int duration)

// Pulse effect - scale up then down
Transition pulseNode(Node node, int duration)

// Shake effect for errors
Transition shakeNode(Node node, int duration)

// Flash effect for deletion/error
Transition flashNode(Shape node, Color errorColor, int duration)

// Slide animation
Transition slideNode(Node node, double toX, double toY, int duration)

// Fade in/out
Transition fadeIn(Node node, int duration)
Transition fadeOut(Node node, int duration)

// Drop animation
Transition dropNode(Node node, double startY, double endY, int duration)

// Color change
Transition changeColor(Shape node, Color from, Color to, int duration)

// Sequential combination
SequentialTransition sequence(Transition... transitions)

// Parallel combination
ParallelTransition parallel(Transition... transitions)
```

**Animation Composition**:
```java
// Multi-step animation
SequentialTransition animation = AnimationFactory.sequence(
    AnimationFactory.highlightNode(cell, BLUE, GREEN, 300),
    AnimationFactory.slideNode(cell, newX, newY, 400),
    AnimationFactory.fadeIn(newElement, 200)
);
animation.play();
```

---

### 5. Utility Layer (`util/`)

**Purpose**: Reusable utilities, constants, validation.

**Classes**:

#### Constants.java
```java
// Window/UI dimensions
WINDOW_WIDTH = 1400
WINDOW_HEIGHT = 900
CONTROL_PANEL_HEIGHT = 100
STATUS_PANEL_HEIGHT = 150

// Colors (predefined palettes)
COLOR_PRIMARY = #2196F3 (Blue)
COLOR_SUCCESS = #4CAF50 (Green)
COLOR_ERROR = #F44336 (Red)
COLOR_WARNING = #FFC107 (Orange)

// Animation durations
ANIMATION_DURATION_FAST = 300ms
ANIMATION_DURATION_NORMAL = 600ms
ANIMATION_DURATION_SLOW = 1000ms

// Speed slider
SPEED_MIN = 0.5
SPEED_MAX = 2.0
SPEED_DEFAULT = 1.0

// Structure-specific sizes
ARRAY_CELL_WIDTH = 60
NODE_RADIUS = 30
TREE_NODE_RADIUS = 25
```

#### ValidationUtil.java
```java
isValidInteger(String input)
isValidIntegerInRange(String input, int min, int max)
isNotEmpty(String input)
getErrorMessage(String input, int min, int max)
```

#### LayoutUtil.java
```java
centerHorizontally(Pane parent, Group child)
centerNode(Pane parent, Group child)
drawArrow(Circle from, Circle to, Group parent)
calculateTreeNodePosition(int level, int position, int siblingCount)
anchorFill(AnchorPane pane, Node node)
clearGroup(Group group)
```

---

### 6. Main Application (`Main.java`)

**Purpose**: Application entry point and orchestration.

**Role**:
- Initialize all controllers
- Setup event handlers
- Manage data structure switching
- Coordinate UI updates

**Architecture**:
```
┌─────────────────────────────────────────┐
│           JavaFX Stage                  │
│  ┌─────────────────────────────────┐   │
│  │    ControlPanel (Input)         │   │
│  ├─────────────────────────────────┤   │
│  │  Visualization Area             │   │
│  │  (Holds current visualizer)     │   │
│  ├─────────────────────────────────┤   │
│  │    StatusPanel (Feedback)       │   │
│  └─────────────────────────────────┘   │
│          ↓ Events ↓                     │
│  ArrayController                        │
│  StackController                        │
│  QueueController                        │
│  LinkedListController                   │
│  BSTController                          │
│  GraphController                        │
│          ↓                              │
│  Model Layer (Data Structures)          │
└─────────────────────────────────────────┘
```

## Design Patterns Used

### 1. Model-View-Controller (MVC)
- **Model**: Data structures
- **View**: JavaFX visualizers
- **Controller**: Operation handlers

### 2. Factory Pattern
- `AnimationFactory` creates animations without exposing creation logic

### 3. Observer Pattern (Implicit)
- Visualizers observe model state
- Controllers trigger updates

### 4. Strategy Pattern
- Different operation strategies per data structure
- Pluggable controllers

### 5. Facade Pattern
- `AnimationFactory` provides simplified animation interface
- `ControlPanel` aggregates multiple UI controls

### 6. Template Method Pattern
- Controllers follow common operation template:
  1. Validate input
  2. Modify model
  3. Update view
  4. Trigger animation
  5. Update status

## Data Flow Example: Array Insert

### Step 1: User Input
```
User types "42" and clicks Insert button
↓
ControlPanel.getInsertButton().setOnAction()
```

### Step 2: Controller Receives Event
```
Main.handleInsertAction()
→ arrayController.insert("42")
```

### Step 3: Validation
```
ValidationUtil.isValidInteger("42") → true
```

### Step 4: Model Modification
```
array.insert(size, 42)
│
├── Shift existing elements
├── Insert at end
└── Increment size
```

### Step 5: View Update
```
arrayVisualizer.render()
│
├── Get array.getElements()
├── Clear visual group
└── Redraw all cells
```

### Step 6: Animation Trigger
```
AnimationFactory.fadeIn(visualizer.getElementsGroup(), 600)
│
├── Create FadeTransition
└── Play animation
```

### Step 7: Feedback Update
```
statusPanel.setOperationLabelSuccess("Inserted 42")
controlPanel.setStatusMessageSuccess("Element inserted!")
```

---

## Extension Points

### Adding a New Data Structure

1. **Create Model** (`model/NewStructure.java`)
   ```java
   public class NewStructure {
       public void insert(int value) { ... }
       public int delete() { ... }
       // Pure Java logic
   }
   ```

2. **Create Visualizer** (`view/NewStructureVisualizer.java`)
   ```java
   public class NewStructureVisualizer {
       private NewStructure model;
       public void render() { ... }
   }
   ```

3. **Create Controller** (`controller/NewStructureController.java`)
   ```java
   public class NewStructureController {
       private NewStructure model;
       private NewStructureVisualizer visualizer;
       public void insert(String input) { ... }
   }
   ```

4. **Update Main.java**
   ```java
   private NewStructureController newController;
   
   // In start():
   newController = new NewStructureController(canvas, controlPanel, statusPanel);
   
   // In switchDataStructure():
   case "New Structure":
       newController = new NewStructureController(...);
       break;
   ```

### Adding a New Operation

1. Add method to model class
2. Add animation in controller
3. Update button handler in Main.java
4. Provide pseudocode in statusPanel

---

## Performance Considerations

### Animation Performance
- Using JavaFX Transition APIs (hardware-accelerated on most platforms)
- Animation duration scaled by speed multiplier
- Sequential transitions prevent excessive parallel animations

### Memory Usage
- Models store only essential data
- Visualizers regenerate from model state (no duplication)
- Large structures (50+ elements) may affect animation smoothness

### Code Organization
- Separation of concerns enables easy optimization
- Can cache visualizations if needed
- Controllers can implement animation queueing

---

## Testing Strategy

### Unit Testing
- Model classes are independently testable
- No JavaFX dependencies needed

```java
@Test
public void testArrayInsert() {
    DynamicArray arr = new DynamicArray(10);
    arr.insert(0, 42);
    assertEquals(42, arr.get(0));
}
```

### Integration Testing
- Test controller operations with models
- Verify animations are created correctly

### Visual Testing
- Manual testing through UI
- Verify animations appear correct
- Test edge cases (empty, full, single element)

---

## Code Quality

### Naming Conventions
- Classes: PascalCase (e.g., `ArrayController`)
- Methods: camelCase (e.g., `insertNode()`)
- Constants: UPPER_CASE (e.g., `ANIMATION_DURATION_NORMAL`)
- Variables: camelCase (e.g., `animationSpeed`)

### Documentation
- All public classes have JavaDoc comments
- Complex methods include inline comments
- README and DESIGN_PATTERNS explain architecture

### Error Handling
- Input validation before operations
- Clear error messages in UI
- Graceful degradation for edge cases

---

## Future Architectural Improvements

1. **Event Bus**: For decoupled communication
2. **Undo/Redo**: Command pattern with history
3. **Plugin System**: Dynamic DS loading
4. **Themes**: Strategy pattern for UI styling
5. **Performance Monitoring**: Metrics collection
6. **Recording/Playback**: Animation capture system

---

This architecture ensures that the visualizer is **educational, maintainable, extensible, and testable**.
