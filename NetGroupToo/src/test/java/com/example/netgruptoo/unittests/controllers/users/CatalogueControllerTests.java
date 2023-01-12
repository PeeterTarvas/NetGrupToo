package com.example.netgruptoo.unittests.controllers.users;

import com.example.netgruptoo.controllers.CatalogueController;
import com.example.netgruptoo.controllers.UserController;
import com.example.netgruptoo.dbos.Catalogue;
import com.example.netgruptoo.dbos.ERole;
import com.example.netgruptoo.dtos.CatalogueDto;
import com.example.netgruptoo.dtos.UserDto;
import com.example.netgruptoo.repositories.CatalogueCatalogueReferenceRepository;
import com.example.netgruptoo.repositories.CatalogueRepository;
import com.example.netgruptoo.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CatalogueControllerTests {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogueController catalogueController;

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private CatalogueCatalogueReferenceRepository catalogueCatalogueReferenceRepository;

    private UserDto userDto;

    private UserDto createUserDto(String username) {
        userRepository.deleteByUsername(username);
        return UserDto.builder()
                .username(username)
                .email("email2@email.com")
                .role(String.valueOf(ERole.ROLE_USER))
                .password("12")
                .cost((double) 0)
                .numberOfItems(0L)
                .maximumItems(10L)
                .build();
    }

    private CatalogueDto createCatalogueDto(String username, String catalogueName, Long parentId) {
        return CatalogueDto.builder()
                .catalogueName(catalogueName)
                .username(username)
                .parent(parentId)
                .build();
    }

    @BeforeEach
    void setUp() {
        catalogueCatalogueReferenceRepository.deleteAllByCatalogueOwner("Peeter2");
        catalogueRepository.deleteCatalogueByCatalogue_owner("Peeter2");
        userDto = createUserDto("Peeter2");
        userController.postUser(userDto);
    }

    @Test
    @Order(1)
    void testCreatingRootCatalogue() {
        CatalogueDto catalogueDto = catalogueController.getInitialCatalogue(userDto.getUsername());
        Catalogue catalogue = catalogueRepository.findInitalUserCatalogue(userDto.getUsername());
        assertEquals(catalogueDto.getCatalogueId(), catalogue.getCatalogue_id());
    }

    @Test
    @Order(2)
    void testCreatingCatalogue() {
        CatalogueDto parentCatalogueDto = catalogueController.getInitialCatalogue(userDto.getUsername());
        CatalogueDto catalogueDto = createCatalogueDto(userDto.getUsername(), "Test", parentCatalogueDto.getCatalogueId());
        HttpStatus httpStatus = catalogueController.createCatalogue(catalogueDto);
        assertEquals(HttpStatus.CREATED, httpStatus);
        Catalogue catalogue = catalogueRepository.findByCatalogue_nameAndCatalogue_owner("Test", userDto.getUsername());
        assertEquals(catalogueDto.getCatalogueName(), catalogue.getCatalogue_name());
        assertEquals(catalogueDto.getUsername(), catalogue.getCatalogue_owner());
    }

    @Test
    @Order(3)
    void testGetCataloguesUnderParentCatalogue() {
        CatalogueDto parentCatalogueDto = catalogueController.getInitialCatalogue(userDto.getUsername());
        CatalogueDto catalogueDto = createCatalogueDto(userDto.getUsername(), "Test", parentCatalogueDto.getCatalogueId());
        catalogueController.createCatalogue(catalogueDto);
        List<CatalogueDto> catalogueDtos = catalogueController.getCataloguesUnderParent(parentCatalogueDto.getCatalogueId());
        assertEquals(1, catalogueDtos.size());
        assertEquals(catalogueDto.getCatalogueName(), catalogueDtos.get(0).getCatalogueName());
        assertEquals(catalogueDto.getParent(), catalogueDtos.get(0).getParent());
    }

    @Test
    @Order(4)
    void testGettingCatalogue() {
        CatalogueDto parentCatalogueDto = catalogueController.getInitialCatalogue(userDto.getUsername());
        CatalogueDto catalogueDto = createCatalogueDto(userDto.getUsername(), "Test", parentCatalogueDto.getCatalogueId());
        catalogueController.createCatalogue(catalogueDto);
        CatalogueDto catalogueDto1 = catalogueController.getCatalogue(userDto.getUsername(), catalogueDto.getCatalogueName());
        assertEquals(catalogueDto.getCatalogueName(), catalogueDto1.getCatalogueName());
        assertEquals(catalogueDto.getParent(), catalogueDto1.getParent());
    }

    @Test
    @Order(5)
    void cleanUp() {
        catalogueCatalogueReferenceRepository.deleteAllByCatalogueOwner(userDto.getUsername());
        catalogueRepository.deleteCatalogueByCatalogue_owner(userDto.getUsername());
        userRepository.deleteByUsername(userDto.getUsername());
    }



}
