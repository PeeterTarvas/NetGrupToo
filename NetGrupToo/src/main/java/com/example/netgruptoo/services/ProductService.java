package com.example.netgruptoo.services;

import com.example.netgruptoo.models.*;
import com.example.netgruptoo.payload.ProductDto;
import com.example.netgruptoo.repositories.CatalogueProductReferenceRepository;
import com.example.netgruptoo.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductsRepository productsRepository;


    @Autowired
    private CatalogueProductReferenceRepository catalogueProductReferenceRepository;

    public void createProduct(ProductDto productDto, Long catalogueId) {
        Product product = createProductFromProductDto(productDto);
        CatalogueProductReference catalogueProductReference = createCatalogueProductReference(product, catalogueId);
        productsRepository.saveAndFlush(product);
        catalogueProductReferenceRepository.saveAndFlush(catalogueProductReference);
    }

    public List<ProductDto> getAllProductsByCatalogueId(Long catalogueId) {
        List<Product> products = productsRepository.getAllProductsByCatalogue(catalogueId);
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(element -> productDtos.add(createProductDtoFromProduct(element)));
        return productDtos;
    }

    public List<ProductDto> getAllProductsByUserSearch(String username, String searchTerm) {
        List<Product> products = productsRepository.getAllProductsByUserAndSearchTerm(username, searchTerm);
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(element -> productDtos.add(createProductDtoFromProduct(element)));
        return productDtos;
    }



    private CatalogueProductReference createCatalogueProductReference(Product product, Long catalogueId) {
        return CatalogueProductReference.builder()
                .product_id(product.getProduct_id())
                .catalogue_id(catalogueId)
                .build();
    }

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
