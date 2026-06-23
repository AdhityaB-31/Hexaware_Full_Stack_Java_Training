package com.hexaware.fastx.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hexaware.fastx.security.FastXUserDetailsService;
import com.hexaware.fastx.security.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// -----------------------------------------------------------
// JwtAuthFilter  –  runs ONCE per HTTP request
//
// Flow for every incoming request:
//
//   Request arrives
//      ↓
//   Does it have "Authorization: Bearer <token>" header?
//      NO  → skip (Spring Security will block protected routes automatically)
//      YES → extract token
//              ↓
//           Read username from token
//              ↓
//           Load user from DB
//              ↓
//           Is token valid (signature OK + not expired)?
//              YES → set Authentication in SecurityContext  ← user is now "logged in" for this request
//              NO  → do nothing  ← Spring Security blocks access
//      ↓
//   Continue to the next filter / controller
// -----------------------------------------------------------
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private FastXUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1 – Read the Authorization header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Token must start with "Bearer " (note the space)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);            // strip "Bearer " prefix
            username = jwtService.extractUsername(token); // read the email embedded in the token
        }

        // Step 2 – If we got a username and no one is logged in yet for this request
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Step 3 – Load the full user record from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Step 4 – Verify the token is genuine and not expired
            if (jwtService.validateToken(token, userDetails)) {

                // Step 5 – Create an Authentication object and place it in the SecurityContext
                //           After this line, Spring Security treats this request as "authenticated"
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Step 6 – Always let the request continue down the filter chain
        filterChain.doFilter(request, response);
    }
}
