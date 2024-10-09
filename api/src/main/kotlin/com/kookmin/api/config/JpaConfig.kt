package com.kookmin.api.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["com.kookmin.domain"])
@EnableJpaRepositories(basePackages = ["com.kookmin.domain"])
class JpaConfig {
}