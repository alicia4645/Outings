package com.example.outings.auth.infrastructure.out.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.outings.auth.application.port.out.UserRepository;
import com.example.outings.auth.domain.model.User;


/* Load user specific data from database whenever a user tries to log in. Returns UserDetails object
representing the authenticated user.
*/
@Service 
public class CustomUserDetails implements UserDetailsService{
   
    private final UserRepository userRepository;

    public CustomUserDetails(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override 
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
       
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(email + " not found."));

        Set<GrantedAuthority> authorities = user
            .getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }
}
