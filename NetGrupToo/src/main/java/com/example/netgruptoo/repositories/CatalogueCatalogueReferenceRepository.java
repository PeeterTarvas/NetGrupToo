package com.example.netgruptoo.repositories;

import com.example.netgruptoo.dbos.CatalogueCatalogueReference;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogueCatalogueReferenceRepository extends JpaRepository<CatalogueCatalogueReference, Integer> {


    @Query(value = "SELECT MAX(reference_id) + 1 AS user_id FROM data.catalogue_catalogue_reference ", nativeQuery = true)
    Long findNextId();

    @Query(value = "SELECT DISTINCT(parent_catalogue_id) FROM data.catalogue_catalogue_reference WHERE child_catalogue_id = :childId", nativeQuery = true)
    Long findParentId(Long childId);

    @Transactional
    @Modifying
    @Query(value = """
    DELETE FROM data.catalogue_catalogue_reference
    WHERE parent_catalogue_id IN (SELECT catalogue_id FROM data.catalogue WHERE catalogue_owner = :catalogueOwner)
    """, nativeQuery = true)
    void deleteAllByCatalogueOwner(String catalogueOwner);

}
