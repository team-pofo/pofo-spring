package com.kookmin.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    @Nullable User findByEmail(String email);
}