package com.example.netgruptoo.dbos;


import jakarta.persistence.*;
import lombok.*;

/**
 * This is the database table object for CatalogueCatalogueReference table.
 */
@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catalogue_catalogue_reference", schema = "data")
public class CatalogueCatalogueReference {

    @Id
    @Column(name="reference_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reference_id;

    @Column
    private Long parent_catalogue_id;

    @Column
    private Long child_catalogue_id;
}
