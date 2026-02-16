package org.udg.pds.springtodo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.udg.pds.springtodo.dto.TaskDto;
import org.udg.pds.springtodo.dto.TaskFullDto;
import org.udg.pds.springtodo.model.Task;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {TagMapper.class, UserMapper.class})
public interface TaskMapper {

    @Mapping(expression = "java( task.getUser().getId() )", target = "userId")
    TaskDto taskToTaskDto(Task task);

    List<TaskDto> toTaskDtoList(Collection<Task> tasks);

    TaskFullDto taskToTaskFullDto(Task task);
}
