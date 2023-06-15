package com.he.engelund.repository;

import com.he.engelund.entity.Role;
import com.he.engelund.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleName(RoleName roleName);
    //TODO: Remove redundant interface stub. Repository already returns Optionals, and this method can be called without creating a custom method.
}
