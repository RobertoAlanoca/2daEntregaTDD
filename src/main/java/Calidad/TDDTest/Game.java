package Calidad.TDDTest;

public class Game {
    private String[][] board = new String[3][3];

    public void placePiece(int x, int y) {
        if (x < 0 || x >= 3) throw new IllegalArgumentException("X fuera de rango");
        if (y < 0 || y >= 3) throw new IllegalArgumentException("Y fuera de rango");
        if (board[x][y] != null) throw new IllegalArgumentException("Posición ocupada");

        board[x][y] = "X"; // Movimiento simulado
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }
}
