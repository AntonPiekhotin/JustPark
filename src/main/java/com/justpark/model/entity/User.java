package com.justpark.model.entity;

import com.justpark.model.constant.AccountStatus;
import com.justpark.model.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class that represents the user in the database.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(max = 50)
    @Email(regexp = ".+@.+\\..+", message = "Email should be valid")
    @NotBlank(message = "Email should not be null or blank")
    String email;

    @NotBlank(message = "Password should not be null or blank")
    String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    Set<Role> roles = new HashSet<>();

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "registration_date")
    LocalDate registrationDate;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(name = "country")
    String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    AccountStatus accountStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
        return !accountStatus.equals(AccountStatus.BANNED);
    }

}
