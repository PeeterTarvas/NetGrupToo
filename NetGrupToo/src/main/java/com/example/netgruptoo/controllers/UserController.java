package com.example.netgruptoo.controllers;

import com.example.netgruptoo.payload.UserDto;
import com.example.netgruptoo.models.User;
import com.example.netgruptoo.repositories.UserRepository;
import com.example.netgruptoo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/register/user")
    public HttpStatus postUser(@RequestBody UserDto body) {
            User user = userService.constructUserWithDto(body);
            userRepository.saveAndFlush(user);
            return HttpStatus.OK;
    }


    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            User userValid =  user.get();
            UserDto userDto = userService.constructUserDtoWithUser(userValid);
            return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
        } return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/update")
    public List<UserDto> updateUser(@RequestBody UserDto userDto) {
        return userService.setUsersMaxItems(userDto);
    }
}
