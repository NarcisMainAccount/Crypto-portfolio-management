package javacode.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(
            String email,
            String username,
            String password
    ) {
        if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        if(userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        OffsetDateTime now = OffsetDateTime.now();

        User user = new User(
                UUID.randomUUID(),
                email,
                username,
                passwordEncoder.encode(password),
                "EUR",
                now,
                now
        );
        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email"));

        if(!passwordEncoder.matches(password, user.getPasswordHash())){
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }
}
