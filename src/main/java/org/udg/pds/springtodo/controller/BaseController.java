package org.udg.pds.springtodo.controller;

import jakarta.servlet.http.HttpSession;
import org.udg.pds.springtodo.exception.ServiceException;

public class BaseController {

    Long getLoggedUser(HttpSession session) {
        if (session == null) {
            throw new ServiceException("No sessions available!");
        }

        Long userId = (Long) session.getAttribute("simpleapp_auth_id");
        if (userId == null) {
            throw new ServiceException("User is not authenticated!");
        }

        return userId;
    }

    void checkNotLoggedIn(HttpSession session) {
        if (session == null) {
            throw new ServiceException("No sessions available!");
        }

        Long userId = (Long) session.getAttribute("simpleapp_auth_id");
        if (userId != null) {
            throw new ServiceException("User is already authenticated!");
        }
    }
}
