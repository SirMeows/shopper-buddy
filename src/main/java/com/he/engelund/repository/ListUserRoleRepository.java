package com.he.engelund.repository;

import com.he.engelund.entity.ListUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ListUserRoleRepository extends JpaRepository<ListUserRole, UUID> {

    default Set<ListUserRole> findAllSet() {
        return new HashSet<>(findAll());
    }
}
