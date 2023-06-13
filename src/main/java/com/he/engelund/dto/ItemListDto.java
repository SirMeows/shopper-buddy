package com.he.engelund.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemListDto {

    private UUID id;

    private String name;

    //TODO: Add lastModified so ItemLists can be displayed ordered by last modified datetime (sort on frontend side)
}
