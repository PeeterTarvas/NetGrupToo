package com.example.netgruptoo.dbos;


import jakarta.persistence.*;
import lombok.*;

/**
 * This is the database table object for Product table.
 */
@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product", schema = "data")
public class Product {


    @Id
    @Column
    private Long product_id;

    @Column
    private String name;

    @Column
    private String serial_number;

    @Column
    private String picture_link;

    @Column
    private String product_owner;

    @Column
    private String condition;

    @Column
    private String description;

    @Column
    private Long amount;

}
