package com.he.engelund.entity;

import lombok.*;
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

    private LocalDateTime lastModified;

    @ManyToMany(mappedBy = "itemLists", fetch = FetchType.LAZY)
    private Set<Item> items;

    @OneToMany(mappedBy = "itemList")
    private Set<ListUserRole> listUserRoles = new HashSet<>();

    @PrePersist
    @PreUpdate
    private void updateLastModified() {
        this.lastModified = LocalDateTime.now();
    }
}
