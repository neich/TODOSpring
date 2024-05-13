package org.udg.pds.springtodo.dto.Tag;

import org.mapstruct.Mapper;
import org.udg.pds.springtodo.entity.Tag;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper
{
    TagDto tagToTagDto(Tag tag);
    List<TagDto> map(Collection<Tag> tasks);

    // UserFullDto userToUserFullDto(User user);

}
