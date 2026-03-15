package com.task.Security;

import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.ENUM.Permission;
import com.task.Entity.UserAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
                 
    // When we receive verification code with some time duration , ie token remains valid for some time 

    
    // 1) key Generation , provide key and time ---> required for authentication since code is going to work for specific time period 
                                                 // same thing provided in token generation s
    private final Key key;  // final variable value cannot be changed , also ca'nt inherit the class , ca'nt override 
                            // same code will work everywhere 

    private final long expireToken=1000L*60*60*24;   // java dont understand hourwise time , hence convert into milisecond  , only python understand hour wise time
                                    // for speed * milisecond * second * 24 hours 
                                    // How much time will the token will be valid 
    
    
    // Create a JWT Constructor and give key features 
    public JWTUtil() {
        
        String secret=System.getenv("JWT_SECRET");
        
        // Check the key 
        if(secret==null  || secret.isEmpty()) {
            secret="Replace this with some secret key!";
        }
        
        key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); 
    }
    
    
    // Generate the token for Authentication 
    @Autowired
    private RolePermissionConfig rolePermissionConfig;

    public String generateToken(UserAuth user) {

        Set<Permission> permission = rolePermissionConfig.getRoleBasedPermissions().get(user.getRole());
        
        return Jwts.builder()
                .setSubject(user.getUserOfficialEmail())  // check for user
                .claim("role", user.getRole().name())
                .claim("permission", permission.stream().map(Enum::name).collect(Collectors.toList()))
                .setIssuedAt(new Date()) // when started , // Fetches the date from system time 
                .setExpiration(new Date(System.currentTimeMillis()+expireToken)) // checks till expire time from now
                .signWith(key,SignatureAlgorithm.HS256)  // algorithm key will be generated  
                .compact(); // builds the token 
    }
    
    
    // Check for Validation of Token if its build up correctly / not 
    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;

        } catch(Exception e) {

            return false;
        }
    }

    
    // claim feature 
    public Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    
    // Get User email who is getting the token - since email is unique 
    public String getUserOfficialEmail(String token) {

        return getClaims(token).getSubject();
    }
    
    
    // Optional feature 
    public String extractToken(String header) {

        if(header!=null && header.startsWith("Bearer ")) {

            return header.substring(7);
        }

        return null;
    }

}