package com.he.engelund.entity;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ExternalAuthenticatedUser {

    @Id
    @Column(nullable = false, unique = true)
    private String providerUserId;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne
    private User user;
}
