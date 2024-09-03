package com.example.recherche.Controller;

import com.example.recherche.Entities.Produit;
import com.example.recherche.Product.ProductRepo;
import com.example.recherche.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    private final ProductService productService;

    @Value("${excel.file.path}")
    private String excelFilePath;

    @Autowired
    public DemoController(ProductService productService) {
        this.productService = productService;
        // Load products from Excel file when the application starts
        this.productService.loadProducts();
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<Produit> getProductById(@PathVariable int id) {
        Optional<Produit> product = productService.findProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Produit>> getProducts() {
        List<Produit> products = productService.readExcelFile(excelFilePath);
        return ResponseEntity.ok(products);
    }
}