package com.amiradilzhanaisha.hospitalmanagementsystem.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;

@Component
public class AmirAdilzhanAishaRequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AmirAdilzhanAishaRequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            Principal principal = request.getUserPrincipal();
            String username = principal != null ? principal.getName() : "anonymous";
            String queryString = request.getQueryString();
            String path = queryString == null
                    ? request.getRequestURI()
                    : request.getRequestURI() + "?" + queryString;

            log.info(
                    "HTTP {} {} status={} durationMs={} user={} ip={}",
                    request.getMethod(),
                    path,
                    response.getStatus(),
                    duration,
                    username,
                    request.getRemoteAddr()
            );
        }
    }
}
