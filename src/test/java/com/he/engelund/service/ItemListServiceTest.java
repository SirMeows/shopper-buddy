package com.he.engelund.service;

import com.he.engelund.entity.*;
import com.he.engelund.exception.ItemListNotFoundException;
import com.he.engelund.exception.UserNotListOwnerException;
import com.he.engelund.repository.ItemListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemListServiceTest {

    @InjectMocks
    private ItemListService itemListService;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private ListUserRoleService listUserRoleService;

    @Mock
    private ItemListRepository itemListRepository;

    private User targetUser;
    private Role ownerRole;
    private Role editorRole;
    private ItemList itemList;
    private String ownerIdString;
    private String targetUserIdString;
    private String itemListId;
    private UUID targetUserUUID;
    private UUID ownerUUID;
    private User owner;

    @BeforeEach
    public void setUp() {
        ownerRole = RoleBuilder.create().addRoleName(RoleName.OWNER).build();
        editorRole = RoleBuilder.create().addRoleName(RoleName.EDITOR).build();
        itemList = ItemListBuilder.create().build();
        ownerUUID = UUID.randomUUID();
        ownerIdString = ownerUUID.toString();
        owner = UserBuilder.create().addId(ownerUUID).build();
        targetUserUUID = UUID.randomUUID();
        targetUserIdString = targetUserUUID.toString();
        targetUser = UserBuilder.create().addId(targetUserUUID).build();
        itemListId = UUID.randomUUID().toString();
    }

    @Test
    void shouldShareItemList() {
        var expectedListUserRole = ListUserRoleBuilder.create()
                .addItemList(itemList)
                .addUser(owner)
                .addRole(ownerRole)
                .build();

        // given
        when(userService.findById(targetUserIdString)).thenReturn(targetUser);
        when(itemListRepository.findById(UUID.fromString(itemListId))).thenReturn(Optional.of(itemList));
        when(roleService.findByName(RoleName.EDITOR)).thenReturn(editorRole);
        when(roleService.findByName(RoleName.OWNER)).thenReturn(ownerRole);
        // when list owner id matches
        when(listUserRoleService.findByItemListAndRole(itemList, ownerRole)).thenReturn(Set.of(expectedListUserRole));
        doNothing().when(listUserRoleService).allocateListUserRole(any(ListUserRole.class));

        itemListService.shareItemList(ownerIdString, targetUserIdString, itemListId);

        // then
        verify(userService).findById(targetUserIdString);
        verify(itemListRepository).findById(UUID.fromString(itemListId));
        verify(roleService).findByName(RoleName.EDITOR);
        verify(listUserRoleService).findByItemListAndRole(itemList, ownerRole);
        verify(listUserRoleService).allocateListUserRole(any(ListUserRole.class));
    }

    @Test
    void shouldThrowExceptionWhenNotOwner() {
        var incorrectListUserRole = ListUserRoleBuilder.create()
                .addItemList(itemList)
                .addUser(targetUser)
                .addRole(editorRole)
                .build();

        // given
        when(userService.findById(targetUserIdString)).thenReturn(targetUser);
        when(itemListRepository.findById(UUID.fromString(itemListId))).thenReturn(Optional.of(itemList));
        when(roleService.findByName(RoleName.EDITOR)).thenReturn(editorRole);
        when(roleService.findByName(RoleName.OWNER)).thenReturn(ownerRole);

        // when list owner id doesn't match
        when(listUserRoleService.findByItemListAndRole(itemList, ownerRole)).thenReturn(Set.of(incorrectListUserRole));

        // then
        assertThrows(UserNotListOwnerException.class, () -> itemListService.shareItemList(ownerIdString, targetUserIdString, itemListId));
    }

    @Test
    void shouldThrowExceptionWhenItemListNotFound() {
        // given
        when(userService.findById(targetUserIdString)).thenReturn(targetUser);
        when(itemListRepository.findById(UUID.fromString(itemListId))).thenReturn(Optional.empty());

        // then
        assertThrows(ItemListNotFoundException.class, () -> itemListService.shareItemList(ownerIdString, targetUserIdString, itemListId));
    }
}