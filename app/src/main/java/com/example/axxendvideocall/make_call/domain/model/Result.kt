package com.example.axxendvideocall.make_call.domain.model


sealed class Result(val message: String?) {

    data class Processing(val progressMsg: String) : Result(progressMsg)
    data class CallReady(val ready: Boolean) : Result(null)
    object CallEstablished : Result(null)
    data class OutgoingCall(val token: String) : Result(token)
    data class IncomingCall(val token: String) : Result(token)
    object IncomingCallDeclined : Result(null)
    object OutgoingCallDeclined : Result(null)
    object NoInternet : Result(null)
    object Success:Result(null)
    data class Error(val errorMessage: String?) : Result(errorMessage)


    /*
    * The States
Processing - message, rotating
CallReady - activate decline and acceptButton, clear message.
CallNotReadyState - Deactivate decline and accept button, activate message.
Ringing - activate decline and accept button and message put dark shadow behind
CallDeclined - message
CallAccepted - message
ErrorState - message
NoInternetState - Deactivate all and activate refresh*/
}