package com.he.engelund.api;

import com.he.engelund.dto.ItemDto;
import com.he.engelund.entity.Item;
import com.he.engelund.service.ItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import static com.he.engelund.config.ModelMapperConfig.SET_TYPE_ITEM_DTO;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/items")
public class ItemController {

    private ModelMapper mm;

    private ItemService itemService;

    @GetMapping("/")
    Set<ItemDto> getItems() {
        var items = itemService.getItems();
        Set<ItemDto> itemDtos = mm.map(items, SET_TYPE_ITEM_DTO);
        return itemDtos;
    }

    @PostMapping("/add")
    ItemDto addItem(@RequestBody ItemDto body) {
        var newItem = mm.map(body, Item.class);
        var savedItem =  itemService.addItem(newItem);
        return mm.map(savedItem, ItemDto.class);
    }
}
