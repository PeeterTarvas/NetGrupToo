package com.example.netgruptoo.repositories;

import com.example.netgruptoo.models.Catalogue;
import com.example.netgruptoo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Integer> {
}
