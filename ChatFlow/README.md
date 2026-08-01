# ChatFlow with MongoDB Backend

## Setup Instructions

### Backend Setup (Node.js + MongoDB)

1. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Configure environment variables:**
   - Edit `.env` file
   - MongoDB URI is already configured with your connection string
   - Change JWT_SECRET to a secure random string

4. **Start the backend server:**
   ```bash
   npm run dev
   ```
   Server will run on `http://localhost:5000`

### Android App Setup

1. **Update API URL in ApiService.kt:**
   - For emulator: Keep `http://10.0.2.2:5000/api/`
   - For real device: Change to your computer's IP (e.g., `http://192.168.1.X:5000/api/`)

2. **Build and run the Android app**

### Architecture

**Backend:**
- Node.js + Express server
- MongoDB Atlas database
- Socket.io for real-time chat
- JWT authentication
- Multer for image uploads

**Android:**
- Retrofit for HTTP requests
- Socket.io client for real-time chat
- JWT token-based authentication
- Clean Architecture with Hilt DI

### API Endpoints

**Authentication:**
- POST `/api/auth/register` - Register new user
- POST `/api/auth/login` - Login user
- GET `/api/auth/me` - Get current user
- PUT `/api/auth/profile` - Update profile
- POST `/api/auth/logout` - Logout

**Chat:**
- POST `/api/chats` - Create chat
- GET `/api/chats` - Get user chats
- GET `/api/chats/:id/messages` - Get chat messages
- POST `/api/chats/:id/messages` - Send message
- PUT `/api/chats/:id/typing` - Update typing status
- PUT `/api/chats/:id/read` - Mark as read
- GET `/api/chats/search/users` - Search users

**Upload:**
- POST `/api/upload/image` - Upload image

### WebSocket Events

**Client to Server:**
- `joinChat` - Join a chat room
- `leaveChat` - Leave a chat room

**Server to Client:**
- `newMessage` - New message received
- `typingStatus` - User typing status changed

### Next Steps

1. Start the backend server
2. Update Android API URL if using real device
3. Build and run Android app
4. Test registration, login, and chat functionality
