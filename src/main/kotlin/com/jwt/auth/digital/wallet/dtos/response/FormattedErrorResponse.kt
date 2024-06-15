package com.jwt.auth.digital.wallet.dtos.response

data class FormattedErrorResponse(
    val errorCode: String,
    val errorMessage: String,
    val cause: String
)
