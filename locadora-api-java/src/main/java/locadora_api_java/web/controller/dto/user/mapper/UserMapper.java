package locadora_api_java.web.controller.dto.user.mapper;

import locadora_api_java.entity.User;
import locadora_api_java.enums.UserRole;
import locadora_api_java.web.controller.dto.user.UserCreateDTO;
import locadora_api_java.web.controller.dto.user.UserDTO;
import locadora_api_java.web.controller.dto.user.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(final UserCreateDTO userDTO) {

        User user = new User();

        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setRole(UserRole.valueOf(userDTO.role()));

        return user;
    }

    public User toUserUpdate(final UserDTO userDTO) {

        User user = new User();

        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setRole(UserRole.valueOf(userDTO.role()));

        return user;
    }

    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getRole().toString()
        );
    }
}
