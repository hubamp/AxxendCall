package com.example.axxendvideocall.make_call.domain.use_case

import com.example.axxendvideocall.make_call.domain.model.CallRepository

class StartCall(private val callRepository: CallRepository) {

    suspend operator fun invoke() = callRepository.startCall()
}