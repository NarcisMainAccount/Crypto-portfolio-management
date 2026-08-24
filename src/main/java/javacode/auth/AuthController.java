package javacode.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import javacode.user.User;
import javacode.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)

    //Converts JSON plain data into a RegisterRequest object
    public void register(@Valid @RequestBody RegisterRequest request){
        userService.register(
                request.email(),
                request.username(),
                request.password()
        );
    }

    @PostMapping("/login")
    public void login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        User user = userService.authenticate(
                request.email(),
                request.password()
        );

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        List.of()

                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        httpRequest.getSession(true)
                .setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        context
                );
    }

    @GetMapping("/me")
    public String me(Authentication authentication){
        return authentication.getName();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        var session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }
}
