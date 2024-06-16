package com.jwt.auth.digital.wallet.dtos.response

data class JwtVerifyFailureErrorResponse(
    val data: Boolean = false,
    val ok: Boolean = false,
    val errorCode: String,
    val errorMessage: String,
    val cause: String
)
