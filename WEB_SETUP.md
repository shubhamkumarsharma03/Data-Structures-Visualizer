# Data Structures Visualizer - Web Version Setup

## Backend (Spring Boot)  ✅ DONE

The Spring Boot server is ready to run and exposes REST APIs for all data structures:

- **Array**: `/api/array/*` (insert, delete, search, get, reset)
- **Stack**: `/api/stack/*` (push, pop, peek, get, reset)
- **Queue**: `/api/queue/*` (enqueue, dequeue, peek, get, reset)

### Run Backend Locally
```bash
mvn spring-boot:run
```
Server runs on `http://localhost:8080`

### Deploy to Heroku
```bash
heroku login
heroku create your-app-name
git push heroku master
```

---

## Frontend (React + Vite)

### Step 1: Create React Project
```bash
# Navigate to a sibling directory
cd ..
npm create vite@latest dsvisualizer-web -- --template react
cd dsvisualizer-web
npm install
```

### Step 2: Create API Client
Create `src/api/client.js`:
```javascript
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

export const api = {
  array: {
    insert: (value) => fetch(`${API_URL}/array/insert?value=${value}`, { method: 'POST' }).then(r => r.json()),
    delete: (index) => fetch(`${API_URL}/array/delete?index=${index}`, { method: 'POST' }).then(r => r.json()),
    search: (value) => fetch(`${API_URL}/array/search?value=${value}`).then(r => r.json()),
    get: () => fetch(`${API_URL}/array/get`).then(r => r.json()),
    reset: () => fetch(`${API_URL}/array/reset`, { method: 'POST' }).then(r => r.json()),
  },
  stack: {
    push: (value) => fetch(`${API_URL}/stack/push?value=${value}`, { method: 'POST' }).then(r => r.json()),
    pop: () => fetch(`${API_URL}/stack/pop`, { method: 'POST' }).then(r => r.json()),
    peek: () => fetch(`${API_URL}/stack/peek`).then(r => r.json()),
    get: () => fetch(`${API_URL}/stack/get`).then(r => r.json()),
    reset: () => fetch(`${API_URL}/stack/reset`, { method: 'POST' }).then(r => r.json()),
  },
  queue: {
    enqueue: (value) => fetch(`${API_URL}/queue/enqueue?value=${value}`, { method: 'POST' }).then(r => r.json()),
    dequeue: () => fetch(`${API_URL}/queue/dequeue`, { method: 'POST' }).then(r => r.json()),
    peek: () => fetch(`${API_URL}/queue/peek`).then(r => r.json()),
    get: () => fetch(`${API_URL}/queue/get`).then(r => r.json()),
    reset: () => fetch(`${API_URL}/queue/reset`, { method: 'POST' }).then(r => r.json()),
  },
};
```

### Step 3: Create Visualizer Component
Create `src/components/ArrayVisualizer.jsx`:
```jsx
import { useEffect, useRef, useState } from 'react';
import { api } from '../api/client';

export function ArrayVisualizer() {
  const canvasRef = useRef(null);
  const [elements, setElements] = useState([]);
  const [inputValue, setInputValue] = useState('');

  const drawArray = (data) => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    const arr = data || elements;
    const boxWidth = 60;
    const boxHeight = 40;
    const startX = 50;
    const startY = 100;

    arr.forEach((value, index) => {
      const x = startX + index * (boxWidth + 10);
      const y = startY;

      // Draw box
      ctx.fillStyle = '#4CAF50';
      ctx.fillRect(x, y, boxWidth, boxHeight);
      ctx.strokeStyle = '#333';
      ctx.lineWidth = 2;
      ctx.strokeRect(x, y, boxWidth, boxHeight);

      // Draw value
      ctx.fillStyle = '#fff';
      ctx.font = '16px Arial';
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText(value, x + boxWidth / 2, y + boxHeight / 2);
    });
  };

  const handleInsert = async () => {
    const value = parseInt(inputValue);
    if (!isNaN(value)) {
      const response = await api.array.insert(value);
      setElements(response.elements);
      setInputValue('');
    }
  };

  const handleReset = async () => {
    await api.array.reset();
    setElements([]);
  };

  useEffect(() => {
    drawArray(elements);
  }, [elements]);

  return (
    <div style={{ padding: '20px' }}>
      <h2>Array Visualizer</h2>
      <div style={{ marginBottom: '20px' }}>
        <input
          type="number"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          placeholder="Enter value"
        />
        <button onClick={handleInsert}>Insert</button>
        <button onClick={handleReset}>Reset</button>
      </div>
      <canvas
        ref={canvasRef}
        width={800}
        height={300}
        style={{ border: '1px solid #ccc' }}
      />
    </div>
  );
}
```

### Step 4: Update `src/App.jsx`
```jsx
import { ArrayVisualizer } from './components/ArrayVisualizer';
import './App.css';

function App() {
  return (
    <div>
      <h1>Data Structures Visualizer - Web</h1>
      <ArrayVisualizer />
    </div>
  );
}

export default App;
```

### Step 5: Build & Run
```bash
npm run dev      # Development
npm run build    # Production build
```

---

## Deployment

### Frontend + Backend Setup (Best Practice)
Option 1: Host frontend on Vercel/Netlify, backend on Heroku
Option 2: Serve frontend from Spring Boot static folder

For Option 2, build React and copy dist to `src/main/resources/static`:
```bash
npm run build
cp -r dist/* ../Data\ Structures\ Visualizer/src/main/resources/static/
```

Then Spring Boot serves both at the same URL.

---

## Architecture

```
Data Structures Visualizer/
├── Spring Boot Backend (Java 21)
│   ├── /api/array/*
│   ├── /api/stack/*
│   ├── /api/queue/*
│   └── REST responses with visualization data
│
└── React Frontend (Separate or served from Spring)
    ├── Canvas-based visualizers
    ├── Real-time updates
    └── Smooth animations
```
