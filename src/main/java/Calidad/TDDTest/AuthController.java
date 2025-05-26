package Calidad.TDDTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que todos los campos estén presentes
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "El nombre de usuario es requerido");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (request.getPassword() == null || request.getPassword().length() < 6) {
                response.put("success", false);
                response.put("message", "La contraseña debe tener al menos 6 caracteres");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "El nombre completo es requerido");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (request.getEmail() == null || !request.getEmail().contains("@")) {
                response.put("success", false);
                response.put("message", "Ingresa un email válido");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Verificar si el usuario ya existe
            if (userService.userExists(request.getUsername())) {
                response.put("success", false);
                response.put("message", "El nombre de usuario ya está en uso");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Verificar si el email ya existe
            if (userService.emailExists(request.getEmail())) {
                response.put("success", false);
                response.put("message", "El email ya está registrado");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Intentar registrar el usuario
            boolean registered = userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getFullName(),
                request.getEmail()
            );
            
            if (registered) {
                response.put("success", true);
                response.put("message", "Usuario registrado exitosamente");
                response.put("username", request.getUsername());
            } else {
                response.put("success", false);
                response.put("message", "Error al registrar el usuario");
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error interno del servidor");
        }
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            User user = userService.authenticateUser(request.getUsername(), request.getPassword());
            
            if (user != null) {
                response.put("success", true);
                response.put("message", "Login exitoso");
                response.put("username", user.getUsername());
                response.put("fullName", user.getFullName());
            } else {
                response.put("success", false);
                response.put("message", "Usuario o contraseña incorrectos");
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error interno del servidor");
        }
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/check-username/{username}")
    public ResponseEntity<Map<String, Object>> checkUsername(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        response.put("exists", userService.userExists(username));
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmail(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();
        response.put("exists", userService.emailExists(email));
        return ResponseEntity.ok(response);
    }
      // Clases internas para las solicitudes
    public static class RegisterRequest {
        private String username;
        private String password;
        private String fullName;
        private String email;
        
        // Constructor vacío necesario para Jackson
        public RegisterRequest() {}
        
        // Getters y Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
    
    public static class LoginRequest {
        private String username;
        private String password;
        
        // Constructor vacío necesario para Jackson
        public LoginRequest() {}
        
        // Getters y Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
