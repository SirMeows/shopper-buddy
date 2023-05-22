package com.he.engelund.service;

import com.he.engelund.entity.*;
import com.he.engelund.repository.ItemListRepository;
import com.he.engelund.repository.ItemRepository;
import com.he.engelund.repository.ListUserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ItemListService {

    private ItemListRepository itemListRepository;

    private ItemRepository itemRepository;

    private ListUserRoleRepository listUserRoleRepository;

    public List<ItemList> getItemListsOrderedByLastModified() {
        return itemListRepository.findAllByOrderByLastModifiedDesc();
    }

    public Set<ItemList> getItemLists() {
        return itemListRepository.findAllSet();
    }

    public ItemList addItemList(ItemList itemList) {
        return itemListRepository.save(itemList);
    }

    public void addItemToItemList(String listId, Item item) {
        var itemList = itemListRepository.getReferenceById(UUID.fromString(listId));
        addToList(itemList, item);
    }

    public void addItemToItemList(String listId, String itemId) {
        var itemList = itemListRepository.getReferenceById(UUID.fromString(listId));
        var item = itemRepository.getReferenceById(UUID.fromString(itemId));
        addToList(itemList, item);
    }

    private void addToList(ItemList itemList, Item item) {
        var items = itemList.getItems();
        items.add(item);
        itemListRepository.save(itemList);
    }

    public void shareItemList(User userToShareWith, ItemList itemList, Role role) {
        var newListUserRole = ListUserRoleBuilder
                .create()
                .addUser(userToShareWith)
                .addItemList(itemList)
                .addRole(role)
                .build();
        listUserRoleRepository.save(newListUserRole);
    }
}
