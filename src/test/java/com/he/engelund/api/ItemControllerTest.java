package com.he.engelund.api;

import com.he.engelund.dto.ItemDto;
import com.he.engelund.entity.Item;
import com.he.engelund.entity.ItemBuilder;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ModelMapper mm;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testGetItems() throws Exception {
        Set<Item> items = new HashSet<>();
        items.addAll(Set.of(
                ItemBuilder.create().addId(1L).addName("Item 1").build(),
                ItemBuilder.create().addId(2L).addName("Item 2").build()
        ));

        when(itemService.getItems()).thenReturn(items);

        Set<ItemDto> itemDtos = new HashSet<>();
        itemDtos.addAll(Set.of(
                new ItemDto(1L, "Item 1"),
                new ItemDto(2L, "Item 2")
        ));

        when(mm.map(items, SET_TYPE_ITEM_DTO)).thenReturn(itemDtos);

        mockMvc.perform(get("/api/items/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Item 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Item 2"));
        //Verifies that the service in question is called once and only once
        verify(itemService, times(1)).getItems();
        verify(mm, times(1)).map(items, SET_TYPE_ITEM_DTO);
    }

    @Test
    public void testAddItem() throws Exception {
        // Given
        ItemDto requestItemDto = new ItemDto(100L, "Test Item");

        Item requestItem = ItemBuilder.create()
                .addId(100L)
                .addName("Test Item")
                .build();

        Item savedItem = ItemBuilder.create()
                .addId(100L)
                .addName("Test Item")
                .build();

        ItemDto responseItemDto = new ItemDto(100L, "Test Item");

        // When
        when(mm.map(requestItemDto, Item.class)).thenReturn(requestItem);
        when(itemService.addItem(requestItem)).thenReturn(savedItem);
        when(mm.map(savedItem, ItemDto.class)).thenReturn(responseItemDto);

        // Then
        mockMvc.perform(post("/api/items/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestItemDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id", is(100L)))
                .andExpect(jsonPath("$[0].name", is("Test Item")));

        verify(mm, times(1)).map(requestItemDto, Item.class);
        verify(itemService, times(1)).addItem(requestItem);
        verify(mm, times(1)).map(savedItem, ItemDto.class);
    }
}