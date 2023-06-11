package com.he.engelund.service;

import com.he.engelund.entity.*;
import com.he.engelund.entity.builder.ListUserRoleBuilder;
import com.he.engelund.exception.ItemListNotFoundException;
import com.he.engelund.exception.UserNotListOwnerException;
import com.he.engelund.repository.*;
import com.he.engelund.service.interfaces.ItemListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.model.IModel;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ItemListServiceImpl implements ItemListService {

    private ItemListRepository itemListRepository;

    private ItemServiceImpl itemService;

    private UserServiceImpl userService;

    private RoleService roleService;

    private ListUserRoleService listUserRoleService;

    @Override
    public List<ItemList> getItemListsOrderedByLastModified() {
        return itemListRepository.findAllByOrderByLastModifiedDesc();
    }

    @Override
    public Set<ItemList> getItemLists() {
        return itemListRepository.findAllSet();
    }

    //TODO: Get itemLists by name (name)

    @Override
    public ItemList getItemListById(UUID id) {
        return itemListRepository.findById(id).orElseThrow(() -> new ItemListNotFoundException());
    }

    @Override
    public ItemList addItemList(ItemList itemList) {
        return itemListRepository.save(itemList);
    }

    @Override
    public void deleteItemList(UUID id) {
        itemListRepository.deleteById(id);
    }

    @Override
    public ItemList editItemList(UUID id, ItemList itemList) {
        var savedItemList = itemListRepository.findById(id).orElseThrow(() -> new ItemListNotFoundException(id));
        if(!itemList.equals(savedItemList)) {
            return itemListRepository.save(itemList);
        }
        return savedItemList; // Returning original saved entity to keep lastEdited stamp unaltered
    }

    @Override
    public Set<Item> getItemsByItemList(UUID id) {
        var savedList = itemListRepository.findById(id).orElseThrow(() -> new ItemListNotFoundException());
        return savedList.getItems();
    }

    @Override
    public ItemList addItemToItemList(UUID listId, Item item) {
        var itemList = itemListRepository.getReferenceById(listId);
        return addItemToItemList(itemList, item);
    }

    @Override
    public ItemList addItemToItemList(UUID listId, UUID itemId) {
        var itemList = itemListRepository.getReferenceById(listId); //TODO:Change to findById() because it's used immediately (no need for lazy loading)
        var item = itemService.findById(itemId);
        return addItemToItemList(itemList, item);
    }

    private ItemList addItemToItemList(ItemList itemList, Item item) {
        var items = itemList.getItems();
        items.add(item);
        return itemListRepository.save(itemList);
    }

    @Override
    public void shareItemList(UUID itemListId, UUID sharerId, UUID targetUserId, RoleName roleName) {
        //TODO: Consider whether more than one owner should be allowed in order to change ownership
        if(roleName == RoleName.OWNER) {
            throw new IllegalArgumentException();
        }
        var targetUser = userService.findById(targetUserId);
        var itemList = itemListRepository.findById(itemListId).orElseThrow(() -> new ItemListNotFoundException(itemListId));
        var role = roleService.findByName(roleName);

        if(userOwnsTheList(sharerId, itemList)) {
            var newListUserRole = ListUserRoleBuilder
                    .create()
                    .addUser(targetUser)
                    .addItemList(itemList)
                    .addRole(role)
                    .build();
            listUserRoleService.allocateListUserRole(newListUserRole);
        }
        else {
            throw new UserNotListOwnerException(sharerId);
        }
    }
    private boolean userOwnsTheList(UUID userId, ItemList itemList) {
        List<User> owners = itemList.getListUserRoles().stream()
                .filter(listUserRole -> listUserRole.getRole().getRoleName() == RoleName.OWNER)
                .map(ListUserRole::getUser)
                .collect(Collectors.toList());

        if (owners.isEmpty()) {
            throw new RuntimeException("No owner found for this list");
        }
        if (owners.size() > 1) {
            //TODO: Could allow more owners if needed for changing list owner functionality
            throw new RuntimeException("More than one owner found for this list");
        }
        return owners.get(0).getId().equals(userId);
    }
}
