package org.pofo.api.service

import org.pofo.common.error.CustomError
import org.pofo.common.error.ErrorType
import org.pofo.domain.project.Project
import org.pofo.domain.project.ProjectCategory
import org.pofo.domain.project.ProjectList
import org.pofo.domain.project.repository.ProjectRepository
import org.pofo.domain.user.User
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