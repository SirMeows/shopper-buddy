package com.he.engelund.repository;

import com.he.engelund.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    default Set<Item> findAllSet() {
        return new HashSet<>(findAll());
    }
}
