package com.example.netgruptoo.repositories;

import com.example.netgruptoo.models.CatalogueCatalogueReference;
import com.example.netgruptoo.models.CatalogueProductReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogueProductReferenceRepository  extends JpaRepository<CatalogueProductReference, Integer> {
}
