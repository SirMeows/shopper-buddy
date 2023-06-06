package com.he.engelund.entity;

import jakarta.validation.constraints.Email;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String username; // Optional, application specific?

    @OneToOne(mappedBy = "user")
    private ExternalAuthenticatedUser externalAuthenticatedUser;

    @URL
    @Column(length = 500)
    private String imgUrl;

    @OneToMany(mappedBy = "user")
    private Set<ListUserRole> listUserRoles = new HashSet<>();
}
