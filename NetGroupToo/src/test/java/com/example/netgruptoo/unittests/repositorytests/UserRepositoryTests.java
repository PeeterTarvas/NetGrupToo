package com.example.netgruptoo.unittests.repositorytests;


import com.example.netgruptoo.dbos.ERole;
import com.example.netgruptoo.dbos.User;
import com.example.netgruptoo.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteByUsername("Peeter2");
        User newUser = User.builder()
                .user_id(userRepository.findNextId())
                .username("Peeter2")
                .email("email@email.com")
                .role_name(ERole.ROLE_USER)
                .password("123")
                .cost((double) 0)
                .number_of_items(0L)
                .maximum_items(10L)
                .build();
        userRepository.saveAndFlush(newUser);
    }

    @Test
    @Order(1)
    void findUserByUsernameAndPassword() {
        Optional<User> newUser = userRepository.findByUsernameAndPassword("Peeter2", "123");
        assertTrue(newUser.isPresent());
        Optional<User> nonExistentUser = userRepository.findByUsernameAndPassword("Peeter123", "123");
        assertFalse(nonExistentUser.isPresent());
    }

    @Test
    @Order(2)
    void findUserByUsername() {
        User user = userRepository.findUserByUsername("Peeter2");
        assertEquals(user.getUsername(), "Peeter2");
        User nonExistentUser = userRepository.findUserByUsername("Peeter123");
        assertNull(nonExistentUser);
    }
    @Test
    @Order(3)
    public void cleanUp() {
        Optional<User> user = userRepository.findByUsernameAndPassword("Peeter2", "123");
        user.ifPresent(value -> userRepository.deleteById(Math.toIntExact(value.getUser_id())));
    }

}
