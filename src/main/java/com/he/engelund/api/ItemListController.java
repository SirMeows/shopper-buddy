package com.he.engelund.api;

import com.he.engelund.dto.ItemDto;
import com.he.engelund.dto.ItemListDto;
import com.he.engelund.entity.Item;
import com.he.engelund.entity.ItemList;
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

    @PostMapping("/share")
    void shareItemList(@RequestParam String ownerId, @RequestParam String targetUserId, @RequestParam String itemListId) {
        itemListService.shareItemList(ownerId, targetUserId, itemListId);
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
