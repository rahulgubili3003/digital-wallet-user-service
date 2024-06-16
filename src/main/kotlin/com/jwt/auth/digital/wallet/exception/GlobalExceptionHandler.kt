package com.jwt.auth.digital.wallet.exception

import com.jwt.auth.digital.wallet.constants.ErrorCodes.ERROR_001
import com.jwt.auth.digital.wallet.constants.ErrorCodes.ERROR_002
import com.jwt.auth.digital.wallet.constants.ErrorCodes.ERROR_003
import com.jwt.auth.digital.wallet.constants.ErrorMessages
import com.jwt.auth.digital.wallet.dtos.response.FormattedErrorResponse
import com.jwt.auth.digital.wallet.dtos.response.JwtVerifyFailureErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestExceptions(ex: UsernameAlreadyExistsException): ResponseEntity<FormattedErrorResponse> {
        val errorResponse = FormattedErrorResponse(
            ERROR_001,
            ErrorMessages.BAD_REQUEST,
            ex.message?: "Username Already Exists"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleJwtExceptions(ex: JwtVerificationFailedException): ResponseEntity<JwtVerifyFailureErrorResponse> {
        val errorResponse = JwtVerifyFailureErrorResponse(
            errorCode = ERROR_002,
            errorMessage = ErrorMessages.BAD_REQUEST,
            cause = ex.message?: "Jwt Verification Failed"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleJwtSignException(ex: JwtSigningKeyException): ResponseEntity<FormattedErrorResponse> {
        val errorResponse = FormattedErrorResponse(
            errorMessage = ErrorMessages.BAD_REQUEST,
            errorCode = ERROR_003,
            cause = ex.message?: "Jwt Signing Key is Weak"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}