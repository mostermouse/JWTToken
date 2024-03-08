package com.jwt.jwttoken.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {
    private static String secretKey = "java21SpringBootJWTTokenIssueMEthod";

    public String create(
            Map<String, Object> claims,
            LocalDateTime exprieAt
    ) {
        //costumor key
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var _expriAt = Date.from(exprieAt.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setExpiration(_expriAt)
                .compact();
    }

    public void validation(String token) {
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        try {

            var result = parser.parseClaimsJws(token);
            result.getBody().entrySet().forEach(value -> {
                log.info("key : {} , value : {}", value.getKey(), value.getValue());
            });
        } catch (Exception e) {
            if(e instanceof SignatureException){
                throw new RuntimeException("JWT Token Not Valid Exception");

            }else if(e instanceof ExpiredJwtException){
                throw new RuntimeException("JWT Token Expired Exception");
            }else{
                throw new RuntimeException("JWT Token Validation Exception");
            }

        }


    }

}
