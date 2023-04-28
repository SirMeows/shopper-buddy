package com.he.engelund.repository;

import com.he.engelund.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    default Set<Tag> findAllSet() {
        return new HashSet<>(findAll());
    }
}
