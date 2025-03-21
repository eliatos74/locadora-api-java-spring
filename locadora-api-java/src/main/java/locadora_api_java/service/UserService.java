package locadora_api_java.service;

import jakarta.validation.constraints.NotNull;
import locadora_api_java.entity.User;
import locadora_api_java.exception.EntityNotFoundException;
import locadora_api_java.repository.UserRepository;
import locadora_api_java.web.controller.dto.user.UserPaginatedResponseDTO;
import locadora_api_java.web.controller.dto.user.UserResponseInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        System.out.println(user.toString());
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com id = %s não encontrado", id))
        );
    }

    public User updateUser(@NotNull Long id, User userUpdate) {
        User user = findUser(id);

        user.setName(userUpdate.getName());
        user.setEmail(userUpdate.getEmail());
        user.setRole(userUpdate.getRole());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    public UserPaginatedResponseDTO<UserResponseInfoDTO> getFilteredUser(String search, Integer page, Integer size, String sort, String direction) {
        Sort sorted = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sorted);

        Page<User> users = userRepository.searchUsers(search, pageable);

        List<UserResponseInfoDTO> userResponses = users.getContent().stream()
                .map(user -> new UserResponseInfoDTO(
                        user.getId(),
                        user.getName(),
                        user.getRole().toString()
                )).toList();

        return new UserPaginatedResponseDTO<>(
                userResponses,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages()
        );
    }
}
