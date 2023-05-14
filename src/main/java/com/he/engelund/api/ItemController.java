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

    private ModelMapper modelMapper;

    private ItemService itemService;

    @GetMapping("/")
    Set<ItemDto> getItems() {
        var items = itemService.getItems();
        return modelMapper.map(items, SET_TYPE_ITEM_DTO);
    }

    @PostMapping("/add")
    ItemDto addItem(@RequestBody ItemDto body) {
        var newItem = modelMapper.map(body, Item.class);
        var savedItem =  itemService.addItem(newItem);
        return modelMapper.map(savedItem, ItemDto.class);
    }
}
