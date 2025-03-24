package locadora_api_java.web.controller;

import jakarta.validation.Valid;
import locadora_api_java.entity.User;
import locadora_api_java.service.UserService;
import locadora_api_java.web.controller.dto.user.*;
import locadora_api_java.web.controller.dto.user.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO request) {
        User user = userService.createUser(userMapper.toUser(request));
        var response = userMapper.toDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        User user = userService.findUser(id);
        var response = userMapper.toDTO(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity updateUser(@Valid @RequestBody UserUpdateDTO request) {
        User user = userService.updateUser(request.id(), userMapper.toUserUpdate(new UserDTO(request.name(), request.email(), request.role())));
        var response = userMapper.toDTO(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserPaginatedResponseDTO<UserResponseInfoDTO>> getAllBooks(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        var response = userService.getFilteredUser(search, page, size, sort, direction);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recovery/requestRecoveryMail")
    public void sendEmail(@Valid @RequestBody UserEmail userEmail) {
        userService.sendEmail(userEmail);
    }

    @PostMapping("/recovery/changePassword")
    public void changePassword(@Valid @RequestBody UserChangePasswordDTO request) {
        userService.changePassword(request.newPassword(), request.repeatPassword(), request.email(), request.otpCode());
    }
}
