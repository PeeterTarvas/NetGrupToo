package com.example.netgruptoo.unittests.controllers.users;

import com.example.netgruptoo.controllers.UserController;
import com.example.netgruptoo.dbos.ERole;
import com.example.netgruptoo.dbos.User;
import com.example.netgruptoo.dtos.UserDto;
import com.example.netgruptoo.repositories.UserRepository;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
         userRepository.deleteByUsername("Peeter2");
         userDto = UserDto.builder()
                .username("Peeter2")
                .email("email2@email.com")
                .role(String.valueOf(ERole.ROLE_USER))
                .password("12")
                .cost((double) 0)
                .numberOfItems(0L)
                .maximumItems(10L)
                .build();
    }


    @Test
    @Order(1)
    void testPostUser() {
        HttpStatus httpStatus = userController.postUser(userDto);
        assertEquals(HttpStatus.OK, httpStatus);
        User newUser = userRepository.findUserByUsername("Peeter2");
        assertEquals(userDto.getEmail(), newUser.getEmail());
        assertEquals(userDto.getUsername(), newUser.getUsername());

    }

    @Test
    @Order(2)
    void testGetUser() {
        userController.postUser(userDto);
        ResponseEntity<UserDto> newUserDto = userController.getUser(userDto.getUsername(), userDto.getPassword());
        assertEquals(userDto.getUsername(), Objects.requireNonNull(newUserDto.getBody()).getUsername());
        assertEquals(userDto.getRole(), Objects.requireNonNull(newUserDto.getBody()).getRole());
        assertEquals(userDto.getEmail(), Objects.requireNonNull(newUserDto.getBody()).getEmail());
        ResponseEntity<UserDto> noUserDto = userController.getUser("None", "None");
        assertEquals(HttpStatus.BAD_REQUEST, noUserDto.getStatusCode());


    }

    List<UserDto> initSomeUsers() {
        UserDto userDto2 = UserDto.builder()
                .username("Peeter3")
                .email("email3@email.com")
                .role(String.valueOf(ERole.ROLE_USER))
                .password("123")
                .cost((double) 0)
                .numberOfItems(0L)
                .maximumItems(10L)
                .build();
        UserDto userDtoAdmin = UserDto.builder()
                .username("Peeter4")
                .email("email4@email.com")
                .role(String.valueOf(ERole.ROLE_ADMIN))
                .password("1234")
                .cost((double) 0)
                .numberOfItems(0L)
                .maximumItems(10L)
                .build();
        UserDto userDtoBusiness = UserDto.builder()
                .username("Peeter5")
                .email("email5@email.com")
                .role(String.valueOf(ERole.ROLE_BUSINESS))
                .password("12345")
                .cost((double) 0)
                .numberOfItems(0L)
                .maximumItems(10L)
                .referenceUserUsername("Peeter2")
                .build();
        return List.of(userDto, userDto2, userDtoAdmin, userDtoBusiness);
    }

    @Test
    @Order(3)
    void testGetAllUsers() {
        initSomeUsers().forEach(element -> userController.postUser(element));
        List<UserDto> userDtos = userController.getAllUsers();
        // There are 3 users already present with data.sql
        assertEquals(7, userDtos.size());
    }

    @Test
    @Order(4)
    void testUpdateUserMaxItems() {
        userController.postUser(userDto);
        userDto = UserDto.builder()
                .username("Peeter2")
                .email("email2@email.com")
                .role(String.valueOf(ERole.ROLE_USER))
                .password("12")
                .cost((double) 0)
                .numberOfItems(0L)
                .maximumItems(100L)
                .build();
        userController.updateUserMaxItems(userDto);
        User user = userRepository.findUserByUsername("Peeter2");
        assertEquals(user.getMaximum_items(),userDto.getMaximumItems());
    }
    @Test
    @Order(5)
    public void cleanUp() {
        List<UserDto> userDtos = initSomeUsers();
        userDtos.forEach(element -> userRepository.deleteByUsername(element.getUsername()));
    }
}