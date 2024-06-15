package com.jwt.auth.digital.wallet.repository

import com.jwt.auth.digital.wallet.entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository: JpaRepository<Users, Long> {

    fun existsByUsername(username: String): Boolean
}