package com.he.engelund.api;

import com.he.engelund.dto.ItemDto;
import com.he.engelund.service.ItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        Set<ItemDto> itemDtos = modelMapper.map(items, SET_TYPE_ITEM_DTO);
        return itemDtos;

    }
}
