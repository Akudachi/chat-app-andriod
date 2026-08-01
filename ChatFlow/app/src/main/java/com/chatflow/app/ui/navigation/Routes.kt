package com.chatflow.app.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Onboarding : Screen("onboarding")
    object ProfileSetup : Screen("profile_setup")
    object Home : Screen("home")
    object Search : Screen("search")
    object NewChat : Screen("new_chat")
    object Chat : Screen("chat/{chatId}") {
        fun createRoute(chatId: String) = "chat/$chatId"
    }
    object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: String) = "profile/$userId"
    }
    object EditProfile : Screen("edit_profile")
    object Settings : Screen("settings")
}
