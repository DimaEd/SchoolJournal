package com.ednach.security.model;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Objects;
//
///**
// * Provides core user information related to user's authentication
// */
//@AllArgsConstructor
//public class AuthenticationUserDetails implements UserDetails {
//
//    @Getter
//    private Long id;
//
//    private String username;
//
//    private String password;
//
//    private Collection<? extends GrantedAuthority> authorities;
//
//    /**
//     * Returns the username used to authenticate the User. Cannot return <code>null</code>.
//     *
//     * @return - username
//     */
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    /**
//     * Returns the password used to authenticate the User
//     *
//     * @return - password
//     */
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    /**
//     * Returns the authorities granted to the User
//     *
//     * @return - the authorities, sorted by natural key
//     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    /**
//     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
//     *
//     * @return - <code>true</code> if the user's account is valid (ie non-expired),
//     * <code>false</code> if no longer valid (ie expired)
//     */
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    /**
//     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
//     *
//     * @return - <code>true</code> if the user is not locked, <code>false</code> otherwise
//     */
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    /**
//     * Indicates whether the user's credentials (password) has expired.
//     * Expired credentials prevent authentication.
//     *
//     * @return - <code>true</code> if the user's credentials are valid (ie non-expired),
//     * <code>false</code> if no longer valid (ie expired)
//     */
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    /**
//     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
//     *
//     * @return - <code>true</code> if the user is enabled, <code>false</code> otherwise
//     */
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof AuthenticationUserDetails)) return false;
//        AuthenticationUserDetails that = (AuthenticationUserDetails) o;
//        return Objects.equals(getId(), that.getId()) &&
//                Objects.equals(username, that.username) &&
//                Objects.equals(getPassword(), that.getPassword());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), username, getPassword());
//    }
//}
