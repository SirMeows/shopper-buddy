package com.he.engelund.api;

import com.he.engelund.dto.ItemDto;
import com.he.engelund.entity.Item;
import com.he.engelund.service.interfaces.ItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.UUID;

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
        var newItem = dtoToItem(body);
        var savedItem =  itemService.addItem(newItem);
        return itemToDto(savedItem);
    }

    @PostMapping("/{id}/add-tag")
    ItemDto addTagToItem(@PathVariable String id, @PathVariable String tag) {
        var savedItem = itemService.addTagToItem(UUID.fromString(id), tag);
        return itemToDto(savedItem);
    }

    @PutMapping("/{id}/edit")
    ItemDto edit(@PathVariable String id, @RequestBody ItemDto body) {
        var itemToEdit = dtoToItem(body);
        var savedItem = itemService.editItem(UUID.fromString(id), itemToEdit);
        return itemToDto(savedItem);
    }

    @DeleteMapping("/{id}/delete")
    void delete(@PathVariable String id) {
        itemService.deleteItem(UUID.fromString(id));
    }

    private ItemDto itemToDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    private Item dtoToItem(ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }


}
