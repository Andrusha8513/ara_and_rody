package ru.website_ara_and_rody.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @NotBlank(message = "Почта не может быть пустой!")
    @Size(min = 6 , max = 100 , message = "Длина почта слишком большая или маленькая")
    private String email;

    @NotBlank(message = "Имя не может быть пустым!")
    @Size(min = 1 , max = 30 , message = "Длина имени слишком большая или маленькая")
    private String name;

    @NotBlank(message = "Пароль не может быть пустым!")
    @Size(min = 8  , message = "Пароль не может быть короче 8 символов и длинней 50")
    private String password;

    private String confirmationCode;
    private Boolean enable = false;

    //подумать над настрокой , если пользователь измениться или кдалиться , чтобы посты не удалились
    @OneToMany(mappedBy = "users" , fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "users" , fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();




    @ElementCollection(targetClass = Role.class , fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role" , joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private String refreshToken;
}
