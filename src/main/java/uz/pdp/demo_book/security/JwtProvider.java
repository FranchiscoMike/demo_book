package uz.pdp.demo_book.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    static long expireTime = 36_000_000; /// 10 soat lik web token
    static String secretWord = "Bu Json web token uchun maxsus";

    // create token for user :
    public String generateToken(String username) {

        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date()) // hozir berildi degan ma'noda
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretWord)
                .compact();
        return token;
    }

    // validating token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretWord)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    // getting username from token
    public String getUsername(String token) {
        String username = null;
        try {
            username = Jwts
                    .parser()
                    .setSigningKey(secretWord)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
       return null;
    }

}
