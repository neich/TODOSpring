package org.udg.pds.springtodo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.udg.pds.springtodo.dto.ErrorResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOG = Logger.getLogger(CustomAccessDeniedHandler.class.getName());

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException exc) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOG.warning("User: " + auth.getName()
                + " attempted to access the protected URL: "
                + request.getRequestURI());
        }

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            "Access denied: " + exc.getMessage(),
            LocalDateTime.now());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(errorResponse);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(json);
        response.flushBuffer();
    }
}
