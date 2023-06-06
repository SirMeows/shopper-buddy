package com.he.engelund.service;

import com.he.engelund.entity.Tag;
import com.he.engelund.repository.TagRepository;
import com.he.engelund.service.interfaces.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@AllArgsConstructor
@Service
public class TagServiceImp implements TagService {

    private TagRepository tagRepository;

    @Override
    public Set<Tag> getTags() {
        return tagRepository.findAllSet();
        }

    @Override
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
