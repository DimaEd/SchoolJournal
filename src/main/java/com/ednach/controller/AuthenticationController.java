package com.ednach.controller;

//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;

//
///**
// * Controller for authentication
// */
//
//@RestController
//@RequestMapping("/authentication")
//public class AuthenticationController {
//
//    private final UserService userService;
//    private final RoleService roleService;
//    private final TokenService tokenService;
//    private final AuthenticationManager authenticationManager;
//    private final PasswordEncoder encoder;
//    private final Mapper mapper;
//
//    public AuthenticationController(UserService userService, RoleService roleService, TokenService tokenService, AuthenticationManager authenticationManager, PasswordEncoder encoder, Mapper mapper) {
//        this.userService = userService;
//        this.roleService = roleService;
//        this.tokenService = tokenService;
//        this.authenticationManager = authenticationManager;
//        this.encoder = encoder;
//        this.mapper = mapper;
//    }
//
//    @GetMapping("/signIn")
//    public TokenResponseDTO authenticateUser(@RequestParam String username, @RequestParam String password) {
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authentication = authenticationManager.authenticate(token);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new TokenResponseDTO(tokenService.generate(authentication));
//    }
//
//    @PostMapping("/refresh")
//    public TokenResponseDTO refreshToken(@RequestParam String token) {
//        return new TokenResponseDTO(tokenService.refresh(token));
//    }
//
//    @PostMapping("/signUp")
//    public UserResponseDto registerUser(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
//        final User user = new User();
//        user.setFirstName(userRegistrationRequestDTO.getFirstName());
//        user.setLastName(userRegistrationRequestDTO.getLastName());
//        user.setLogin(userRegistrationRequestDTO.getLogin());
//        user.setPassword(encoder.encode(userRegistrationRequestDTO.getPassword()));
//        final Set<Role> roles = userRegistrationRequestDTO.getRoles().stream()
//                .map(roleService::findByName)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        user.setRoles(roles);
//        return mapper.map(userService.save(user), UserResponseDto.class);
//    }
//}
