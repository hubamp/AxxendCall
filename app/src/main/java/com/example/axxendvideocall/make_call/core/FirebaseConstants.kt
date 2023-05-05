package com.example.axxendvideocall.make_call.core

object FirebaseConstants {
    val CALLERS = "callers"
    val ACTIVE_CALL = "active_call"
    val CALLER_SESSION_1 ="caller1_in_session"
    val CALLER_SESSION_2 ="caller2_in_session"
    val CALLER_TOKEN_1 ="caller1_token"
    val CALLER_TOKEN_2 ="caller2_token"
    val CALLER_RINGING_2 ="caller2_rings"
    val CALLER_RINGING_1 ="caller1_rings"
    val CALL_STATUS_1 = "caller1_call_status"
    val CALL_STATUS_2 = "caller2_call_status"
    val INCOMING_CALL_1 ="caller1_incoming_call_from"
    val INCOMING_CALL_2 ="caller2_incoming_call_from"
}

enum class CallStatus{
    NONE,
    ACCEPTED,
    DECLINED,
    ENDED
}