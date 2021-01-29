package org.sid.elearning.Auth;


import org.sid.elearning.Models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetails implements UserDetails {


    private long id ;
    private  String username ;
    private String password ;


    public MyUserDetails( User user){
        this.id = user.getId();
        this.username = user.getUsername()  ;
        this.password = user.getPassword() ;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return null; }

    @Override
    public String getPassword() { return this.password; }
    @Override
    public String getUsername() { return this.username; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
