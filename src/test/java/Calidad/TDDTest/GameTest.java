package Calidad.TDDTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class GameTest {


    @Test
    void prueba(){//este codigo sale bien
        Game game = new Game();
        game.placePiece(-4, 2);

    }

}
