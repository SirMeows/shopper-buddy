package com.he.engelund.repository;

import com.he.engelund.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default Set<User> findAllSet() {
        return new HashSet<>(findAll());
    }

    User getUserByUsername(String username);

    User getUserByEmail(Email email);
}
