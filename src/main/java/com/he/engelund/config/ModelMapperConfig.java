package com.he.engelund.config;

import com.he.engelund.dto.ItemDto;
import com.he.engelund.dto.ItemListDto;
import com.he.engelund.dto.TagDto;
import com.he.engelund.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static final Type SET_TYPE_ITEM_DTO = new TypeToken<HashSet<ItemDto>>() {}.getType();

    public static final Type SET_TYPE_ITEM_LIST_DTO = new TypeToken<HashSet<ItemListDto>>() {}.getType();

    public static final Type SET_TYPE_TAG_DTO = new TypeToken<HashSet<TagDto>>() {}.getType();

    public static final Type SET_TYPE_USER_DTO = new TypeToken<HashSet<UserDto>>() {}.getType();
}
