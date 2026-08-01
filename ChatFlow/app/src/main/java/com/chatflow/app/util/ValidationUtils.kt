package com.chatflow.app.util

object ValidationUtils {

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailPattern.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun isValidName(name: String): Boolean {
        return name.trim().length >= 2
    }

    fun isValidPhoneNumber(phone: String): Boolean {
        val phonePattern = "^[+]?[0-9]{10,15}$"
        return phone.matches(phonePattern.toRegex())
    }

    fun getPasswordErrorMessage(password: String): String? {
        return when {
            password.isEmpty() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }

    fun getEmailErrorMessage(email: String): String? {
        return when {
            email.isEmpty() -> "Email is required"
            !isValidEmail(email) -> "Please enter a valid email"
            else -> null
        }
    }

    fun getNameErrorMessage(name: String): String? {
        return when {
            name.isEmpty() -> "Name is required"
            name.trim().length < 2 -> "Name must be at least 2 characters"
            else -> null
        }
    }
}
