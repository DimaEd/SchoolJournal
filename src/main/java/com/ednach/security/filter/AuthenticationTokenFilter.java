package com.ednach.security.filter;

import com.ednach.service.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    /**
     * Gets token from request. Validates it. Loads User by username from token. Creates Authentication object with all
     * necessary data and registries it SecurityContext.
     *
     * <p>Invokes filter chain once per request within a single request thread that stores a request attribute for
     * "already filtered", proceeding without filtering again if the attribute is already there.
     *
     * @param httpRequest         - the request to pass along the filter chain (a HttpServletRequest object)
     * @param httpServletResponse - the response to pass along the chain (a HttpServletResponse object)
     * @param chain               - a FilterChain object
     * @throws ServletException - if an exception has occurred that interferes with
     *                          the filterChain's normal operation
     * @throws IOException      - if an I/O related error has occurred during the processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, FilterChain chain) throws IOException, ServletException {
        String token = getToken(httpRequest);
        if (token != null && tokenService.validate(token)) {
            String username = tokenService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(httpRequest, httpServletResponse);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        return authHeader != null && authHeader.startsWith(BEARER)
                ? authHeader.replace(BEARER, "") : authHeader;
    }
}