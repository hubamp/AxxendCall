package com.example.axxendvideocall.make_call.di

import com.example.axxendvideocall.make_call.data.CallRepositoryImpl
import com.example.axxendvideocall.make_call.domain.use_case.StartCall
import com.example.axxendvideocall.make_call.domain.model.CallRepository
import com.example.axxendvideocall.make_call.domain.use_case.UpdateLastUserToken
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object VideoCallModule {

    @Provides
    fun provideFirestoredb() = FirebaseFirestore.getInstance()

    @Provides
    fun provideFirebaseMessage() = FirebaseMessaging.getInstance()

    @Provides
    fun provideCallRepo(db: FirebaseFirestore, fmc: FirebaseMessaging): CallRepository = CallRepositoryImpl(db,fmc)

    @Provides
    fun provideStartCall(repo: CallRepository) = StartCall(repo)

    @Provides
    fun provideUpdateLastToken(repo: CallRepository) = UpdateLastUserToken(repo)
}