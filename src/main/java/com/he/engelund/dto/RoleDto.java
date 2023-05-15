package com.he.engelund.dto;

import com.he.engelund.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {

    private UUID id;

    private RoleName roleName;
}
