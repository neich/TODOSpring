package org.udg.pds.springtodo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.configuration.exceptions.ControllerException;
import org.udg.pds.springtodo.dto.User.UserDto;
import org.udg.pds.springtodo.dto.User.UserFullDto;
import org.udg.pds.springtodo.dto.User.UserMapper;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.service.UserService;

// This class is used to process all the authentication related URLs
@RequestMapping(path = "/users")
@RestController
public class UserController extends BaseController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @PostMapping(path = "/login")
    public UserDto login(HttpSession session, @Valid @RequestBody LoginUser user) {
        checkNotLoggedIn(session);

        User u = userService.matchPassword(user.username, user.password);
        session.setAttribute("simpleapp_auth_id", u.getId());
        return userMapper.userToUserDto(u);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Void> logout(HttpSession session) {

        getLoggedUser(session);

        session.removeAttribute("simpleapp_auth_id");

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}")
    public UserDto getPublicUser(HttpSession session, @PathVariable("id") @Valid Long userId) {

        getLoggedUser(session);

        return userMapper.userToUserDto(userService.getUser(userId));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(HttpSession session, @PathVariable("id") Long userId) {

        Long loggedUserId = getLoggedUser(session);

        if (!loggedUserId.equals(userId))
            throw new ControllerException("You cannot delete other users!");

        userService.deleteUser(userId);
        session.removeAttribute("simpleapp_auth_id");

        return ResponseEntity.noContent().build();
    }


    @PostMapping(path = "/register", consumes = "application/json")
    public UserDto register(HttpSession session, @Valid @RequestBody RegisterUser ru) {

        checkNotLoggedIn(session);
        User u = userService.register(ru.username, ru.email, ru.password);
        return userMapper.userToUserDto(u);

    }

    @GetMapping(path = "/me")
    public UserFullDto getUserProfile(HttpSession session) {

        Long loggedUserId = getLoggedUser(session);

        return userMapper.userToUserFullDto(userService.getUserProfile(loggedUserId));
    }

    @GetMapping(path = "/check")
    public ResponseEntity<Void> checkLoggedIn(HttpSession session) {

        getLoggedUser(session);

        return ResponseEntity.noContent().build();
    }

    private static class LoginUser {

        @NotNull
        public String username;

        @NotNull
        public String password;
    }

    private static class RegisterUser {

        @NotNull
        public String username;

        @NotNull
        public String email;

        @NotNull
        public String password;
    }

}
