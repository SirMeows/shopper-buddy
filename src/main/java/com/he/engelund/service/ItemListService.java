package com.he.engelund.service;

import com.he.engelund.entity.ItemList;
import com.he.engelund.repository.ItemListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ItemListService {

    private ItemListRepository iListRepository;

    public List<ItemList> getItemListsOrderedByLastModified() {
        return iListRepository.findAllByOrderByLastModifiedDesc();
    }

    public Set<ItemList> getItemLists() {
        return iListRepository.findAllSet();
    }

    public ItemList addItemList(ItemList itemList) {
        return iListRepository.save(itemList);
    }
}
