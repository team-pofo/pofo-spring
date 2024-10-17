package com.kookmin.api.service

import com.kookmin.api.dto.RegisterRequest
import com.kookmin.common.error.CustomError
import com.kookmin.common.error.ErrorType
import com.kookmin.domain.user.User
import com.kookmin.domain.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun createUser(registerRequest: RegisterRequest): User {
        val email = registerRequest.email
        if (userRepository.existsByEmail(email)) {
            throw CustomError(ErrorType.USER_EXISTS)
        }
        val encodedPassword = passwordEncoder.encode(registerRequest.password)
        val user = User.create(email, encodedPassword)
        return userRepository.save(user)
    }

    fun fetchUserByEmail(email: String): User {
        val user = userRepository.findByEmail(email) ?: throw CustomError(ErrorType.USER_NOT_FOUND)
        return user
    }
}