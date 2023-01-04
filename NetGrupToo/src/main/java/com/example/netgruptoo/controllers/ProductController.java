package com.example.netgruptoo.controllers;


import com.example.netgruptoo.payload.ProductDto;
import com.example.netgruptoo.services.ProductService;
import com.example.netgruptoo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @PostMapping("/create/{catalogueId}/{username}")
    public HttpStatus createProduct(@RequestBody ProductDto productDto
                                    ,@PathVariable("catalogueId") Long catalogueId
                                    ,@PathVariable("username") String username
    ) {
        productService.createProduct(productDto, catalogueId);
        userService.updateUserProductStatus(username, productDto.getAmount());
        return HttpStatus.CREATED;
    }
    @GetMapping("/get/{parentId}/products")
    public List<ProductDto> getProductsByCatalogueId(@PathVariable("parentId") Long catalogueId) {
       return productService.getAllProductsByCatalogueId(catalogueId);

    }

    @GetMapping("/get/like")
    public List<ProductDto> getProductsBySearchTerm(@RequestParam("username") String username
            , @RequestParam("searchTerm") String searchTerm) {
        return productService.getAllProductsByUserSearch(username, searchTerm);
    }

}
