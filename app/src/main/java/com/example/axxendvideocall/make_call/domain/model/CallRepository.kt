package com.example.axxendvideocall.make_call.domain.model

import kotlinx.coroutines.flow.Flow

interface CallRepository {

    val amITheLastUser: Flow<Boolean>
    val myToken: Flow<String>
    suspend fun startCall(): Flow<Result>
    suspend fun endEstablishedCall(): Flow<Result>
    suspend fun answerCall(): Flow<Result>
    suspend fun declineCall(): Flow<Result>
    suspend fun setCallInSession(): Flow<Result>
    suspend fun updateLastUserToken(): Flow<Result>


}