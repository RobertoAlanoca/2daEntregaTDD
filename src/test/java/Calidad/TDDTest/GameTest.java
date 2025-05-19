package Calidad.TDDTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    // --- Requerimiento 1 ---

    @Test
    void testPlacePiece_OutOfBoundsX_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.placePiece(3, 0); // X = 3 está fuera del rango 0–2
        });
        assertEquals("X fuera de rango", exception.getMessage());
    }

    @Test
    void testPlacePiece_OutOfBoundsY_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.placePiece(0, -1); // Y = -1 está fuera del rango 0–2
        });
        assertEquals("Y fuera de rango", exception.getMessage());
    }

    @Test
    void testPlacePiece_OccupiedPosition_ThrowsException() {
        game.placePiece(1, 1); // Primera jugada válida
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            game.placePiece(1, 1); // Posición ya ocupada
        });
        assertEquals("Posición ocupada", exception.getMessage());
    }
}
