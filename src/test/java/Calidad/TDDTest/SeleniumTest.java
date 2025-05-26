package Calidad.TDDTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTest {
    private static WebDriver driver;
    /*
    @BeforeAll
    public static void setup() {
        // WebDriverManager descarga automáticamente la versión correcta de ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testIndexPageLoads() throws InterruptedException {
        driver.get("http://localhost:8080/");
        String title = driver.getTitle();
        // El título debe contener "Tres en Raya" según index.html
        assertTrue(title.contains("Tres en Raya"));
        // Espera 5 segundos para que la ventana sea visible
        Thread.sleep(5000);
    }

    @Test
    public void testJugarTresEnRaya() throws InterruptedException {
        driver.get("http://localhost:8080/");
        Thread.sleep(1000); // Espera a que cargue la página

        // Simula una partida: X en (0,0), O en (0,1), X en (1,1), O en (0,2), X en (2,2) (X gana)
        driver.findElements(By.className("cell")).get(0).click(); // (0,0)
        Thread.sleep(500);
        driver.findElements(By.className("cell")).get(1).click(); // (0,1)
        Thread.sleep(500);
        driver.findElements(By.className("cell")).get(4).click(); // (1,1)
        Thread.sleep(500);
        driver.findElements(By.className("cell")).get(2).click(); // (0,2)
        Thread.sleep(500);
        driver.findElements(By.className("cell")).get(8).click(); // (2,2)
        Thread.sleep(1000);

        // Verifica si el mensaje de estado indica victoria
        WebElement status = driver.findElement(By.id("status"));
        String mensaje = status.getText().toLowerCase();
        assertTrue(mensaje.contains("ganó") || mensaje.contains("gana") || mensaje.contains("victoria"));

        Thread.sleep(3000); // Para ver el resultado antes de cerrar

        // Reinicia el juego para dejar la página limpia
        driver.findElement(By.id("reset")).click();
        Thread.sleep(1000); // Espera a que se limpie el tablero
    }

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }*/
}
