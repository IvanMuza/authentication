package co.com.crediya.model.user.gateways;

public interface JwtProviderPort {
    String generateToken(String username, String role);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}
