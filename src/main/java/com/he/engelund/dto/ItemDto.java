package com.he.engelund.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonInclude(JsonInclude.Include.NON_NULL) // this line should exclude null properties
public class ItemDto {

    private Long id;

    private String name;
}
