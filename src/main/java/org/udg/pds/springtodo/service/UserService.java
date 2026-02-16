package org.udg.pds.springtodo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.pds.springtodo.dto.LoginRequest;
import org.udg.pds.springtodo.dto.RegisterRequest;
import org.udg.pds.springtodo.dto.UserDto;
import org.udg.pds.springtodo.dto.UserFullDto;
import org.udg.pds.springtodo.exception.DuplicateResourceException;
import org.udg.pds.springtodo.exception.ResourceNotFoundException;
import org.udg.pds.springtodo.exception.ServiceException;
import org.udg.pds.springtodo.mapper.UserMapper;
import org.udg.pds.springtodo.model.Task;
import org.udg.pds.springtodo.model.User;
import org.udg.pds.springtodo.repository.UserRepository;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public UserDto matchPassword(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        if (!user.getPassword().equals(request.password())) {
            throw new ServiceException("Password does not match");
        }
        return userMapper.userToUserDto(user);
    }

    @Transactional
    public UserDto register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new DuplicateResourceException("Username already exists");
        }

        User user = new User(request.username(), request.email(), request.password());
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        User user = getUserEntity(id);
        return userMapper.userToUserDto(user);
    }

    @Transactional(readOnly = true)
    public UserFullDto getUserProfile(Long id) {
        User user = getUserEntity(id);
        for (Task t : user.getTasks()) {
            t.getTags();
        }
        return userMapper.userToUserFullDto(user);
    }

    @Transactional
    public void deleteUser(Long loggedUserId, Long userId) {
        if (!Objects.equals(loggedUserId, userId)) {
            throw new ServiceException("You cannot delete other users");
        }
        User user = getUserEntity(userId);
        userRepository.delete(user);
    }

    public User getUserEntity(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format("User with id = %d does not exist", id)));
    }

    @Transactional
    public User registerEntity(String username, String email, String password) {
        User user = new User(username, email, password);
        userRepository.save(user);
        return user;
    }
}
