package Calidad.TDDTest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    @GetMapping("/")
    public String login() {
        return "login";
    }
      @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }
    
    @GetMapping("/game")
    public String game() {
        return "game";
    }
    
    @GetMapping("/config")
    public String config() {
        return "config";
    }
    
    @GetMapping("/history")
    public String history() {
        return "history";
    }
    
    @GetMapping("/info")
    public String info() {
        return "info";
    }
}
