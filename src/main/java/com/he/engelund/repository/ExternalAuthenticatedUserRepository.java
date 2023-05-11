package com.he.engelund.repository;

import com.he.engelund.entity.ExternalAuthenticatedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ExternalAuthenticatedUserRepository extends JpaRepository<ExternalAuthenticatedUser, UUID>  {

    default Set<ExternalAuthenticatedUser> getAllSet() { return new HashSet<>(findAll()); }

    ExternalAuthenticatedUser getExternalAuthenticatedUserByProvidedUserId(String providedUserId);

    ExternalAuthenticatedUser getExternalAuthenticatedUserByEmail(String email);
}
