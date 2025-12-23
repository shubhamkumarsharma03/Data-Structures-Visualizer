# Quick Start Guide

## Installation & Setup

### Prerequisites
- **Java 11+** installed and configured
- **Maven 3.6+** installed
- JavaFX SDK 20+ (Maven will download automatically)
- IDE: IntelliJ IDEA or Eclipse (optional but recommended)

### Step 1: Clone/Download Project
```bash
cd Data\ Structures\ Visualizer
```

### Step 2: Build Project
```bash
mvn clean install
```

### Step 3: Run Application
```bash
mvn javafx:run
```

The application window should open with the visualizer ready to use.

## First Time Usage

### 1. Choose a Data Structure
Click the dropdown at top-left and select one of:
- Array
- Stack  
- Queue
- Linked List
- BST
- Graph

### 2. Try Insert Operation
1. Type a number (e.g., `42`) in the input field
2. Click the **Insert** button
3. Watch the animation show the element being added
4. Check the status panel for pseudocode explanation

### 3. Try More Operations

#### For Array:
- **Insert**: Type `50` → Click Insert → Appends to end
- **Delete**: Type `0` → Click Delete → Removes element at index 0
- **Search**: Type `42` → Click Search → Highlights if found

#### For Stack:
- **Push**: Type `10` → Click Insert → Adds to top
- **Pop**: Click Delete → Removes top element
- **Peek**: Click Search → Shows top value without removing

#### For Queue:
- **Enqueue**: Type `5` → Click Insert → Adds to rear
- **Dequeue**: Click Delete → Removes from front
- **Peek**: Click Search → Shows front value

#### For Linked List:
- **Insert Head**: Type `99` → Click Insert → Adds at front
- **Delete Head**: Click Delete → Removes head
- **Search**: Type `50` → Click Search → Finds value

#### For BST:
- **Insert**: Type `30` → Click Insert → Adds with BST ordering
- **Delete**: Type `30` → Click Delete → Removes node
- **Search**: Type `30` → Click Search → Finds node
- **Inorder**: Click Search with any value → Shows ordered traversal

#### For Graph:
- **Add Vertex**: Type `1` → Click Insert → Creates vertex
- **Add Edge**: Type `1 2` → Click Delete → Connects vertices 1 and 2
- **BFS**: Type `1` → Click Search → Breadth-first traversal
- **DFS**: Type `1` → Click Search → Depth-first traversal

### 4. Control Animation Speed
Use the **Speed** slider at top to adjust:
- Slider left = 0.5x (slower, more detailed)
- Slider center = 1.0x (normal speed)
- Slider right = 2.0x (faster)

### 5. Reset
Click **Reset** to clear the current data structure and start fresh.

## Interface Overview

```
┌─────────────────────────────────────────────────────────────────┐
│  Data Structure: [Array ▼]  Input: [____]  [Insert] [Delete] ... │  ← Control Panel
├─────────────────────────────────────────────────────────────────┤
│                                                                     │
│                                                                     │
│                    VISUALIZATION AREA                              │
│                  (Shows data structure)                            │
│                                                                     │
│                                                                     │
├─────────────────────────────────────────────────────────────────┤
│ Operation: Inserting 42                                           │
│ Pseudocode: array[9] = 42                                         │ ← Status Panel
│ Explanation: Adding element at end of array (index 9)             │
└─────────────────────────────────────────────────────────────────┘
```

## Common Workflows

### Workflow 1: Learn Array Operations
```
1. Select "Array"
2. Insert: 10, 20, 30, 40, 50
3. Search: 30 (watch it find the element)
4. Delete: 2 (removes element at index 2)
5. Search: 30 (watch it find moved element)
6. Reset
```

### Workflow 2: Understand Stack LIFO
```
1. Select "Stack"
2. Insert: 5, 10, 15, 20
3. Search (Peek) - see TOP pointer
4. Delete (Pop) - removes 20
5. Delete (Pop) - removes 15
6. Search (Peek) - see current top
```

### Workflow 3: Explore BST Properties
```
1. Select "BST"
2. Insert: 50 (becomes root)
3. Insert: 30 (goes left)
4. Insert: 70 (goes right)
5. Insert: 20, 40, 60, 80
6. Search: 40 (watch traversal path)
7. Observe balanced tree structure
```

### Workflow 4: Build Simple Graph
```
1. Select "Graph"
2. Insert: 1, 2, 3, 4 (add 4 vertices)
3. Delete (which actually adds edges): 
   - "1 2" (connects 1-2)
   - "2 3" (connects 2-3)
   - "3 4" (connects 3-4)
   - "1 4" (connects 1-4)
4. Search: 1 (performs BFS from vertex 1)
```

## Troubleshooting

### Issue: "Module not found" error
**Solution**: 
- Ensure you're in the project root directory
- Run `mvn clean install` first

### Issue: JavaFX not loading
**Solution**:
- Check JavaFX SDK is in Maven dependencies
- Run `mvn dependency:resolve`
- Verify Java version is 11+

### Issue: Visualization appears blank
**Solution**:
- Try resizing the window
- Click Reset button
- Make sure you've inserted data

### Issue: Animation is very slow/fast
**Solution**:
- Use Speed slider to adjust animation pace
- Each operation should take 1-3 seconds at normal speed

## Tips & Tricks

1. **Keyboard Focus**: Input field is auto-focused, press Enter to execute Insert
2. **Quick Testing**: Rapidly click Insert to add many elements and see batch animations
3. **Error Testing**: Try invalid inputs (letters, negative for array index) to see error handling
4. **Speed Learning**: 
   - Slow (0.5x) - Detailed step-by-step learning
   - Normal (1.0x) - Recommended for most users
   - Fast (2.0x) - Quick overview mode
5. **Edge Cases**: Try operations at boundaries (empty structure, overflow)

## Keyboard Shortcuts

| Key | Action |
|-----|--------|
| Enter | Execute Insert operation (when input field focused) |
| Escape | Clear input field |
| Tab | Cycle through buttons |

## Video Walkthrough

(In production version, would include video tutorials for each data structure)

## Next Steps

- Experiment with different data structures
- Try complex sequences (e.g., multiple insertions then deletions)
- Observe how different operations affect the visualization
- Read the pseudocode in status panel to understand algorithms
- Check the main README.md for technical architecture details

## Support

For issues or questions:
1. Check README.md for detailed documentation
2. Review the status panel explanations
3. Try Reset and restart the operation
4. Examine the pseudocode for algorithm understanding

---

**Happy Learning!** 🎓
