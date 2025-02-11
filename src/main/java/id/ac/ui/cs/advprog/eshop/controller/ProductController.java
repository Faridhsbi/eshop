package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping( "/create")
    public String createProductPost(@ModelAttribute Product product, Model model){
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model){
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productId") String productId) {
        Product productToDelete = new Product();
        productToDelete.setProductId(productId);
        service.delete(productToDelete);
        return "redirect:list";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable String id, Model model) {
        Iterator<Product> productIterator = service.findAll().iterator();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            if (product.getProductId().equals(id)) {
                model.addAttribute("product", product);
                break;
            }
        }
        return "editProduct";
    }

    @PostMapping("/edit/{id}")
    public String editProductPost(@PathVariable String id, @ModelAttribute Product updatedProduct) {
        service.edit(id, updatedProduct);
        return "redirect:/product/list";
    }


}
