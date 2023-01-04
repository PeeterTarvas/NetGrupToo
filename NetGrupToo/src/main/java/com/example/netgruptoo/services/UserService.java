package com.example.netgruptoo.services;


import com.example.netgruptoo.models.ERole;
import com.example.netgruptoo.models.User;
import com.example.netgruptoo.payload.UserDto;
import com.example.netgruptoo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(element -> userDtos.add(constructUserDtoWithUser(element)));
        return userDtos;
    }

    public UserDto constructUserDtoWithUser(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole_name().toString())
                .cost(user.getCost())
                .maximum_items(user.getMaximum_items())
                .number_of_items(user.getNumber_of_items())
                .referenceUserUsername(user.getReference_user_username())
                .build();
    }

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



    public User constructUserWithDto(UserDto userDto) {
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

    public void updateUserProductStatus(String username, Long items) {
        User user = userRepository.findUserByUsername(username);
        user.setNumber_of_items(user.getNumber_of_items() + items);
        if (user.getMaximum_items() < user.getNumber_of_items() && user.getRole_name().equals(ERole.ROLE_BUSINESS)) {
            user.setCost(user.getCost() + (0.01 * items));
        }
        userRepository.saveAndFlush(user);
    }


}
