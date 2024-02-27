package am.itspace.eshop.service.impl;

import am.itspace.eshop.entity.User;
import am.itspace.eshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSchedulerService {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 44 23 * * *")
    public void removeNotActiveUsers() {
        List<User> allByActive = userRepository.findAllByActive(false);
        log.info("removeNotActiveUsers scheduler started working. Inactive Users count is {}", allByActive.size());
        for (User user : allByActive) {
            log.info("User with {} email was not active. The user account was deleted!", user.getEmail());
            userRepository.delete(user);
        }
    }
}
