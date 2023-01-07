package com.example.netgruptoo.dtos;

import lombok.*;

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
    private Long maximum_items;
    private Long number_of_items;
    private Double cost;

}
