package com.he.engelund.repository;

import com.he.engelund.entity.Role;
import com.he.engelund.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    //Optional<Role> findById(UUID uuid);

    Optional<Role> findByRoleName(RoleName roleName);
}
