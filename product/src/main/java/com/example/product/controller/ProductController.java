package com.example.product.controller;

import com.example.product.dto.ProductDTO;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1")

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getproduct")
    public List<ProductDTO> getProducts(){

        return productService.getAllProducts();
    }

    @PostMapping("/saveproduct")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO){

        return productService.saveProduct(productDTO);
    }

    @PutMapping("/updateproduct")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO){

        return productService.updateProduct(productDTO);
    }

    @DeleteMapping("/deleteproduct")
    public String deleteProduct(@RequestBody ProductDTO productDTO){

        return productService.deleteProduct(productDTO);
    }



}
