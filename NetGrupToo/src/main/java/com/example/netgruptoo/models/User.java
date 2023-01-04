package com.example.netgruptoo.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "data")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "data.user_user_id_seq")
    @SequenceGenerator(name = "data.user_user_id_seq", sequenceName = "data.user_user_id_seq", allocationSize = 1)
    private Long user_id;
    @Column
    private String username;
    @Column
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private ERole role_name;
    @Column
    private String password;

    @Column
    private String reference_user_username;

    @Column
    private Long maximum_items;

    @Column
    private Long number_of_items;

    @Column
    private Double cost;


    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role_name=" + role_name +
                ", password='" + password + '\'' +
                '}';
    }
}
