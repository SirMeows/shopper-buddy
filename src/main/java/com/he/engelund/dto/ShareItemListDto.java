package com.he.engelund.dto;

import com.he.engelund.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShareItemListDto {

    private UserDto userDto;

    private ItemListDto itemListDto;

    private Role role;
}
