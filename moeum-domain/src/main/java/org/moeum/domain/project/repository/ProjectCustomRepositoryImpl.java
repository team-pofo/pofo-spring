package org.moeum.domain.project.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.moeum.domain.project.Project;
import org.moeum.domain.project.ProjectList;
import org.moeum.domain.project.QProject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectCustomRepositoryImpl implements ProjectCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public ProjectList searchProjecWithCursor(int size, Long cursor) {
        QProject project = QProject.project;
        BooleanExpression predicate = null;

        if (cursor != null) {
            predicate = project.id.lt(cursor);
        }

        List<Project> projects = queryFactory.selectFrom(project)
                .where(predicate)
                .orderBy(project.id.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = projects.size() > size;

        if (hasNext) {
            projects.remove(size);
        }

        return new ProjectList(projects, hasNext, 0);
    }
}
