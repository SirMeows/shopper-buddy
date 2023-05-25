package com.he.engelund.repository;

import com.he.engelund.entity.ItemList;
import com.he.engelund.entity.ListUserRole;
import com.he.engelund.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListUserRoleRepository extends JpaRepository<ListUserRole, UUID> {

    Optional<ListUserRole> findByItemListAndRole(ItemList itemList, Role role);
}
