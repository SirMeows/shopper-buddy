package com.he.engelund.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "item_tag",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "item_itemList",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "itemList_id"))
    private Set<ItemList> itemLists = new HashSet<>();
}
