package org.udg.pds.springtodo.dto.Task;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.udg.pds.springtodo.dto.Tag.TagMapper;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.Task;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface TaskMapper {

    TaskDto taskToTaskDto(Task task);
    List<TaskDto> map(Collection<Task> tasks);

    @Mapping(expression = "java( task.getUser().getId() )", target = "ownerId")
    TaskFullDto taskToTaskFullDto(Task task);

}
