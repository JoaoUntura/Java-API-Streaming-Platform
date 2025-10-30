package dev.joaountura.streaming_project.security;

import dev.joaountura.streaming_project.models.UserApp;
import dev.joaountura.streaming_project.services.UserServices;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserServices userServices;

    @Autowired
    private JwtComponent jwtComponent;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User auth2User = (OAuth2User) authentication.getPrincipal();

       Map<String, Object> userData =  auth2User.getAttributes();

       String email = userData.get("email").toString();

        String name = userData.get("name").toString();
        String picture = userData.get("picture").toString();

        UserApp userApp = userServices.findUserByEmail(email).orElseGet(() -> userServices.createUser(email, name, picture));

        String jwtToken = jwtComponent.encodeToken(userApp);

        Cookie cookie = new Cookie("jwt-token", jwtToken);
        cookie.setMaxAge(jwtComponent.duration);
        cookie.isHttpOnly();
        cookie.setPath("/");

        response.addCookie(cookie);
        response.sendRedirect("http://localhost:3000/courses");


    }
}
