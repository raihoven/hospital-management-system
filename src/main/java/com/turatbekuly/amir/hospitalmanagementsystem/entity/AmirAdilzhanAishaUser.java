package com.turatbekuly.amir.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class AmirAdilzhanAishaUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не должно быть пустым")
    @Size(max = 50, message = "Имя пользователя не должно превышать 50 символов")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Фамилия пользователя не должна быть пустой")
    @Size(max = 50, message = "Фамилия пользователя не должна превышать 50 символов")
    @Column(nullable = false, length = 50)
    private String lastName;

    @Email(message = "Email должен быть корректным")
    @NotBlank(message = "Email не должен быть пустым")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AmirAdilzhanAishaRole role;

    public AmirAdilzhanAishaUser() {
    }

    public AmirAdilzhanAishaUser(String firstName, String lastName, String email, String password, AmirAdilzhanAishaRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AmirAdilzhanAishaRole getRole() {
        return role;
    }

    public void setRole(AmirAdilzhanAishaRole role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
