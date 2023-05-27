package com.he.engelund.api;

import com.he.engelund.dto.ItemDto;
import com.he.engelund.dto.ItemListDto;
import com.he.engelund.entity.Item;
import com.he.engelund.entity.ItemList;
import com.he.engelund.entity.RoleName;
import com.he.engelund.service.ItemListService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import static com.he.engelund.config.ModelMapperConfig.SET_TYPE_ITEM_LIST_DTO;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/item-lists")
public class ItemListController {

    //TODO: Convert String IDs to UUID ASAP, already in Controllers, not Service
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

    //TODO: ItemListID as first path variable, the rest in a dto
    @PostMapping("/{listId}/share")
    void shareItemList(@PathVariable String listId, @RequestParam String ownerId, @RequestParam String targetUserId, @RequestParam String targetUserRole) {
        RoleName roleName = Enum.valueOf(RoleName.class, targetUserRole); // Will throw IllegalArgumentException if this is not a valid RoleName
        itemListService.shareItemList(ownerId, targetUserId, listId, roleName);
    }

    @PostMapping("/create")
    void createItemList(@RequestBody ItemListDto body) {
        var newItemList = modelMapper.map(body, ItemList.class);
        itemListService.addItemList(newItemList);
    }

    @PostMapping("/{id}/new-item")
    void addNewItemToItemList(@PathVariable String id, @RequestBody ItemDto body) {
        var newItem = modelMapper.map(body, Item.class);
        itemListService.addItemToItemList(id, newItem);
    }

    @PostMapping("/{listId}/add/{itemId}")
    void addExistingItemToItemList(@PathVariable String listId, @PathVariable String itemId) {
        itemListService.addItemToItemList(listId, itemId);
    }
}
