package com.example.netgruptoo.controllers;

import com.example.netgruptoo.dtos.UserDto;
import com.example.netgruptoo.dbos.User;
import com.example.netgruptoo.repositories.UserRepository;
import com.example.netgruptoo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This controller class is for handling all the endpoints related to User functionalities.
 * Endpoint for this controller is a bit different from the other ones http://localhost:8000/..function params
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    /**
     * Database repository for the users.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Service that holds all the user related functional logic.
     */
    @Autowired
    private UserService userService;

    /**
     * This method is for registering a new user.
     * @param body is the UserDto that holds the params for the new user.
     * @return HttpStatus.Ok if user is created.
     */
    @PostMapping("/register/user")
    public HttpStatus postUser(@RequestBody UserDto body) {
            User user = userService.constructUserWithDto(body);
            userRepository.saveAndFlush(user);
            return HttpStatus.OK;
    }


    /**
     * This method is used for login usually.
     * @param username of the user.
     * @param password of the user.
     * @return ResponseEntity who's response body is the user.
     */
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            User userValid =  user.get();
            UserDto userDto = userService.constructUserDtoWithUser(userValid);
            return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
        } return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is the endpoint for getting all the users, this function is used by the admin to get all the users.
     * @return all the users in the application.
     */
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * This function is the endpoint for updating the max items of a user.
     * This is used by the admin to increase/decrease the maximum number of free items that the business user has.
     * @param userDto of the user whose maximum number of items will be changed.
     * @return list of all the users, this is so that the admin can get up-to-date info about all the users.
     */
    @PutMapping("/update")
    public List<UserDto> updateUserMaxItems(@RequestBody UserDto userDto) {
        return userService.setUsersMaxItems(userDto);
    }
}
