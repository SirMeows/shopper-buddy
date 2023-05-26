package com.he.engelund.service;

import com.he.engelund.entity.ItemList;
import com.he.engelund.entity.ListUserRole;
import com.he.engelund.entity.Role;
import com.he.engelund.repository.ListUserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class ListUserRoleService {

    private ListUserRoleRepository listUserRoleRepository;

    public void allocateListUserRole(ListUserRole listUserRole) {
        listUserRoleRepository.save(listUserRole);
    }

    public Set<ListUserRole> findByItemListAndRole(ItemList itemList, Role role) {
        return listUserRoleRepository.findByItemListAndRole(itemList, role);
    }
}
