package com.bepsik.moneymotivator.entity;

import com.bepsik.moneymotivator.enumeration.AuthProvider;
import com.bepsik.moneymotivator.enumeration.Role;
import com.bepsik.moneymotivator.service.UserRolesConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "t_user")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_user_id_seq")
    @SequenceGenerator(name = "t_user_id_seq", sequenceName = "t_user_id_seq", allocationSize = 1)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @Column(insertable = false, updatable = false)
    private LocalDateTime created;

    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = UserRolesConverter.class)
    private List<Role> roles;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

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
        return true;
    }
}
