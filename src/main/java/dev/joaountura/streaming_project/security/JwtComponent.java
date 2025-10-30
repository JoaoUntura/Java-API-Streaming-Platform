package dev.joaountura.streaming_project.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import dev.joaountura.streaming_project.models.UserApp;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtComponent {


    @Value("${jwt.issuer}")
    private  String issuer;

    @Value("${jwt.secret}")
    private String secret;

    public final int duration = 86400;


    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(this.secret);
    }


    public String encodeToken(UserApp userApp){
        Instant expirationTime = Instant.now().plusSeconds(this.duration);
        return JWT.create()
                .withIssuer(this.issuer)
                .withSubject(userApp.getExternalId())
                .withClaim("roles", userApp.getAuthorities().stream().map(Object::toString).toList())
                .withExpiresAt(expirationTime)
                .sign(this.algorithm);
    }



    public DecodedJWT verifyAndDecode(String token){
        JWTVerifier verifier = JWT.require(this.algorithm).withIssuer(this.issuer).build();

        return verifier.verify(token);

    }

    public void verifyExpiration(DecodedJWT decodedJWT) throws ServletException {
        if(Instant.now().isAfter(decodedJWT.getExpiresAtAsInstant())){
            throw new ServletException("Expired JWT Token");
        }
    }

}
