package javacode.auth;

public record LoginRequest(
        String email,
        String password
) {
}
