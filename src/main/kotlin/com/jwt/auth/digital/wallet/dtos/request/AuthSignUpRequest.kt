package com.jwt.auth.digital.wallet.dtos.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class AuthSignUpRequest(
    @field:Size(message = "Username length should lie between 7 and 15 characters", max = 15, min = 7)
    val username: String,
    @field:Size(message = "Password length should lie between 7 and 15 characters", max = 15, min = 7)
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String
)
