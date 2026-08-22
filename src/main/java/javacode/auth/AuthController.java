package javacode.auth;

import javacode.user.User;
import javacode.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public void register(@RequestBody RegisterRequest request){
        userService.register(
                request.email(),
                request.username(),
                request.password()
        );
    }
}
