# Deploy Data Structures Visualizer to Heroku

## Prerequisites
- Heroku student plan ($13/month, 24 months free credits)
- Git installed
- Heroku CLI installed
- Java 21 runtime on Heroku

## Step-by-Step Deployment

### 1. Install Heroku CLI
```bash
# Download from: https://devcenter.heroku.com/articles/heroku-cli
# Or via npm:
npm install -g heroku
```

### 2. Login to Heroku
```bash
heroku login
# Opens browser for authentication
```

### 3. Create Heroku App
```bash
cd "C:\Users\shubh\Documents\GitHub\Data Structures Visualizer"
heroku create dsvisualizer-app
# or use a custom name
heroku create your-custom-app-name
```

### 4. Verify Files for Deployment
Ensure these files exist in your repo:
- ✅ `Procfile` - Tells Heroku how to run the app
- ✅ `system.properties` - Specifies Java 21 runtime
- ✅ `pom.xml` - Maven configuration
- ✅ `target/data-structures-visualizer-2.0.0.jar` - Built JAR (will be created during push)

### 5. Deploy
```bash
# From the project root:
git push heroku master
```

Heroku will:
1. Detect Maven project
2. Build with `mvn clean install`
3. Package JAR with `mvn package`
4. Run with Procfile configuration
5. Assign URL like: `https://dsvisualizer-app.herokuapp.com`

### 6. View Logs
```bash
heroku logs --tail
# or specific app
heroku logs --tail -a dsvisualizer-app
```

### 7. Test API Endpoints
Once deployed, test at:
```
https://your-app-name.herokuapp.com/api/health
https://your-app-name.herokuapp.com/api/stack/get
https://your-app-name.herokuapp.com/api/array/get
```

---

## Common Issues & Solutions

### Issue: "Java 25 not supported"
**Solution**: Ensured pom.xml uses Java 21 ✅

### Issue: "Port already in use"
**Solution**: Heroku automatically assigns `$PORT` environment variable
- Procfile uses: `-Dserver.port=${PORT}`
- application.properties reads: `server.port=${PORT:8080}`

### Issue: "Spring Boot repackage failed"
**Solution**: Changed to Java 21 which is fully compatible with Spring Boot 3.2.0

### Issue: "Module not found errors"
**Solution**: Removed `module-info.java` - prevents Spring classpath scanning issues

---

## Managing Your App

### Restart App
```bash
heroku restart -a dsvisualizer-app
```

### View App Info
```bash
heroku info -a dsvisualizer-app
```

### Set Environment Variables
```bash
heroku config:set JAVA_OPTS="-Xms512m -Xmx1024m" -a dsvisualizer-app
```

### Scale Dynos (if needed)
```bash
heroku dyno:scale web=1 -a dsvisualizer-app
```

---

## Next Steps: Add React Frontend

### Option A: Host on Separate Service (Recommended)
1. Create separate React Vite project
2. Deploy to Vercel: `npm run deploy`
3. Set React env to point to Heroku backend:
   ```
   VITE_API_URL=https://your-app-name.herokuapp.com/api
   ```

### Option B: Serve from Spring Boot
1. Build React: `npm run build`
2. Copy dist to Spring resources:
   ```bash
   cp -r dist/* src/main/resources/static/
   ```
3. Spring will serve index.html for static files
4. APIs available at `/api/...` same origin

---

## Monitoring & Logs

View real-time logs:
```bash
heroku logs --tail
```

View specific resource usage:
```bash
heroku ps
```

---

## Cost Breakdown (Student Plan)
- **Free app hours**: 1000/month ($7 value per dyno)
- **Postgres database**: Free tier
- **Student credit**: $13/month for 24 months
- **Total**: Can run 1-2 apps continuously within free tier

---

## Useful Commands
```bash
# Create app
heroku create app-name

# Deploy
git push heroku master

# View logs
heroku logs --tail

# Run one-off process
heroku run bash

# Open app in browser
heroku open

# Rename app
heroku apps:rename new-name

# Destroy app
heroku apps:destroy app-name
```

---

## Your Deployment Command Summary
```bash
cd "C:\Users\shubh\Documents\GitHub\Data Structures Visualizer"

# First time only
heroku create dsvisualizer-app

# Deploy (every time you push)
git push heroku master

# View logs
heroku logs --tail

# Test API
curl https://dsvisualizer-app.herokuapp.com/api/health
```

Your backend will be live at: **`https://dsvisualizer-app.herokuapp.com`**
