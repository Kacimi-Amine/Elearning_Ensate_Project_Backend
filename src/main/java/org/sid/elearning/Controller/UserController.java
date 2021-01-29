package org.sid.elearning.Controller;


import org.sid.elearning.Auth.MyUserDetails;
import org.sid.elearning.Jwt.JwtProvider;
import org.sid.elearning.Jwt.jwtResponse;
import org.sid.elearning.Models.User;
import org.sid.elearning.Repositories.UserRepository;
import org.sid.elearning.SecurityConfi.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/auth/api")
public class UserController {


    @Autowired
    private JwtProvider jwtProvider ;
    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private MyPasswordEncoder mypasswordEncoder ;

    @Autowired
    private AuthenticationManager authenticationManager ;

    @PostMapping(value ="/login")
    public ResponseEntity<?> login(@RequestBody User user ){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())) ;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String ACCESS_TOKEN = jwtProvider.GenerateToken(authentication) ;
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new jwtResponse(ACCESS_TOKEN,userDetails.getUsername(), userDetails.getAuthorities()) );
    }

    @PostMapping(value =  "/createAccount")
    public ResponseEntity<?> register(@RequestBody User user){
        User existUser = userRepository.findByUsername(user.getUsername()) ;
        if (existUser !=  null ){
            return new ResponseEntity<>("username is already used" , HttpStatus.BAD_REQUEST) ;
        }
        user.setPassword(mypasswordEncoder.MypasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(  "done",HttpStatus.OK) ;
    }


    @GetMapping(value="/logout" )
    public ResponseEntity<?> logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){new SecurityContextLogoutHandler().logout(request, response, auth);}
        return  new ResponseEntity<>("logout  successfully!"  , HttpStatus.OK);
    }

   // @PreAuthorize("isAuthenticated()")
}
