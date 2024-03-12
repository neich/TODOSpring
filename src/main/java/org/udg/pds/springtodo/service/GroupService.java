package org.udg.pds.springtodo.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.configuration.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Group;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.repository.GroupRepository;

import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    public Group getGroup(Long id) {
        Optional<Group> g = groupRepository.findById(id);
        if (g.isEmpty()) throw new ServiceException("Task does not exists");
        return g.get();
    }

    @Transactional
    public void addGroup(Long userId, String name, String descripcio) {
        User u = userService.getUser(userId);
        Group g = new Group(name, descripcio);
        g.setOwner(u);
        groupRepository.save(g);
    }
}
