package org.pofo.api.dto

data class LoginRequest(
    val email: String,
    val password: String,
)
