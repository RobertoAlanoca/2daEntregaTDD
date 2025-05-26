package Calidad.TDDTest;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private Map<String, User> users = new HashMap<>();
    
    public UserService() {
        // Crear usuario administrador por defecto
        User admin = new User("admin", "admin", "Administrador", "admin@tresenraya.com");
        users.put("admin", admin);
    }
    
    public boolean registerUser(String username, String password, String fullName, String email) {
        if (users.containsKey(username)) {
            return false; // Usuario ya existe
        }
        
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            return false; // Datos inválidos
        }
        
        // Validar formato de email básico
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        
        // Validar longitud de contraseña
        if (password.length() < 6) {
            return false;
        }
        
        User newUser = new User(username, password, fullName, email);
        users.put(username, newUser);
        return true;
    }
    
    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            user.setLastLogin(LocalDateTime.now());
            return user;
        }
        return null;
    }
    
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
    
    public boolean emailExists(String email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
    
    public User getUserByUsername(String username) {
        return users.get(username);
    }
    
    public int getTotalUsers() {
        return users.size();
    }
}
