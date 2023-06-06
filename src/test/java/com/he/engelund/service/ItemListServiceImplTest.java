package com.he.engelund.service;

import com.he.engelund.entity.*;
import com.he.engelund.entity.builder.ItemListBuilder;
import com.he.engelund.entity.builder.ListUserRoleBuilder;
import com.he.engelund.entity.builder.RoleBuilder;
import com.he.engelund.entity.builder.UserBuilder;
import com.he.engelund.exception.ItemListNotFoundException;
import com.he.engelund.exception.UserNotFoundException;
import com.he.engelund.exception.UserNotListOwnerException;
import com.he.engelund.repository.ItemListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemListServiceImplTest {

    @InjectMocks
    private ItemListServiceImpl itemListServiceImpl;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private RoleService roleService;

    @Mock
    private ListUserRoleService listUserRoleService;

    @Mock
    private ItemListRepository itemListRepository;

    private User owner, targetUser;
    private Role ownerRole, editorRole;
    private String ownerIdString, targetUserIdString, itemListIdString;
    private UUID ownerUUID, targetUserUUID, itemListUUID;
    private ItemList itemList;

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
        itemListUUID = UUID.randomUUID();
        itemListIdString = itemListUUID.toString();
    }

    @Test
    void shouldShareItemList() {
        ListUserRoleBuilder.create()
                .addItemList(itemList)
                .addUser(owner)
                .addRole(ownerRole)
                .build();

        // given
        when(userService.findById(targetUserUUID)).thenReturn(targetUser);
        when(itemListRepository.findById(itemListUUID)).thenReturn(Optional.of(itemList));
        when(roleService.findByName(RoleName.EDITOR)).thenReturn(editorRole);
        doNothing().when(listUserRoleService).allocateListUserRole(any(ListUserRole.class)); // Ignore this method call

        itemListServiceImpl.shareItemList(itemListUUID, ownerUUID, targetUserUUID, RoleName.EDITOR);

        // then
        verify(listUserRoleService).allocateListUserRole(any(ListUserRole.class));
    }

    @Test
    void shouldThrowExceptionWhenNotOwner() {
        var sharerUser = UserBuilder.create().addId("df3b80ea-3d68-42f2-bc64-4ca465630ad1").build();
        var newTargetUser = UserBuilder.create().addId("691f7261-a4e9-4733-9c36-b883cd2ca448").build();
        ListUserRoleBuilder.create()
                .addItemList(itemList)
                .addUser(sharerUser)
                .addRole(editorRole)
                .build();
        ListUserRoleBuilder.create()
                .addItemList(itemList)
                .addUser(owner)
                .addRole(ownerRole)
                .build();

        // given
        when(userService.findById(newTargetUser.getId())).thenReturn(newTargetUser);
        when(itemListRepository.findById(itemListUUID)).thenReturn(Optional.of(itemList));
        when(roleService.findByName(RoleName.EDITOR)).thenReturn(editorRole);

        // then
        assertThrows(UserNotListOwnerException.class, () -> itemListServiceImpl.shareItemList(itemListUUID, sharerUser.getId(), newTargetUser.getId(), RoleName.EDITOR));
    }

    @Test
    void shouldThrowExceptionWhenItemListNotFound() {
        // given
        when(userService.findById(targetUserUUID)).thenReturn(targetUser);
        when(itemListRepository.findById(itemListUUID)).thenReturn(Optional.empty());

        // then
        assertThrows(ItemListNotFoundException.class, () -> itemListServiceImpl.shareItemList(itemListUUID, ownerUUID, targetUserUUID, RoleName.EDITOR));
    }

    @Test
    void shouldThrowExceptionWhenTargetUserDoesNotExist() {
        // given
        var incorrectUserId = UUID.randomUUID();
        when(userService.findById(incorrectUserId)).thenThrow(new UserNotFoundException(incorrectUserId));

        // then
        assertThrows(UserNotFoundException.class, () -> itemListServiceImpl.shareItemList(itemListUUID, ownerUUID, incorrectUserId, RoleName.EDITOR));
    }
}