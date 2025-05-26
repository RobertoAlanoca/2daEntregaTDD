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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCase3 {
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
        }
    }
    @Test
    public void testFlowVerHistorialYLogout() throws InterruptedException {
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

        // Esperar a que redirija al menú
        wait.until(ExpectedConditions.urlContains("/menu"));
        assertTrue(driver.getCurrentUrl().contains("/menu"));
        Thread.sleep(2000);

        // === 3. Navegar a historial ===
        // Buscar el enlace o botón que lleva a historial. Supongamos un enlace con texto "Historial"
        WebElement historialLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Historial')]")));
        historialLink.click();

        // Esperar que cargue la página de historial (url contiene "/history" por ejemplo)
        wait.until(ExpectedConditions.urlContains("/history"));
        assertTrue(driver.getCurrentUrl().contains("/history"));

        // Esperar 20 segundos "viendo" el historial
        Thread.sleep(20000);

        // === 4. Cerrar sesión ===
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout-btn")));
        logoutButton.click();

        // Esperar a que redirija al login ("/")
        wait.until(ExpectedConditions.urlContains("/"));
        Thread.sleep(2000);
    }


}
