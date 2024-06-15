package com.jwt.auth.digital.wallet.exception

import com.jwt.auth.digital.wallet.dtos.response.FormattedErrorResponse
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
}