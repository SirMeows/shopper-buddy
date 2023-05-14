package com.he.engelund.dto;

import lombok.*;

import java.util.UUID;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDto {

    private UUID id;

    private String name;
}
