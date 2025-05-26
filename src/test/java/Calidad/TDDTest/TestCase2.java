package Calidad.TDDTest;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCase2 {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        // ...ajusta el path del driver si es necesario...
    WebDriverManager.chromedriver().setup(); // ← este es importante
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    }

    @Test
    public void testConfiguracionYJuego() throws InterruptedException {
        driver.get("http://localhost:8080"); // Ajusta la URL si es necesario

        // Paso 1: Login
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-btn")));
loginButton.click();


        // Paso 2: Ir a configuración
        driver.findElement(By.id("configuracionBtn")).click();

        // Paso 3: Cambiar símbolo jugador 1 a estrella
        WebElement simboloJ1 = driver.findElement(By.id("simboloJugador1"));
        simboloJ1.clear();
        simboloJ1.sendKeys("★");

        // Paso 4: Cambiar símbolo jugador 2 a corazón
        WebElement simboloJ2 = driver.findElement(By.id("simboloJugador2"));
        simboloJ2.clear();
        simboloJ2.sendKeys("❤");

        // Paso 5: Cambiar color tablero a naranja
        WebElement colorTablero = driver.findElement(By.id("colorTablero"));
        colorTablero.clear();
        colorTablero.sendKeys("#FFA500"); // naranja

        // Paso 6: Cambiar color fondo a morado
        WebElement colorFondo = driver.findElement(By.id("colorFondo"));
        colorFondo.clear();
        colorFondo.sendKeys("#800080"); // morado

        // Paso 7: Guardar configuración
        driver.findElement(By.id("guardarConfigBtn")).click();

        // Paso 8: Ir al juego
        driver.findElement(By.id("jugarBtn")).click();

        // Paso 9: Hacer 2 movimientos y verificar cambios
        WebElement celda1 = driver.findElement(By.id("celda-0-0"));
        celda1.click();
        WebElement celda2 = driver.findElement(By.id("celda-0-1"));
        celda2.click();

        // Verificar símbolos
        assertEquals("★", celda1.getText());
        assertEquals("❤", celda2.getText());

        // Verificar color tablero
        String tableroColor = driver.findElement(By.id("tablero")).getCssValue("background-color");
        assertTrue(tableroColor.contains("255, 165, 0")); // naranja en rgb

        // Verificar color fondo
        String fondoColor = driver.findElement(By.tagName("body")).getCssValue("background-color");
        assertTrue(fondoColor.contains("128, 0, 128")); // morado en rgb
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
