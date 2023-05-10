package com.he.engelund.api;

import com.he.engelund.dto.ItemListDto;
import com.he.engelund.entity.ItemList;
import com.he.engelund.service.ItemListService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    Set<ItemList> itemLists() {
        var itemLists = itemListService.getItemLists();
        return modelMapper.map(itemLists, SET_TYPE_ITEM_LIST_DTO);
    }

    @GetMapping("/get-all-last-modified")
    List<ItemList> itemListsOrderedByLastModified() {
        var itemLists = itemListService.getItemListsOrderedByLastModified();
        return modelMapper.map(itemLists, SET_TYPE_ITEM_LIST_DTO);
    }
}
