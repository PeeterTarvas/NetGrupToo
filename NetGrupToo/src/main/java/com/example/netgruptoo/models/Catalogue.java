package com.example.netgruptoo.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "catalogue", schema = "data")
public class Catalogue {

    @Id
    @Column
    private Long catalogue_id;
    @Column
    private Long catalogue_owner;

}
