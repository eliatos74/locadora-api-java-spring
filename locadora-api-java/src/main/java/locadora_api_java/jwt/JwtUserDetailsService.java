package locadora_api_java.jwt;

import locadora_api_java.entity.User;
import locadora_api_java.enums.UserRole;
import locadora_api_java.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.findUserName(name);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String name) {
        UserRole role = userService.findRoleByName(name);
        return JwtUtils.createToken(name, role.name());
    }

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }
}
