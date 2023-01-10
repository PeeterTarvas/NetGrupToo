package com.example.netgruptoo.unittests.repositorytests;

import com.example.netgruptoo.dbos.*;
import com.example.netgruptoo.repositories.CatalogueProductReferenceRepository;
import com.example.netgruptoo.repositories.CatalogueRepository;
import com.example.netgruptoo.repositories.ProductsRepository;
import com.example.netgruptoo.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTests {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    CatalogueRepository catalogueRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CatalogueProductReferenceRepository catalogueProductReferenceRepository;

    private User initUser() {
        userRepository.deleteByUsername("Peeter2");
        return User.builder()
                .user_id(userRepository.findNextId())
                .username("Peeter2")
                .email("email@email.com")
                .role_name(ERole.ROLE_USER)
                .password("123")
                .cost((double) 0)
                .number_of_items(0L)
                .maximum_items(10L)
                .build();
    }

    private Catalogue initCatalogue() {
        Long newCatalogueId = catalogueRepository.findNextId();
        return Catalogue.builder()
                .catalogue_id(newCatalogueId)
                .catalogue_name("Kool")
                .catalogue_owner("Peeter2")
                .build();
    }

    private List<Product> initProducts() {
        Product product = Product.builder()
                .product_id(productsRepository.findNextId())
                .name("Product")
                .serial_number("123")
                .product_owner("Peeter2")
                .picture_link("none")
                .condition("GOOD")
                .description("abc abcabc abc")
                .amount(3L)
                .build();
        Product product2 = Product.builder()
                .product_id(productsRepository.findNextId() + 1)
                .name("csssc")
                .serial_number("csssc")
                .product_owner("Peeter2")
                .picture_link("csssc")
                .condition("BAD")
                .description("abc abcabc abc")
                .amount(3L)
                .build();
        return List.of(product, product2);
    }

    @BeforeEach
    public void setUp() {
        catalogueProductReferenceRepository.deleteAllByCatalogueOwner("Peeter2");
        productsRepository.deleteAllByProduct_owner("Peeter2");
        catalogueRepository.deleteCatalogueByCatalogue_owner("Peeter2");
        Optional<User> user = userRepository.findByUsernameAndPassword("Peeter2", "123");
        user.ifPresent(value -> userRepository.deleteById(Math.toIntExact(value.getUser_id())));

        User newUser = initUser();
        userRepository.saveAndFlush(newUser);
        Catalogue catalogue =  initCatalogue();
        catalogueRepository.saveAndFlush(catalogue);
        List<Product> products = initProducts();
        productsRepository.saveAndFlush(products.get(0));
        productsRepository.saveAndFlush(products.get(1));
        CatalogueProductReference catalogueProductReference1 = CatalogueProductReference.builder()
                .catalogue_id(catalogue.getCatalogue_id())
                .product_id(products.get(0).getProduct_id())
                .build();
        CatalogueProductReference catalogueProductReference2 = CatalogueProductReference.builder()
                .catalogue_id(catalogue.getCatalogue_id())
                .product_id(products.get(1).getProduct_id())
                .build();
        catalogueProductReferenceRepository.saveAllAndFlush(List.of(catalogueProductReference1, catalogueProductReference2));
    }

    @Test
    @Order(1)
    void testGetProductByCatalogue() {
        Catalogue catalogue = catalogueRepository.findByCatalogue_nameAndCatalogue_owner("Kool", "Peeter2");
        List<Product> products = productsRepository.getAllProductsByCatalogue(catalogue.getCatalogue_id());
        assertEquals(2, products.size());
    }
    @Test
    @Order(2)
    void testFindingProductBySearchTerm() {
        List<Product> products = productsRepository.getAllProductsByUserAndSearchTerm("Peeter2", "cs");
        assertEquals(1, products.size());
        assertEquals("csssc", products.get(0).getName());

        List<Product> noProducts = productsRepository.getAllProductsByUserAndSearchTerm("Peeter2", "xzy");
        assertEquals(0, noProducts.size());
    }


    @Test
    @Order(3)
    public void cleanUp() {
        catalogueProductReferenceRepository.deleteAllByCatalogueOwner("Peeter2");
        productsRepository.deleteAllByProduct_owner("Peeter2");
        catalogueRepository.deleteCatalogueByCatalogue_owner("Peeter2");
        Optional<User> user = userRepository.findByUsernameAndPassword("Peeter2", "123");
        user.ifPresent(value -> userRepository.deleteById(Math.toIntExact(value.getUser_id())));
    }




}
