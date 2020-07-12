package com.ednach.controller;

import com.ednach.component.LocalizedMessageSource;
import com.ednach.dto.request.UserRequestDto;
import com.ednach.model.Role;
import com.ednach.model.User;
import com.ednach.service.UserService;
import com.ednach.dto.response.UserResponseDto;
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
    private final UserService userService;
    private final LocalizedMessageSource localizedMessageSource;

    /**
     * Finds all User entities
     *
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        final List<User> users = userService.findAll();
        final List<UserResponseDto> userResponseDtoList = users.stream()
                .map((user) -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds user entity by id and maps it to DTO
     *
     * @param firstName - user entity firstName
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "firstName")
    public ResponseEntity<List<UserResponseDto>> getAllByFirstName(@RequestParam(value = "firstName") String firstName) {
        final List<User> users = userService.findByFirstName(firstName);
        final List<UserResponseDto> userResponseDtoList = users.stream()
                .map((user) -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    /**
     * Finds user entity by id and maps it to DTO
     *
     * @param id - user entity id
     * @return - ResponseEntity with the given body and status code
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> getOne(@PathVariable Long id) {
        final UserResponseDto userResponseDto = mapper.map(userService.findById(id), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * Saves user entity with transferred parameters and maps it to DTO
     *
     * @param userRequestDto - the body of the web request
     * @return - ResponseEntity with the given body and status code
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto userRequestDto) {
        userRequestDto.setId(null);
        final UserResponseDto userResponseDto = mapper.map(userService.save(getUser(userRequestDto)), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * Updates user entity with transferred parameters by entities id and maps it to DTO
     *
     * @param userRequestDto - the body of the web request
     * @param id             - user entity id
     * @return - ResponseEntity with the given body and status code
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> update(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long id) {
        if (!Objects.equals(id, userRequestDto.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.user.unexpectedId", new Object[]{}));
        }
        final UserResponseDto userResponseDto = mapper.map(userService.update(getUser(userRequestDto)), UserResponseDto.class);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    /**
     * Deletes user entity by its id
     *
     * @param id - user entity id
     */
    @DeleteMapping(value = "/{id}")
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
