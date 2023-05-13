package com.he.engelund.repository;

import com.he.engelund.entity.ExternalAuthenticatedUser;
import com.he.engelund.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    default Set<User> findAllSet() {
        return new HashSet<>(findAll());
    }

    User getUserByExternalAuthenticatedUserId(String externalId);

    boolean existsByExternalAuthenticatedUserId(String externalId);
}
