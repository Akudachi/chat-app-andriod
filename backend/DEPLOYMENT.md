# Easy Cloud Hosting Options for ChatFlow Backend

## Option 1: Render (Easiest - Free Tier Available)

### Steps:
1. Go to https://render.com
2. Sign up/login with GitHub
3. Click "New +" → "Web Service"
4. Connect your GitHub repository
5. Build settings:
   - Root directory: `backend`
   - Build command: `npm install`
   - Start command: `npm start`
6. Environment variables:
   - `MONGODB_URI`: Your MongoDB connection string
   - `JWT_SECRET`: Generate a random string
   - `PORT`: 5000
7. Deploy!

**Pros:** Free tier, easy GitHub integration, automatic SSL
**Cons:** Limited resources on free tier

## Option 2: Railway (Simple - Free Tier Available)

### Steps:
1. Go to https://railway.app
2. Click "New Project" → "Deploy from GitHub repo"
3. Select your repository
4. Add MongoDB service (Railway has built-in MongoDB)
5. Add Node.js service
6. Environment variables will be auto-configured
7. Deploy!

**Pros:** Very easy, built-in MongoDB, automatic SSL
**Cons:** Free tier has sleep time

## Option 3: Heroku (Requires Credit Card)

### Steps:
1. Go to https://heroku.com
2. Click "Create new app"
3. Connect GitHub repository
4. Add MongoDB Atlas as add-on
5. Deploy

**Pros:** Reliable, good performance
**Cons:** Requires credit card, no free tier

## Option 4: Vercel (Great for APIs)

### Steps:
1. Go to https://vercel.com
2. Import your GitHub repository
3. Configure as Node.js app
4. Add environment variables
5. Deploy

**Pros:** Great performance, free tier, automatic SSL
**Cons:** Not optimized for long-running WebSocket servers

## Recommendation: **Render** or **Railway**

Both are free, easy to use, and support Node.js + MongoDB well.

## Quick Setup with Render:

1. Push your code to GitHub
2. Create account on render.com
3. Follow the steps above
4. Your backend will be live at: `https://your-app-name.onrender.com`

## Update Android App URL:

After deployment, update the URL in `ApiService.kt`:
```kotlin
private const val BASE_URL = "https://your-app-name.onrender.com/api/"
```

## Environment Variables Needed:

- `MONGODB_URI`: `mongodb+srv://opticalpulp:rdECxs7vyOXWlXxy@opticalpulp.2r51u9c.mongodb.net/?appName=opticalpulp`
- `JWT_SECRET`: Generate a random string (use: `openssl rand -base64 32`)
- `PORT`: 5000
- `NODE_ENV`: production
