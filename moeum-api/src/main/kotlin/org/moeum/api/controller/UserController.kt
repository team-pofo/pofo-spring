package org.moeum.api.controller

import org.moeum.api.dto.RegisterRequest
import org.moeum.api.service.UserService
import org.moeum.domain.user.User
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping("")
    fun register(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody registerRequest: RegisterRequest
    ): ResponseEntity<*> {
        val user = userService.createUser(registerRequest)
        return ResponseEntity.ok(user)
    }

    @GetMapping("/me")
    fun getMe(
        @AuthenticationPrincipal user: User,
    ): ResponseEntity<*> {
        return ResponseEntity.ok(user)
    }
}