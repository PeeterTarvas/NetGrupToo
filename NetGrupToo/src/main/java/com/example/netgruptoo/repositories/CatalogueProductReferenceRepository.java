package com.example.netgruptoo.repositories;

import com.example.netgruptoo.dbos.CatalogueProductReference;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * This class is for database operations for CatalogueProductReference database table.
 */
@Repository
public interface CatalogueProductReferenceRepository  extends JpaRepository<CatalogueProductReference, Integer> {

    @Transactional
    @Modifying
    @Query(value = """
    DELETE FROM data.catalogue_product_reference
    WHERE catalogue_id IN (SELECT catalogue_id FROM data.catalogue WHERE catalogue_owner = :catalogueOwner)
    """, nativeQuery = true)
    void deleteAllByCatalogueOwner(String catalogueOwner);
}
