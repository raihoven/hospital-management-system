package com.amiradilzhanaisha.hospitalmanagementsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.amiradilzhanaisha.hospitalmanagementsystem.dto.AmirAdilzhanAishaApiErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AmirAdilzhanAishaAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public AmirAdilzhanAishaAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        AmirAdilzhanAishaApiErrorResponse body = new AmirAdilzhanAishaApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                "Недостаточно прав для выполнения операции",
                request.getRequestURI()
        );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
