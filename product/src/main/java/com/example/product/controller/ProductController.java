package com.example.product.controller;

import com.example.product.dto.ProductDTO;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/product")

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getproduct")
    public List<ProductDTO> getProducts(){

        return productService.getAllProducts();
    }

    @GetMapping("/getproductbyid/{id}")
    public ProductDTO getProductById(@PathVariable Integer id){
        return  productService.getItemById(id);
    }


    @PostMapping("/saveproduct")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO){

        return productService.saveProduct(productDTO);
    }

    @PutMapping("/updateproduct")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO){

        return productService.updateProduct(productDTO);
    }

    @DeleteMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable int id ){

        return productService.deleteProduct(id);
    }



}
