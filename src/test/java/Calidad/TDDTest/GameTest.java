package Calidad.TDDTest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {


    @Test
    public void testNoWinnerAtStart() {// Prueba exitosa Req3 Prueba 1
        Game game = new Game();

        game.placePiece(0, 0); // X
        game.placePiece(1, 0); // O
        game.placePiece(0, 1); // X
        game.placePiece(1, 1); // O
        System.out.println("\n--- Juego en Curso ---");
        System.out.println();
        System.out.println();
        System.out.println("Juego en progreso");
        System.out.println();
        System.out.println();
        assertNull(game.getWinner(), "No debería haber ganador aún");
        assertFalse(game.isDraw(), "Todavía no es empate");
    }
    @Test
    public void testHorizontalWin() { // Req3 Prueba 1
        Game game = new Game();
        game.placePiece(0, 0); // X
        game.placePiece(1, 0); // O
        game.placePiece(0, 1); // X
        game.placePiece(1, 1); // O
        game.placePiece(0, 2); // X -> gana

        String winner = game.getWinner();
        System.out.println("\n--- Victoria Horizontal ---");
        System.out.println("Casillas ocupadas por " + winner + ": (0,0), (0,1), (0,2)");
        System.out.println("Ganador detectado: " + winner + "\n");

        assertEquals("X", winner, "X debería ganar con una línea horizontal en la fila 0");
    }

}
