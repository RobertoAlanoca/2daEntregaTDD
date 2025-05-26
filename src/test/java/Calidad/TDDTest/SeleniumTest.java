package Calidad.TDDTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTest {
   private WebDriver driver;
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
        }    }

    @Test
    public void testCompleteFlowTresEnRaya() throws InterruptedException {        // === 1. Verificar que la página principal carga ===
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

        // Esperar a que redirija al menú
        wait.until(ExpectedConditions.urlContains("/menu"));
        assertTrue(driver.getCurrentUrl().contains("/menu"));
        Thread.sleep(2000);

        // === 3. Navegar al juego ===
        WebElement playLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/game' and contains(text(), 'Jugar')]")));
        playLink.click();

        // Esperar a que cargue la página del juego
        wait.until(ExpectedConditions.urlContains("/game"));
        assertTrue(driver.getCurrentUrl().contains("/game"));        // Verificar que el tablero existe
        WebElement board = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("board")));
        assertTrue(board.isDisplayed());
        Thread.sleep(2000);

        // === 4. Jugar una partida completa ===        // Simula una partida: X en (0,0), O en (0,1), X en (1,1), O en (0,2), X en (2,2) (X gana)
        driver.findElements(By.className("cell")).get(0).click(); // (0,0)
        Thread.sleep(2000);
        driver.findElements(By.className("cell")).get(1).click(); // (0,1)
        Thread.sleep(2000);
        driver.findElements(By.className("cell")).get(4).click(); // (1,1)
        Thread.sleep(2000);
        driver.findElements(By.className("cell")).get(2).click(); // (0,2)
        Thread.sleep(2000);
        driver.findElements(By.className("cell")).get(8).click(); // (2,2)
        Thread.sleep(2000);// Verifica si el mensaje de estado indica victoria
        WebElement status = driver.findElement(By.id("status"));        String mensaje = status.getText().toLowerCase();
        assertTrue(mensaje.contains("ganó") || mensaje.contains("gana") || mensaje.contains("victoria"));
        Thread.sleep(2000);

        // === 5. Resetear y hacer logout ===
        WebElement resetButton = driver.findElement(By.id("reset"));
        resetButton.click();
        Thread.sleep(2000);
          // Hacer logout al final del test
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout-btn")));
        logoutButton.click();
        // Espera a que redirija al login
        wait.until(ExpectedConditions.urlContains("/"));
        Thread.sleep(2000);
    }

}
