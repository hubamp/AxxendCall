package com.example.axxendvideocall.make_call.data

import android.util.Log
import com.example.axxendvideocall.make_call.core.CallStatus
import com.example.axxendvideocall.make_call.core.FirebaseConstants.ACTIVE_CALL
import com.example.axxendvideocall.make_call.core.FirebaseConstants.CALLERS
import com.example.axxendvideocall.make_call.core.FirebaseConstants.CALLER_RINGING_1
import com.example.axxendvideocall.make_call.core.FirebaseConstants.CALLER_RINGING_2
import com.example.axxendvideocall.make_call.core.FirebaseConstants.CALLER_SESSION_1
import com.example.axxendvideocall.make_call.core.FirebaseConstants.CALLER_SESSION_2
import com.example.axxendvideocall.make_call.core.FirebaseConstants.CALLER_TOKEN_1
import com.example.axxendvideocall.make_call.core.FirebaseConstants.CALLER_TOKEN_2
import com.example.axxendvideocall.make_call.core.FirebaseConstants.CALL_STATUS_1
import com.example.axxendvideocall.make_call.core.FirebaseConstants.CALL_STATUS_2
import com.example.axxendvideocall.make_call.core.FirebaseConstants.INCOMING_CALL_1
import com.example.axxendvideocall.make_call.core.FirebaseConstants.INCOMING_CALL_2
import com.example.axxendvideocall.make_call.domain.model.CallRepository
import com.example.axxendvideocall.make_call.domain.model.Result
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class CallRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val fmc: FirebaseMessaging,
) :
    CallRepository {
    override val amITheLastUser = flow<Boolean> {

    }
    override val myToken: Flow<String>
        get() = channelFlow<String> {
            fmc.token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                trySend(token)
            })

            awaitClose()
        }


    override suspend fun startCall() = channelFlow<Result> {
        send(Result.Processing("Verifying call availability"))

        firestore.collection(CALLERS).document(ACTIVE_CALL).get().addOnSuccessListener { snapShot ->

            val firstSession = snapShot.getBoolean(CALLER_SESSION_1)
            val secondSession = snapShot.getBoolean(CALLER_SESSION_2)
            val firstCaller = snapShot.getString(CALLER_TOKEN_1)
            val secondCaller = snapShot.getString(CALLER_TOKEN_2)

            if (firstSession == true || secondSession == true) {

                trySend(Result.Processing("Another call may be in session"))
                trySend(Result.CallReady(false))

            } else {
                trySend(Result.Processing("Call is Available"))
                trySend(Result.CallReady(true))

                this.launch {
                    myToken.mapNotNull { token ->

                        if (firstCaller?.equals(token) == true) {
                            //ringing 2

                            val map = mapOf<String, Any?>(
                                Pair(CALLER_SESSION_1, true),
                                Pair(CALLER_SESSION_2, false),
                                Pair(CALLER_RINGING_1, false),
                                Pair(CALLER_RINGING_2, true),
                                Pair(CALLER_SESSION_2, false),
                                Pair(CALLER_TOKEN_1, token),
                                Pair(CALLER_TOKEN_2, secondCaller),
                            )


                            snapShot.reference.update(map).addOnSuccessListener {
                                trySend(Result.OutgoingCall(secondCaller.toString()))
                            }

                        } else if (secondCaller?.equals(token) == true) {

                            //ringing 1

                            val map = mapOf<String, Any?>(
                                Pair(CALLER_SESSION_1, false),
                                Pair(CALLER_SESSION_2, false),
                                Pair(CALLER_RINGING_1, true),
                                Pair(CALLER_RINGING_2, false),
                                Pair(CALLER_SESSION_2, true),
                                Pair(CALLER_TOKEN_1, token),
                                Pair(CALLER_TOKEN_2, secondCaller),
                            )


                            snapShot.reference.update(map).addOnSuccessListener {
                                trySend(Result.OutgoingCall(secondCaller.toString()))
                            }
                        } else {

                            myToken.mapNotNull {

                            }
                            val map = mapOf<String, Any?>(
                                Pair(CALLER_SESSION_1, false),
                                Pair(CALLER_SESSION_2, false),
                                Pair(CALLER_RINGING_1, true),
                                Pair(CALLER_RINGING_2, false),
                                Pair(CALLER_TOKEN_1, token),
                                Pair(CALLER_TOKEN_2, secondCaller),
                            )


                            snapShot.reference.update(map).addOnSuccessListener {
                                trySend(Result.OutgoingCall(secondCaller.toString()))
                            }

                        }


                    }.collect()
                }

            }

        }.addOnFailureListener {
            trySend(Result.Error("Could not verify: ${it.message}"))
        }

        awaitClose()
    }

    override suspend fun endEstablishedCall(): Flow<Result> {
        TODO("Not yet implemented")
    }

    override suspend fun answerCall(): Flow<Result> {
        TODO("Not yet implemented")
    }

    override suspend fun declineCall() = channelFlow {
        send(Result.Processing("Verifying call availability"))


        firestore.collection(CALLERS).document(ACTIVE_CALL).get().addOnSuccessListener { snapShot ->

            val firstSession = snapShot.getBoolean(CALLER_SESSION_1)
            val secondSession = snapShot.getBoolean(CALLER_SESSION_2)
            val firstCaller = snapShot.getString(CALLER_TOKEN_1)
            val secondCaller = snapShot.getString(CALLER_TOKEN_2)

            this.launch {
                myToken.mapNotNull { token ->

                    var map = hashMapOf<String, Any?>()
                    if (firstCaller?.equals(token) == true) {
                        map = hashMapOf<String, Any?>(
                            Pair(CALLER_SESSION_1, false),
                            Pair(CALLER_SESSION_2, false),
                            Pair(CALLER_RINGING_1, false),
                            Pair(CALLER_RINGING_2, false),
                            Pair(CALL_STATUS_1, CallStatus.DECLINED),
                            Pair(CALL_STATUS_2, CallStatus.NONE),
                            Pair(INCOMING_CALL_1, CallStatus.NONE),
                            Pair(INCOMING_CALL_2, CallStatus.NONE)
                        )

                        snapShot.reference.update(map).addOnSuccessListener {
                            trySend(Result.IncomingCallDeclined)
                        }
                    }

                    if (secondCaller?.equals(token) == true) {
                        map = hashMapOf<String, Any?>(
                            Pair(CALLER_SESSION_1, false),
                            Pair(CALLER_SESSION_2, false),
                            Pair(CALLER_RINGING_1, false),
                            Pair(CALLER_RINGING_2, false),
                            Pair(CALL_STATUS_1, CallStatus.NONE),
                            Pair(CALL_STATUS_2, CallStatus.DECLINED),
                            Pair(INCOMING_CALL_1, CallStatus.NONE),
                            Pair(INCOMING_CALL_2, CallStatus.NONE)
                        )

                        snapShot.reference.update(map).addOnSuccessListener {
                            trySend(Result.IncomingCallDeclined)
                        }.addOnFailureListener {
                            trySend(Result.Error("Failed to Decline"))

                        }
                    }


                }.collect()
            }


        }.addOnFailureListener {

            trySend(Result.Error("Could not verify: ${it.message}"))
        }

        awaitClose()
    }

    override suspend fun setCallInSession(): Flow<Result> {
        TODO("Not yet implemented")
    }


    override suspend fun updateLastUserToken() = channelFlow<Result> {

        send(Result.Processing("Verifying call availability"))

        firestore.collection(CALLERS).document(ACTIVE_CALL).get().addOnSuccessListener { snapShot ->

            val firstSession = snapShot.getBoolean(CALLER_SESSION_1)
            val secondSession = snapShot.getBoolean(CALLER_SESSION_2)
            val firstCaller = snapShot.getString(CALLER_TOKEN_1)
            val secondCaller = snapShot.getString(CALLER_TOKEN_2)

            if (firstSession == true || secondSession == true) {
                trySend(Result.Processing("Another call in session"))
            } else {

                this.launch {
                    myToken.mapNotNull { token ->

                        val map = hashMapOf<String, Any?>(
                            Pair(CALLER_SESSION_1, false),
                            Pair(CALLER_SESSION_2, false),
                            Pair(CALLER_RINGING_1, false),
                            Pair(CALLER_RINGING_2, false),
                            Pair(CALLER_TOKEN_1, token),
                            Pair(CALLER_TOKEN_2, secondCaller),
                            Pair(CALL_STATUS_1, CallStatus.NONE),
                            Pair(CALL_STATUS_2, CallStatus.NONE),
                            Pair(INCOMING_CALL_1, CallStatus.NONE),
                            Pair(INCOMING_CALL_2, CallStatus.NONE)
                        )

                        snapShot.reference.update(map).addOnSuccessListener {
                            trySend(Result.Processing("Updating Token"))
                        }

                    }.collect()
                }

            }

        }.addOnFailureListener {
            trySend(Result.Error("Could not verify: ${it.message}"))
        }

        awaitClose()
    }
}