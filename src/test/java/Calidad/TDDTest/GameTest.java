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

        assertNull(game.getWinner(), "No debería haber ganador aún");
        assertFalse(game.isDraw(), "Todavía no es empate");
    }


}
