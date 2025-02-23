package com.example.phonics.service;


import com.example.phonics.entity.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {


    private final String secret_key = "mysecretkey";

    private final long accessTokenValidity = 30*60*1000;  //valid till 30 minutes

    private final JwtParser jwtParser;

    public JwtService()
    {
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    //this
    public String createToken(User user , Map<String , Object> extraClaims) {
System.out.println(user.getId());

            return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(String.valueOf(user.getId()))

                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .signWith(SignatureAlgorithm.HS256, secret_key)
                    .compact();
        }

    private Claims parseJwtClaims(String token)
    {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    private String resolveToken(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    //this
    public Claims resolveClaimsFromRequest(HttpServletRequest req)
    {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public Claims resolveClaimsFromToken(String  token)
    {
        try {
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }


    private boolean isTokenExpired(Date expirationDate) throws AuthenticationException
    {
        try {
            if(expirationDate.before(new Date()))
                return true;
            else
                return false;
        } catch (Exception e) {
            throw e;
        }
    }


    //this
    public boolean isTokenValid(String accessToken , UserDetails userDetails) {
        String username = userDetails.getUsername();
        Claims claims = parseJwtClaims(accessToken);
        return username.equals(claims.getSubject()) && !isTokenExpired(claims.getExpiration());
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }

}
