package com.he.engelund.service;

import com.he.engelund.entity.*;
import com.he.engelund.exception.ItemListNotFoundException;
import com.he.engelund.exception.UserNotListOwnerException;
import com.he.engelund.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ItemListService {

    private ItemListRepository itemListRepository;

    private ItemService itemService;

    private UserService userService;

    private RoleService roleService;

    private ListUserRoleService listUserRoleService;

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
        var item = itemService.getItemById(itemId);
        addToList(itemList, item);
    }

    private void addToList(ItemList itemList, Item item) {
        var items = itemList.getItems();
        items.add(item);
        itemListRepository.save(itemList);
    }

    public void shareItemList(String ownerId, String targetUserId, String itemListId) {
        var targetUser = userService.findById(targetUserId);
        ItemList itemList = itemListRepository.findById(UUID.fromString(itemListId)).orElseThrow(() -> new ItemListNotFoundException(itemListId));
        var editorRole = roleService.findByName(RoleName.EDITOR);

        // Check that the ownerId matches the owner of the itemListId
        if(userOwnsTheList(ownerId, itemList)) {
            var newListUserRole = ListUserRoleBuilder
                    .create()
                    .addUser(targetUser)
                    .addItemList(itemList)
                    .addRole(editorRole)
                    .build();
            listUserRoleService.allocateListUserRole(newListUserRole);
        }
        else {
            throw new UserNotListOwnerException(ownerId);
        }
    }

    private boolean userOwnsTheList(String userId, ItemList itemList) {
        var ownerRole = roleService.findByName(RoleName.OWNER);
        var listUserRoles = listUserRoleService.findByItemListAndRole(itemList, ownerRole);
        var providedUserId = UUID.fromString(userId);
        var actualOwnerId = findActualOwner(listUserRoles).getId();

        if(providedUserId.equals(actualOwnerId)) {
            return true;
        }
        return false;
    }

    private User findActualOwner(Set<ListUserRole> listUserRoles) {
        List<User> owners = listUserRoles.stream()
                .map(ListUserRole::getUser)
                .collect(Collectors.toList());

        if (owners.size() > 1) {
            throw new RuntimeException("Unexpected number of owners: " + owners.size());
        } else if (owners.isEmpty()) {
            throw new RuntimeException("No owners found");
        }

        return owners.get(0);
    }
}
