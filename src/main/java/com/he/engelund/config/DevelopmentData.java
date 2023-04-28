package com.he.engelund.config;

import com.he.engelund.entity.*;
import com.he.engelund.repository.ItemListRepository;
import com.he.engelund.repository.ItemRepository;
import com.he.engelund.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;

@AllArgsConstructor
@Controller
@Profile("!test")
public class DevelopmentData implements ApplicationRunner {

    private ItemRepository iRepository;
    private List<Item> items;

    private ItemListRepository iListRepository;
    private List<ItemList> itemLists;

    private TagRepository tagRepository;
    private List<Tag> tags;

    private void makeItems() {

        var muesli = ItemBuilder
                .create()
                .addName("fruit and nut muesli")
                .build();

        var bread = ItemBuilder
                .create()
                .addName("bread")
                .build();

        var juice = ItemBuilder
                .create()
                .addName("orange juice")
                .build();

        items.addAll(List.of(muesli, bread));
        iRepository.saveAll(items);
    }

    private void makeItemLists() {

        var groceries = ItemListBuilder
                .create()
                .addName("groceries")
                .build();

        var gifts = ItemListBuilder
                .create()
                .addName("gifts")
                .build();

        var hobbyItems = ItemListBuilder
                .create()
                .addName("hobbyItems")
                .build();

        itemLists.addAll(List.of(groceries, gifts, hobbyItems));
        iListRepository.saveAll(itemLists);
    }

    private void makeTags() {

    var bakeryProducts = TagBuilder
            .create()
            .addName("bakery products")
            .build();

        var drinks = TagBuilder
                .create()
                .addName("drinks")
                .build();

        var hFavorite = TagBuilder
                .create()
                .addName("He likes")
                .build();

        var wFavorite = TagBuilder
                .create()
                .addName("Wouter likes")
                .build();

        tags.addAll(List.of(bakeryProducts, drinks, hFavorite, wFavorite));
        tagRepository.saveAll(tags);
    }

    @Override
    public void run(ApplicationArguments args) {
        makeItems();
        makeItemLists();
        makeTags();
    }
}
