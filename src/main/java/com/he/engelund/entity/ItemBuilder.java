package com.he.engelund.entity;

import lombok.NoArgsConstructor;
import java.util.HashSet;

@NoArgsConstructor
public class ItemBuilder {

    private Item item;

    public static ItemBuilder create() {
        var iBuilder = new ItemBuilder();
        var item = new Item();
        iBuilder.setItem(item);
        return iBuilder;
    }

    private void setItem(Item item) {
        this.item = item;
    }

    public ItemBuilder addId(Long id) {
        item.setId(id);
        return this;
    }

    public ItemBuilder addName(String name) {
        item.setName(name);
        return this;
    }

    public ItemBuilder addEmptyTagSet() {
        item.setTags(new HashSet<>());
        return this;
    }

    public ItemBuilder addEmptyItemListSet() {
        item.setItemLists(new HashSet<>());
        return this;
    }

    public Item build() {
        var temp = item;
        item = null;
        return temp;
    }
}
