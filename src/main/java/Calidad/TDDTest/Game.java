package Calidad.TDDTest;

public class Game {
    private String[][] board = new String[3][3];
    private String currentPlayer = "X";
    private String winner = null;
    private boolean draw = false;
    private int moves = 0;

    public void placePiece(int x, int y) {
        if (winner != null || draw) throw new IllegalStateException("El juego ha terminado");
        if (x < 0 || x >= 3) throw new IllegalArgumentException("X fuera de rango");
        if (y < 0 || y >= 3) throw new IllegalArgumentException("Y fuera de rango");
        //if (board[x][y] != null) throw new IllegalArgumentException("Posición ocupada");

        board[x][y] = currentPlayer;
        moves++;

        if (checkWin(x, y)) {
            winner = currentPlayer;
        } else if (moves == 9) {
            draw = true;
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
    }

    // Verifica si el jugador actual ha ganado
    private boolean checkWin(int x, int y) {
        String p = board[x][y];
        if (p == null) return false;

        // Verificar fila
        if (board[x][0] != null && board[x][1] != null && board[x][2] != null &&
                board[x][0].equals(p) && board[x][1].equals(p) && board[x][2].equals(p)) return true;

        // Verificar columna
        if (board[0][y] != null && board[1][y] != null && board[2][y] != null &&
                board[0][y].equals(p) && board[1][y].equals(p) && board[2][y].equals(p)) return true;

        // Verificar diagonal principal
        if (x == y &&
                board[0][0] != null && board[1][1] != null && board[2][2] != null &&
                board[0][0].equals(p) && board[1][1].equals(p) && board[2][2].equals(p)) return true;

        // Verificar diagonal secundaria
        if (x + y == 2 &&
                board[0][2] != null && board[1][1] != null && board[2][0] != null &&
                board[0][2].equals(p) && board[1][1].equals(p) && board[2][0].equals(p)) return true;

        return false;
    }


    public String[][] getBoard() {
        return board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getWinner() {
        return winner;
    }

    public boolean isDraw() {
        return draw;
    }

    public void reset() {
        board = new String[3][3];
        currentPlayer = "X";
        winner = null;
        draw = false;
        moves = 0;
    }
}
