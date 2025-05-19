package Calidad.TDDTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    void testPieceOutsideXThrowsException() {
        Game game = new Game();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.placePiece(3, 0);
        });
        assertEquals("X fuera de rango", exception.getMessage());
    }

    @Test
    void testValidPlacementDoesNotThrow() {
        Game game = new Game();
        assertDoesNotThrow(() -> game.placePiece(1, 1));
    }
}
