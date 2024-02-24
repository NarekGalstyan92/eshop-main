package am.itspace.eshop.service.impl;

import am.itspace.eshop.entity.User;
import am.itspace.eshop.repository.UserRepository;
import am.itspace.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SendMailService sendMailService;

    @Override
    public User register(User user) {
        String activationToken = UUID.randomUUID().toString();
        user.setActive(false);
        user.setToken(activationToken);
        User saved = userRepository.save(user);
        String verifyUrl = "http://localhost:8080/user/verify?token=" + activationToken;
        sendMailService.send(user.getEmail(), "Congrats on registering 🎉", String.format("Welcome %s . You have successfully registered. " + "Please open %s to activate your account!", user.getName(), verifyUrl));
        return saved;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public User findByToken(String token) {
        return userRepository.findByToken(token).orElse(null);
    }
}
