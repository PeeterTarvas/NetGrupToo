package com.example.netgruptoo.unittests.controllers.users;

import com.example.netgruptoo.controllers.CatalogueController;
import com.example.netgruptoo.controllers.ProductController;
import com.example.netgruptoo.controllers.UserController;
import com.example.netgruptoo.dbos.ERole;
import com.example.netgruptoo.dbos.Product;
import com.example.netgruptoo.dbos.User;
import com.example.netgruptoo.dtos.CatalogueDto;
import com.example.netgruptoo.dtos.ProductDto;
import com.example.netgruptoo.dtos.UserDto;
import com.example.netgruptoo.repositories.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTests {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogueController catalogueController;

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductController productController;

    @Autowired
    CatalogueProductReferenceRepository catalogueProductReferenceRepository;

    private UserDto userDto;

    private CatalogueDto catalogueDto;

    private UserDto createUserDto(String username) {
        userRepository.deleteByUsername(username);
        return UserDto.builder()
                .username(username)
                .email("email2@email.com")
                .role(String.valueOf(ERole.ROLE_USER))
                .password("12")
                .cost((double) 0)
                .number_of_items(0L)
                .maximum_items(1L)
                .build();
    }

    private List<ProductDto> initProducts() {
        ProductDto product = ProductDto.builder()
                .name("Product")
                .serialNumber("1")
                .productOwner("Peeter2")
                .pictureLink("none")
                .condition("GOOD")
                .description("abc abcabc abc")
                .amount(3L)
                .build();
        ProductDto product2 = ProductDto.builder()
                .name("Product2")
                .serialNumber("12")
                .productOwner("Peeter2")
                .pictureLink("none")
                .condition("BAD")
                .description("abc abcabc abc")
                .amount(5L)
                .build();
        return List.of(product, product2);
    }


    @BeforeEach
    void setUp() {
        catalogueProductReferenceRepository.deleteAllByCatalogueOwner("Peeter2");
        productsRepository.deleteAllByProduct_owner("Peeter2");
        catalogueRepository.deleteCatalogueByCatalogue_owner("Peeter2");
        userRepository.deleteByUsername("Peeter2");

        userDto = createUserDto("Peeter2");
        userController.postUser(userDto);
        catalogueDto = catalogueController.getInitialCatalogue(userDto.getUsername());
    }

    @Test
    @Order(1)
    void createProduct() {
        ProductDto product = ProductDto.builder()
                .name("Product")
                .serialNumber("123")
                .productOwner("Peeter2")
                .pictureLink("none")
                .condition("GOOD")
                .description("abc abcabc abc")
                .amount(3L)
                .build();
        HttpStatus httpStatus = productController.createProduct(product, catalogueDto.getCatalogueId(), userDto.getUsername());
        assertEquals(HttpStatus.CREATED, httpStatus);
        List<Product> products = productsRepository.getAllProductsByCatalogue(catalogueDto.getCatalogueId());
        assertEquals(1, products.size());
        assertEquals(product.getName(), products.get(0).getName());
        assertEquals(product.getSerialNumber(), products.get(0).getSerial_number());
        assertEquals(3, userRepository.findUserByUsername("Peeter2").getNumber_of_items());
    }

    @Test
    @Order(2)
    void testGetProductByCatalogue() {
        List<ProductDto> productDtos = initProducts();
        productDtos.forEach(element ->  productController.createProduct(element, catalogueDto.getCatalogueId(), userDto.getUsername()));
        List<ProductDto> products = productController.getProductsByCatalogueId(catalogueDto.getCatalogueId());
        assertEquals(productDtos.size(), products.size());
        for (int i = 0; i < productDtos.size(); i++) {
            assertEquals(productDtos.get(i).getName(), products.get(i).getName());
            assertEquals(productDtos.get(i).getProductOwner(), products.get(i).getProductOwner());
        }
    }


    @Test
    @Order(3)
    void testGetProductBySearchTerm() {
        ProductDto product = ProductDto.builder()
                .name("Product")
                .serialNumber("123")
                .productOwner("Peeter2")
                .pictureLink("none")
                .condition("GOOD")
                .description("abc abcabc abc")
                .amount(3L)
                .build();
        productController.createProduct(product, catalogueDto.getCatalogueId(), userDto.getUsername());
        List<ProductDto> productDtos = productController.getProductsBySearchTerm("Peeter2", "Product");
        assertEquals(1, productDtos.size());
        assertEquals(product.getName(), productDtos.get(0).getName());
        productDtos = productController.getProductsBySearchTerm("Peeter2", "xzy");
        assertEquals(0, productDtos.size());
    }

    @Test
    @Order(4)
    void testCostIncreaseForBusiness() {
        UserDto userDto = UserDto.builder()
                .username("Peeter2 OU")
                .email("email3@email.com")
                .role(String.valueOf(ERole.ROLE_BUSINESS))
                .referenceUserUsername("Peeter2")
                .password("12")
                .cost((double) 0)
                .number_of_items(0L)
                .maximum_items(10L)
                .build();
        userController.postUser(userDto);
        CatalogueDto catalogueDto = catalogueController.getInitialCatalogue(userDto.getUsername());
        List<ProductDto> productDtos = initProducts();
        UserDto finalUserDto = userDto;
        productDtos.forEach(element ->  productController.createProduct(element, catalogueDto.getCatalogueId(), finalUserDto.getUsername()));
        userDto = userController.getUser(userDto.getUsername(), userDto.getPassword()).getBody();
        userDto.setMaximum_items(1L);
        List<UserDto> userDtos = userController.updateUserMaxItems(userDto);
        assertEquals(0.07, userDtos.get(userDtos.size() - 1).getCost());

        catalogueProductReferenceRepository.deleteAllByCatalogueOwner("Peeter2 OU");
        productsRepository.deleteAllByProduct_owner("Peeter2 OU");
        catalogueRepository.deleteCatalogueByCatalogue_owner("Peeter2 OU");
        userRepository.deleteByUsername("Peeter2 OU");
    }

    @Test
    @Order(5)
    void cleanUp() {
        catalogueProductReferenceRepository.deleteAllByCatalogueOwner("Peeter2");
        productsRepository.deleteAllByProduct_owner("Peeter2");
        catalogueRepository.deleteCatalogueByCatalogue_owner("Peeter2");
        userRepository.deleteByUsername("Peeter2");
    }
}
