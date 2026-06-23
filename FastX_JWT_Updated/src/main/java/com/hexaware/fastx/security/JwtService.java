package com.hexaware.fastx.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// -----------------------------------------------------------
// JwtService  –  creates and reads JWT tokens
//
// How it works:
//   1. When user logs in, we call generateToken(username)
//      → returns a signed JWT string (like a digital ID card)
//   2. On every future request the client sends that token
//      → we call validateToken() to check it is genuine & not expired
// -----------------------------------------------------------
@Service
public class JwtService {

    // Secret key used to sign tokens.  Keep this private & long!
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    // Token is valid for 30 minutes (in milliseconds)
    private static final long TOKEN_VALIDITY_MS = 1000L * 60 * 30;

    // -------------------------------------------------------
    // GENERATE TOKEN
    // -------------------------------------------------------

    // Step 1 – called after login succeeds
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();  // extra data can be added here
        return createToken(claims, username);
    }

    // Step 2 – builds the actual JWT string
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)                                       // "who" the token belongs to
                .setIssuedAt(new Date(System.currentTimeMillis()))          // "when" it was created
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_MS)) // "when" it expires
                .signWith(getSignKey(), SignatureAlgorithm.HS256)           // digital signature
                .compact();
    }

    // Converts the SECRET string into a cryptographic key object
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // -------------------------------------------------------
    // READ / VALIDATE TOKEN
    // -------------------------------------------------------

    // Extracts all claims (payload) from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Generic helper – extracts any single piece of data from claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extracts the username ("subject") embedded in the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extracts the expiry date embedded in the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Returns true if the token has passed its expiry time
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Final check: username matches AND token has not expired
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
