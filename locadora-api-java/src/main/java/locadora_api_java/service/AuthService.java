package locadora_api_java.service;

import locadora_api_java.entity.User;
import locadora_api_java.jwt.JwtToken;
import locadora_api_java.web.controller.dto.auth.LoginResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public LoginResponseDTO infoLogin(String name, JwtToken token) {

        User user = userService.findUserName(name);

        LoginResponseDTO response = new LoginResponseDTO(
                user.getName(),
                token.getToken(),
                user.getRole().toString()
        );

        return response;
    }
}
