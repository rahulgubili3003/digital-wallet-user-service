package com.jwt.auth.digital.wallet.service

import com.jwt.auth.digital.wallet.repository.UsersRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val repository: UsersRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = repository.findByUsername(username)
        // Check if the user is null
        if (user!= null) {
            // If the user is not null, proceed to create and return the User object
            return User(user.username, user.password, ArrayList())
        } else {
            // If the user is null, throw an exception
            throw UsernameNotFoundException("User not found with username: $username")
        }
    }
}