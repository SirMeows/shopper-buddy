package com.he.engelund.repository;

import com.he.engelund.entity.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ItemListRepository extends JpaRepository<ItemList, UUID> {

    default Set<ItemList> findAllSet() {
        return new HashSet<>(findAll());
    }
}
