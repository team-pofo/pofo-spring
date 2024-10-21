package org.pofo.api.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val customAuthenticationSuccessHandler: CustomAuthenticationSuccessHandler,
    private val customAuthenticationFailureHandler: CustomAuthenticationFailureHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .headers { headerConfigs -> headerConfigs.frameOptions { it.disable() } }
            .authorizeHttpRequests {
                it.requestMatchers(PathRequest.toH2Console()).permitAll()
                    .requestMatchers("/user").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                customAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun customAuthenticationFilter(): CustomAuthenticationFilter {
        val requestMatcher = AntPathRequestMatcher("/auth/login", HttpMethod.POST.name())
        val filter = CustomAuthenticationFilter(requestMatcher)
        filter.setAuthenticationManager(authenticationConfiguration.authenticationManager)
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler)
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler)
        filter.setSecurityContextRepository(HttpSessionSecurityContextRepository())
        return filter
    }
}