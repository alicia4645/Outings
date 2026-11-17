package com.example.outings.auth.infrastructure.in.web;

import com.example.outings.auth.application.port.command.LoginCommand;
import com.example.outings.auth.application.port.command.LoginResult;
import com.example.outings.auth.application.port.command.SignupCommand;
import com.example.outings.auth.application.port.in.LoginUseCase;
import com.example.outings.auth.application.port.in.SignupUseCase;
import com.example.outings.auth.domain.exceptions.InvalidCredentialsException;
import com.example.outings.auth.domain.exceptions.UserAlreadyExistsException;
import com.example.outings.auth.domain.exceptions.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController 
@RequestMapping("api/auth")
public class AuthenticationController {

    private SignupUseCase signupUseCase;
    private LoginUseCase loginUseCase;

    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    public AuthenticationController(SignupUseCase signupUseCase, LoginUseCase loginUseCase){
        this.signupUseCase = signupUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginCommand loginCommand, HttpServletRequest request){
        try{
            LoginResult loginResult = loginUseCase.handle(loginCommand);

            List<SimpleGrantedAuthority> authorities = loginResult.roles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .toList();
            
            Authentication authentication = UsernamePasswordAuthenticationToken
                .authenticated(loginCommand.email(), null, authorities);
            

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return new ResponseEntity<>("User logged in successfully!", HttpStatus.OK);
        }catch(UserNotFoundException e){
            return new ResponseEntity<>("Invalid email or password!", HttpStatus.UNAUTHORIZED);
        }catch(InvalidCredentialsException e){
            return new ResponseEntity<>("Invalid email or password!", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupCommand signupCommand, HttpServletRequest request){
        try{
            signupUseCase.handle(signupCommand);
        
            return new ResponseEntity<>("User Signed up successfully!", HttpStatus.OK);
        }catch(UserAlreadyExistsException e){
            return new ResponseEntity<>("User already exists!", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        logoutHandler.logout(request, response, authentication);

        request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();

        return new ResponseEntity<>("User Logged out successfully!", HttpStatus.OK);
    }
}
