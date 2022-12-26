package com.example.netgruptoo.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "product", schema = "data")
public class Product {


    @Id
    @Column
    private Long product_id;

    @Column
    private Long name;

    @Column
    private Long serial_number;

    @Column
    private String picture_link;

    @Column
    private Long product_owner;

    @Column
    @Enumerated(EnumType.STRING)
    private Condition product_condition;

}
