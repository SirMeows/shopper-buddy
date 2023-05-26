package com.he.engelund.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoleBuilder {

    private Role role;

    public static RoleBuilder create() {
        var roleBuilder = new RoleBuilder();
        var role = new Role();
        roleBuilder.setRole(role);
        return roleBuilder;
    }

    private void setRole(Role role) {
        this.role = role;
    }

    public RoleBuilder addRoleName(RoleName roleName) {
        role.setRoleName(roleName);
        return this;
    }

    public Role build() {
        var temp = role;
        role = null;
        return temp;
    }
}
