package com.jwt.auth.digital.wallet.dtos.request

import jakarta.validation.constraints.Size

data class AuthLoginRequest(
    @field:Size(message = "Username Length Invalid", max = 15, min = 7)
    val username: String,
    @field:Size(message = "Password Length invalid", max = 15, min = 7)
    val password: String
)
