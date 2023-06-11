package com.he.engelund.service;

import com.he.engelund.entity.Item;
import com.he.engelund.exception.ItemNotFoundException;
import com.he.engelund.repository.ItemRepository;
import com.he.engelund.service.interfaces.ItemService;
import com.he.engelund.service.interfaces.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    private TagService tagService;

    @Override
    public Set<Item> getItems() {
        return itemRepository.findAllSet();
    }

    @Override
    public Item findById(UUID id) {
        return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    @Override
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item editItem(UUID id, Item item) {
        var savedItem = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        if(!item.equals(savedItem)) {
            return itemRepository.save(item);
        }
        return savedItem;
    }

    @Override
    public void deleteItem(UUID id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item addTagToItem(UUID id, String name) {
        var savedItem = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        var tag = tagService.findByName(name);// TODO: Make sure that this now works as intended
        savedItem.getTags().add(tag);
        return itemRepository.save(savedItem);
    }
}
