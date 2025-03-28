package locadora_api_java.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import locadora_api_java.jwt.JwtToken;
import locadora_api_java.jwt.JwtUserDetailsService;
import locadora_api_java.service.AuthService;
import locadora_api_java.web.controller.dto.auth.LoginDTO;
import locadora_api_java.web.controller.dto.auth.LoginResponseDTO;
import locadora_api_java.web.controller.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthenticationController(final JwtUserDetailsService detailsService, AuthenticationManager authenticationManager, AuthService authService) {
        this.detailsService = detailsService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody @Valid LoginDTO dto, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", dto.name());
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.name(), dto.password());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.name());

            LoginResponseDTO loginResponseDTO = authService.infoLogin(dto.name(), token);

            return ResponseEntity.ok(loginResponseDTO);
        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials from username '{}'", dto.name());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }
}