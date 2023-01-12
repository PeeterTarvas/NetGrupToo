package com.example.netgruptoo.dtos;

import lombok.*;

import java.util.HashMap;

/**
 * This is a transfer object for User class.
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String role;
    private String referenceUserUsername;
    private Long maximumItems;
    private Long numberOfItems;
    private Double cost;
    private HashMap<String, Integer> itemsStatus;

}
