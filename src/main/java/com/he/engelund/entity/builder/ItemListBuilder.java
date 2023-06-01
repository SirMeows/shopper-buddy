package com.he.engelund.entity.builder;

import com.he.engelund.entity.ItemList;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class ItemListBuilder {

    private ItemList itemList;

    public static ItemListBuilder create() {
        var iListBuilder = new ItemListBuilder();
        var itemList = new ItemList();
        iListBuilder.setItemList(itemList);
        return iListBuilder;
    }

    private void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }

    public ItemListBuilder addId(UUID uuid) {
        itemList.setId(uuid);
        return this;
    }

    public ItemListBuilder addName(String name) {
        itemList.setName(name);
        return this;
    }

    public ItemList build() {
        var temp = itemList;
        itemList = null;
        return temp;
    }
}

