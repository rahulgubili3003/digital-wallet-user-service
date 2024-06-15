package com.jwt.auth.digital.wallet.service

import com.jwt.auth.digital.wallet.dtos.request.AuthLoginRequest
import com.jwt.auth.digital.wallet.dtos.request.AuthSignUpRequest
import com.jwt.auth.digital.wallet.exception.UsernameAlreadyExistsException
import com.jwt.auth.digital.wallet.exception.UsernameOrPasswordInvalidException
import com.jwt.auth.digital.wallet.repository.UsersRepository
import com.jwt.auth.digital.wallet.util.jwt.JwtUtil
import com.jwt.auth.digital.wallet.util.mappers.DtoToEntityMapper
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usersRepository: UsersRepository,
    private val bCrypt: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil
) {

    @Transactional
    fun registerUser(@Valid authSignUpRequest: AuthSignUpRequest): String {
        val username = authSignUpRequest.username
        val password = authSignUpRequest.password

        val isUsernameAlreadyExists = usersRepository.existsByUsername(username)

        if (isUsernameAlreadyExists) {
            throw UsernameAlreadyExistsException("Username already exists: $username")
        }
        val user = DtoToEntityMapper.dtoToEntityAuthSignUpRequest(authSignUpRequest)
        user.password = bCrypt.encode(password)
        val savedEntity = usersRepository.save(user)
        return savedEntity.username
    }

    fun login(@Valid authLoginRequest: AuthLoginRequest): String {
        return authenticate(authLoginRequest.username, authLoginRequest.password)
    }

    private fun authenticate(username: String, password: String): String {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    username,
                    password
                )
            )
        } catch (e: AuthenticationException) {
            throw UsernameOrPasswordInvalidException("Authentication Failed")
        }
        return jwtUtil.generateToken(username).trim('"')
    }


}