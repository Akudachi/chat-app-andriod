const mongoose = require('mongoose');

const chatSchema = new mongoose.Schema({
  participants: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  }],
  lastMessage: {
    type: String,
    default: ""
  },
  lastMessageTimestamp: {
    type: Date,
    default: Date.now
  },
  lastMessageSenderId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  },
  unreadCount: {
    type: Map,
    of: Number,
    default: new Map()
  },
  isTyping: {
    type: Map,
    of: Boolean,
    default: new Map()
  }
}, {
  timestamps: true
});

// Index for faster queries
chatSchema.index({ participants: 1 });

module.exports = mongoose.model('Chat', chatSchema);
