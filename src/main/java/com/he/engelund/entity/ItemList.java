package com.he.engelund.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ItemList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private final LocalDateTime created = LocalDateTime.now();

    private LocalDateTime lastModified; //TODO: Sort lists so that the last modified list is displayed at the top

    @ManyToMany(mappedBy = "itemLists", fetch = FetchType.LAZY)
    private Set<Item> items;

    @PrePersist
    @PreUpdate
    private void updateLastModified() {
        this.lastModified = LocalDateTime.now();
    }
}
