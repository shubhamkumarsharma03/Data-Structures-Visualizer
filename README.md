# 📊 Data Structures Visualizer

An interactive, animation-driven **Data Structures Visualizer** built with **JavaFX**, designed for educational purposes. This application provides step-by-step visualizations of how fundamental data structures work internally.

## 🎯 Features

### Supported Data Structures
- ✅ **Array** - Index-based insertion, deletion, search
- ✅ **Stack (LIFO)** - Push, Pop, Peek operations
- ✅ **Queue (FIFO)** - Enqueue, Dequeue, Peek operations
- ✅ **Singly Linked List** - Insert head, insert tail, delete head, search
- ✅ **Binary Search Tree** - Insert, delete, search, in-order traversal
- ✅ **Graph** - Add vertices/edges, BFS, DFS

### Core Capabilities
- 🎬 **Smooth Animations** - Every operation is decomposed into sequential steps
- 🎨 **Color-Coded Feedback** - Visual indication of operations (insert, delete, found, error)
- ⚡ **Adjustable Speed** - Control animation speed with a slider (0.5x to 2.0x)
- 📋 **Status Panel** - Displays pseudocode and operation explanations
- 🛡️ **Error Handling** - Input validation with helpful error messages
- 🔄 **Reset Functionality** - Clear data structure and start fresh

## 📦 Project Architecture

Strict **MVC (Model-View-Controller)** pattern with clean separation of concerns:

```
src/
├── model/           # Pure data structure logic (no JavaFX)
│   ├── DynamicArray.java
│   ├── Stack.java
│   ├── Queue.java
│   ├── LinkedList.java
│   ├── BinarySearchTree.java
│   └── Graph.java
│
├── view/            # JavaFX UI rendering only
│   ├── ControlPanel.java
│   ├── StatusPanel.java
│   ├── ArrayVisualizer.java
│   ├── StackVisualizer.java
│   ├── QueueVisualizer.java
│   ├── LinkedListVisualizer.java
│   ├── BSTVisualizer.java
│   └── GraphVisualizer.java
│
├── controller/      # Event handling & orchestration
│   ├── ArrayController.java
│   ├── StackController.java
│   ├── QueueController.java
│   ├── LinkedListController.java
│   ├── BSTController.java
│   └── GraphController.java
│
├── animation/       # JavaFX animation utilities
│   └── AnimationFactory.java
│
├── util/           # Constants, helpers, validators
│   ├── Constants.java
│   ├── LayoutUtil.java
│   └── ValidationUtil.java
│
└── Main.java       # Application entry point
```

## 🏗️ Architecture Principles

### Model Layer
- Pure Java data structure implementations
- **Zero JavaFX dependencies**
- Complete separation from UI concerns
- Testable and reusable logic

### View Layer
- **JavaFX components only**
- No data structure logic
- Visualizes current state of models
- Custom visualizer classes for each DS

### Controller Layer
- Bridges model and view
- Handles user events
- Triggers animations based on operations
- Manages application flow

### Animation Layer
- Centralized animation factory
- Transition-based animations
- Speed-adjustable via multiplier
- Types: Fade, Slide, Scale, Color, Pulse, Shake

## 🎬 Animation Types

All animations use **JavaFX Transition APIs**:

- **FadeTransition** - Visibility changes
- **TranslateTransition** - Node movement
- **ScaleTransition** - Size changes (pulse effects)
- **FillTransition** - Color changes
- **SequentialTransition** - Ordered animation sequences
- **ParallelTransition** - Simultaneous animations

## 🎮 Usage Guide

### Running the Application

#### Option 1: Maven
```bash
# Build
mvn clean install

# Run
mvn javafx:run
```

#### Option 2: IDE
1. Open project in IntelliJ IDEA or Eclipse
2. Configure JavaFX SDK path in project settings
3. Run `Main.java` as JavaFX Application

### Using the Visualizer

1. **Select Data Structure** - Choose from dropdown (Array, Stack, Queue, etc.)
2. **Enter Value** - Input integer value(s) to operate on
3. **Click Operation** - Press Insert, Delete, or Search button
4. **Watch Animation** - See step-by-step visualization
5. **Adjust Speed** - Use slider to control animation pace
6. **Read Status** - Check explanation panel for pseudocode and details

### Keyboard Shortcuts
- Input field is auto-focused
- Press Enter to execute Insert operation

## 🔧 Data Structure Operations

