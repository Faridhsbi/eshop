package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    private Product createTestProduct(String id, String name, int quantity) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setProductQuantity(quantity);
        return product;
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        assertEquals("CreateProduct", viewName);
        verify(model).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPost() {
        Product product = createTestProduct("99999aura999999999", "Cakwe Malang", 50);
        String viewName = productController.createProductPost(product, model);
        verify(productService).create(product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage() {
        List<Product> productList = new ArrayList<>();
        productList.add(createTestProduct("90128309", "Sambal bakar", 9));
        productList.add(createTestProduct("32498579", "Ayam bakar", 20));

        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.productListPage(model);
        assertEquals("ProductList", viewName);
        verify(model).addAttribute("products", productList);
    }

    @Test
    void testDeleteProduct() {
        String productId = "209387450293875";
        String viewName = productController.deleteProduct(productId);

        verify(productService).delete(argThat(product ->
                product.getProductId().equals(productId)
        ));
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testEditProductPageFound() {
        String productId = "777";
        Product expectedProduct = createTestProduct(productId, "Halo dek", 10);
        List<Product> productList = List.of(expectedProduct);

        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.editProductPage(productId, model);
        assertEquals("EditProduct", viewName);
        verify(model).addAttribute("product", expectedProduct);
    }

    @Test
    void testEditProductPageNotFound() {
        String productId = "a123asfasfsa-123987sadfasf";
        when(productService.findAll()).thenReturn(new ArrayList<>());

        String viewName = productController.editProductPage(productId, model);
        assertEquals("EditProduct", viewName);
        verify(model, never()).addAttribute(eq("product"), any());
    }

    @Test
    void testEditProductPageProductFoundNotFirstInList() {
        // Init data
        String searchedId = "z8zihsd2342jhz0985-08345jkjdasf";
        Product firstProduct = createTestProduct("h3ahsdfk2034asdf-jasklf234kjh902", "Bebek Madura Carog", 5);
        Product targetProduct = createTestProduct("z8zihsd2342jhz0985-08345jkjdasf", "Ayam Almaz", 10);
        List<Product> productList = List.of(firstProduct, targetProduct);

        // Mock service
        when(productService.findAll()).thenReturn(productList);

        // Recall method
        String viewName = productController.editProductPage(searchedId, model);

        // Verifikasi hasil
        assertEquals("EditProduct", viewName);
        verify(model).addAttribute("product", targetProduct);
    }

    @Test
    void testEditProductPost() {
        String productId = "k9j234jkhugk-234kjjkhg92jb";
        Product updatedProduct = createTestProduct(productId, "Updated Product", 20);

        String viewName = productController.editProductPost(productId, updatedProduct);
        verify(productService).update(productId, updatedProduct);
        assertEquals("redirect:/product/list", viewName);
    }
}