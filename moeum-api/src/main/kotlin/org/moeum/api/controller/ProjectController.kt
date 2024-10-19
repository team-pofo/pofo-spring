package org.moeum.api.controller

import org.moeum.api.service.ProjectService
import org.moeum.domain.project.Project
import org.moeum.domain.project.ProjectCategory
import org.moeum.domain.project.ProjectList
import org.moeum.domain.user.User
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller

@Controller
class ProjectController(
    private val projectService: ProjectService
) {

    @QueryMapping
    fun projectById(@Argument projectId: Long): Project? {
        return projectService.findProjectById(projectId)
    }

    @QueryMapping
    fun getAllProjectsByPagination(@Argument cursor: Long, @Argument size: Int): ProjectList {
        return projectService.getAllProjectsByPagination(size, cursor)
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    fun createProject(
        @Argument title: String,
        @Argument bio: String?,
        @Argument urls: List<String>?,
        @Argument imageUrls: List<String>?,
        @Argument content: String,
        @Argument category: ProjectCategory,
        @AuthenticationPrincipal user: User
    ): Project {
        return projectService.createProject(title, bio, urls, imageUrls, content, category, user)
    }

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    fun updateProject(
        @Argument projectId: Long,
        @Argument title: String?,
        @Argument bio: String?,
        @Argument urls: List<String>?,
        @Argument imageUrls: List<String>?,
        @Argument content: String?,
        @Argument category: ProjectCategory?,
        @AuthenticationPrincipal user: User
    ): Project {
        return projectService.updateProject(projectId, title, bio, urls, imageUrls, content, category)
    }

}