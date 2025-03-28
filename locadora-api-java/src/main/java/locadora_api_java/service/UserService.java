package locadora_api_java.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotNull;
import locadora_api_java.entity.User;
import locadora_api_java.enums.UserRole;
import locadora_api_java.exception.*;
import locadora_api_java.repository.UserRepository;
import locadora_api_java.web.controller.dto.user.UserEmail;
import locadora_api_java.web.controller.dto.user.UserPaginatedResponseDTO;
import locadora_api_java.web.controller.dto.user.UserResponseInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final CodeService codeService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JavaMailSender mailSender, CodeService codeService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.codeService = codeService;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new NameUniqueViolationException(String.format("nome %s ja existe", user.getEmail()));
        }
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

    public void sendEmail(UserEmail userEmail) {
        User user = userRepository.findUserByEmail(userEmail.email());
        if (user == null) {
            throw new UserByEmailNotFoundException(String.format("usuario com email %s não encontrado", userEmail.email()));
        }

        Long tokenResetPassword = codeService.addUserAndToken(user);
        sendPasswordResetEmail(user.getEmail(), tokenResetPassword);
    }

    public void sendPasswordResetEmail(String email, Long token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("teste@gmail.com");
            helper.setTo(email);
            helper.setSubject("Código de Redefinição de Senha");

            String htmlContent = "<html>"
                    + "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                    + "<div style='background-color: #ffffff; padding: 20px; border-radius: 10px; max-width: 600px; margin: 0 auto;'>"
                    + "<h2 style='color: #007BFF; text-align: center;'>Redefinição de Senha</h2>"
                    + "<p>Olá,</p>"
                    + "<p>Recebemos uma solicitação para redefinir a senha da sua conta WDA Livraria. Use o código abaixo para prosseguir:</p>"
                    + "<div style='background-color: #f8f9fa; padding: 15px; border-radius: 5px; text-align: center; margin: 20px 0;'>"
                    + "<h3 style='color: #333333; margin: 0;'>" + token + "</h3>"
                    + "</div>"
                    + "<p>Se você não solicitou essa alteração, ignore este e-mail.</p>"
                    + "<p>Atenciosamente,<br>Equipe de Suporte</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new EmailSendingException("Erro ao enviar e-mail de redefinição de senha");
        }
    }

    public void changePassword(String newPassword, String repeatPassword, String email, Long otpCode) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UserByEmailNotFoundException(String.format("usuario com email %s não encontrado", email));

        }

        if (!newPassword.equals(repeatPassword)) {
            throw new PasswordMismatchException("as senhas não confere");
        }

        codeService.checkCode(user, otpCode);

        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserName(String name) {
        return userRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com nome %s não encontrado", name))
        );
    }

    @Transactional(readOnly = true)
    public UserRole findRoleByName(String name) {
        return userRepository.findRoleByName(name);
    }
}
