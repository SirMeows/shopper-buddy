package com.he.engelund.service;

import com.he.engelund.entity.ItemList;
import com.he.engelund.entity.ListUserRoleBuilder;
import com.he.engelund.entity.Role;
import com.he.engelund.entity.User;
import com.he.engelund.repository.ItemListRepository;
import com.he.engelund.repository.ListUserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ItemListService {

    private ItemListRepository iListRepository;

    private ListUserRoleRepository listUserRoleRepository;

    public List<ItemList> getItemListsOrderedByLastModified() {
        return iListRepository.findAllByOrderByLastModifiedDesc();
    }

    public Set<ItemList> getItemLists() {
        return iListRepository.findAllSet();
    }

    public ItemList addItemList(ItemList itemList) {
        return iListRepository.save(itemList);
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
