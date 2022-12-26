package com.example.netgruptoo.services;


import com.example.netgruptoo.models.ERole;
import com.example.netgruptoo.models.User;
import com.example.netgruptoo.payload.UserDto;
import com.example.netgruptoo.repositories.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto constructUserDtoWithUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(userDto.getEmail());
        userDto.setRole(user.getRole_name().toString());
        return userDto;
    }

    public User constructUserWithDto(UserDto userDto) {
        User user = new User();
        user.setUser_id(userRepository.findNextId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRole_name(ERole.valueOf(userDto.getRole()));
        user.setPassword(userDto.getPassword());
        String referenceUser = userDto.getReferenceUserUsername();
        if (user.getRole_name().equals(ERole.ROLE_BUSINESS)) {
            user.setReference_user_username(referenceUser);
            user.setMaximum_items(10L);
        } else {
            user.setMaximum_items(Long.MAX_VALUE);
        }
        return user;
    }

}
