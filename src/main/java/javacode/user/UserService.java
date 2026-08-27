package javacode.user;

import javacode.exception.DuplicateResourceException;
import javacode.exception.InvalidCredentialsException;
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
            throw new DuplicateResourceException("Email already exists");
        }

        if(userRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Username already exists");
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
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email"));

        if(!passwordEncoder.matches(password, user.getPasswordHash())){
            throw new InvalidCredentialsException("Invalid password");
        }
        return user;
    }
}
