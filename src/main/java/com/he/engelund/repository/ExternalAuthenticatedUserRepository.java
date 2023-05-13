package com.he.engelund.repository;

import com.he.engelund.entity.ExternalAuthenticatedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ExternalAuthenticatedUserRepository extends JpaRepository<ExternalAuthenticatedUser, UUID>  {

    ExternalAuthenticatedUser getExternalAuthenticatedUserByProvidedUserId(String providedUserId);
}
