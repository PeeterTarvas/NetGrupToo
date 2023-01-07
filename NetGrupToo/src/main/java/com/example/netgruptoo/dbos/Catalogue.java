package com.example.netgruptoo.dbos;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catalogue", schema = "data")
public class Catalogue {

    @Id
    @Column
    private Long catalogue_id;
    @Column
    private String catalogue_owner;
    @Column
    private String catalogue_name;

}
