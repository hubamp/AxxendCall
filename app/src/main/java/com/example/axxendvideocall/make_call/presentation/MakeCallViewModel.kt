package com.example.axxendvideocall.make_call.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.axxendvideocall.make_call.domain.use_case.StartCall
import com.example.axxendvideocall.make_call.domain.model.Result
import com.example.axxendvideocall.make_call.domain.use_case.UpdateLastUserToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MakeCallViewModel @Inject constructor(
    val startCall: StartCall,
    val updateLastUserToken: UpdateLastUserToken
) : ViewModel() {

    var makeCallState = MutableStateFlow(MakeCallResource())
        private set


    init {
//        makeCall()
        checkCallAvailability()
    }

    fun checkCallAvailability() {
        viewModelScope.launch {
            updateLastUserToken.invoke().map { result ->

                when (result) {
                    Result.CallEstablished -> {}
                    is Result.CallReady -> {
                        makeCallState.update {
                            it.copy(
                                message = result.message,
                                isCallAvailable = result.ready
                            )
                        }

                    }
                    is Result.Error -> {
                        makeCallState.update {
                            it.copy(
                                message = result.errorMessage,
                                isCallAvailable = false

                            )
                        }
                    }
                    is Result.IncomingCall -> {

                    }
                    Result.IncomingCallDeclined -> {

                    }
                    Result.NoInternet -> {

                    }
                    is Result.OutgoingCall -> {

                    }
                    Result.OutgoingCallDeclined -> {

                    }
                    is Result.Processing -> {
                        makeCallState.update {
                            it.copy(
                                message = result.progressMsg
                            )
                        }
                    }

                    Result.Success -> {
                        makeCallState.update {
                            it.copy(
                                message = result.message
                            )
                        }
                    }
                }

            }.collect()

        }
    }


    fun makeCall() {

        viewModelScope.launch {

            startCall.invoke().map { result ->

                when (result) {
                    is Result.OutgoingCall -> {
                        makeCallState.update {
                            it.copy(
                                message = result.message,
                                outGoingCallRinging = true
                            )

                        }
                    }
                    is Result.OutgoingCallDeclined -> {
                        makeCallState.update {
                            it.copy(
                                message = result.message,
                                outGoingCallRinging = false,
                                outGoingCallDeclined = true,
                            )

                        }
                    }

                    is Result.Processing -> {
                        makeCallState.update {
                            it.copy(
                                message = result.progressMsg
                            )

                        }
                    }

                    else -> {}
                }
            }.collect()
        }

    }


    fun listenToIncomingCall() {

    }


}

enum class BottomCallStates{

}

sealed interface MakeCallState {
    data class Processing(val progressMsg: String) : MakeCallState
    data class CallReady(val ready: Boolean) : MakeCallState
    object CallEstablished : MakeCallState
    data class OutgoingCall(val token: String) : MakeCallState
    data class IncomingCall(val token: String) : MakeCallState
    object IncomingCallDeclined : MakeCallState
    object OutgoingCallDeclined : MakeCallState
    object NoInternet : MakeCallState
    data class Error(val errorMessage: String?) : MakeCallState
}