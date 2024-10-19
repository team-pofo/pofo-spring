package org.moeum.api.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["org.moeum.domain"])
@EnableJpaRepositories(basePackages = ["org.moeum.domain"])
class JpaConfig {
}