### Array
| Operation | Input | Behavior |
|-----------|-------|----------|
| Insert | Value | Adds to end, shifts visualization |
| Delete | Index | Removes at index, shifts left |
| Search | Value | Finds index, highlights cell |
| Reset | - | Clears all elements |

### Stack
| Operation | Input | Behavior |
|-----------|-------|----------|
| Insert (Push) | Value | Adds to top, TOP pointer moves |
| Delete (Pop) | - | Removes top, fades out |
| Search (Peek) | - | Shows top value, pulse animation |
| Reset | - | Clears stack |

### Queue
| Operation | Input | Behavior |
|-----------|-------|----------|
| Insert (Enqueue) | Value | Adds to rear, REAR pointer moves |
| Delete (Dequeue) | - | Removes front, FRONT pointer moves |
| Search (Peek) | - | Shows front value |
| Reset | - | Clears queue |

### Linked List
| Operation | Input | Behavior |
|-----------|-------|----------|
| Insert | Value | Inserts at head, renders links |
| Delete | - | Removes head node |
| Search | Value | Traverses and finds node |
| Reset | - | Clears list |

### Binary Search Tree
| Operation | Input | Behavior |
|-----------|-------|----------|
| Insert | Value | Traversal highlight, node drop, edge draw |
| Delete | Value | Removes node, maintains BST property |
| Search | Value | Traversal animation, highlight found node |
| Peek (Inorder) | - | Shows in-order traversal result |
| Reset | - | Clears tree |

### Graph
| Operation | Input | Behavior |
|-----------|-------|----------|
| Insert (Add Vertex) | Value | Adds vertex to circle layout |
| Delete (Add Edge) | "from to" | Connects two vertices |
| Search (BFS) | Start Vertex | Breadth-first visualization |
| Peek (DFS) | Start Vertex | Depth-first traversal |
| Reset | - | Clears graph |

## 🎨 Color Scheme

- **Primary Blue** (#2196F3) - Normal elements
- **Secondary Orange** (#FF9800) - Secondary operations
- **Success Green** (#4CAF50) - Found/completed
- **Error Red** (#F44336) - Errors/deletions
- **Warning Yellow** (#FFC107) - Warnings
- **Purple** (#9C27B0) - Visited nodes
- **Gray** (#CCCCCC) - Neutral/background

## ⚙️ Configuration

### Constants (in `Constants.java`)

```java
// Animation speeds (milliseconds)
ANIMATION_DURATION_FAST = 300
ANIMATION_DURATION_NORMAL = 600
ANIMATION_DURATION_SLOW = 1000

// Visualization dimensions
ARRAY_CELL_WIDTH = 60
STACK_ELEMENT_WIDTH = 80
NODE_RADIUS = 30
TREE_NODE_RADIUS = 25

// Speed slider range
SPEED_MIN = 0.5
SPEED_MAX = 2.0
```

## 🧪 Testing

Currently, the project focuses on visual testing through the UI. 

To add unit tests:

```bash
# Create test file
src/test/java/dsvisualizer/model/ArrayTest.java

# Run tests
mvn test
```

## 📚 Educational Benefits

- **Visual Learning** - See exactly what happens during operations
- **Step-by-Step Clarity** - Each operation broken into animated steps
- **Error Prevention** - Visual feedback prevents misunderstanding
- **Pseudocode Display** - Understand algorithm alongside animation
- **Interactive Exploration** - Experiment with different inputs

## 🚀 Future Enhancements

- [ ] Additional data structures (AVL Tree, Heap, Trie)
- [ ] Sorting visualizations (Bubble Sort, Quick Sort, Merge Sort)
- [ ] Path-finding algorithms (Dijkstra, A*)
- [ ] Performance metrics (operation count, time complexity)
- [ ] Dark mode theme
- [ ] Export animations as video
- [ ] Keyboard controls for hands-free operation
- [ ] Code generation from visualizations

## 🐛 Known Limitations

- Graph visualization uses circular layout (may overlap with large vertex counts)
- Tree layout may need adjustment for very deep trees
- Step-by-step mode not fully implemented (animations run automatically)
- Maximum tested array/stack/queue size: 50 elements

## 📄 License

This project is open-source and available for educational use.

## 👨‍💻 Author

**Data Structures Visualizer** - Created for teaching and learning fundamental data structures through interactive visualization.

---

**Last Updated**: December 2024  
**Java Version**: 11+  
**JavaFX Version**: 20+
