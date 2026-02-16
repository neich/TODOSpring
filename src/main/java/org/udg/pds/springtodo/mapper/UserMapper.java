package org.udg.pds.springtodo.mapper;

import org.mapstruct.Mapper;
import org.udg.pds.springtodo.dto.UserDto;
import org.udg.pds.springtodo.dto.UserFullDto;
import org.udg.pds.springtodo.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    UserFullDto userToUserFullDto(User user);
}
