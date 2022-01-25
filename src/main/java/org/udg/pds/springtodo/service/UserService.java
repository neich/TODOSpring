package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Task;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserRepository crud() {
        return userRepository;
    }

    public User matchPassword(String username, String password) {

        List<User> uc = userRepository.findByUsername(username);

        if (uc.size() == 0) throw new ServiceException("User does not exists");

        User u = uc.get(0);

        if (u.getPassword().equals(password))
            return u;
        else
            throw new ServiceException("Password does not match");
    }

    public User register(String username, String email, String password) {

        List<User> uEmail = userRepository.findByEmail(email);
        if (uEmail.size() > 0)
            throw new ServiceException("Email already exist");


        List<User> uUsername = userRepository.findByUsername(username);
        if (uUsername.size() > 0)
            throw new ServiceException("Username already exists");

        User nu = new User(username, email, password);
        userRepository.save(nu);
        return nu;
    }

    public User getUser(Long id) {
        Optional<User> uo = userRepository.findById(id);
        if (uo.isPresent())
            return uo.get();
        else
            throw new ServiceException(String.format("User with id = % dos not exists", id));
    }

    public User getUserProfile(long id) {
        User u = this.getUser(id);
        for (Task t : u.getTasks())
            t.getTags();
        return u;
    }
}
