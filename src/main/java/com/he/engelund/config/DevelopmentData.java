package com.he.engelund.config;

import com.he.engelund.entity.*;
import com.he.engelund.entity.builder.*;
import com.he.engelund.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

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

    private UserRepository userRepository;
    private List<User> users;

    private RoleRepository roleRepository;
    private List<Role> roles;

    private ListUserRoleRepository listUserRoleRepo;
    private List<ListUserRole> listUserRoles;

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

        items.addAll(List.of(muesli, bread, juice));
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
        var bakeryProducts = TagBuilder.create().addName("bakery products").build();
        var drinks = TagBuilder.create().addName("drinks").build();
        var hFavorite = TagBuilder.create().addName("He likes").build();
        var wFavorite = TagBuilder.create().addName("Wouter likes").build();

        tags.addAll(List.of(bakeryProducts, drinks, hFavorite, wFavorite));
        tagRepository.saveAll(tags);
    }

    private void makeUsers() {
        var he = UserBuilder
                .create()
                .addUsername("he")
                .addEmail("he-email@gmail.com")
                .build();

        var wp = UserBuilder
                .create()
                .addUsername("wp")
                .addEmail("wp-email@google.com")
                .build();

        users.addAll(List.of(he, wp));
        userRepository.saveAll(users);
    }

    private void makeRoles() {
        var owner = new Role();
        owner.setRoleName(RoleName.OWNER);

        var editor = new Role();
        editor.setRoleName(RoleName.EDITOR);

        var viewer = new Role();
        viewer.setRoleName(RoleName.VIEWER);

        roles.addAll(List.of(owner, editor, viewer));
        roleRepository.saveAll(roles);
    }

    private void makeListUserRoles() {
        IntStream.rangeClosed(0,10).forEach(i -> listUserRoles.add(makeListUserRole()));
        listUserRoleRepo.saveAll(listUserRoles);
    }

    private ListUserRole makeListUserRole() {
        var random = new Random();
        var randItemList = itemLists.get(random.nextInt(itemLists.size()));
        var randUser = users.get(random.nextInt(users.size()));
        var randRole = roles.get(random.nextInt(roles.size()));

        var newListUserRole = ListUserRoleBuilder
                .create()
                .addItemList(randItemList)
                .addUser(randUser)
                .addRole(randRole)
                .build();

        return newListUserRole;
    }

    @Override
    public void run(ApplicationArguments args) {
        makeItems();
        makeItemLists();
        makeTags();
        makeUsers();
        makeRoles();
        makeListUserRoles();
    }
}
