package com.jwt.auth.digital.wallet.service

import com.jwt.auth.digital.wallet.dtos.request.AuthSignUpRequest
import com.jwt.auth.digital.wallet.exception.UsernameAlreadyExistsException
import com.jwt.auth.digital.wallet.repository.UsersRepository
import com.jwt.auth.digital.wallet.util.mappers.DtoToEntityMapper
import jakarta.validation.Valid
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usersRepository: UsersRepository,
    private val bCrypt: BCryptPasswordEncoder
) {

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
}