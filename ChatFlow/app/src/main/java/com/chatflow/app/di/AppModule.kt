package com.chatflow.app.di

import android.content.Context
import com.chatflow.app.data.repository.AuthRepositoryImpl
import com.chatflow.app.data.repository.ChatRepositoryImpl
import com.chatflow.app.data.repository.MessageRepositoryImpl
import com.chatflow.app.data.repository.StorageRepositoryImpl
import com.chatflow.app.data.repository.UserRepositoryImpl
import com.chatflow.app.domain.repository.AuthRepository
import com.chatflow.app.domain.repository.ChatRepository
import com.chatflow.app.domain.repository.MessageRepository
import com.chatflow.app.domain.repository.StorageRepository
import com.chatflow.app.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context
    ): AuthRepository = AuthRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideUserRepository(
        authRepository: AuthRepositoryImpl
    ): UserRepository = UserRepositoryImpl(authRepository)

    @Provides
    @Singleton
    fun provideChatRepository(
        authRepository: AuthRepositoryImpl
    ): ChatRepository = ChatRepositoryImpl(authRepository)

    @Provides
    @Singleton
    fun provideMessageRepository(
        authRepository: AuthRepositoryImpl
    ): MessageRepository = MessageRepositoryImpl(authRepository)

    @Provides
    @Singleton
    fun provideStorageRepository(
        authRepository: AuthRepositoryImpl
    ): StorageRepository = StorageRepositoryImpl(authRepository)
}
