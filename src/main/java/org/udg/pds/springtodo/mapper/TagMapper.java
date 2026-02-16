package org.udg.pds.springtodo.mapper;

import org.mapstruct.Mapper;
import org.udg.pds.springtodo.dto.TagDto;
import org.udg.pds.springtodo.model.Tag;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto tagToTagDto(Tag tag);

    List<TagDto> toTagDtoList(Collection<Tag> tags);

    Collection<String> mapToString(Collection<Tag> tags);

    default String tagToString(Tag tag) {
        return tag.getName();
    }
}
