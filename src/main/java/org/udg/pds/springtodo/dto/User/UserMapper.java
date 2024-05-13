package org.udg.pds.springtodo.dto.User;

import org.mapstruct.Mapper;
import org.udg.pds.springtodo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    UserFullDto userToUserFullDto(User user);

    // Long userToIdDto(User user);
}
