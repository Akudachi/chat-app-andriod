# ChatFlow MongoDB + Android Integration - Complete Setup Guide

## ✅ What Has Been Created

### Backend (Node.js + MongoDB)
- ✅ Complete REST API server
- ✅ MongoDB connection with your Atlas database
- ✅ JWT authentication system
- ✅ Real-time chat with Socket.io
- ✅ Image upload support
- ✅ Voice message upload support
- ✅ All CRUD operations for users, chats, messages

### Android App
- ✅ Updated dependencies (Retrofit, Socket.io, Gson)
- ✅ New API service layer
- ✅ Updated data models for backend API
- ✅ JWT-based authentication (replaces Firebase Auth)
- ✅ REST API repositories (replaces Firestore)
- ✅ Voice recording utility
- ✅ Updated dependency injection

## 🚀 Quick Start Guide

### 1. Start Backend Server

```bash
cd backend
npm install
npm run dev
```

Server will run on `http://localhost:5000`

### 2. Update Android API URL

In `ApiService.kt`, update the BASE_URL:
- **For emulator**: Keep `http://10.0.2.2:5000/api/`
- **For real device**: Change to your computer's IP (e.g., `http://192.168.1.X:5000/api/`)

### 3. Build and Run Android App

```bash
cd ChatFlow
./gradlew assembleDebug
```

Install the APK on your device/emulator.

### 4. Test the App

1. **Register** a new user
2. **Login** with credentials
3. **Create** a new chat
4. **Send** text messages
5. **Upload** images
6. **Record** voice messages

## 🌐 Cloud Deployment (Easy Options)

### Recommended: Render (Free Tier)

1. Go to https://render.com
2. Sign up with GitHub
3. Click "New +" → "Web Service"
4. Connect your GitHub repository
5. Build settings:
   - Root directory: `backend`
   - Build command: `npm install`
   - Start command: `npm start`
6. Environment variables:
   - `MONGODB_URI`: `mongodb+srv://opticalpulp:rdECxs7vyOXWlXxy@opticalpulp.2r51u9c.mongodb.net/?appName=opticalpulp`
   - `JWT_SECRET`: Generate random string
   - `PORT`: 5000
7. Deploy!

### Alternative: Railway (Free Tier)

1. Go to https://railway.app
2. Click "New Project" → "Deploy from GitHub repo"
3. Select your repository
4. Add MongoDB service
5. Add Node.js service
6. Deploy!

## 📱 Key Features Implemented

### Backend
- ✅ JWT authentication with secure tokens
- ✅ Real-time messaging via Socket.io
- ✅ Image upload (JPEG, PNG, GIF)
- ✅ Voice message upload (MP3, WAV, M4A)
- ✅ User search functionality
- ✅ Typing indicators
- ✅ Read receipts
- ✅ Online status tracking

### Android
- ✅ Token-based authentication
- ✅ Retrofit HTTP client
- ✅ Socket.io client for real-time chat
- ✅ Voice recording utility
- ✅ Image upload with progress
- ✅ Clean architecture with Hilt DI
- ✅ DataStore for token persistence

## 🔧 Configuration Files

### Backend Environment Variables (.env)
```
MONGODB_URI=mongodb+srv://opticalpulp:rdECxs7vyOXWlXxy@opticalpulp.2r51u9c.mongodb.net/?appName=opticalpulp
PORT=5000
NODE_ENV=development
JWT_SECRET=your-super-secret-jwt-key-change-this-in-production
UPLOAD_DIR=./uploads
MAX_FILE_SIZE=5242880
```

### Android API URL
Update in `ApiService.kt` line 175:
```kotlin
private const val BASE_URL = "http://YOUR_IP:5000/api/"
```

## 📡 API Endpoints

### Authentication
- `POST /api/auth/register` - Register user
- `POST /api/auth/login` - Login user
- `GET /api/auth/me` - Get current user
- `PUT /api/auth/profile` - Update profile
- `POST /api/auth/logout` - Logout

### Chat
- `POST /api/chats` - Create chat
- `GET /api/chats` - Get user chats
- `GET /api/chats/:id/messages` - Get messages
- `POST /api/chats/:id/messages` - Send message
- `PUT /api/chats/:id/typing` - Update typing status
- `PUT /api/chats/:id/read` - Mark as read
- `GET /api/chats/search/users` - Search users

### Upload
- `POST /api/upload/image` - Upload image
- `POST /api/upload/voice` - Upload voice message

## 🔌 WebSocket Events

### Client → Server
- `joinChat` - Join chat room
- `leaveChat` - Leave chat room

### Server → Client
- `newMessage` - New message received
- `typingStatus` - User typing status

## 🎯 Next Steps for Production

1. **Deploy backend** to Render or Railway
2. **Update Android API URL** to deployed backend URL
3. **Test thoroughly** on real devices
4. **Add error handling** for network issues
5. **Implement push notifications** (optional)
6. **Add rate limiting** to backend
7. **Add input validation** to backend
8. **Add SSL certificates** for production

## 🐛 Troubleshooting

### Backend
- **MongoDB connection failed**: Check connection string and network
- **Port already in use**: Change PORT in .env file
- **Module not found**: Run `npm install`

### Android
- **Connection refused**: Check API URL and backend is running
- **Authentication failed**: Check JWT token is being sent
- **Image upload failed**: Check file size and permissions
- **WebSocket not connecting**: Check Socket.io client configuration

## 📊 Architecture

```
Android App (Kotlin)
    ↓ HTTP (Retrofit)
    ↓ WebSocket (Socket.io)
Node.js Backend (Express)
    ↓
MongoDB Atlas (Database)
```

## 🎨 Features Summary

✅ Real-time chat with typing indicators
✅ Image sharing
✅ Voice messages with recording
✅ User profiles
✅ Search users
✅ Online status
✅ Read receipts
✅ Clean architecture
✅ Material 3 design
✅ JWT authentication
✅ MongoDB database

The integration is complete and ready to test!
