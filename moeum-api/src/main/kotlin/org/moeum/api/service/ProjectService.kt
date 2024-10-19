package org.moeum.api.service

import org.moeum.common.error.CustomError
import org.moeum.common.error.ErrorType
import org.moeum.domain.project.Project
import org.moeum.domain.project.ProjectCategory
import org.moeum.domain.project.ProjectList
import org.moeum.domain.project.repository.ProjectRepository
import org.moeum.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProjectService(
    private val projectRepository: ProjectRepository
) {
    fun findProjectById(projectId: Long): Project {
        return projectRepository.findById(projectId) ?: throw CustomError(ErrorType.PROJECT_NOT_FOUND)
    }

    fun getAllProjectsByPagination(size: Int, cursor: Long): ProjectList {
        return projectRepository.searchProjecWithCursor(size, cursor)
    }

    fun createProject(
        title: String,
        bio: String?,
        urls: List<String>?,
        imageUrls: List<String>?,
        content: String,
        category: ProjectCategory,
        user: User
    ): Project {
        val project = Project.builder()
            .title(title)
            .Bio(bio)
            .urls(urls)
            .imageUrls(imageUrls)
            .content(content)
            .category(category)
            .author(user)
            .build()

        return projectRepository.save(project)
    }

    fun updateProject(
        projectId: Long,
        title: String?,
        bio: String?,
        urls: List<String>?,
        imageUrls: List<String>?,
        content: String?,
        category: ProjectCategory?
    ): Project {
        var project = projectRepository.findById(projectId) ?: throw CustomError(ErrorType.PROJECT_NOT_FOUND)
        project = project.update(title, bio, urls, imageUrls, content, category)

        return projectRepository.save(project)
    }
}