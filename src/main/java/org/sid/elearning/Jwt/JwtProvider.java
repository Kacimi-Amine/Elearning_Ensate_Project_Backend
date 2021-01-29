package org.sid.elearning.Jwt;


import org.sid.elearning.Auth.MyUserDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String JWT_SECRET ;
    @Value("${jwt.expiration}")
    private int JWT_EXPIRATION  ;


    public   String GenerateToken(Authentication authentication){
        MyUserDetails userDetails  = (MyUserDetails) authentication.getPrincipal();
        String ACCESS_TOKEN = Jwts.builder().
                setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date( (new Date()).getTime()+JWT_EXPIRATION*1000))
                .signWith(SignatureAlgorithm.HS256 , JWT_SECRET)
                .compact();
        return ACCESS_TOKEN ;
    }

    public boolean validateJwtToken(String AUTH_TOKEN){
        // check if  the token is valid
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(AUTH_TOKEN) ;
            return true  ;
        }catch (SignatureException e){
            System.out.print("invalid jwt signature "+e.getMessage());
        }catch (MalformedJwtException e ){
            System.out.println("invalid jwt  ");
        }catch (ExpiredJwtException e){
            System.out.println(" expired access token  ");
        }catch (UnsupportedJwtException e ){
            System.out.println(" Unsupported  access token  ");
        }catch(IllegalArgumentException e ){
            System.out.println("JWT claims string is empty");
        }
        return false  ;
    }


    public String getUserNameFromJwt (String token ){

        try {
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        }catch (Exception e ){
            return null   ;
        }

    }

}
