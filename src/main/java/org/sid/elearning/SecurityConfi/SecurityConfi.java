package org.sid.elearning.SecurityConfi;

import org.sid.elearning.Auth.MyUserDetailsService;
import org.sid.elearning.Jwt.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfi extends WebSecurityConfigurerAdapter {

    private final String[] PUBLIC_ENDPOINT = {"/auth/api/**"} ;

    @Autowired
    private MyUserDetailsService myuserDetailsService;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myuserDetailsService)
                .passwordEncoder(myPasswordEncoder.MypasswordEncoder()) ;
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return   new JwtAuthTokenFilter() ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(PUBLIC_ENDPOINT).permitAll()
                .anyRequest().permitAll()
                .and().logout().logoutUrl("/logout").permitAll()
                .and().addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class) ;;
    }

}
