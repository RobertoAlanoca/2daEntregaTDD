package Calidad.TDDTest;

public class ManualTestRunner {

    public static void main(String[] args) {
        // Prueba simple de lógica (simula una pieza colocada en el tablero)
        Game game = new Game();
        try {
            game.placePiece(0, 0); // Coloca una pieza válida
            game.placePiece(3, 1); // Coordenada X inválida: debe lanzar excepción
        } catch (Exception e) {
            System.out.println("Se capturó una excepción como se esperaba: " + e.getMessage());
        }
    }
}
