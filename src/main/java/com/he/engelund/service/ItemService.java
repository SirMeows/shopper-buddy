package com.he.engelund.service;

import com.he.engelund.entity.Item;
import com.he.engelund.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@AllArgsConstructor
@Service
public class ItemService {

    private ItemRepository itemRepository;

    public Set<Item> getItems() {
        return itemRepository.findAllSet();
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }
}