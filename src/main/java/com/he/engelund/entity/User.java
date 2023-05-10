package com.he.engelund.entity;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Email
    @Column(nullable = false, unique = true)
    private String email;


    @OneToOne(mappedBy = "user")
    private ExternalAuthenticatedUser externalAuthenticatedUser;

    @Enumerated(EnumType.STRING) // TODO: Consider whether provider type is needed
    private Provider provider;

    private boolean enabled;

    @URL
    @Column(length = 500)
    private String imgUrl;
}
