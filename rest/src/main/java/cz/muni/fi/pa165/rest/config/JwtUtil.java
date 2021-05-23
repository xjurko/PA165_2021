package cz.muni.fi.pa165.rest.config;

import cz.muni.fi.pa165.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;


/**
 * @author juraj
 * adapted from https://github.com/Yoh0xFF/java-spring-security-example/blob/master/src/main/java/io/example/configuration/security/JwtTokenUtil.java
 * and https://dzone.com/articles/integrating-spring-boot-and-react-with-spring-secu-1
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final String jwtSecret = "asfg123312faaa1";
    private final String jwtIssuer = "fi.muni.cz";

//    private final Logger logger;

    public String generateAccessToken(UserDto user) {
        return Jwts.builder()
            .setClaims(new HashMap<>(Map.of("isAdmin", user.isAdmin())))
            .setSubject(user.getName())
            .setId(user.getId().toString())
            .setIssuer(jwtIssuer)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();

        return Long.valueOf(claims.getId());
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();

        return claims.getExpiration();
    }

    public boolean isExpired(String token) {
        return (new Date()).after(getExpirationDate(token));
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

}