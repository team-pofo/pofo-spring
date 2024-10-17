package com.kookmin.api.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class CustomAuthenticationToken(
    private val principal: Any,
    private val credentials: Any?,
    authorities: List<GrantedAuthority> = emptyList()
) : AbstractAuthenticationToken(authorities) {
    init {
        if (authorities.isNotEmpty()) {
            super.setAuthenticated(true)
        }
    }

    override fun getCredentials(): Any {
        return credentials ?: ""
    }

    override fun getPrincipal(): Any {
        return principal
    }
}
