package com.he.engelund.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShareItemListDto {

    private String itemListId;

    private String listOwnerId;

    private String targetUserId;

    private String targetUserRoleName;
}
