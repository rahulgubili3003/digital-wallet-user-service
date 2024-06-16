package com.jwt.auth.digital.wallet.controller

import com.jwt.auth.digital.wallet.dtos.response.OkResponse
import com.jwt.auth.digital.wallet.exception.InvalidUsernameException
import com.jwt.auth.digital.wallet.service.CustomUserDetailsService
import com.jwt.auth.digital.wallet.util.jwt.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class JwtController(private val userDetailsService: CustomUserDetailsService) {

    @PostMapping("/validate-jwt")
    fun validateJwt(@RequestBody jwtToken: String): ResponseEntity<OkResponse> {
        val username: String = JwtUtil.extractUsername(jwtToken) ?: throw InvalidUsernameException("Username Invalid")
        val userDetails = userDetailsService.loadUserByUsername(username)
        val result = JwtUtil.validateToken(jwtToken, userDetails)
        return ResponseEntity.ok(OkResponse(data = result))
    }
}