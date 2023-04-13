package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.auth.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final String ISSUER = "API Voll.med";

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm());
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro generating JWT token", exception);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    public String getSubject(String jwtToken) {
        try {
            return JWT.require(algorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(jwtToken)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid or expired token", exception);
        }
    }

}
