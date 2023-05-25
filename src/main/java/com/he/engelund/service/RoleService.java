package com.he.engelund.service;

import com.he.engelund.entity.Role;
import com.he.engelund.entity.RoleName;
import com.he.engelund.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleService {

    private RoleRepository roleRepository;

    public Role findByName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new EntityNotFoundException());
    }
}
