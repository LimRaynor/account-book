package com.tickle_moa.backend.user.command.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @Builder
    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = (role != null) ? role : UserRole.USER;
        this.createdAt = LocalDateTime.now();
    }

    /* 암호화된 비밀번호를 설정하는 메서드 */
    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    /* 권한 변경 메서드 */
    public void modifyRole(String role) {
        this.role = UserRole.valueOf(role);
    }
}
