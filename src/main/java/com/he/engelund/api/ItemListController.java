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

import static com.he.engelund.config.ModelMapperConfig.SET_TYPE_ITEM_LIST_DTO;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/item-lists")
public class ItemListController {

    private ItemListService itemListService;

    private ModelMapper modelMapper;

    @GetMapping("/")
    Set<ItemList> itemLists() { // returns
        var itemLists = itemListService.getItemLists();
        return modelMapper.map(itemLists, SET_TYPE_ITEM_LIST_DTO);
    }

    @GetMapping("/last-modified")
    List<ItemList> itemListsOrderedByLastModified() {
        var itemLists = itemListService.getItemListsOrderedByLastModified();
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
    void createItemList(@RequestBody ItemListDto body) {
        var newItemList = dtoToItemList(body);
        itemListService.addItemList(newItemList);
    }

    @PostMapping("/{id}/add-item/new")
    ItemList addNewItemToItemList(@PathVariable String id, @RequestBody ItemDto body) {
        var newItem = modelMapper.map(body, Item.class);
        return itemListService.addItemToItemList(UUID.fromString(id), newItem);
    }

    @PostMapping("/{id}/add-item/{itemId}")
    void addExistingItemToItemList(@PathVariable String id, @PathVariable String itemId) {
        itemListService.addItemToItemList(UUID.fromString(id), UUID.fromString(itemId));
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
