package com.chatflow.app.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateUtils {

    private const val MINUTE_MILLIS = 60 * 1000L
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    private const val WEEK_MILLIS = 7 * DAY_MILLIS
    private const val MONTH_MILLIS = 30 * DAY_MILLIS
    private const val YEAR_MILLIS = 365 * DAY_MILLIS

    fun getTimeAgo(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < MINUTE_MILLIS -> "just now"
            diff < 2 * MINUTE_MILLIS -> "1m ago"
            diff < HOUR_MILLIS -> "${diff / MINUTE_MILLIS}m ago"
            diff < 2 * HOUR_MILLIS -> "1h ago"
            diff < DAY_MILLIS -> "${diff / HOUR_MILLIS}h ago"
            diff < 2 * DAY_MILLIS -> "yesterday"
            diff < WEEK_MILLIS -> "${diff / DAY_MILLIS}d ago"
            diff < MONTH_MILLIS -> "${diff / WEEK_MILLIS}w ago"
            diff < YEAR_MILLIS -> "${diff / MONTH_MILLIS}mo ago"
            else -> formatDate(timestamp)
        }
    }

    fun formatTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun formatDateTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun isToday(timestamp: Long): Boolean {
        val calendar = Calendar.getInstance()
        val today = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val nextDay = today + DAY_MILLIS
        return timestamp in today until nextDay
    }

    fun isYesterday(timestamp: Long): Boolean {
        val calendar = Calendar.getInstance()
        val today = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val yesterday = today - DAY_MILLIS
        return timestamp in yesterday until today
    }

    fun isThisWeek(timestamp: Long): Boolean {
        val now = System.currentTimeMillis()
        val weekAgo = now - WEEK_MILLIS
        return timestamp in weekAgo..now
    }

    fun getDateHeader(timestamp: Long): String {
        return when {
            isToday(timestamp) -> "Today"
            isYesterday(timestamp) -> "Yesterday"
            isThisWeek(timestamp) -> {
                val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
                sdf.format(Date(timestamp))
            }
            else -> formatDate(timestamp)
        }
    }
}
