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
}
