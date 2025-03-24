package locadora_api_java.service;

import locadora_api_java.entity.Code;
import locadora_api_java.entity.User;
import locadora_api_java.exception.CodeInvalidOrExpired;
import locadora_api_java.repository.CodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class CodeService {
    private CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Long addUserAndToken(User user) {

        Random random = new Random();
        Long codeNumber = random.nextLong(900000) + 100000;

        Code code = codeRepository.findByCodeByUserId(user.getId());

        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);

        if (code == null) {
            code = new Code();
            code.setUser(user);
            code.setCode(codeNumber);
            code.setTimeCodeExpired(expirationTime);
            codeRepository.save(code);
        } else {
            code.setCode(codeNumber);
            code.setTimeCodeExpired(expirationTime);
            codeRepository.save(code);
        }
        return codeNumber;
    }

    public void checkCode(User user, Long otpCode) {

        Code code = codeRepository.findByCodeByUserId(user.getId());

        if (!code.getCode().equals(otpCode) || code.getTimeCodeExpired().isBefore(LocalDateTime.now())) {
            throw new CodeInvalidOrExpired("codigo invalido ou expirado");

        }

    }
}
