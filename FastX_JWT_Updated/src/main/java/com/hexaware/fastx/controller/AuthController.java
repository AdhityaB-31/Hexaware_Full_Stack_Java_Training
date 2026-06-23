package com.hexaware.fastx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.fastx.dto.AuthRequest;
import com.hexaware.fastx.dto.BusOperatorDto;
import com.hexaware.fastx.dto.UserDto;
import com.hexaware.fastx.security.JwtService;
import com.hexaware.fastx.service.BusOperatorService;
import com.hexaware.fastx.service.UserService;

import jakarta.validation.Valid;

// -----------------------------------------------------------
// AuthController  –  handles all login / register actions
//
// Public endpoints (no token required):
//   POST /api/auth/register/user          → register a new customer
//   POST /api/auth/register/operator      → register a new bus operator
//   POST /api/auth/login                  → login for ALL roles
//
// After a successful login, the client receives a JWT token.
// The client must send this token in the Authorization header
// for all protected requests:
//   Authorization: Bearer <token>
// -----------------------------------------------------------
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BusOperatorService busOperatorService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    // ----------------------------------------------------------
    // REGISTER – Regular User (role = USER)
    // ----------------------------------------------------------
    @PostMapping("/register/user")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        // The service layer will hash the password with BCrypt before saving
        UserDto savedUser = userService.createUser(userDto);
        logger.info("New user registered: {}", savedUser.getEmail());
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // ----------------------------------------------------------
    // REGISTER – Bus Operator (role = BUS_OPERATOR)
    // ----------------------------------------------------------
    @PostMapping("/register/operator")
    public ResponseEntity<BusOperatorDto> registerOperator(@Valid @RequestBody BusOperatorDto operatorDto) {
        BusOperatorDto savedOperator = busOperatorService.createOperator(operatorDto);
        logger.info("New bus operator registered: {}", savedOperator.getEmail());
        return new ResponseEntity<>(savedOperator, HttpStatus.CREATED);
    }

    // ----------------------------------------------------------
    // LOGIN – Works for all roles (USER, BUS_OPERATOR, ADMIN)
    //
    // Step 1: Spring Security verifies email + password
    // Step 2: If valid, we generate and return a JWT token
    //
    // The token encodes the user's email.  On the next request,
    // our JwtAuthFilter decodes the token and loads the user's
    // role from the database automatically.
    // ----------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {

        // Delegate to Spring Security's AuthenticationManager.
        // It calls FastXUserDetailsService.loadUserByUsername() internally,
        // then checks the password with BCrypt.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            // Generate JWT token using the email as the subject
            String token = jwtService.generateToken(authRequest.getEmail());
            logger.info("User logged in: {}", authRequest.getEmail());
            return ResponseEntity.ok(token);  // send token back to the client
        } else {
            logger.warn("Failed login attempt for: {}", authRequest.getEmail());
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}
