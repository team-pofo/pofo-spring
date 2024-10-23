package org.pofo.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "\"user\"")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NonNull
    private String email;

    @JsonIgnore
    @Column
    @NonNull
    private String password;

    @Column
    @NonNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    protected User(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
        this.role = UserRole.ROLE_USER;
    }

    public static User create(@NonNull String email, @NonNull String password) {
        return new User(email, password);
    }
}