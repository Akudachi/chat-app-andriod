const express = require('express');
const Chat = require('../models/Chat');
const Message = require('../models/Message');
const User = require('../models/User');
const auth = require('../middleware/auth');

const router = express.Router();

// Create chat
router.post('/', auth, async (req, res) => {
  try {
    const { participantId } = req.body;

    // Check if chat already exists
    const existingChat = await Chat.findOne({
      participants: { $all: [req.user._id, participantId] }
    });

    if (existingChat) {
      return res.json({ chatId: existingChat._id });
    }

    // Create new chat
    const chat = new Chat({
      participants: [req.user._id, participantId]
    });

    await chat.save();

    res.status(201).json({ chatId: chat._id });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Get user chats
router.get('/', auth, async (req, res) => {
  try {
    const chats = await Chat.find({
      participants: req.user._id
    })
    .populate('participants', 'name photoUrl isOnline lastSeen')
    .sort({ updatedAt: -1 });

    // Get other user info for each chat
    const chatsWithInfo = await Promise.all(chats.map(async (chat) => {
      const otherUser = chat.participants.find(
        p => p._id.toString() !== req.user._id.toString()
      );

      return {
        chatId: chat._id,
        participants: chat.participants,
        lastMessage: chat.lastMessage,
        lastMessageTimestamp: chat.lastMessageTimestamp,
        lastMessageSenderId: chat.lastMessageSenderId,
        unreadCount: chat.unreadCount,
        isTyping: chat.isTyping,
        otherUser: otherUser ? {
          id: otherUser._id,
          name: otherUser.name,
          photoUrl: otherUser.photoUrl,
          isOnline: otherUser.isOnline,
          lastSeen: otherUser.lastSeen
        } : null,
        createdAt: chat.createdAt,
        updatedAt: chat.updatedAt
      };
    }));

    res.json({ chats: chatsWithInfo });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Get chat messages
router.get('/:chatId/messages', auth, async (req, res) => {
  try {
    const { chatId } = req.params;
    const { limit = 50 } = req.query;

    // Verify user is participant
    const chat = await Chat.findById(chatId);
    if (!chat || !chat.participants.includes(req.user._id)) {
      return res.status(403).json({ error: 'Access denied' });
    }

    const messages = await Message.find({ chatId })
      .populate('senderId', 'name photoUrl')
      .sort({ createdAt: -1 })
      .limit(parseInt(limit));

    res.json({ messages: messages.reverse() });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Send message
router.post('/:chatId/messages', auth, async (req, res) => {
  try {
    const { chatId } = req.params;
    const { text, type = 'TEXT', mediaUrl } = req.body;

    // Verify user is participant
    const chat = await Chat.findById(chatId);
    if (!chat || !chat.participants.includes(req.user._id)) {
      return res.status(403).json({ error: 'Access denied' });
    }

    // Create message
    const message = new Message({
      chatId,
      senderId: req.user._id,
      text,
      type,
      mediaUrl,
      status: 'SENT'
    });

    await message.save();

    // Update chat
    chat.lastMessage = text;
    chat.lastMessageTimestamp = new Date();
    chat.lastMessageSenderId = req.user._id;
    await chat.save();

    // Emit socket event
    req.io.to(chatId).emit('newMessage', {
      message: await message.populate('senderId', 'name photoUrl'),
      chatId
    });

    res.status(201).json({ message });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Update typing status
router.put('/:chatId/typing', auth, async (req, res) => {
  try {
    const { chatId } = req.params;
    const { isTyping } = req.body;

    const chat = await Chat.findById(chatId);
    if (!chat || !chat.participants.includes(req.user._id)) {
      return res.status(403).json({ error: 'Access denied' });
    }

    chat.isTyping.set(req.user._id.toString(), isTyping);
    await chat.save();

    req.io.to(chatId).emit('typingStatus', {
      userId: req.user._id,
      isTyping,
      chatId
    });

    res.json({ success: true });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Mark as read
router.put('/:chatId/read', auth, async (req, res) => {
  try {
    const { chatId } = req.params;

    const chat = await Chat.findById(chatId);
    if (!chat || !chat.participants.includes(req.user._id)) {
      return res.status(403).json({ error: 'Access denied' });
    }

    chat.unreadCount.set(req.user._id.toString(), 0);
    await chat.save();

    await Message.updateMany(
      { chatId, senderId: { $ne: req.user._id } },
      { status: 'READ' }
    );

    res.json({ success: true });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Search users
router.get('/search/users', auth, async (req, res) => {
  try {
    const { query } = req.query;

    const users = await User.find({
      $and: [
        { _id: { $ne: req.user._id } },
        {
          $or: [
            { name: { $regex: query, $options: 'i' } },
            { email: { $regex: query, $options: 'i' } }
          ]
        }
      ]
    })
    .select('name email photoUrl status isOnline lastSeen')
    .limit(20);

    res.json({ users });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
