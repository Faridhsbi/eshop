package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepostioryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void testCreateAndFind(){
        Product product = new Product();
        product.setProductId("ab55e9f-1c39-460e-8860-71aaf6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty(){
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct(){
        Product product = new Product();
        product.setProductId("ab55e9f-1c39-460e-8860-71aaf6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDelete(){
        // Arrange
        Product product = new Product();
        product.setProductId("ab55e9f-1c39-460e-8860-71aaf6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Cek produk
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        // delete product
        productRepository.delete(product);

        // cek kembali product
        productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }


    @Test
    void testEdit(){
        Product product = new Product();
        product.setProductId("ab55e9f-1c39-460e-8860-71aaf6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Edit produk
        Product editedProduct = new Product();
        editedProduct.setProductId("ab55e9f-1c39-460e-8860-71aaf6af63bd6");
        editedProduct.setProductName("Sampo Cap Bangoo");
        editedProduct.setProductQuantity(200);
        productRepository.edit(editedProduct.getProductId(), editedProduct);

        // Ambil produk yang sudah diedit dan cek hasil edit
        Product result = productRepository.findById("ab55e9f-1c39-460e-8860-71aaf6af63bd6");
        assertNotNull(result);
        assertEquals("Sampo Cap Bangoo", result.getProductName());
        assertEquals(200, result.getProductQuantity());
        assertEquals("ab55e9f-1c39-460e-8860-71aaf6af63bd6", result.getProductId());

        // Hapus produk setelah diedit
        productRepository.delete(result);

        // Pastikan produk telah dihapus
        assertNull(productRepository.findById("ab55e9f-1c39-460e-8860-71aaf6af63bd6"));
    }

    @Test
    void testEditNonExistentProduct() {
        //
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("None");
        nonExistentProduct.setProductQuantity(500);

        productRepository.edit(nonExistentProduct.getProductId(), nonExistentProduct);

        assertNull(productRepository.findById("non-existent-id"));
    }

    @Test
    void testDeleteNonExistentProduct() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        productRepository.delete(nonExistentProduct);

        assertNull(productRepository.findById("non-existent-id"));
    }



}
