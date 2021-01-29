package org.sid.elearning.Jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class jwtResponse {
    private final  String type =  "Bearer" ;
    private String token  ;
    private String username ;
    private Collection<? extends GrantedAuthority> authorities ;
}