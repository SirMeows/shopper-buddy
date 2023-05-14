package com.he.engelund.entity;

import lombok.NoArgsConstructor;
import java.util.UUID;

@NoArgsConstructor
public class ListUserRoleBuilder {

    private ListUserRole listUserRole;

    public static ListUserRoleBuilder create() {
        var builder = new ListUserRoleBuilder();
        var listUserRole = new ListUserRole();
        builder.setListUserRole(listUserRole);
        return builder;
    }

    private void setListUserRole(ListUserRole listUserRole) {
        this.listUserRole = listUserRole;
    }

    public ListUserRoleBuilder addId(UUID id) {
        listUserRole.setId(id);
        return this;
    }

    public ListUserRoleBuilder addItemList(ItemList itemList) {
        listUserRole.setItemList(itemList);
        return this;
    }

    public ListUserRoleBuilder addUser(User user) {
        listUserRole.setUser(user);
        return this;
    }

    public ListUserRoleBuilder addRole(Role role) {
        listUserRole.setRole(role);
        return this;
    }

    public ListUserRole build() {
        var temp = listUserRole;
        listUserRole = null;
        return temp;
    }
}
