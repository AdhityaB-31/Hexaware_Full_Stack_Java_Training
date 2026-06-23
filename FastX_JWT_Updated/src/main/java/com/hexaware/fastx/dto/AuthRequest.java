package com.hexaware.fastx.dto;

// -----------------------------------------------------------
// AuthRequest  –  the body the client sends to /api/auth/login
//
// Example JSON:
//   {
//     "email": "john@example.com",
//     "password": "Pass@123"
//   }
// -----------------------------------------------------------
public class AuthRequest {

    private String email;
    private String password;

    // Default constructor – needed for JSON deserialization
    public AuthRequest() {}

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
