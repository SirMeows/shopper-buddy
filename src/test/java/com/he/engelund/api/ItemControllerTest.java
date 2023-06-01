package com.he.engelund.api;

import com.he.engelund.dto.ItemDto;
import com.he.engelund.entity.Item;
import com.he.engelund.entity.builder.ItemBuilder;
import com.he.engelund.service.ItemService;
import static com.he.engelund.config.ModelMapperConfig.SET_TYPE_ITEM_DTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {


    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ModelMapper modelMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testGetItems() throws Exception {
        var item1Id = UUID.randomUUID();
        var item2Id = UUID.randomUUID();
        Set<Item> items = new HashSet<>();
        items.addAll(Set.of(
                ItemBuilder.create().addId(item1Id).addName("Item 1").build(),
                ItemBuilder.create().addId(item2Id).addName("Item 2").build()
        ));

        when(itemService.getItems()).thenReturn(items);

        Set<ItemDto> itemDtos = new HashSet<>();
        itemDtos.addAll(Set.of(
                new ItemDto(item1Id, "Item 1"),
                new ItemDto(item2Id, "Item 2")
        ));

        when(modelMapper.map(items, SET_TYPE_ITEM_DTO)).thenReturn(itemDtos);

        //TODO: Rewrite test. Set elements can't be fetched with index (is unordered)
        mockMvc.perform(get("/api/items/"))
                .andExpect(status().isOk())
                //Checks that JSON response contains the items with correct id and name
                .andExpect(jsonPath("$..[?(@.id == '" + item1Id + "' && @.name == 'Item 1')]").exists())
                .andExpect(jsonPath("$..[?(@.id == '" + item2Id + "' && @.name == 'Item 2')]").exists())
                //Verifies that the service in question is called once and only once
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(item1Id.toString(), item2Id.toString())))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Item 1", "Item 2")));

        //Verifies that the service in question is called once and only once
        verify(itemService, times(1)).getItems();
        verify(modelMapper, times(1)).map(items, SET_TYPE_ITEM_DTO);
    }

    @Test
    public void testAddItem() throws Exception {
        // Given
        final UUID itemId = UUID.randomUUID();
        ItemDto requestItemDto = new ItemDto(itemId, "Test Item");
        Item requestItem = ItemBuilder.create().addId(itemId).addName("Test Item").build();
        Item savedItem = ItemBuilder.create().addId(itemId).addName("Test Item").build();
        ItemDto responseItemDto = new ItemDto(itemId, "Test Item");

        // When
        when(modelMapper.map(requestItemDto, Item.class)).thenReturn(requestItem);
        when(itemService.addItem(requestItem)).thenReturn(savedItem);
        when(modelMapper.map(savedItem, ItemDto.class)).thenReturn(responseItemDto);

        // Then
        var content = objectMapper.writeValueAsString(requestItemDto);
        mockMvc.perform(post("/api/items/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id", is(itemId.toString())))
                .andExpect(jsonPath("$.name", is("Test Item")))
                .andDo(print());

        verify(modelMapper, times(1)).map(requestItemDto, Item.class);
        verify(itemService, times(1)).addItem(requestItem);
        verify(modelMapper, times(1)).map(savedItem, ItemDto.class);
    }
}