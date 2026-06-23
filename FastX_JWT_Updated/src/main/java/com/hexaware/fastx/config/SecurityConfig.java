package com.hexaware.fastx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hexaware.fastx.filter.JwtAuthFilter;
import com.hexaware.fastx.security.FastXUserDetailsService;

// -----------------------------------------------------------
// SecurityConfig  –  the main security setup class
//
// Three things happen here:
//   1. Define which URLs are PUBLIC and which need a login token
//   2. Set up BCrypt password hashing
//   3. Wire together our JwtAuthFilter so it runs on every request
//
// Role-based access summary:
//   PUBLIC         → register, login, search buses, swagger docs
//   ROLE_USER      → view profile, book tickets, cancel bookings, view/pay
//   ROLE_BUS_OPERATOR → manage own buses, view own bookings, manage seats
//   ROLE_ADMIN     → everything (users, operators, all bookings, dashboard)
// -----------------------------------------------------------
@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // allows @PreAuthorize on individual controller methods
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private FastXUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // Disable CSRF – not needed for REST APIs that use JWT (stateless)
                .csrf().disable()

                // ── URL-level access rules ──────────────────────────────────────
                .authorizeHttpRequests()

                // PUBLIC routes – no token needed
                .requestMatchers(
                        "/api/auth/**",                 // login & register endpoints
                        "/v3/api-docs/**",              // Swagger JSON
                        "/swagger-ui/**",               // Swagger UI
                        "/swagger-ui.html",
                        "/api/buses/search",            // anyone can search for buses
                        "/api/buses/cheapest",          // anyone can view cheapest buses
                        "/api/buses/type",              // anyone can filter by bus type
                        "/api/buses/fare"               // anyone can filter by fare range
                ).permitAll()

                // ADMIN-only routes
                .and().authorizeHttpRequests()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // BUS OPERATOR routes  (operators manage their own buses)
                .and().authorizeHttpRequests()
                .requestMatchers(
                        "/api/buses/create",
                        "/api/buses/update/**",
                        "/api/buses/delete/**",
                        "/api/operators/update/**",
                        "/api/operators/delete/**"
                ).hasRole("BUS_OPERATOR")

                // USER routes  (regular users book, cancel, pay)
                .and().authorizeHttpRequests()
                .requestMatchers(
                        "/api/bookings/create",
                        "/api/bookings/*/cancel",
                        "/api/payments/**",
                        "/api/refunds/**"
                ).hasRole("USER")

                // Everything else just needs any valid token (any logged-in role)
                .and().authorizeHttpRequests()
                .anyRequest().authenticated()

                // ── Session management ──────────────────────────────────────────
                // STATELESS = no HttpSession; every request must carry the JWT token
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // ── Plug in our auth provider and JWT filter ────────────────────
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    // BCrypt is a strong, slow hashing algorithm – ideal for passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Tells Spring Security WHERE to load users from and HOW passwords are encoded
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);   // our custom service
        provider.setPasswordEncoder(passwordEncoder());        // BCrypt
        return provider;
    }

    // AuthenticationManager is needed by the login controller
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
