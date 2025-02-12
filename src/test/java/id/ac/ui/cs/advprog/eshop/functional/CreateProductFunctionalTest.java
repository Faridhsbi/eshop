package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_success(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        WebElement nameField = driver.findElement(By.id("nameInput"));
        WebElement quantityField = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

        // Create
        nameField.sendKeys("Bakso Malang");
        quantityField.sendKeys("20");
        submitButton.click();

        // Verify
        driver.get(baseUrl + "/product/list");
        assertTrue(driver.getPageSource().contains("Bakso Malang"));
    }

}
