//package com.service.security.impl;
//
//
//import com.model.User;
//import com.security.model.AuthenticationUserDetails;
//import com.service.UserService;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * Implementation of UserDetailsService interface which loads user-specific data
// */
//@Service
//@Transactional
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserService userService;
//
//    public UserDetailsServiceImpl(UserService userService) {
//        this.userService = userService;
//    }
//
//    /**
//     * Locates the user based on the username and checks if an authority is granted to the User
//     *
//     * @param username - the username identifying the user whose data is required
//     * @return - new instance of AuthenticationUserDetails.
//     * @throws UsernameNotFoundException - if the user could not be found or the user has no
//     *                                   GrantedAuthority
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        final User user = userService.findByLogin(username);
//        final Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toSet());
//        return new AuthenticationUserDetails(user.getId(), user.getLogin(), user.getPassword(), authorities);
//    }
//}
