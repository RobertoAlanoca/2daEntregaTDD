package Calidad.TDDTest;

import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCase2 {
    /*private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    public static void setupClass() {
        // WebDriverManager descarga automáticamente la versión correcta de ChromeDriver
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testConfiguracionYJuego() throws InterruptedException {
        // === 1. Abrir página principal ===
        driver.get("http://localhost:8080/");
        String title = driver.getTitle();
        assertTrue(title.contains("Tres en Raya"));
        Thread.sleep(2000);

        // === 2. Login como admin ===
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-btn"));

        usernameField.clear();
        usernameField.sendKeys("admin");
        passwordField.clear();
        passwordField.sendKeys("admin");
        loginButton.click();

        // Esperar redirección al menú
        wait.until(ExpectedConditions.urlContains("/menu"));
        assertTrue(driver.getCurrentUrl().contains("/menu"));
        Thread.sleep(2000);

        // === 3. Ir a configuración ===
        try {
            WebElement configLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/config']")));
            wait.until(ExpectedConditions.elementToBeClickable(configLink)).click();
        } catch (Exception e) {
            throw new RuntimeException("ERROR: No se encontró el enlace a configuración (/config).");
        }

        // Resto del test igual que antes...

        // Paso 2: Seleccionar símbolo jugador 1 a estrella
        WebElement player1Symbols = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("player1-symbols")));
        WebElement estrella = player1Symbols.findElements(By.className("symbol-option"))
                .stream()
                .filter(e -> e.getText().equals("★"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Símbolo ★ no encontrado para jugador 1"));
        estrella.click();

        // Paso 3: Jugador 2 a corazón
        WebElement player2Symbols = driver.findElement(By.id("player2-symbols"));
        WebElement corazon = player2Symbols.findElements(By.className("symbol-option"))
                .stream()
                .filter(e -> e.getText().equals("❤"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Símbolo ❤ no encontrado para jugador 2"));
        corazon.click();

        // Paso 4: Color tablero a naranja
        WebElement boardColors = driver.findElement(By.id("board-colors"));
        WebElement naranja = boardColors.findElements(By.className("color-option"))
                .stream()
                .filter(e -> e.getCssValue("background-color").contains("255, 165, 0"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Color naranja no encontrado en tablero"));
        naranja.click();

        // Paso 5: Color fondo a morado
        WebElement backgroundColors = driver.findElement(By.id("background-colors"));
        WebElement morado = backgroundColors.findElements(By.className("color-option"))
                .stream()
                .filter(e -> e.getCssValue("background-color").contains("128, 0, 128"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Color morado no encontrado en fondo"));
        morado.click();

        // Paso 6: Guardar configuración
        WebElement guardarBtn = driver.findElement(By.id("save-config"));
        guardarBtn.click();

        // Paso 7: Esperar mensaje de éxito
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-message")));

        // Paso 8: Ir al juego
        WebElement jugarBtn = driver.findElement(By.cssSelector("a[href='/game']"));
        jugarBtn.click();

        // Paso 9: Hacer 2 movimientos
        WebElement celda1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("celda-0-0")));
        celda1.click();
        WebElement celda2 = driver.findElement(By.id("celda-0-1"));
        celda2.click();

        // Verificar símbolos
        assertEquals("★", celda1.getText());
        assertEquals("❤", celda2.getText());

        // Verificar colores
        String tableroColor = driver.findElement(By.id("tablero")).getCssValue("background-color");
        assertTrue(tableroColor.contains("255, 165, 0"));

        String fondoColor = driver.findElement(By.tagName("body")).getCssValue("background-color");
        assertTrue(fondoColor.contains("128, 0, 128"));
        // Paso 10: Hacer logout
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout-btn")));
        logoutButton.click();

        // Esperar a que redirija al login ("/")
        wait.until(ExpectedConditions.urlContains("/"));
        Thread.sleep(2000);
    }

*/
}
