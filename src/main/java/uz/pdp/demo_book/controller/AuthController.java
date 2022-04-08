package uz.pdp.demo_book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.demo_book.payload.RegisterDTO;
import uz.pdp.demo_book.payload.UserDto;
import uz.pdp.demo_book.security.JwtProvider;
import uz.pdp.demo_book.service.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDTO dto){
        String register = authService.register(dto);

        return ResponseEntity.ok(register);

    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody UserDto dto) {

        try {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(),
                            dto.getPassword()));
            // token qaytarish kerak :

        String token = jwtProvider.generateToken(dto.getUsername());
            System.out.println(token);

        return ResponseEntity.ok().body(token);




        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Login yoki password xato");
        }
    }
}
