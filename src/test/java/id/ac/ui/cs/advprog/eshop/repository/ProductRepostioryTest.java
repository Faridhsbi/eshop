package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepostioryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        // Reset repository jika diperlukan
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

        // Cek produk sudah tersimpan
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        // Delete produk
        productRepository.delete(product);

        // Cek kembali bahwa produk telah dihapus
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

        // Ambil produk yang telah diedit dan cek hasil edit
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
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("None");
        nonExistentProduct.setProductQuantity(500);

        productRepository.edit(nonExistentProduct.getProductId(), nonExistentProduct);

        assertNull(productRepository.findById("non-existent-id"));
    }

    @Test
    void testEditWithNullId() {
        // buat produk dengan id valid
        Product product = new Product();
        product.setProductId("ab55e9f-1c39-460e-8860-71aaf6af63bd6");
        product.setProductName("Bebek Madura Carog");
        product.setProductQuantity(100);
        productRepository.create(product);

        // edit dengan id null
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Ayam Madura Carog");
        updatedProduct.setProductQuantity(20);
        Product result = productRepository.edit(null, updatedProduct);

        // tidak ada produk yang memiliki id null
        assertNull(result);
    }

    @Test
    void testEditWithNullUpdatedProduct() {
        // buat produk dengan id valid
        Product product = new Product();
        product.setProductId("zx54e0a-5c39-100w-9861-123dgf63bd6");
        product.setProductName("Ayam Almaaz");
        product.setProductQuantity(105);
        productRepository.create(product);

        // edit dengan updatedProduct null harus menghasilkan NullPointerException
        assertThrows(NullPointerException.class, () -> {
            productRepository.edit("zx54e0a-5c39-100w-9861-123dgf63bd6", null);
        });
    }

    @Test
    void testDeleteNonExistentProduct() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        productRepository.delete(nonExistentProduct);

        assertNull(productRepository.findById("non-existent-id"));
    }

    @Test
    void testCreateWithNullProductName() {
        Product product = new Product();
        product.setProductName(null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productRepository.create(product));
        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void testCreateWithWhitespaceProductName() {
        Product product = new Product();
        product.setProductName("   ");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productRepository.create(product));
        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void testCreateWithNullProductId() {
        Product product = new Product();
        product.setProductName("Pecel Lele Lela");
        product.setProductId(null);
        Product created = productRepository.create(product);
        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isEmpty());
    }

    @Test
    void testCreateWithEmptyProductId() {
        Product product = new Product();
        product.setProductName("Pecel Lele Lela");
        product.setProductId("");
        Product created = productRepository.create(product);
        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isEmpty());
    }

    @Test
    void testFindByIdWithNullId() {
        Product result = productRepository.findById(null);
        assertNull(result);
    }

    @Test
    void testDeleteWithNullProduct() {
        productRepository.delete(null);
    }

    @Test
    void testDeleteProductWithNullProductId() {
        Product product = new Product();
        product.setProductName("Pecel Ayam Lale");
        // set productId null
        productRepository.delete(product);
        Iterator<Product> it = productRepository.findAll();
        assertFalse(it.hasNext());
    }
}
