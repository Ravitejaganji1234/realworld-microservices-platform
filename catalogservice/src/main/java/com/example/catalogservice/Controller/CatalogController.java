package com.example.catalogservice.Controller;

import com.example.catalogservice.Entity.Product;
import com.example.catalogservice.Repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final ProductRepository repo;

    public CatalogController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @GetMapping("/slow")
    public String slow(@RequestParam long delay) throws InterruptedException {
        Thread.sleep(delay);
        return "OK";
    }
}

