/*
 * AuthService.java -- Defines AuthService class 
 * This code is implemented as part of assignment   
 * course Service Oriented Computing of MTECH Program Software Engineering
 * Student Name : Deepish Sharma
 * Student Id   : 2022MT93012
 * Course       : Scalable Services
 * Program      : MTECH Software Engineering
 * Student Email: 2022MT93012@wilp.bits-pilani.ac.in
 */

package serviceorientedcomputing.assignment.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The <code>AuthService</code> is micro service that provides REST/JSON
 * interface to handle user authentication. It provides functionality to generate
 * token
 *
 * @author Deepish Sharma
 */
@Slf4j
@RestController
public class AuthService {

    public static final String SECRET = 
        "ServiceOrientedComputingAssignmentDeepishDMartStoreCaseStudy";

    @Autowired
    private UserRepository userRepository;

    /**
     * Generates token for given valid credentials
     * @param request       : AuthRequest that holds username & password    
     * @return              : String that has Jwt token
     */
    @PostMapping("/auth")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest request) {
        log.info("In AuthService.generateToken()...");
        Optional<UserEntity> optionalEntity = userRepository
            .findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (optionalEntity.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials!");
        }
        UserEntity dbEntity = optionalEntity.get();
        if (!dbEntity.isEnabled()) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN).body("User account is disabled!");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", String.valueOf(dbEntity.getId()));
        claims.put("firstName", dbEntity.getFirstName());
        claims.put("lastName", dbEntity.getLastName());
        claims.put("role", dbEntity.getRole());
        
        String jwtToken = createToken(dbEntity.getUsername(), claims);
        log.debug("Jwt Token: "+jwtToken);

        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }
    
    /**
     * Creates token for given username and claims.
     * @param username      : username for which token is created
     * @param claims        : additional user information 
     * @return              : String represent JwtToken
     */
    private String createToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) //10 minutes
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    }
    
    /**
     * Get Key used as Sign 
     * @return 
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
