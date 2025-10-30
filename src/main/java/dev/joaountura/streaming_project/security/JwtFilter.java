package dev.joaountura.streaming_project.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtComponent jwtComponent;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = findCookie(request, "jwt-token");

        if(cookie == null){
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = jwtComponent.verifyAndDecode(cookie.getValue());

        jwtComponent.verifyExpiration(decodedJWT);

        List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles")
                .asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        var auth = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);


    }


    private  Cookie findCookie(HttpServletRequest request, String cookieName) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {

            return null;
        }

        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(cookie1 -> Objects.equals(cookie1.getName(), cookieName)).findFirst();

        return cookie.orElse(null);

    }
}
