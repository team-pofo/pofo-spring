package org.pofo.api.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.pofo.domain.project.Project
import org.pofo.domain.project.ProjectCategory
import org.pofo.domain.project.repository.ProjectRepository
import org.pofo.domain.user.User
import org.pofo.domain.user.UserRepository
import org.pofo.domain.user.UserRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureGraphQlTester
@Transactional
class ProjectControllerTest {
    @Autowired
    private lateinit var graphQlTester: GraphQlTester

    @Autowired
    private lateinit var projectRepository: ProjectRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun getProjectById() {
        // given
        val savedProject =
            projectRepository.save(
                Project
                    .builder()
                    .title("Luminia")
                    .Bio("줄거리로 애니를 찾아주고, 애니를 검색하고 리뷰를 달 수 있는 플랫폼입니다.")
                    .content("취업좀 시켜줘라")
                    .category(ProjectCategory.CATEGORY_A)
                    .isApproved(false)
                    .build(),
            )

        // when & then
        graphQlTester
            .documentName("getProjectById")
            .variable("projectId", savedProject.id)
            .execute()
            .path("projectById.id")
            .entity(Long::class.java)
            .isEqualTo(savedProject.id)
            .path("projectById.title")
            .entity(String::class.java)
            .isEqualTo("Luminia")
    }

    @Test
    fun getAllProjectsByPagination() {
        // given
        projectRepository.saveAll(
            listOf(
                Project
                    .builder()
                    .title("Luminia Project 1")
                    .Bio("첫 번째 프로젝트")
                    .content("프로젝트 내용 1")
                    .category(ProjectCategory.CATEGORY_A)
                    .isApproved(true)
                    .build(),
                Project
                    .builder()
                    .title("Luminia Project 2")
                    .Bio("두 번째 프로젝트")
                    .content("프로젝트 내용 2")
                    .category(ProjectCategory.CATEGORY_B)
                    .isApproved(true)
                    .build(),
                Project
                    .builder()
                    .title("Luminia Project 3")
                    .Bio("세 번째 프로젝트")
                    .content("프로젝트 내용 3")
                    .category(ProjectCategory.CATEGORY_C)
                    .isApproved(true)
                    .build(),
            ),
        )

        val allProjects = projectRepository.findAll()
        assertThat(allProjects.size).isGreaterThanOrEqualTo(3)

        // when & then
        graphQlTester
            .documentName("getAllProjectsByPagination")
            .variable("cursor", 3)
            .variable("size", 2)
            .execute()
            .path("getAllProjectsByPagination.projects[*].title")
            .entityList(String::class.java)
            .containsExactly("Luminia Project 2", "Luminia Project 1")
            .path("getAllProjectsByPagination.hasNext")
            .entity(Boolean::class.java)
            .isEqualTo(false)
            .path("getAllProjectsByPagination.projectCount")
            .entity(Int::class.java)
            .isEqualTo(2)
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = ["USER"])
    fun createProjectSuccess() {
        // given
        val mockUser =
            userRepository.save(
                User
                    .builder()
                    .email("testuser@example.com")
                    .password("password")
                    .role(UserRole.ROLE_USER)
                    .build(),
            )

        val variables =
            mapOf(
                "title" to "새로운 프로젝트",
                "bio" to "프로젝트 설명",
                "urls" to listOf("https://example.com"),
                "imageUrls" to listOf("https://example.com/image.png"),
                "content" to "프로젝트 내용",
                "category" to "CATEGORY_A",
            )

        graphQlTester
            .documentName("createProject")
            .variable("title", variables["title"])
            .variable("bio", variables["bio"])
            .variable("urls", variables["urls"])
            .variable("imageUrls", variables["imageUrls"])
            .variable("content", variables["content"])
            .variable("category", variables["category"])
            .execute()
            .path("createProject.title")
            .entity(String::class.java)
            .isEqualTo("새로운 프로젝트")
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = ["USER"])
    fun createProjectFail() {
        // given
        val variables =
            mapOf(
                "title" to "새로운 프로젝트",
                "bio" to "프로젝트 설명",
                "urls" to listOf("https://example.com"),
                "imageUrls" to listOf("https://example.com/image.png"),
                "content" to "프로젝트 내용",
                "category" to "CATEGORY_A",
            )

        graphQlTester
            .documentName("createProject")
            .variable("title", variables["title"])
            .variable("bio", variables["bio"])
            .variable("urls", variables["urls"])
            .variable("imageUrls", variables["imageUrls"])
            .variable("content", variables["content"])
            .variable("category", variables["category"])
            .execute()
            .errors()
            .satisfy { errors ->
                assertThat(errors).hasSize(1)
                assertThat(errors[0].message).contains("유저를 찾을 수 없습니다.")
            }
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = ["USER"])
    fun updateProject() {
        // given
        val mockUser =
            userRepository.save(
                User
                    .builder()
                    .email("testuser@example.com")
                    .password("password")
                    .role(UserRole.ROLE_USER)
                    .build(),
            )

        val savedProject =
            projectRepository.save(
                Project
                    .builder()
                    .title("Old Luminia Project")
                    .Bio("예전 설명")
                    .content("이전 프로젝트 내용")
                    .category(ProjectCategory.CATEGORY_B)
                    .isApproved(true)
                    .author(mockUser)
                    .build(),
            )

        val variables =
            mapOf(
                "projectId" to savedProject.id.toString(),
                "title" to "새로운 프로젝트",
                "bio" to "프로젝트 설명",
                "urls" to listOf("https://example.com"),
                "imageUrls" to listOf("https://example.com/image.png"),
                "content" to "프로젝트 내용",
                "category" to "CATEGORY_A",
            )

        graphQlTester
            .documentName("updateProject")
            .variable("projectId", variables["projectId"])
            .variable("title", variables["title"])
            .variable("bio", variables["bio"])
            .variable("urls", variables["urls"])
            .variable("imageUrls", variables["imageUrls"])
            .variable("content", variables["content"])
            .variable("category", variables["category"])
            .execute()
            .path("updateProject.title")
            .entity(String::class.java)
            .isEqualTo("새로운 프로젝트")
    }
}
