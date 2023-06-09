package com.he.engelund.service.interfaces;

import com.he.engelund.entity.Item;
import com.he.engelund.entity.ItemList;
import com.he.engelund.entity.RoleName;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;
@Service
public interface ItemListService {

    Set<ItemList> getItemLists();

    ItemList addItemList(ItemList itemList);

    ItemList addItemToItemList(UUID listId, Item item);

    ItemList addItemToItemList(UUID listId, UUID itemId);

    void shareItemList(UUID itemListId, UUID sharerId, UUID targetUserId, RoleName roleName);

    ItemList editItemList(UUID uuid, ItemList itemListToEdit);

    Set<Item> getItemsByItemList(UUID id);

    void deleteItemList(UUID uuid);
}
