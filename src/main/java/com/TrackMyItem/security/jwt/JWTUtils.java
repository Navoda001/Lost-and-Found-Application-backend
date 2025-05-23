package com.TrackMyItem.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Configuration
public class JWTUtils {
    @Value("${signKey}")
    private String signKey;

    //create Key
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(signKey));
    }

    //generate Token
    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 *0 * 24))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
//validate token
    public boolean validateToken (String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key()).build().parse(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // extract username
    public String getUsernameFromToken (String token) {
        return Jwts.parser()
                .setSigningKey(key()).build()
                .parseSignedClaims(token)
                .getBody().getSubject();
    }
}
