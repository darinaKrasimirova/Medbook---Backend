package bg.medbook;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTUtil {
    private static final String ISSUER = "Medbook";

    @Value("${jwt_secret}")
    private String secret;

    @Value("${jwt_expiration}")
    private long expirationTimeInMs;

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
	return JWT.create()//
		.withSubject("User Details")//
		.withClaim("username", username)//
		.withIssuedAt(new Date())//
		.withIssuer(ISSUER)//
		.withExpiresAt(Date.from(new Date().toInstant().plusMillis(expirationTimeInMs)))//
		.sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
	JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))//
		.withSubject("User Details")//
		.withIssuer(ISSUER)//
		.build();
	DecodedJWT jwt = verifier.verify(token);
	return jwt.getClaim("username").asString();
    }

}