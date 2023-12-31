package com.mtvs.crimecapturetv.configuration;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String requestUri = request.getRequestURI();

        if (requestUri.contains("/api/v1/store/add-store")) {
            request.setAttribute("msg", "관리자 등급 유저만 작성 가능합니다.");
            request.setAttribute("nextPage", "/");
            request.getRequestDispatcher("/error/redirect").forward(request, response);
        }  else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}

