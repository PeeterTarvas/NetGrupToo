package com.example.netgruptoo.controllers;


import com.example.netgruptoo.dtos.ProductDto;
import com.example.netgruptoo.services.ProductService;
import com.example.netgruptoo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * This class is a controller method for handling all the functionalities that are related to products.
 * If you want to call out this class the endpoint is http://localhost:8000/product/..function param
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

    /**
     * Service class for handling product related logic.
     */
    @Autowired
    private ProductService productService;

    /**
     * Service class for handling user related logic, this is needed in createProduct function.
     */
    @Autowired
    private UserService userService;


    /**
     * This function is the endpoint for creating a product inside a catalogue.
     * @param productDto product transfer object that holds all the params of the product that will be created.
     * @param catalogueId is the id of the catalogue that the product will be in.
     * @param username of the user who creates the product.
     * @return HttpStatus.Created if product is successfully created.
     */
    @PostMapping("/create/{catalogueId}/{username}")
    public HttpStatus createProduct(@RequestBody ProductDto productDto
                                    ,@PathVariable("catalogueId") Long catalogueId
                                    ,@PathVariable("username") String username
    ) {
        productService.createProduct(productDto, catalogueId);
        userService.updateUserItemStatus(username, productDto.getAmount());
        return HttpStatus.CREATED;
    }

    /**
     * This function is an endpoint that will return all the products under a catalogue.
     * @param catalogueId of the catalogue that we want the products from.
     * @return all the products in ProductDtos that are under the catalogue.
     */
    @GetMapping("/get/{parentId}/products")
    public List<ProductDto> getProductsByCatalogueId(@PathVariable("parentId") Long catalogueId) {
       return productService.getAllProductsByCatalogueId(catalogueId);

    }

    /**
     * This method is an endpoint for searching all products by their names.
     * The name doesn't need to be fully typed out, for instance for finding a product named "product"
     * simply the searchTerm "pr" will do or even "p".
     * @param username of the user that wants to search for product.
     * @param searchTerm of the product name that resembles the products name or part of its name.
     * @return all the products with a name similar to search term.
     */
    @GetMapping("/get/like")
    public List<ProductDto> getProductsBySearchTerm(@RequestParam("username") String username
            , @RequestParam("searchTerm") String searchTerm) {
        return productService.getAllProductsByUserSearch(username, searchTerm);
    }

}
