package com.hexaware.fastx.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// -----------------------------------------------------------
// FastXUserDetails  –  "UserDetails implementation"
//
// Spring Security does not know about our User / BusOperator
// entities.  It only understands its own UserDetails interface.
// This class acts as a bridge:
//   our entity  →  FastXUserDetails  →  Spring Security
//
// It stores: username (email), password, and the role
// -----------------------------------------------------------
public class FastXUserDetails implements UserDetails {

    private String username;   // the email address used to log in
    private String password;
    private String role;       // e.g.  "ROLE_USER"  /  "ROLE_BUS_OPERATOR"  /  "ROLE_ADMIN"

    // Constructor used for regular Users
    public FastXUserDetails(String email, String password, String roleName) {
        this.username = email;
        this.password = password;
        // Spring Security expects role strings prefixed with "ROLE_"
        this.role = "ROLE_" + roleName.toUpperCase();
    }

    // Returns the list of roles / permissions this account has
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // SimpleGrantedAuthority wraps a role string so Spring Security can read it
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;  // email is the username in FastX
    }

    // The four methods below are account-status checks.
    // We return true for all of them (no locking / expiry logic yet).
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
