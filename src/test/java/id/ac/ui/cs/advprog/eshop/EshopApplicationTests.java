package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully.
        // No assertions are needed because if the context fails to load, the test will automatically fail.
    }

    @Test
    void mainMethodTest() {
        EshopApplication.main(new String[] {});
    }
}
