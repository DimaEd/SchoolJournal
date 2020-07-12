package com.ednach.service.security.impl;

import com.ednach.model.User;
import com.ednach.security.model.AuthenticationUserDetails;
import com.ednach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of UserDetailsService interface which loads user-specific data
 */
@RequiredArgsConstructor
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    /**
     * Locates the user based on the email and checks if an authority is granted to the User
     *
     * @param email - the email identifying the user whose data is required
     * @return - new instance of AuthenticationUserDetails.
     * @throws UsernameNotFoundException - if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userService.findByEmail(email);
        final Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new AuthenticationUserDetails(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }
}