package org.moeum.domain.project.repository;

import jakarta.annotation.Nullable;
import org.moeum.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectCustomRepository {
    @Nullable
    Project findById(long id);
}
