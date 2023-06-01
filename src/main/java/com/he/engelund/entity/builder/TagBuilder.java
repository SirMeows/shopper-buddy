package com.he.engelund.entity.builder;

import com.he.engelund.entity.Tag;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TagBuilder {

    private Tag tag;

    public static TagBuilder create() {
        var tagBuilder = new TagBuilder();
        var tag = new Tag();
        tagBuilder.setTag(tag);
        return tagBuilder;
    }

    private void setTag(Tag tag) {
        this.tag = tag;
    }

    public TagBuilder addName(String name) {
        tag.setName(name);
        return this;
    }

    public Tag build() {
        var temp = tag;
        tag = null;
        return temp;
    }
}
