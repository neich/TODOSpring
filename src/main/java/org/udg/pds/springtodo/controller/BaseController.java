package org.udg.pds.springtodo.controller;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.udg.pds.springtodo.controller.exceptions.ControllerException;

import javax.servlet.http.HttpSession;

/**
 * Created by imartin on 21/02/17.
 */
public class BaseController {

    static String OK_MESSAGE = "\"ok\"";

    Long getLoggedUser(HttpSession session) {

        if (session == null) {
            throw new ControllerException("No sessions available!");
        }

        Long userId = (Long) session.getAttribute("simpleapp_auth_id");
        // Check if the session has the attribute "simpleapp_auth_id"
        if (userId == null)
            throw new ControllerException("User is not authenticated!");

        return userId;
    }

    void checkNotLoggedIn(HttpSession session) {
        // Access to the HTTP session

        if (session == null) {
            throw new ControllerException("No sessions available!");
        }

        Long userId = (Long) session.getAttribute("simpleapp_auth_id");
        // Check if the session has the attribute "simpleapp_auth_id"
        if (userId != null)
            throw new ControllerException("User is already authenticated!");
    }

    MappingJacksonValue toResponse(Object pojo, Class<?> view) {
        final MappingJacksonValue result = new MappingJacksonValue(pojo);
        result.setSerializationView(view);
        return result;
    }

}
