package com.example.netgruptoo.services;


import com.example.netgruptoo.dbos.ERole;
import com.example.netgruptoo.dbos.User;
import com.example.netgruptoo.dtos.UserDto;
import com.example.netgruptoo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a service class for all functionalities related to user logic.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    /**
     * This function returns all users form the user database repository.
     * @return a list of UserDtos
     */
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(element -> userDtos.add(constructUserDtoWithUser(element)));
        return userDtos;
    }

    /**
     * This function for constructing userDto with the User class.
     * @param user that the UserDto will be constructed from.
     * @return UserDto constructed from User.
     */
    public UserDto constructUserDtoWithUser(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole_name().toString())
                .cost(user.getCost())
                .maximum_items(user.getMaximum_items())
                .number_of_items(user.getNumber_of_items())
                .referenceUserUsername(user.getReference_user_username())
                .items_status(productService.getItemsStatusForUser(user))
                .build();
    }

    /**
     * This method sets users max items.
     * @param userDto from which the users new max items was set.
     * @return a list of all the users.
     */
    public List<UserDto> setUsersMaxItems(UserDto userDto) {
        User user = userRepository.findUserByUsername(userDto.getUsername());
        user.setMaximum_items(userDto.getMaximum_items());
        if (userDto.getMaximum_items() < userDto.getNumber_of_items()) {
            user.setCost(0.01 * (userDto.getNumber_of_items() - userDto.getMaximum_items()));
        } else {
            user.setCost(0.00);
        }
        userRepository.saveAndFlush(user);
        return getAllUsers();
    }

    /**
     * This method is for helping to make a new user form the users dto.
     * @param userDto from which the user is constructed.
     * @return newly created user
     */
    public User createUserWithDto(UserDto userDto) {
        User user = User.builder()
                .user_id(userRepository.findNextId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .role_name(ERole.valueOf(userDto.getRole()))
                .password(userDto.getPassword())
                .cost((double) 0)
                .number_of_items(0L)
                .build();
        String referenceUser = userDto.getReferenceUserUsername();
        if (user.getRole_name().equals(ERole.ROLE_BUSINESS)) {
            user.setReference_user_username(referenceUser);
            user.setMaximum_items(10L);
        } else {
            user.setMaximum_items(Long.MAX_VALUE);
        }
        return user;
    }

    /**
     * Update the users item status with this function, so this method is for changing the number of items the user has.
     * @param username of the user whose items we want to change.
     * @param items count how much the users items will change.
     */
    public void updateUserItemStatus(String username, Long items) {
        User user = userRepository.findUserByUsername(username);
        user.setNumber_of_items(user.getNumber_of_items() + items);
        if (user.getMaximum_items() < user.getNumber_of_items() && user.getRole_name().equals(ERole.ROLE_BUSINESS)) {
            user.setCost(user.getCost() + (0.01 * items));
        }
        userRepository.saveAndFlush(user);
    }



}
