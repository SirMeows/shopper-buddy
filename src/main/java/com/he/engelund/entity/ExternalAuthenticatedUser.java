package com.he.engelund.entity;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ExternalAuthenticatedUser {
    //The id is a String because input data type is unknown

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String providedUserId;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "saved_user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING) // TODO: Consider whether provider type is needed
    private Provider provider;
}
