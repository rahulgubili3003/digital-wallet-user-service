package com.jwt.auth.digital.wallet.exception

import com.jwt.auth.digital.wallet.dtos.response.FormattedErrorResponse
import com.jwt.auth.digital.wallet.dtos.response.JwtVerifyFailureErrorResponse
import com.jwt.auth.digital.wallet.dtos.response.OkResponse
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
            "001",
            "Bad Request",
            ex.message?: "Username Already Exists"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleJwtExceptions(ex: JwtVerificationFailedException): ResponseEntity<JwtVerifyFailureErrorResponse> {
        val errorResponse = JwtVerifyFailureErrorResponse(
            errorCode = "002",
            errorMessage = "Bad Request",
            cause = ex.message?: "Jwt Verification Failed"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleJwtSignException(ex: JwtSigningKeyException): ResponseEntity<FormattedErrorResponse> {
        val errorResponse = FormattedErrorResponse(
            errorMessage = "Bad Request",
            errorCode = "003",
            cause = ex.message?: "Jwt Signing Key is Weak"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

}