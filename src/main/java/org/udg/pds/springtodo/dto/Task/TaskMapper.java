package org.udg.pds.springtodo.dto.Task;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.Task;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto taskToTaskDto(Task task);
    List<TaskDto> map(Collection<Task> tasks);

    @Mapping(expression = "java( task.getUser().getId() )", target = "ownerId")
    TaskFullDto taskToTaskFullDto(Task task);

    Collection<String> mapToString(Collection<Tag> tags);
    default String tagToString(Tag tag) {
        return tag.getName();
    }

    // Long userToIdDto(User user);
}
