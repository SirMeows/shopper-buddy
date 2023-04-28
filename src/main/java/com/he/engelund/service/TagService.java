package com.he.engelund.service;

import com.he.engelund.entity.Tag;
import com.he.engelund.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@AllArgsConstructor
@Service
public class TagService {

    private TagRepository tagRepository;

    public Set<Tag> getTags() {
        return tagRepository.findAllSet();
        }

    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
