package org.moeum.domain.project.repository;

import org.moeum.domain.project.ProjectList;

public interface ProjectCustomRepository {
    ProjectList searchProjecWithCursor(int size, Long cursor);
}
