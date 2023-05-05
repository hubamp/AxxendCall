package com.example.axxendvideocall.make_call.domain.use_case

import com.example.axxendvideocall.make_call.domain.model.CallRepository
import javax.inject.Inject

class UpdateLastUserToken @Inject constructor(private val repository: CallRepository) {

    suspend operator fun invoke() = repository.updateLastUserToken()
}