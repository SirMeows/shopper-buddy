package com.he.engelund.repository;

import com.he.engelund.entity.ItemList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public interface ItemListRepository extends JpaRepository<ItemList, Long> {

    default Set<ItemList> findAllSet() {
        return new HashSet<>(findAll());
    }

    List<ItemList> findAllByOrderByLastModifiedDesc();
}
