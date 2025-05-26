package Calidad.TDDTest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    // --- Requerimiento 1 ---
    @Test
    void pruebasReq1() { //Requerimiento 1
        System.out.println("\n--- Requerimiento 1 ---\n");
        this.testPlacePiece_OutOfBoundsX_ThrowsException(); // prueba 1
        this.testPlacePiece_OutOfBoundsY_ThrowsException(); // prueba 2
        this.testPlacePiece_OccupiedPosition_ThrowsException(); // prueba 3
        System.out.println("\n--- Requerimiento 1 completado ---\n");
    }
    @Test
    void pruebasReq2(){//requerimiento 2
        System.out.println("\n--- Requerimiento 2 ---\n");
        this.primerTurnoSiempreEsX(); // prueba 1
        this.despuesDeXJuegaO(); // prueba 2
        this.despuesDeOJuegaX(); // prueba 3
        System.out.println("\n--- Requerimiento 2 completado ---\n");
    }
    @Test
    void pruebasReq3(){ //requerimiento 3
        System.out.println("\n--- Requerimiento 3 ---\n");
        this.testNoWinnerAtStart(); // prueba 1
        this.testHorizontalWin(); // prueba 2
        this.testVerticalWin(); // prueba 3
        this.testDiagonalWin(); // prueba 4
        System.out.println("\n--- Requerimiento 3 completado ---\n");
    }
    //Requerimiento 1
    void testPlacePiece_OutOfBoundsX_ThrowsException() {//prueba 1
        Game game = new Game();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.placePiece(3, 0); // X = 3 está fuera del rango 0–2
        });
        System.out.println("\n- Fuera de juego en X -\n");
        assertEquals("X fuera de rango", exception.getMessage());

    }

    void testPlacePiece_OutOfBoundsY_ThrowsException() {//Prueba 2
        Game game = new Game();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.placePiece(0, -1); // Y = -1 está fuera del rango 0–2
        });
        System.out.println("\n- Fuera de juego en Y -\n");
        assertEquals("Y fuera de rango", exception.getMessage());
    }

    void testPlacePiece_OccupiedPosition_ThrowsException() {// Prueba 3
        Game game = new Game();
        game.placePiece(1, 1); // Primera jugada válida
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.placePiece(1, 1); // Posición ya ocupada
        });
        System.out.println("\n- Posicion ocupada -\n");
        assertEquals("Posición ocupada", exception.getMessage());
    }
    // --- Requerimiento 2 ---

    void primerTurnoSiempreEsX() {//Prueba 1
        Game game = new Game();
        System.out.println("\n- Primer turno de X -\n");
        assertEquals("X", game.getCurrentPlayer(), "El primer turno debe ser de X");
    }
    void despuesDeXJuegaO() {// Prueba 2
        Game game = new Game();
        game.placePiece(0, 0); // X juega
        System.out.println("\n- Ahora juega Y -\n");
        assertEquals("O", game.getCurrentPlayer(), "Después de X debe jugar O");
    }

    void despuesDeOJuegaX() {//Prueba 3
        Game game = new Game();
        game.placePiece(0, 0); // X
        game.placePiece(1, 0); // O
        System.out.println("\n- Ahora juega X -\n");
        assertEquals("X", game.getCurrentPlayer(), "Después de O debe jugar X");
    }
    // --- Requerimiento 3 ---
    public void testNoWinnerAtStart() {// Prueba Prueba 1
        Game game = new Game();

        game.placePiece(0, 0); // X
        game.placePiece(1, 0); // O
        game.placePiece(0, 1); // X
        game.placePiece(1, 1); // O
        System.out.println("\n- Juego en Curso -");
        System.out.println();
        System.out.println();
        System.out.println("Juego en progreso");
        System.out.println();
        System.out.println();
        assertNull(game.getWinner(), "No debería haber ganador aún");
        assertFalse(game.isDraw(), "Todavía no es empate");
    }

    public void testHorizontalWin() { // Req3 Prueba 2
        Game game = new Game();
        game.placePiece(0, 0); // X
        game.placePiece(1, 0); // O
        game.placePiece(0, 1); // X
        game.placePiece(1, 1); // O
        game.placePiece(0, 2); // X -> gana

        String winner = game.getWinner();
        System.out.println("\n- Victoria Horizontal -");
        System.out.println("Casillas ocupadas por " + winner + ": (0,0), (0,1), (0,2)");
        System.out.println("Ganador detectado: " + winner + "\n");

        assertEquals("X", winner, "X debería ganar con una línea horizontal en la fila 0");
    }
    public void testVerticalWin() { // Req3 Prueba 3
        Game game = new Game();
        game.placePiece(0, 0); // X
        game.placePiece(0, 1); // O
        game.placePiece(1, 0); // X
        game.placePiece(1, 1); // O
        game.placePiece(2, 0); // X -> gana

        String winner = game.getWinner();
        System.out.println("\n- Victoria Vertical -");
        System.out.println("Casillas ocupadas por " + winner + ": (0,0), (1,0), (2,0)");
        System.out.println("Ganador detectado: " + winner + "\n");

        assertEquals("X", winner, "X debería ganar con una línea vertical en la columna 0");
    }
    public void testDiagonalWin() { // Req3 Prueba 4
        Game game = new Game();
        game.placePiece(0, 0); // X
        game.placePiece(0, 1); // O
        game.placePiece(1, 1); // X
        game.placePiece(1, 0); // O
        game.placePiece(2, 2); // X -> gana

        String winner = game.getWinner();
        System.out.println("\n- Victoria Diagonal -");
        System.out.println("Casillas ocupadas por " + winner + ": (0,0), (1,1), (2,2)");
        System.out.println("Ganador detectado: " + winner + "\n");

        assertEquals("X", winner, "X debería ganar con una línea diagonal");
    }
}