package uz.pdp.demo_book.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.demo_book.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthService myAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
         // requetdan tokenni olish
        String authorization = request.getHeader("Authorization");

        // token tekshirilayapti
        if (authorization!=null && authorization.startsWith("Bearer")) {

            // being validating
            boolean validateToken = jwtProvider.validateToken(authorization.split(" ")[1]);

            if (validateToken) {

                //tokendan usernameni oldik
                String username = jwtProvider.getUsername(authorization);
                System.out.println(username);


                //shu yergacha kelgan odam sytemga kirgan bo'ladi!

                UserDetails userDetails = myAuthService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                //sytemga kim kirganini o'rnatdik
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            filterChain.doFilter(request,response);
        }
    }
}
