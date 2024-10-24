package org.pofo.api.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["org.pofo.domain"])
@EnableJpaRepositories(basePackages = ["org.pofo.domain"])
class JpaConfig(
    @PersistenceContext
    val entityManager: EntityManager,
) {
    @Bean
    fun queryFactory(): JPAQueryFactory = JPAQueryFactory(entityManager)
}
