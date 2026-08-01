package com.chatflow.app.data.service

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.chatflow.app.domain.repository.UserRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineStatusService @Inject constructor(
    private val userRepository: UserRepository
) : LifecycleEventObserver {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: Lifecycle, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                // App moved to foreground
                applicationScope.launch {
                    val userResult = userRepository.getCurrentUser()
                    if (userResult.isSuccess && userResult.getOrNull() != null) {
                        val user = userResult.getOrNull()!!
                        userRepository.updateOnlineStatus(user.userId, true)
                    }
                }
            }
            Lifecycle.Event.ON_STOP -> {
                // App moved to background
                applicationScope.launch {
                    val userResult = userRepository.getCurrentUser()
                    if (userResult.isSuccess && userResult.getOrNull() != null) {
                        val user = userResult.getOrNull()!!
                        userRepository.updateOnlineStatus(user.userId, false)
                        userRepository.updateLastSeen(user.userId, System.currentTimeMillis())
                    }
                }
            }
            else -> {}
        }
    }
}
