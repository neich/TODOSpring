package org.udg.pds.springtodo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.udg.pds.springtodo.dto.LoginRequest;
import org.udg.pds.springtodo.dto.RegisterRequest;
import org.udg.pds.springtodo.dto.UserDto;
import org.udg.pds.springtodo.dto.UserFullDto;
import org.udg.pds.springtodo.service.UserService;

import java.net.URI;

@RequestMapping(path = "/users")
@RestController
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<UserDto> login(HttpSession session,
                                         @Valid @RequestBody LoginRequest request) {
        checkNotLoggedIn(session);
        UserDto user = userService.matchPassword(request);
        session.setAttribute("simpleapp_auth_id", user.id());
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        getLoggedUser(session);
        session.removeAttribute("simpleapp_auth_id");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/register", consumes = "application/json")
    public ResponseEntity<UserDto> register(HttpSession session,
                                            @Valid @RequestBody RegisterRequest request) {
        checkNotLoggedIn(session);
        UserDto created = userService.register(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/../{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserFullDto> getUserProfile(HttpSession session) {
        Long loggedUserId = getLoggedUser(session);
        return ResponseEntity.ok(userService.getUserProfile(loggedUserId));
    }

    @GetMapping(path = "/check")
    public ResponseEntity<Void> checkLoggedIn(HttpSession session) {
        getLoggedUser(session);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> getPublicUser(HttpSession session,
                                                 @PathVariable("id") Long userId) {
        getLoggedUser(session);
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(HttpSession session,
                                           @PathVariable("id") Long userId) {
        Long loggedUserId = getLoggedUser(session);
        userService.deleteUser(loggedUserId, userId);
        session.removeAttribute("simpleapp_auth_id");
        return ResponseEntity.noContent().build();
    }
}
