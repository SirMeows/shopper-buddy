package com.he.engelund.api;

import com.he.engelund.dto.TagDto;
import com.he.engelund.entity.Tag;
import com.he.engelund.service.TagService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import static com.he.engelund.config.ModelMapperConfig.SET_TYPE_TAG_DTO;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tags")
public class TagController {

    private ModelMapper modelMapper;

    private TagService tagService;

    @GetMapping("/")
    Set<TagDto> getTags() {
        var tags = tagService.getTags();
        Set<TagDto> tagDtos = modelMapper.map(tags, SET_TYPE_TAG_DTO);
        return tagDtos;
    }

    @PostMapping("/add")
    TagDto addTag(@RequestBody TagDto body) {
        var newTag = modelMapper.map(body, Tag.class);
        var savedTag =  tagService.addTag(newTag);
        return modelMapper.map(savedTag, TagDto.class);
    }
}
