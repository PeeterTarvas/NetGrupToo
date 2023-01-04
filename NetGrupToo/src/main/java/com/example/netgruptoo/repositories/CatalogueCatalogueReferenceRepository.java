package com.example.netgruptoo.repositories;

import com.example.netgruptoo.models.CatalogueCatalogueReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogueCatalogueReferenceRepository extends JpaRepository<CatalogueCatalogueReference, Integer> {


    @Query(value = "SELECT MAX(reference_id) + 1 AS user_id FROM data.catalogue_catalogue_reference ", nativeQuery = true)
    Long findNextId();

}
