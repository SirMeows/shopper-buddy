package com.he.engelund.service.interfaces;

import com.he.engelund.entity.Item;

import java.util.Set;
import java.util.UUID;

public interface ItemService {
    Set<Item> getItems();

    Item findById(UUID itemId);

    Item addItem(Item item);

    Item editItem(UUID id, Item itemToEdit);

    void deleteItem(UUID id);

    Item addTagToItem(UUID uuid, String tag);
}
