package com.jwt.auth.digital.wallet.controller

import com.jwt.auth.digital.wallet.dtos.request.AuthLoginRequest
import com.jwt.auth.digital.wallet.dtos.request.AuthSignUpRequest
import com.jwt.auth.digital.wallet.dtos.response.OkResponse
import com.jwt.auth.digital.wallet.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun registerUsers(@Valid @RequestBody authSignUpRequest: AuthSignUpRequest): ResponseEntity<OkResponse> {
        val response = authService.registerUser(authSignUpRequest)
        return ResponseEntity.ok(OkResponse(data = response))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody authLoginRequest: AuthLoginRequest): ResponseEntity<OkResponse> {
        val response = authService.login(authLoginRequest)
        return ResponseEntity.ok(OkResponse(data = response))
    }
}