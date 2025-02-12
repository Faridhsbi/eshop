package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
public class ProductTest {
    Product product;
    @BeforeEach
    public void setUp() {
        this.product = new Product();
        this.product.setProductId("ab55e9f-1c39-460e-8860-71aaf6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }
    @Test
    void testSetProductId() {
        assertEquals("ab55e9f-1c39-460e-8860-71aaf6af63bd6", this.product.getProductId());
    }

    @Test
    void testSetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testSetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

}
