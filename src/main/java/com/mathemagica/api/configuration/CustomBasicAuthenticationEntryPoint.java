package com.mathemagica.api.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Azael on 2017/08/04.
 */
@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

        @Override
        public void commence
        (HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
        throws IOException, ServletException {
            //Authentication failed, send error response.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");

            PrintWriter writer = response.getWriter();
            writer.println("HTTP Status 401 : " + authenticationException.getMessage());
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            setRealmName("Mathemagica");
            super.afterPropertiesSet();
        }
}
