package com.example.netgruptoo.services;

import com.example.netgruptoo.dbos.*;
import com.example.netgruptoo.dtos.ProductDto;
import com.example.netgruptoo.repositories.CatalogueProductReferenceRepository;
import com.example.netgruptoo.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a service class for handling all the functionalities related to Product class creation etc...
 */
@Service
public class ProductService {

    /**
     * Database for Product class.
     */
    @Autowired
    private ProductsRepository productsRepository;

    /**
     * Catalogue Product reference repository that determines which product is in which catalogue.
     */
    @Autowired
    private CatalogueProductReferenceRepository catalogueProductReferenceRepository;

    /**
     * This function is for creating a new product from ProductDto and catalogueId that contains the product.
     * @param productDto has the params from which the product will be created from.
     * @param catalogueId is the parent catalogue that will hold the product.
     */
    public void createProduct(ProductDto productDto, Long catalogueId) {
        Product product = createProductFromProductDto(productDto);
        CatalogueProductReference catalogueProductReference = createCatalogueProductReference(product, catalogueId);
        productsRepository.saveAndFlush(product);
        catalogueProductReferenceRepository.saveAndFlush(catalogueProductReference);
    }

    /**
     * This method returns all the products inside a catalogue.
     * @param catalogueId of the catalogue whose products we want.
     * @return all the products as ProductDtos in a list that are in the catalogue.
     */
    public List<ProductDto> getAllProductsByCatalogueId(Long catalogueId) {
        List<Product> products = productsRepository.getAllProductsByCatalogue(catalogueId);
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(element -> productDtos.add(createProductDtoFromProduct(element)));
        return productDtos;
    }

    /**
     * This method is for getting all the products that match the users search term.
     * @param username of the user who wants to search for a product that she/he owns.
     * @param searchTerm of the products that we want to search for.
     * @return all the products that match the search term.
     */
    public List<ProductDto> getAllProductsByUserSearch(String username, String searchTerm) {
        List<Product> products = productsRepository.getAllProductsByUserAndSearchTerm(username, searchTerm);
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(element -> productDtos.add(createProductDtoFromProduct(element)));
        return productDtos;
    }


    /**
     * Helper method for creating a catalogue product reference object that
     * determines under which catalogue the product will be in.
     * @param product tact will be in the catalogue.
     * @param catalogueId of the catalogue that the product will be in.
     * @return the reference object.
     */
    private CatalogueProductReference createCatalogueProductReference(Product product, Long catalogueId) {
        return CatalogueProductReference.builder()
                .product_id(product.getProduct_id())
                .catalogue_id(catalogueId)
                .build();
    }

    /**
     * Helper method for creating a ProductDto form a Product class.
     * @param product that the ProductDto will be built from.
     * @return the ProductDto that has been created from the Product class,
     */
    private ProductDto createProductDtoFromProduct(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .serialNumber(product.getSerial_number())
                .productOwner(product.getProduct_owner())
                .pictureLink(product.getPicture_link())
                .condition(product.getCondition())
                .description(product.getDescription())
                .amount(product.getAmount())
                .build();
    }

    /**
     * This is a helper method for creating a Product from ProductDto.
     * @param productDto that the product will be created from.
     * @return the Product that has been created from ProductDto class.
     */
    private Product createProductFromProductDto(ProductDto productDto) {
        return Product.builder()
                .product_id(productsRepository.findNextId())
                .name(productDto.getName())
                .serial_number(productDto.getSerialNumber())
                .product_owner(productDto.getProductOwner())
                .picture_link(productDto.getPictureLink())
                .condition(productDto.getCondition())
                .description(productDto.getDescription())
                .amount(productDto.getAmount())
                .build();
    }

}
