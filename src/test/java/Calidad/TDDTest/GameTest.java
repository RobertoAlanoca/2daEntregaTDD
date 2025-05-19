package Calidad.TDDTest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void testHorizontalWin() { // Req3 Prueba 2
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
    @Test
    public void testVerticalWin() { // Req3 Prueba 3
        Game game = new Game();
        game.placePiece(0, 0); // X
        game.placePiece(0, 1); // O
        game.placePiece(1, 0); // X
        game.placePiece(1, 1); // O
        game.placePiece(2, 0); // X -> gana

        String winner = game.getWinner();
        System.out.println("\n--- Victoria Vertical ---");
        System.out.println("Casillas ocupadas por " + winner + ": (0,0), (1,0), (2,0)");
        System.out.println("Ganador detectado: " + winner + "\n");

        assertEquals("X", winner, "X debería ganar con una línea vertical en la columna 0");
    }
    @Test
    public void testDiagonalWin() { // Req3 Prueba 4
        Game game = new Game();
        game.placePiece(0, 0); // X
        game.placePiece(0, 1); // O
        game.placePiece(1, 1); // X
        game.placePiece(1, 0); // O
        game.placePiece(2, 2); // X -> gana

        String winner = game.getWinner();
        System.out.println("\n--- Victoria Diagonal ---");
        System.out.println("Casillas ocupadas por " + winner + ": (0,0), (1,1), (2,2)");
        System.out.println("Ganador detectado: " + winner + "\n");

        assertEquals("X", winner, "X debería ganar con una línea diagonal");
    }
    @Test
    void primerTurnoSiempreEsX() {
        Game game = new Game();
        assertEquals("X", game.getCurrentPlayer(), "El primer turno debe ser de X");
    }
    @Test
    void despuesDeXJuegaO() {
        Game game = new Game();
        game.placePiece(0, 0); // X juega
        assertEquals("O", game.getCurrentPlayer(), "Después de X debe jugar O");
    }
    @Test
    void despuesDeOJuegaX() {
        Game game = new Game();
        game.placePiece(0, 0); // X
        game.placePiece(1, 0); // O
        assertEquals("X", game.getCurrentPlayer(), "Después de O debe jugar X");
    }
}