package com.inn.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JWTHelper {

    private final String secret = "afljhgshvasfkjbsdjhvsdcmnsdclhsbsjbdlaaaskwmsdjhsdssdjhafsdjasbdaaaaadcsdvdvdbsbfcvfsbnb";  // Use a secure random key
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;  // Token validity in milliseconds

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
    	return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(CustomUserDetails userDetails) {
    	
    	System.out.println("====UserDetails : "+userDetails.getId());
//    	System.out.println("===User Id : "+ userDetails.getId());
    	
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
//                .claim("id", userDetails.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        System.out.println("%%%% I am here : "+userDetails);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
