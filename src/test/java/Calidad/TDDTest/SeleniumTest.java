package Calidad.TDDTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setup() {
        // Asegúrate de tener ChromeDriver en tu PATH
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

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
