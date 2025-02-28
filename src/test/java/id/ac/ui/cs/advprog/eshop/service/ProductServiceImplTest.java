package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setProductUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        sampleProduct.setProductName("Sabana Lele");
        sampleProduct.setProductQuantity(12);
    }

    @Test
    void testCreate() {
        when(productRepository.create(sampleProduct)).thenReturn(sampleProduct);

        Product result = productService.create(sampleProduct);

        assertNotNull(result);
        assertEquals(sampleProduct.getProductId(), result.getProductId());
        assertEquals(sampleProduct.getProductName(), result.getProductName());
        assertEquals(sampleProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository, times(1)).create(sampleProduct);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);

        Product secondProduct = new Product();
        secondProduct.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        secondProduct.setProductName("Ayam Bakar");
        secondProduct.setProductQuantity(35);
        productList.add(secondProduct);

        when(productRepository.findAll()).thenReturn(productList.iterator());

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(sampleProduct.getProductId(), result.get(0).getProductId());
        assertEquals(secondProduct.getProductId(), result.get(1).getProductId());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById(sampleProduct.getProductId())).thenReturn(sampleProduct);

        Product result = productService.findById(sampleProduct.getProductId());

        assertNotNull(result);
        assertEquals(sampleProduct.getProductId(), result.getProductId());
        assertEquals(sampleProduct.getProductName(), result.getProductName());
        assertEquals(sampleProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository, times(1)).findById(sampleProduct.getProductId());
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete(sampleProduct.getProductId());

        productService.deleteById(sampleProduct.getProductId());

        verify(productRepository, times(1)).delete(eq(sampleProduct.getProductId()));
    }
    @Test
    void testEdit() {
        // Siapkan data produk yang diperbarui
        Product updatedProduct = new Product();
        updatedProduct.setProductId(sampleProduct.getProductId());
        updatedProduct.setProductName("Gurame Kering");
        updatedProduct.setProductQuantity(10);

        when(productRepository.update(sampleProduct.getProductId(), updatedProduct)).thenReturn(updatedProduct);
        productService.update(sampleProduct.getProductId(), updatedProduct);
        verify(productRepository, times(1)).update(sampleProduct.getProductId(), updatedProduct);
    }
}