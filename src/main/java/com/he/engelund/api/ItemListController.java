package com.he.engelund.api;

import com.he.engelund.dto.ItemDto;
import com.he.engelund.dto.ItemListDto;
import com.he.engelund.dto.ShareItemListDto;
import com.he.engelund.entity.Item;
import com.he.engelund.entity.ItemList;
import com.he.engelund.entity.RoleName;
import com.he.engelund.service.interfaces.ItemListService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.he.engelund.config.ModelMapperConfig.SET_TYPE_ITEM_DTO;
import static com.he.engelund.config.ModelMapperConfig.SET_TYPE_ITEM_LIST_DTO;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/item-lists")
public class ItemListController {

    private ItemListService itemListService;

    private ModelMapper modelMapper;

    @GetMapping("/")
    Set<ItemListDto> itemLists() {
        var itemLists = itemListService.getItemLists();
        return modelMapper.map(itemLists, SET_TYPE_ITEM_LIST_DTO);
    }

    @PostMapping("/{id}/share")
    void shareItemList(@PathVariable String id, @RequestBody ShareItemListDto body) {
        var roleName = Enum.valueOf(RoleName.class, body.getTargetUserRoleName()); // Will throw IllegalArgumentException if this is not a valid RoleName
        var listUserId = UUID.fromString(body.getListOwnerId());
        var targetUserId = UUID.fromString(body.getTargetUserId());
        itemListService.shareItemList(UUID.fromString(id), listUserId, targetUserId, roleName);
    }

    @PostMapping("/add")
    ItemListDto createItemList(@RequestBody ItemListDto body) {
        var newItemList = dtoToItemList(body);
        var savedItemList = itemListService.addItemList(newItemList);
        return itemListToDto(savedItemList);
    }

    @PostMapping("/{id}/add-item/new")
    ItemListDto addNewItemToItemList(@PathVariable String id, @RequestBody ItemDto body) {
        var newItem = modelMapper.map(body, Item.class);
        var itemList = itemListService.addItemToItemList(UUID.fromString(id), newItem);
        return itemListToDto(itemList);
    }

    @PostMapping("/{id}/add-item/{itemId}")
    Set<ItemListDto> addExistingItemToItemList(@PathVariable String id, @PathVariable String itemId) {
        var itemList = itemListService.addItemToItemList(UUID.fromString(id), UUID.fromString(itemId));
        return modelMapper.map(itemList, SET_TYPE_ITEM_LIST_DTO);
    }

    @GetMapping("/{id}/items-by-list")
    Set<ItemDto> itemsByItemList(@PathVariable String id) {
        var items = itemListService.getItemsByItemList(UUID.fromString(id));
        return modelMapper.map(items, SET_TYPE_ITEM_DTO);
    }

    @PutMapping("/{id}/edit")
    ItemListDto edit(@PathVariable String id, @RequestBody ItemListDto body) {
        var itemListToEdit = dtoToItemList(body);
        var savedItemList = itemListService.editItemList(UUID.fromString(id), itemListToEdit);
        return itemListToDto(savedItemList);
    }

    @DeleteMapping("/{id}/delete")
    void deleteItemList(@PathVariable String id) {
        itemListService.deleteItemList(UUID.fromString(id));
    }

    private ItemList dtoToItemList(ItemListDto itemListDto) {
        return modelMapper.map(itemListDto, ItemList.class);
    }

    private ItemListDto itemListToDto(ItemList itemList) {
        return modelMapper.map(itemList, ItemListDto.class);
    }
}
