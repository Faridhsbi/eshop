package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        // Generate UUID untuk productId jika belum ada
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            product.setProductId(UUID.randomUUID().toString());
        }
        productData.add(product);
        return product;
    }
    
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public void delete(Product product) {
        if (product != null && product.getProductId() != null) {
            productData.removeIf(p ->
                    product.getProductId().equals(p.getProductId())
            );
        }
    }



}
