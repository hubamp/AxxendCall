package com.example.axxendvideocall.make_call.presentation

data class MakeCallResource(
    val message:String? = null,
    val declineButtonText:String = "",
    val acceptButtonText:String = "Call",
    val inComingCallRinging:Boolean = false,
    val inComingCallDeclined: Boolean = false,
    val inComingCallAccepted: Boolean = false,
    val isCallAvailable:Boolean = false,
    val outGoingCallRinging: Boolean = false,
    val outGoingCallDeclined:Boolean = false,
    val outGoingCallAccepted:Boolean = false,
    val processError: Boolean = false
)