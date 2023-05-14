package com.he.engelund.repository;

import com.he.engelund.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    default Set<Role> findAllSet() {
        return new HashSet<>(findAll());
    }
}
