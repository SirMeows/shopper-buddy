package com.he.engelund.repository;

import com.he.engelund.entity.Item;
import com.he.engelund.entity.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    default Set<Item> findAllSet() {
        return new HashSet<>(findAll());
    }

// TODO: Add searching with keyword from several columns
//  https://medium.com/javarevisited/jpa-specification-a-generic-search-e8695b1d19ec
}
