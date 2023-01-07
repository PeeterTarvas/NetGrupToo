package com.example.netgruptoo.dbos;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catalogue_product_reference", schema = "data")
public class CatalogueProductReference {

    @Id
    @Column(name="reference_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reference_id;

    @Column
    private Long catalogue_id;
    @Column
    private Long product_id;
}
