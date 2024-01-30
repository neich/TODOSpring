package org.udg.pds.springtodo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.udg.pds.springtodo.Global;
import org.udg.pds.springtodo.entity.Error;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Logger;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    public static final Logger LOG
        = Logger.getLogger(String.valueOf(CustomAccessDeniedHandler.class));

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException exc) throws IOException, ServletException {

        Authentication auth
            = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOG.warning("User: " + auth.getName()
                + " attempted to access the protected URL: "
                + request.getRequestURI());
        }

        Error errorObj = new Error(Global.AppDateFormatter.format(ZonedDateTime.now()),
            HttpStatus.BAD_REQUEST.value(),
            "Access denied",
            exc.getMessage());

        String json = new ObjectMapper().writeValueAsString(errorObj);

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(json);
        response.flushBuffer();
    }
}
