package org.moeum.domain.project;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.moeum.domain.converter.StringListConverter;
import org.moeum.domain.user.User;

import java.util.List;

@Entity
@Table(name = "project")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String Bio; // 한줄 소개

    @Convert(converter = StringListConverter.class)
    private List<String> urls; // 유저가 설정한 url list ex) github, npm 등등

    @Convert(converter = StringListConverter.class)
    private List<String> imageUrls;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column()
    private Boolean isApproved; // 모음팀 측에서 인증됬는지 (타 앱 연동을 통해)

    @Column()
    @Enumerated(EnumType.STRING)
    private ProjectCategory category; // 프로젝트 유형

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

}
