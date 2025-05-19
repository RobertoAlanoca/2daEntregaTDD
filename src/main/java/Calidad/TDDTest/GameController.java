package Calidad.TDDTest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {
    private final Game game = new Game();

    @GetMapping("/board")
    public String[][] getBoard() {
        return game.getBoard();
    }

    @GetMapping("/status")
    public String getStatus() {
        if (game.getWinner() != null) {
            return "Ganador: " + game.getWinner();
        } else if (game.isDraw()) {
            return "Empate";
        } else {
            return "Turno de: " + game.getCurrentPlayer();
        }
    }

    @PostMapping("/move")
    public String makeMove(@RequestParam int x, @RequestParam int y) {
        try {
            game.placePiece(x, y);
        } catch (Exception e) {
            return e.getMessage();
        }
        if (game.getWinner() != null) {
            return "¡Ganó " + game.getWinner() + "!";
        } else if (game.isDraw()) {
            return "Empate";
        } else {
            return "Movimiento realizado. Turno de: " + game.getCurrentPlayer();
        }
    }

    @PostMapping("/reset")
    public String reset() {
        game.reset();
        return "Juego reiniciado. Turno de: " + game.getCurrentPlayer();
    }

    @GetMapping("/hello")
    public String hello() {
        return "¡El backend de TDDTest está corriendo!";
    }
}
