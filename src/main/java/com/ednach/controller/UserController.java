package com.ednach.controller;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.dto.request.UserRequestDto;
import com.ednach.model.Role;
import com.ednach.model.User;
import com.ednach.service.RoleService;
import com.ednach.service.UserService;
import com.ednach.dto.responce.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final Mapper mapper;
    private final RoleService roleService;
    private final UserService userService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserResponseDto>> getAll() {
        final List<User> users = userService.findAll();
        final List<UserResponseDto> userResponseDtoList = users.stream()
                .map((user) -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{firstName}", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDto> getName(@PathVariable String firstName) {
        final UserResponseDto userResponseDto = mapper.map(userService.findByFirstName(firstName), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping("{id:[\\d]+}")
    public ResponseEntity<UserResponseDto> getOne(@PathVariable Long id) {
        final UserResponseDto userResponseDto = mapper.map(userService.findById(id), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

//    @RequestMapping(value = "{firstName},{lastName}", method = RequestMethod.GET)
//    public ResponseEntity<UserResponseDto> getByFirstName(@PathVariable String firstName,@PathVariable String lastName) {
//        final UserResponseDto userResponse = mapper.map(userService.findByFirstNameAndLastName(firstName,lastName), UserResponseDto.class);
//        return new ResponseEntity<>(userResponse, HttpStatus.OK);
//    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto userRequestDto) {
        userRequestDto.setId(null);
        final UserResponseDto userResponseDto = mapper.map(userService.save(getUser(userRequestDto)), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserResponseDto> update(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, userRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.user.unexpectedId", new Object[]{}));
        }
        final UserResponseDto userResponseDto = mapper.map(userService.update(getUser(userRequestDto)), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    private User getUser(UserRequestDto userRequestDto) {
        final User user = mapper.map(userRequestDto, User.class);
        final Set<Role> roles = userRequestDto.getRolesId().stream().map(roleId -> {
            Role role = new Role();
            role.setId(roleId);
            return role;
        }).collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }
}
