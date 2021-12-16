package com.runetopic.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

/**
 * @author Jordan Abraham
 */
fun loginToken(username: String): String = JWT.create()
    .withClaim("username", username)
    .withExpiresAt(Date(System.currentTimeMillis() + 604_800_000)) // 7 Days
    .sign(Algorithm.HMAC256(System.getenv("JWT_SECRET")))
