# Running Both Desktop & Web Versions

## Architecture Overview

Your project now has **two ways** to use the Data Structures Visualizer:

### 1. Desktop Version (JavaFX) - Original
- Runs locally as a standalone GUI application
- Uses `Main.java` with responsive screen sizing
- No internet required

### 2. Web Version (Spring Boot + React) - New
- Backend REST API on Spring Boot
- Frontend visualization in React/Canvas
- Can deploy to Heroku cloud
- Access from any browser

---

## Running Desktop Version Locally

```bash
cd "C:\Users\shubh\Documents\GitHub\Data Structures Visualizer"

# Run the JavaFX application
mvn javafx:run
```

**Features**:
- ✅ Responsive window that adapts to screen size
- ✅ Local-only, no network required  
- ✅ Smooth JavaFX animations
- ✅ All 6 data structures (Array, Stack, Queue, LinkedList, BST, Graph)

---

## Running Web Version Locally

### Backend (Spring Boot)
```bash
cd "C:\Users\shubh\Documents\GitHub\Data Structures Visualizer"

# Run the REST API server
mvn spring-boot:run
```

Server will start at: `http://localhost:8080`

Available endpoints:
- `GET  /api/health` - Health check
- `POST /api/array/insert?value=5`
- `POST /api/stack/push?value=10`
- `POST /api/queue/enqueue?value=15`
- `GET  /api/*/get` - Get current state

### Frontend (React)
```bash
# In a different terminal/folder
cd ../dsvisualizer-web

# Development server
npm run dev

# Production build
npm run build
```

Frontend runs at: `http://localhost:5173`

---

## Project Structure After Setup

```
Data Structures Visualizer/         (Java/Spring Boot Backend)
├── src/main/java/
│   ├── dsvisualizer/
│   │   ├── Main.java               (JavaFX Desktop App)
│   │   ├── WebApplication.java     (Spring Boot Server)
│   │   ├── web/
│   │   │   ├── controller/         (REST endpoints)
│   │   │   └── model/              (Web-specific models)
│   │   └── model/                  (Original JavaFX models)
│   └── resources/
│       ├── application.properties   (Spring config)
│       └── static/                 (React build output)
├── pom.xml                         (Java dependencies)
├── Procfile                        (Heroku deployment)
└── system.properties               (Java version for Heroku)

../dsvisualizer-web/               (React Frontend - separate repo)
├── src/
│   ├── api/client.js              (API calls to backend)
│   ├── components/                (Canvas visualizers)
│   └── App.jsx
├── package.json
└── vite.config.js
```

---

## Deployment Options

### Option 1: Desktop Only (Current)
```bash
mvn clean package
# Creates: target/data-structures-visualizer-2.0.0.jar
# Run on any machine with Java 21
java -jar target/data-structures-visualizer-2.0.0.jar --desktop
```

### Option 2: Web Backend Only (Heroku)
```bash
# See HEROKU_DEPLOYMENT.md for full setup
git push heroku master
# Your API is now live!
```

### Option 3: Both + Integrated (Recommended)
1. **Build React frontend**:
   ```bash
   cd ../dsvisualizer-web
   npm run build
   ```

2. **Copy to Spring static folder**:
   ```bash
   cp -r dist/* ../Data\ Structures\ Visualizer/src/main/resources/static/
   ```

3. **Rebuild Spring Boot**:
   ```bash
   cd ../Data\ Structures\ Visualizer
   mvn clean package
   ```

4. **Deploy to Heroku**:
   ```bash
   git push heroku master
   ```

5. **Access at**:
   ```
   https://your-app-name.herokuapp.com
   ```
   - Frontend at `/` (home page)
   - API at `/api/...` (same origin)

---

## Why Two Versions?

| Aspect | Desktop | Web |
|--------|---------|-----|
| **Deployment** | Run locally | Cloud (Heroku) |
| **Access** | Local machine only | Anywhere with browser |
| **Performance** | Faster (no network) | Slight latency |
| **Graphics** | JavaFX (native) | Canvas (browser) |
| **Sharing** | Share JAR file | Share URL |
| **Updates** | Rebuild & redeploy | Update server only |
| **Offline** | ✅ Works offline | ❌ Needs internet |

---

## Development Workflow

### When Making Changes to Desktop (JavaFX)
```bash
cd "C:\Users\shubh\Documents\GitHub\Data Structures Visualizer"
# Edit src/main/java/dsvisualizer/Main.java or model classes
mvn javafx:run   # Test immediately
git commit ...
git push origin master
```

### When Making Changes to Web Backend (Spring)
```bash
cd "C:\Users\shubh\Documents\GitHub\Data Structures Visualizer"
# Edit src/main/java/dsvisualizer/web/controller/*.java
mvn spring-boot:run  # Test at localhost:8080
curl http://localhost:8080/api/health
git commit ...
git push heroku master  # Deploy to production
```

### When Making Changes to Web Frontend (React)
```bash
cd ../dsvisualizer-web
# Edit src/components/*.jsx
npm run dev  # Test at localhost:5173
# Test with backend:
# In another terminal: cd ../Data\ Structures\ Visualizer && mvn spring-boot:run
git commit ...
npm run build
# Copy dist to Spring static folder and redeploy
```

---

## Quick Start Commands

```bash
# Desktop Version
mvn javafx:run

# Web Backend
mvn spring-boot:run

# Web Frontend (separate folder)
npm run dev

# Build for Heroku
mvn clean package

# Deploy to Heroku
git push heroku master

# Check Heroku logs
heroku logs --tail
```

---

## Next Steps

1. ✅ **Desktop version**: Already responsive and working
2. ⏳ **Web backend**: Ready to deploy to Heroku
3. ⏳ **React frontend**: Follow WEB_SETUP.md to create it
4. ⏳ **Integration**: Combine both into single deployment

Your project now supports both modern approaches!
