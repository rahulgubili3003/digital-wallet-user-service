package com.jwt.auth.digital.wallet.util.mappers

import com.jwt.auth.digital.wallet.dtos.request.AuthSignUpRequest
import com.jwt.auth.digital.wallet.entity.Users

class DtoToEntityMapper {

    companion object {
        fun dtoToEntityAuthSignUpRequest(authSignUpRequest: AuthSignUpRequest): Users {
            return Users(
                username = authSignUpRequest.username,
                password = authSignUpRequest.password,
                firstName = authSignUpRequest.firstName,
                lastName = authSignUpRequest.lastName,
                email = authSignUpRequest.email
            )
        }
    }
}