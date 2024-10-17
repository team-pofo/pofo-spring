package org.moeum.api.service

import org.moeum.api.dto.RegisterRequest
import org.moeum.common.error.CustomError
import org.moeum.common.error.ErrorType
import org.moeum.domain.user.User
import org.moeum.domain.user.UserRepository
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