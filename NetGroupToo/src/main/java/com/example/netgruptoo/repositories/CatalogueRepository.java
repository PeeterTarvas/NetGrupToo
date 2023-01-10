package com.example.netgruptoo.repositories;

import com.example.netgruptoo.dbos.Catalogue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is for database operations for Catalogue database table.
 */
@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Integer> {

    @Query(value = "SELECT MAX(catalogue_id) + 1 AS user_id FROM data.catalogue ", nativeQuery = true)
    Long findNextId();

    @Query(value = "SELECT * FROM data.catalogue WHERE catalogue_owner = :owner AND catalogue_name = :catalogueName",
            nativeQuery = true)
    Catalogue findByCatalogue_nameAndCatalogue_owner(String catalogueName, String owner);

    @Query(value = "SELECT * FROM data.catalogue WHERE catalogue_owner = :username ORDER BY catalogue_id LIMIT 1",nativeQuery = true)
    Catalogue findInitalUserCatalogue(String username);

    @Query(value = """
            SELECT * FROM (
                            SELECT child_catalogue_id FROM data.catalogue_catalogue_reference WHERE parent_catalogue_id = :parentId
                            ) as ccrcci, data.catalogue as catalogue WHERE ccrcci.child_catalogue_id = catalogue.catalogue_id"""
            ,nativeQuery = true)
    List<Catalogue> findAllByParentId(Long parentId);

    @Transactional
    @Modifying
    @Query(value = " DELETE FROM data.catalogue WHERE catalogue_owner = :catalogueOwner", nativeQuery = true)
    void deleteCatalogueByCatalogue_owner(String catalogueOwner);
}
