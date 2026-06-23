package com.hexaware.fastx.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.fastx.entity.BusOperator;
import com.hexaware.fastx.entity.User;
import com.hexaware.fastx.repository.BusOperatorRepository;
import com.hexaware.fastx.repository.UserRepository;

// -----------------------------------------------------------
// FastXUserDetailsService  –  "UserDetailsService implementation"
//
// Spring Security calls loadUserByUsername() automatically
// during login to fetch the account from the database.
//
// FastX has TWO types of accounts:
//   1. Regular User      (stored in `users` table,          role comes from Role entity)
//   2. Bus Operator      (stored in `bus_operators` table,  role = "BUS_OPERATOR" always)
//
// We search both tables.  If neither has the email → throw error.
// -----------------------------------------------------------
@Service
public class FastXUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusOperatorRepository busOperatorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Try to find a regular User by email
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Get the role name from the Role entity linked to this User
            // e.g.  "USER"  or  "ADMIN"
            String roleName = (user.getRole() != null) ? user.getRole().getRoleName() : "USER";

            return new FastXUserDetails(user.getEmail(), user.getPassword(), roleName);
        }

        // 2. If not found as a User, try the Bus Operator table
        Optional<BusOperator> operatorOpt = busOperatorRepository.findByEmail(email);
        if (operatorOpt.isPresent()) {
            BusOperator operator = operatorOpt.get();
            // All bus operators always get the BUS_OPERATOR role
            return new FastXUserDetails(operator.getEmail(), operator.getPassword(), "BUS_OPERATOR");
        }

        // 3. Email not found in either table → login should fail
        throw new UsernameNotFoundException("No account found with email: " + email);
    }
}
