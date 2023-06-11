package com.he.engelund.service.interfaces;

import com.he.engelund.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface TagService {
    Set<Tag> getTags();

    Tag addTag(Tag tag);

    Tag findByName(String name);
}
