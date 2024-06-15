package com.jwt.auth.digital.wallet.util.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Component
class JwtUtil {
    val secret: String = "eW91ckxvbmdlclNlY3JldFBsYWNlaG9sZGVyc2RkZmVlZmV3d3d3d2RxZGZxd2RxNDM1MzU0dDY0NnF3MjUxNXdkc3g="

    fun extractUsername(token: String?): String? {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(username: String): String {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, username)
    }

    private fun createToken(claims: Map<String, Any>, userName: String): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userName)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 30))
            .signWith(getSignKey(), SignatureAlgorithm.HS256).compact()
    }

    private fun getSignKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }
}