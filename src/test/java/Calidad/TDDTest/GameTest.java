package Calidad.TDDTest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class GameTest {
//Requeriminetos 2
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